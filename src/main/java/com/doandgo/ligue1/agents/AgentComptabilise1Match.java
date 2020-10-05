package com.doandgo.ligue1.agents;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import com.axemble.vdoc.sdk.Modules;
import com.axemble.vdoc.sdk.agent.base.BaseAgent;
import com.axemble.vdoc.sdk.exceptions.ModuleException;
import com.axemble.vdoc.sdk.exceptions.WorkflowModuleException;
import com.axemble.vdoc.sdk.interfaces.IContext;
import com.axemble.vdoc.sdk.interfaces.IProject;
import com.axemble.vdoc.sdk.interfaces.IReport;
import com.axemble.vdoc.sdk.interfaces.IResourceController;
import com.axemble.vdoc.sdk.interfaces.IStorageResource;
import com.axemble.vdoc.sdk.interfaces.IUser;
import com.axemble.vdoc.sdk.interfaces.IWorkflow;
import com.axemble.vdoc.sdk.interfaces.IWorkflowInstance;
import com.axemble.vdoc.sdk.modules.IDirectoryModule;
import com.axemble.vdoc.sdk.modules.IProjectModule;
import com.axemble.vdoc.sdk.modules.IWorkflowModule;
import com.axemble.vdoc.sdk.utils.Logger;
import com.doandgo.ligue1.matchs.Match;
import com.doandgo.ligue1.matchs.StandingComparator;
import com.doandgo.ligue1.utils.UtilitaireLigue1;
import com.doandgo.moovapps.exceptions.VdocHelperException;
import com.doandgo.moovapps.kpi.util.KPIUtil;
import com.doandgo.moovapps.studio.DoandgoUtil;
import com.doandgo.moovapps.utils.VdocHelper;

/**
 * Comptabilise les documents process correspondants à des matchs en enregistrant les données liées
 * aux résultats dans les tables "Equipes" et "Confrontations"
 * Les documents process à comptabiliser par cet agent sont ceux pour lesquels la case "Comptabilisé ?" n'est pas cochée
 * 
 * @author Thomas CHARMES
 */
public class AgentComptabilise1Match extends BaseAgent {

	private static final Logger logger = Logger.getLogger(AgentComptabilise1Match.class);
	public static IReport iReport = null;

