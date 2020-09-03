package com.doandgo.ligue1.agents;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.axemble.vdoc.sdk.agent.base.BaseAgent;
import com.axemble.vdoc.sdk.interfaces.IReport;
import com.axemble.vdoc.sdk.interfaces.IStorageResource;
import com.axemble.vdoc.sdk.utils.Logger;
import com.doandgo.ligue1.matchs.Confrontation;
import com.doandgo.ligue1.utils.UtilitaireLigue1;
import com.doandgo.moovapps.exceptions.VdocHelperException;
import com.doandgo.moovapps.utils.VdocHelper;

/**
 * Agent qui remplit la table "Statistiques" avec les données traitées de la
 * table "Confrontations"
 * 
 * @author Thomas CHARMES
 */
public class AgentMiseAJourStatistiquesDesConfrontations extends BaseAgent {

	private static final Logger logger = Logger.getLogger(AgentMiseAJourStatistiquesDesConfrontations.class);
	public static IReport iReport = null;

	@Override
	protected void execute() {
		try {

			iReport = getReport();
			Collection<IStorageResource> allConfrontations = UtilitaireLigue1.getResourcesConfrontations();

			if (allConfrontations.isEmpty() || allConfrontations == null) {

				reportInfo("Aucune confrontation n'est enregistrée dans la table");
				return;
			}

			Collection<Confrontation> confrontations = getConfrontations(allConfrontations);

			if (confrontations.isEmpty() || confrontations == null) {
				reportError("Erreur à la création des objets de type Confrontation");
				return;
			}
			reportInfo("Les confrontations ont bien été récupérées");

			for (Confrontation confrontation : confrontations) {

				if (confrontation.getNbConfrontations() == 0F) {
					reportInfo("La confrontation " + confrontation.getMatch()
							+ " est inédite et ne comporte pas de stats !");
				} else {

					if (getAgent().getLastSuccessDate() == null || 
							confrontation.getDateLastModification().after(getAgent().getLastSuccessDate())) {
						
						setStatistiques(confrontation);

						reportInfo(
								"La table \"Statistiques\" a été mise à jour avec les informations de la confrontation "
										+ confrontation.getMatch());
					}
					if (confrontation.getDateLastModification().before(getAgent().getLastSuccessDate())) {
						reportInfo("La confrontation " + confrontation.getMatch() + " était déjà à jour");
					}
				}
			}

		} catch (Exception e) {
			reportError("Erreur à l'éxécution de l'agent " + this.getClass().getName() + " : " + e.getMessage());
			e.printStackTrace();
		}
	}