	@Override
	protected void execute() {
		try {
			getParametersForAgentChangeEtape(getReport());
		} catch (VdocHelperException | ModuleException e) {
			reportError("Erreur à l'éxécution de l'agent " + this.getClass().getName() + " : " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Récupère les données de la table de paramétrage "ChangeEtape" de l'apps Do&GoUtils
	 * Appelle la méthode changeEtape qui effectue le traitement avec ses paramètres là
	 * Met à jour les classements
	 * @param report
	 * @throws VdocHelperException
	 * @throws ModuleException
	 */
	private static void getParametersForAgentChangeEtape(IReport report) throws VdocHelperException, ModuleException {

		iReport = report;

		IDirectoryModule dm = Modules.getDirectoryModule();
		IProjectModule pm = Modules.getProjectModule();
		IWorkflowModule wm = Modules.getWorkflowModule();

		Collection<IStorageResource> lignesTableChangeEtape = VdocHelper.getDataUniverseResources(dm, pm, wm,
				DoandgoUtil.DOANDGO_ORGANIZATION_NAME, DoandgoUtil.DOANDGO_PROJECT_NAME,
				DoandgoUtil.DOANDGO_REFERENTIEL_AGENTS_NAME, DoandgoUtil.DOANDGO_TABLE_CHANGE_ETAPE_NAME);

		if (lignesTableChangeEtape == null) {
			reportError("La table de configuration de l'agent ne possède aucune ligne");
			return;
		}

		for (IStorageResource ligneTableChangeEtape : lignesTableChangeEtape) {

			Boolean activerChangementEtape = (Boolean) ligneTableChangeEtape
					.getValue(KPIUtil.FIELD_ACTIVER_CHANGEMENT_ETAPE);
			String commentaire = (String) ligneTableChangeEtape.getValue(KPIUtil.FIELD_COMMENTAIRE);
			String libelleEtape = (String) ligneTableChangeEtape.getValue(KPIUtil.FIELD_LIBELLE_ETAPE);
			String boutonAction = (String) ligneTableChangeEtape.getValue(KPIUtil.ACTION_BUTTON_NAME);
			String projectString = (String) ligneTableChangeEtape.getValue(KPIUtil.FIELD_SELECTEUR_PROJECTS);
			String workflowString = (String) ligneTableChangeEtape.getValue(KPIUtil.FIELD_SELECTEUR_PROCESS);

			if (!activerChangementEtape) {
				reportError("Le changement d'étape n'est pas activé pour la ligne " + ligneTableChangeEtape.getName()
						+ " de la table ChangeEtape");
				return;
			}

			IUser user = (IUser) ligneTableChangeEtape.getValue(KPIUtil.FIELD_SELECTEUR_USERS);

			if (user == null) {
				reportError("Erreur à la récupération de l'utilisateur depuis le login");
				return;
			}

			IProject project = (IProject) pm.getElementByProtocolURI(projectString);

			if (project == null) {
				reportError("Erreur à la récupération du projet : null");
				return;
			}

			IWorkflow workflow = (IWorkflow) wm.getElementByProtocolURI(workflowString);

			if (workflow == null) {
				reportError("Erreur à la récupération du workflow : null");
				return;
			}

			if (commentaire == null) {
				countMatchInStandingsAndOppositions(null, libelleEtape, user, project, workflow, boutonAction, wm);
			} else {
				countMatchInStandingsAndOppositions(commentaire, libelleEtape, user, project, workflow, boutonAction, wm);
			}
		}
		
		updateStandings();

		Modules.releaseModule(wm);
		Modules.releaseModule(pm);
		Modules.releaseModule(dm);
	}

	/**
	 * Comptabilise les matchs pour lesquels la case "Comptabilisé ?" n'est pas cochée et le score est renseigné
	 * Met à jour les score dans la table "Configurations"
	 * 
	 * @param commentaire
	 * @param libelleEtape
	 * @param user
	 * @param project
	 * @param workflow
	 * @param boutonAction
	 * @param wm
	 * @throws VdocHelperException
	 * @throws WorkflowModuleException
	 */
	private static void countMatchInStandingsAndOppositions(String commentaire, String libelleEtape, IUser user, IProject project,
			IWorkflow workflow, String boutonAction, IWorkflowModule wm)
			throws VdocHelperException, WorkflowModuleException {

		List<IWorkflowInstance> listWI = VdocHelper.getWorkflowInstancesByCurrentStep(workflow, libelleEtape, wm);

		if (listWI == null || listWI.isEmpty()) {
			reportInfo("Aucun document ne se trouve à l'étape " + libelleEtape);
			return;
		}

		IContext context = wm.getContext(user);

		for (IWorkflowInstance wi : listWI) {

			Boolean comptabilise = (Boolean) wi.getValue(UtilitaireLigue1.FORM_FIELD_CHECK_BOX_COMPTABILISE);
			String score = (String) wi.getValue(UtilitaireLigue1.FORM_FIELD_SCORE);
			String e1 = (String) wi.getValue(UtilitaireLigue1.FORM_FIELD_SURNOM_EQUIPE_DOMICILE);
			String e2 = (String) wi.getValue(UtilitaireLigue1.FORM_FIELD_SURNOM_EQUIPE_EXTERIEUR);

			IResourceController controller = wm.getResourceController(wi);

			if (controller == null) {
				reportError("controller null");
				return;
			}

			if (!comptabilise && ! UtilitaireLigue1.isEmpty(score)) {

				boolean executeMatch = Match.checkAllFieldsAreOKAndExecuteMatch(wi, controller, wm);

				if (!executeMatch) {
					reportError("Erreur à la comptabilisation du match : " + e1 + " - " + e2);
					return;
				}

				VdocHelper.documentChangeCurrentTask(wi, boutonAction, context, commentaire);
				reportInfo("Le match " + (String) wi.getValue(UtilitaireLigue1.FORM_FIELD_SURNOM_EQUIPE_DOMICILE)
						+ " - " + (String) wi.getValue(UtilitaireLigue1.FORM_FIELD_SURNOM_EQUIPE_EXTERIEUR)
						+ " a été comptabilisé.");

			} else {
				reportInfo("Le match " + (String) wi.getValue(UtilitaireLigue1.FORM_FIELD_SURNOM_EQUIPE_DOMICILE)
						+ " - " + (String) wi.getValue(UtilitaireLigue1.FORM_FIELD_SURNOM_EQUIPE_EXTERIEUR)
						+ " était déjà comptabilisé avant l'exécution de l'agent.");
			}
		}
		reportInfo("Les scores ont bien été mis à jour dans la table Confrontations");
	}
	
	/**
	 * Met à jour les classements suivants : Général, Domicile, Extérieur
	 */
	private static void updateStandings() {

		IDirectoryModule dm = Modules.getDirectoryModule();
		IProjectModule pm = Modules.getProjectModule();
		IWorkflowModule wm = Modules.getWorkflowModule();

		Collection<IStorageResource> equipes;
		try {
			equipes = VdocHelper.getDataUniverseResources(dm, pm, wm, UtilitaireLigue1.ORGANIZATION_NAME,
					UtilitaireLigue1.APPLICATION_NAME, UtilitaireLigue1.DATA_RESERVOIR_EQUIPES_NAME,
					UtilitaireLigue1.TABLE_EQUIPES_NAME);

			if (equipes == null) {
				reportError("La table ne contient aucune équipe. Tu peux t'inquiéter...");
				return;
			}

			updateAllStanding(wm, equipes, UtilitaireLigue1.TABLE_FIELD_NOMBRE_POINTS,
					UtilitaireLigue1.TABLE_FIELD_DIFFERENCE_DE_BUTS, "classement");

			reportInfo("Le classement général a bien été mis à jour.");

			updateAllStanding(wm, equipes, UtilitaireLigue1.TABLE_FIELD_NOMBRE_POINTS_DOMICILE,
					UtilitaireLigue1.TABLE_FIELD_DIFFERENCE_DE_BUTS_DOMICILE, "classementDomicile");
			
			reportInfo("Le classement domicile a bien été mis à jour.");
			
			updateAllStanding(wm, equipes, UtilitaireLigue1.TABLE_FIELD_NOMBRE_POINTS_EXTERIEUR,
					UtilitaireLigue1.TABLE_FIELD_DIFFERENCE_DE_BUTS_EXTERIEUR, "classementExterieur");

			reportInfo("Le classement extérieur a bien été mis à jour.");

		} catch (VdocHelperException e) {
			reportError("Erreur à la récupération des équipes : " + e.getMessage());
			e.printStackTrace();
		}
		Modules.releaseModule(wm);
		Modules.releaseModule(pm);
		Modules.releaseModule(dm);
	}
	
	private static void updateAllStanding(IWorkflowModule wm, Collection<IStorageResource> equipes, String fieldNbPoints,
			String fieldGoalAverage, String standingType) throws VdocHelperException {

		Map<String, Long[]> ratiosEquipes = new HashMap<String, Long[]>();
		StandingComparator comparator = new StandingComparator(ratiosEquipes);
		TreeMap<String, Long[]> maptree = new TreeMap<String, Long[]>(comparator);

		for (IStorageResource equipe : equipes) {

			String surnom = (String) equipe.getValue("surnom");
			Long nbPoints = (Long) equipe.getValue(fieldNbPoints);
			Long differenceButs = (Long) equipe.getValue(fieldGoalAverage);

			Long[] stats = { nbPoints, differenceButs };
			ratiosEquipes.put(surnom, stats);

		}

		maptree.putAll(ratiosEquipes);
		Map<String, String> classementDefinitif = new HashMap<String, String>();
		int i = 1;
		for (Entry<String, Long[]> entry : maptree.entrySet()) {
			classementDefinitif.put(String.valueOf(i), entry.getKey());
			i++;
		}

		for (Entry<String, String> classement : classementDefinitif.entrySet()) {

			IStorageResource equipe = UtilitaireLigue1.getResourceEquipe(classement.getValue());
			equipe.setValue(standingType, classement.getKey());
			equipe.save(wm.getSysadminContext());
		}
	}

	public static void reportInfo(String log) {
		if (iReport != null)
			iReport.addInfo(log);
		logger.debug(log);
	}

	public static void reportError(String log) {
		if (iReport != null)
			iReport.addError(log);
		logger.error(log);
	}

}