	private void setStatistiques(Confrontation confrontation) throws VdocHelperException {

		IStorageResource statistique = UtilitaireLigue1.getResourceStatistique(confrontation.getMatch());

		if (statistique == null) {

			Map<String, Object> equalsConstraints = new HashMap<String, Object>();
			equalsConstraints.put(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_MATCH, confrontation.getMatch());
			statistique = VdocHelper.createDataUniverseStorageResource(UtilitaireLigue1.ORGANIZATION_NAME,
					UtilitaireLigue1.APPLICATION_NAME, UtilitaireLigue1.DATA_RESERVOIR_EQUIPES_NAME,
					UtilitaireLigue1.TABLE_STATISTIQUES_NAME, equalsConstraints);
			if (statistique == null) {
				reportError(
						"Erreur à la création initiale de la resource dans la table statistique avant de mettre les données");
				return;
			}
		}
		statistique.setValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_MATCH, confrontation.getMatch());
		statistique.setValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_MOYENNE_BUTS_DOMICILE,
				confrontation.getMoyenneButsParMatchEquipe1());
		statistique.setValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_MOYENNE_BUTS_EXTERIEUR,
				confrontation.getMoyenneButsParMatchEquipe2());
		statistique.setValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_MOYENNE_BUTS_MATCH,
				confrontation.getMoyenneButsParMatch());
		statistique.setValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_DOMICILE_MOINS_0_5_BUTS,
				confrontation.getPourcentageEquipeDomicileMoinsBut(0.5F));
		statistique.setValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_DOMICILE_MOINS_1_5_BUTS,
				confrontation.getPourcentageEquipeDomicileMoinsBut(1.5F));
		statistique.setValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_DOMICILE_MOINS_2_5_BUTS,
				confrontation.getPourcentageEquipeDomicileMoinsBut(2.5F));
		statistique.setValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_DOMICILE_MOINS_3_5_BUTS,
				confrontation.getPourcentageEquipeDomicileMoinsBut(3.5F));
		statistique.setValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_DOMICILE_MOINS_4_5_BUTS,
				confrontation.getPourcentageEquipeDomicileMoinsBut(4.5F));
		statistique.setValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_DOMICILE_PLUS_0_5_BUTS,
				confrontation.getPourcentageEquipeDomicilePlusBut(0.5F));
		statistique.setValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_DOMICILE_PLUS_1_5_BUTS,
				confrontation.getPourcentageEquipeDomicilePlusBut(1.5F));
		statistique.setValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_DOMICILE_PLUS_2_5_BUTS,
				confrontation.getPourcentageEquipeDomicilePlusBut(2.5F));
		statistique.setValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_DOMICILE_PLUS_3_5_BUTS,
				confrontation.getPourcentageEquipeDomicilePlusBut(3.5F));
		statistique.setValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_DOMICILE_PLUS_4_5_BUTS,
				confrontation.getPourcentageEquipeDomicilePlusBut(4.5F));
		statistique.setValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_EXTERIEUR_MOINS_0_5_BUTS,
				confrontation.getPourcentageEquipeExterieurMoinsBut(0.5F));
		statistique.setValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_EXTERIEUR_MOINS_1_5_BUTS,
				confrontation.getPourcentageEquipeExterieurMoinsBut(1.5F));
		statistique.setValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_EXTERIEUR_MOINS_2_5_BUTS,
				confrontation.getPourcentageEquipeExterieurMoinsBut(2.5F));
		statistique.setValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_EXTERIEUR_MOINS_3_5_BUTS,
				confrontation.getPourcentageEquipeExterieurMoinsBut(3.5F));
		statistique.setValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_EXTERIEUR_MOINS_4_5_BUTS,
				confrontation.getPourcentageEquipeExterieurMoinsBut(4.5F));
		statistique.setValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_EXTERIEUR_PLUS_0_5_BUTS,
				confrontation.getPourcentageEquipeExterieurPlusBut(0.5F));
		statistique.setValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_EXTERIEUR_PLUS_1_5_BUTS,
				confrontation.getPourcentageEquipeExterieurPlusBut(1.5F));
		statistique.setValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_EXTERIEUR_PLUS_2_5_BUTS,
				confrontation.getPourcentageEquipeExterieurPlusBut(2.5F));
		statistique.setValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_EXTERIEUR_PLUS_3_5_BUTS,
				confrontation.getPourcentageEquipeExterieurPlusBut(3.5F));
		statistique.setValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_EXTERIEUR_PLUS_4_5_BUTS,
				confrontation.getPourcentageEquipeExterieurPlusBut(4.5F));
		statistique.setValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_LDEM,
				confrontation.getPourcentageLDEM());
		statistique.setValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_MATCH_MOINS_0_5_BUTS,
				confrontation.getPourcentageMatchMoinsBut(0.5F));
		statistique.setValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_MATCH_MOINS_1_5_BUTS,
				confrontation.getPourcentageMatchMoinsBut(1.5F));
		statistique.setValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_MATCH_MOINS_2_5_BUTS,
				confrontation.getPourcentageMatchMoinsBut(2.5F));
		statistique.setValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_MATCH_MOINS_3_5_BUTS,
				confrontation.getPourcentageMatchMoinsBut(3.5F));
		statistique.setValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_MATCH_MOINS_4_5_BUTS,
				confrontation.getPourcentageMatchMoinsBut(4.5F));
		statistique.setValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_MATCH_PLUS_0_5_BUTS,
				confrontation.getPourcentageMatchPlusBut(0.5F));
		statistique.setValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_MATCH_PLUS_1_5_BUTS,
				confrontation.getPourcentageMatchPlusBut(1.5F));
		statistique.setValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_MATCH_PLUS_2_5_BUTS,
				confrontation.getPourcentageMatchPlusBut(2.5F));
		statistique.setValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_MATCH_PLUS_3_5_BUTS,
				confrontation.getPourcentageMatchPlusBut(3.5F));
		statistique.setValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_MATCH_PLUS_4_5_BUTS,
				confrontation.getPourcentageMatchPlusBut(4.5F));
		statistique.setValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_EQUIPE_DOMICILE_0_BUT_ENCAISSE,
				confrontation.getPourcentageMatchsEquipe1EncaisseExactementXButs(0F));
		statistique.setValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_EQUIPE_DOMICILE_1_BUT_ENCAISSE,
				confrontation.getPourcentageMatchsEquipe1EncaisseExactementXButs(1F));
		statistique.setValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_EQUIPE_DOMICILE_2_BUTS_ENCAISSES,
				confrontation.getPourcentageMatchsEquipe1EncaisseExactementXButs(2F));
		statistique.setValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_EQUIPE_DOMICILE_3_BUTS_ENCAISSES,
				confrontation.getPourcentageMatchsEquipe1EncaisseExactementXButs(3F));
		statistique.setValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_EQUIPE_DOMICILE_4_BUTS_ENCAISSES,
				confrontation.getPourcentageMatchsEquipe1EncaisseExactementXButs(4F));
		statistique.setValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_EQUIPE_DOMICILE_5_BUTS_ENCAISSES,
				confrontation.getPourcentageMatchsEquipe1EncaisseExactementXButs(5F));
		statistique.setValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_EQUIPE_DOMICILE_0_BUT_MARQUE,
				confrontation.getPourcentageMatchsEquipe1MarqueExactementXButs(0F));
		statistique.setValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_EQUIPE_DOMICILE_1_BUT_MARQUE,
				confrontation.getPourcentageMatchsEquipe1MarqueExactementXButs(1F));
		statistique.setValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_EQUIPE_DOMICILE_2_BUTS_MARQUES,
				confrontation.getPourcentageMatchsEquipe1MarqueExactementXButs(2F));
		statistique.setValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_EQUIPE_DOMICILE_3_BUTS_MARQUES,
				confrontation.getPourcentageMatchsEquipe1MarqueExactementXButs(3F));
		statistique.setValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_EQUIPE_DOMICILE_4_BUTS_MARQUES,
				confrontation.getPourcentageMatchsEquipe1MarqueExactementXButs(4F));
		statistique.setValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_EQUIPE_DOMICILE_5_BUTS_MARQUES,
				confrontation.getPourcentageMatchsEquipe1MarqueExactementXButs(5F));
		statistique.setValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_EQUIPE_EXTERIEUR_0_BUT_ENCAISSE,
				confrontation.getPourcentageMatchsEquipe2EncaisseExactementXButs(0F));
		statistique.setValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_EQUIPE_EXTERIEUR_1_BUT_ENCAISSE,
				confrontation.getPourcentageMatchsEquipe2EncaisseExactementXButs(1F));
		statistique.setValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_EQUIPE_EXTERIEUR_2_BUTS_ENCAISSES,
				confrontation.getPourcentageMatchsEquipe2EncaisseExactementXButs(2F));
		statistique.setValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_EQUIPE_EXTERIEUR_3_BUTS_ENCAISSES,
				confrontation.getPourcentageMatchsEquipe2EncaisseExactementXButs(3F));
		statistique.setValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_EQUIPE_EXTERIEUR_4_BUTS_ENCAISSES,
				confrontation.getPourcentageMatchsEquipe2EncaisseExactementXButs(4F));
		statistique.setValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_EQUIPE_EXTERIEUR_5_BUTS_ENCAISSES,
				confrontation.getPourcentageMatchsEquipe2EncaisseExactementXButs(5F));
		statistique.setValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_EQUIPE_EXTERIEUR_0_BUT_MARQUE,
				confrontation.getPourcentageMatchsEquipe2MarqueExactementXButs(0F));
		statistique.setValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_EQUIPE_EXTERIEUR_1_BUT_MARQUE,
				confrontation.getPourcentageMatchsEquipe2MarqueExactementXButs(1F));
		statistique.setValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_EQUIPE_EXTERIEUR_2_BUTS_MARQUES,
				confrontation.getPourcentageMatchsEquipe2MarqueExactementXButs(2F));
		statistique.setValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_EQUIPE_EXTERIEUR_3_BUTS_MARQUES,
				confrontation.getPourcentageMatchsEquipe2MarqueExactementXButs(3F));
		statistique.setValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_EQUIPE_EXTERIEUR_4_BUTS_MARQUES,
				confrontation.getPourcentageMatchsEquipe2MarqueExactementXButs(4F));
		statistique.setValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_EQUIPE_EXTERIEUR_5_BUTS_MARQUES,
				confrontation.getPourcentageMatchsEquipe2MarqueExactementXButs(5F));
		statistique.setValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_NULS,
				confrontation.getPourcentageNuls());
		statistique.setValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_VICTOIRE_DOMICILE,
				confrontation.getPourcentageVictoiresEquipe1());
		statistique.setValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_VICTOIRE_EXTERIEUR,
				confrontation.getPourcentageVictoiresEquipe2());
		statistique.setValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_ECART_BUT_PLUS_1,
				confrontation.getPourcentageVictoiresEquipe1ParXButs(1F));
		statistique.setValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_ECART_BUT_PLUS_2,
				confrontation.getPourcentageVictoiresEquipe1ParXButs(2F));
		statistique.setValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_ECART_BUT_PLUS_3,
				confrontation.getPourcentageVictoiresEquipe1ParXButs(3F));
		statistique.setValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_ECART_BUT_PLUS_4,
				confrontation.getPourcentageVictoiresEquipe1ParXButs(4F));
		statistique.setValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_ECART_BUT_PLUS_5,
				confrontation.getPourcentageVictoiresEquipe1ParXButs(5F));
		statistique.setValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_ECART_BUT_PLUS_6,
				confrontation.getPourcentageVictoiresEquipe1ParXButs(6F));
		statistique.setValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_ECART_BUT_PLUS_7,
				confrontation.getPourcentageVictoiresEquipe1ParXButs(7F));
		statistique.setValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_ECART_BUT_PLUS_8,
				confrontation.getPourcentageVictoiresEquipe1ParXButs(8F));
		statistique.setValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_ECART_BUT_PLUS_9,
				confrontation.getPourcentageVictoiresEquipe1ParXButs(9F));
		statistique.setValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_ECART_BUT_MOINS_1,
				confrontation.getPourcentageVictoiresEquipe2ParXButs(1F));
		statistique.setValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_ECART_BUT_MOINS_2,
				confrontation.getPourcentageVictoiresEquipe2ParXButs(2F));
		statistique.setValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_ECART_BUT_MOINS_3,
				confrontation.getPourcentageVictoiresEquipe2ParXButs(3F));
		statistique.setValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_ECART_BUT_MOINS_4,
				confrontation.getPourcentageVictoiresEquipe2ParXButs(4F));
		statistique.setValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_ECART_BUT_MOINS_5,
				confrontation.getPourcentageVictoiresEquipe2ParXButs(5F));
		statistique.setValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_ECART_BUT_MOINS_6,
				confrontation.getPourcentageVictoiresEquipe2ParXButs(6F));
		statistique.setValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_ECART_BUT_MOINS_7,
				confrontation.getPourcentageVictoiresEquipe2ParXButs(7F));
		statistique.setValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_ECART_BUT_MOINS_8,
				confrontation.getPourcentageVictoiresEquipe2ParXButs(8F));
		statistique.setValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_ECART_BUT_MOINS_9,
				confrontation.getPourcentageVictoiresEquipe2ParXButs(9F));

		statistique.save(getWorkflowModule().getSysadminContext());

	}

	private static Collection<Confrontation> getConfrontations(Collection<IStorageResource> allConfrontations) {

		Collection<Confrontation> listeConfrontations = new ArrayList<Confrontation>();

		for (IStorageResource confrontation : allConfrontations) {

			String match = (String) confrontation.getValue(UtilitaireLigue1.TABLE_CONFRONTATIONS_FIELD_MATCH);
			String recent1 = (String) confrontation.getValue(UtilitaireLigue1.TABLE_CONFRONTATIONS_FIELD_RECENT_1);
			String recent2 = (String) confrontation.getValue(UtilitaireLigue1.TABLE_CONFRONTATIONS_FIELD_RECENT_2);
			String recent3 = (String) confrontation.getValue(UtilitaireLigue1.TABLE_CONFRONTATIONS_FIELD_RECENT_3);
			String recent4 = (String) confrontation.getValue(UtilitaireLigue1.TABLE_CONFRONTATIONS_FIELD_RECENT_4);
			String recent5 = (String) confrontation.getValue(UtilitaireLigue1.TABLE_CONFRONTATIONS_FIELD_RECENT_5);
			Date dateLastModification = (Date) confrontation
					.getValue(UtilitaireLigue1.TABLE_CONFRONTATIONS_FIELD_DATE_LAST_MODIFICATION);

			Confrontation c = new Confrontation(match, recent1, recent2, recent3, recent4, recent5,
					dateLastModification);
			listeConfrontations.add(c);
		}
		return listeConfrontations;
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
