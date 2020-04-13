package utilitaire;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.axemble.vdoc.sdk.Modules;
import com.axemble.vdoc.sdk.document.extensions.BaseResourceExtension;
import com.axemble.vdoc.sdk.interfaces.IContext;
import com.axemble.vdoc.sdk.interfaces.IStorageResource;
import com.axemble.vdoc.sdk.modules.IDirectoryModule;
import com.axemble.vdoc.sdk.modules.IProjectModule;
import com.axemble.vdoc.sdk.modules.IWorkflowModule;
import com.axemble.vdoc.sdk.utils.Logger;
import com.doandgo.moovapps.exceptions.VdocHelperException;
import com.doandgo.moovapps.utils.VdocHelper;

public class UtilitaireLigue1 extends BaseResourceExtension {

	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("unused")
	private static final Logger LOGGER = Logger.getLogger(UtilitaireLigue1.class);

	public final static String FORM_FIELD_SURNOM_EQUIPE_DOMICILE = "SurnomE1";
	public final static String FORM_FIELD_SURNOM_EQUIPE_EXTERIEUR = "SurnomE2";
	public final static String FORM_FIELD_CHECK_BOX_VICTOIRE_EQUIPE_DOMICILE = "VictoireEquipeDomicile";
	public final static String FORM_FIELD_CHECK_BOX_VICTOIRE_EQUIPE_EXTERIEUR = "VictoireEquipeExterieur";
	public final static String FORM_FIELD_CHECK_BOX_NUL = "nul";
	public final static String FORM_FIELD_CHECK_BOX_IMPORTANT_EQUIPE_DOMICILE = "ImportantEquipeDomicile";
	public final static String FORM_FIELD_CHECK_BOX_IMPORTANT_EQUIPE_EXTERIEUR = "ImportantEquipeExterieur";
	public final static String FORM_FIELD_CHECK_BOX_EQUIPE_DOMICILE_MIEUX_CLASSEE = "EquipeDomicileMieuxClassee";
	public final static String FORM_FIELD_CHECK_BOX_REINITIALISER_SAISON = "ResetSeason";
	public final static String FORM_FIELD_CHECK_BOX_RESET_ALL_SEASON = "ResetAllSeason";
	
	public final static String TABLE_FIELD_NOMBRE_MATCHS_JOUES = "nombreMatchsJoues";
	public final static String TABLE_FIELD_STRING_CLASSEMENT = "classement";
	public final static String TABLE_FIELD_STRING_CLASSEMENT_DOMICILE = "classementDomicile";
	public final static String TABLE_FIELD_STRING_CLASSEMENT_EXTERIEUR = "classementExterieur";
	public final static String TABLE_FIELD_STRING_ID = "id";
	public final static String TABLE_FIELD_NAME = "nom";
	public final static String TABLE_FIELD_SURNAME = "surnom";
	public final static String TABLE_FIELD_NOMBRE_POINTS = "NombrePoints";
	public final static String TABLE_FIELD_NOMBRE_POINTS_DOMICILE = "NombrePointsDomicile";
	public final static String TABLE_FIELD_NOMBRE_POINTS_EXTERIEUR = "NombrePointsExterieur";
	
	public final static String TABLE_FIELD_STRING_MATCH_PRECEDENT_5 = "matchPrecedentCinq";
	public final static String TABLE_FIELD_STRING_MATCH_PRECEDENT_5_DOMICILE = "MatchPrecedentCinqDomicile";
	public final static String TABLE_FIELD_STRING_MATCH_PRECEDENT_5_EXTERIEUR = "MatchPrecedentCinqExterieur";
	public final static String TABLE_FIELD_STRING_MATCH_PRECEDENT_4 = "matchPrecedentQuatre";
	public final static String TABLE_FIELD_STRING_MATCH_PRECEDENT_4_DOMICILE = "MatchPrecedentQuatreDomicile";
	public final static String TABLE_FIELD_STRING_MATCH_PRECEDENT_4_EXTERIEUR = "MatchPrecedentQuatreExterieur";
	public final static String TABLE_FIELD_STRING_MATCH_PRECEDENT_3 = "matchPrecedentTrois";
	public final static String TABLE_FIELD_STRING_MATCH_PRECEDENT_3_DOMICILE = "MatchPrecedentTroisDomicile";
	public final static String TABLE_FIELD_STRING_MATCH_PRECEDENT_3_EXTERIEUR = "MatchPrecedentTroisExterieur";
	public final static String TABLE_FIELD_STRING_MATCH_PRECEDENT_2 = "matchPrecedentDeux";
	public final static String TABLE_FIELD_STRING_MATCH_PRECEDENT_2_DOMICILE = "MatchPrecedentDeuxDomicile";
	public final static String TABLE_FIELD_STRING_MATCH_PRECEDENT_2_EXTERIEUR = "MatchPrecedentDeuxExterieur";
	public final static String TABLE_FIELD_STRING_MATCH_PRECEDENT_1 = "matchPrecedentUn";
	public final static String TABLE_FIELD_STRING_MATCH_PRECEDENT_1_DOMICILE = "MatchPrecedentUnDomicile";
	public final static String TABLE_FIELD_STRING_MATCH_PRECEDENT_1_EXTERIEUR = "MatchPrecedentUnExterieur";
	
	public final static String TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_CLASSEMENT_INFERIEUR = "NombreDClassementInf";
	public final static String TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_CLASSEMENT_INFERIEUR_DOMICILE = "NombreDClassementInfDomicile";
	public final static String TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_CLASSEMENT_INFERIEUR_EXTERIEUR = "NombreDClassementInfExterieur";
	public final static String TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_CLASSEMENT_SUPERIEUR = "NombreDClassementSup";
	public final static String TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_CLASSEMENT_SUPERIEUR_DOMICILE = "NombreDClassementSupDomicile";
	public final static String TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_CLASSEMENT_SUPERIEUR_EXTERIEUR = "NombreDClassementSupExterieur";
	public final static String TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_IMPORTANT_DOMICILE = "NombreDImportantsDomicile";
	public final static String TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_IMPORTANT_EXTERIEUR = "NombreDImportantsExterieur";
	public final static String TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_BANAL = "NombreDMatchBanal";
	public final static String TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_BANAL_DOMICILE = "NombreDMatchBanalDomicile";
	public final static String TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_BANAL_EXTERIEUR = "NombreDMatchBanalExterieur";
	public final static String TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_IMPORTANT = "NombreDMatchsImportants";
	
	public final static String TABLE_FIELD_NOMBRE_MATCHS_JOUES_DOMICILE = "nombreMatchsJouesDomicile";
	public final static String TABLE_FIELD_NOMBRE_MATCHS_JOUES_EXTERIEUR = "nombreMatchsJouesExterieur";
	public final static String TABLE_FIELD_NOMBRE_MATCHS_GAGNES = "nombreMatchsGagnes";
	public final static String TABLE_FIELD_NOMBRE_MATCHS_GAGNES_DOMICILE = "nombreMatchsGagnesDomicile";
	public final static String TABLE_FIELD_NOMBRE_MATCHS_GAGNES_EXTERIEUR = "nombreMatchsGagnesExterieur";
	
	public final static String TABLE_FIELD_NOMBRE_MATCHS_JOUES_BANAL = "NombreMatchsJouesBanal";
	public final static String TABLE_FIELD_NOMBRE_MATCHS_JOUES_BANAL_DOMICILE = "NombreMatchsJouesBanalDomicile";
	public final static String TABLE_FIELD_NOMBRE_MATCHS_JOUES_BANAL_EXTERIEUR = "NombreMatchsJouesBanalExterieur";
	
	public final static String TABLE_FIELD_NOMBRE_MATCHS_JOUES_CLASSEMENT_INF = "NombreMatchsJouesClassementInf";
	public final static String TABLE_FIELD_NOMBRE_MATCHS_JOUES_CLASSEMENT_INF_DOMICILE = "NombreMatchsJouesClassementInfDomicile";
	public final static String TABLE_FIELD_NOMBRE_MATCHS_JOUES_CLASSEMENT_INF_EXTERIEUR = "NombreMatchsJouesClassementInfExterieur";
	public final static String TABLE_FIELD_NOMBRE_MATCHS_JOUES_CLASSEMENT_SUP = "NombreMatchsJouesClassementSup";
	public final static String TABLE_FIELD_NOMBRE_MATCHS_JOUES_CLASSEMENT_SUP_EXTERIEUR = "NombreMatchsJouesClassementSupExterieur";
	public final static String TABLE_FIELD_NOMBRE_MATCHS_JOUES_CLASSEMENT_SUP_DOMICILE = "NombreMatchsJouesClassementSupDomicile";
	
	public final static String TABLE_FIELD_NOMBRE_MATCHS_JOUES_IMPORTANTS = "NombreMatchsJouesImportants";
	public final static String TABLE_FIELD_NOMBRE_MATCHS_JOUES_IMPORTANTS_DOMICILE = "NombreMatchsJouesImportantsDomicile";
	public final static String TABLE_FIELD_NOMBRE_MATCHS_JOUES_IMPORTANTS_EXTERIEUR = "NombreMatchsJouesImportantsExterieur";
	public final static String TABLE_FIELD_NOMBRE_MATCHS_NULS = "nombreMatchsNuls";
	public final static String TABLE_FIELD_NOMBRE_MATCHS_NULS_DOMICILE = "nombreMatchsNulsDomicile";
	public final static String TABLE_FIELD_NOMBRE_MATCHS_NULS_EXTERIEUR = "nombreMatchsNulsExterieur";
	public final static String TABLE_FIELD_NOMBRE_MATCHS_PERDUS = "nombreMatchsPerdus";
	public final static String TABLE_FIELD_NOMBRE_MATCHS_PERDUS_DOMICILE = "nombreMatchsPerdusDomicile";
	public final static String TABLE_FIELD_NOMBRE_MATCHS_PERDUS_EXTERIEUR = "nombreMatchsPerdusExterieur";
	
	public final static String TABLE_FIELD_NOMBRE_MATCHS_NULS_CLASSEMENT_INF = "NombreNClassementInf";
	public final static String TABLE_FIELD_NOMBRE_MATCHS_NULS_CLASSEMENT_INF_DOMICILE = "NombreNClassementInfDomicile";
	public final static String TABLE_FIELD_NOMBRE_MATCHS_NULS_CLASSEMENT_INF_EXTERIEUR = "NombreNClassementInfExterieur";
	public final static String TABLE_FIELD_NOMBRE_MATCHS_NULS_CLASSEMENT_SUP = "NombreNClassementSup";
	public final static String TABLE_FIELD_NOMBRE_MATCHS_NULS_CLASSEMENT_SUP_DOMICILE = "NombreNClassementSupDomicile";
	public final static String TABLE_FIELD_NOMBRE_MATCHS_NULS_CLASSEMENT_SUP_EXTERIEUR = "NombreNClassementSupExterieur";
	public final static String TABLE_FIELD_NOMBRE_MATCHS_NULS_IMPORTANT_DOMICILE = "NombreNImportantsDomicile";
	public final static String TABLE_FIELD_NOMBRE_MATCHS_NULS_IMPORTANT_EXTERIEUR = "NombreNImportantsExterieur";
	public final static String TABLE_FIELD_NOMBRE_MATCHS_NULS_BANAL = "NombreNMatchBanal";
	public final static String TABLE_FIELD_NOMBRE_MATCHS_NULS_BANAL_DOMICILE = "NombreNMatchBanalDomicile";
	public final static String TABLE_FIELD_NOMBRE_MATCHS_NULS_BANAL_EXTERIEUR = "NombreNMatchBanalExterieur";
	public final static String TABLE_FIELD_NOMBRE_MATCHS_NULS_IMPORTANT = "NombreNMatchsImportants";
	
	public final static String TABLE_FIELD_NOMBRE_VICTOIRES_CLASSEMENT_INF = "NombreVClassementInf";
	public final static String TABLE_FIELD_NOMBRE_VICTOIRES_CLASSEMENT_INF_DOMICILE = "NombreVClassementInfDomicile";
	public final static String TABLE_FIELD_NOMBRE_VICTOIRES_CLASSEMENT_INF_EXTERIEUR = "NombreVClassementInfExterieur";
	public final static String TABLE_FIELD_NOMBRE_VICTOIRES_CLASSEMENT_SUP = "NombreVClassementSup";
	public final static String TABLE_FIELD_NOMBRE_VICTOIRES_CLASSEMENT_SUP_DOMICILE = "NombreVClassementSupDomicile";
	public final static String TABLE_FIELD_NOMBRE_VICTOIRES_CLASSEMENT_SUP_EXTERIEUR = "NombreVClassementSupExterieur";
	public final static String TABLE_FIELD_NOMBRE_VICTOIRES_IMPORTANT_DOMICILE = "NombreVImportantsDomicile";
	public final static String TABLE_FIELD_NOMBRE_VICTOIRES_IMPORTANT_EXTERIEUR = "NombreVImportantsExterieur";
	public final static String TABLE_FIELD_NOMBRE_VICTOIRES_BANAL = "NombreVMatchBanal";
	public final static String TABLE_FIELD_NOMBRE_VICTOIRES_BANAL_DOMICILE = "NombreVMatchBanalDomicile";
	public final static String TABLE_FIELD_NOMBRE_VICTOIRES_BANAL_EXTERIEUR = "NombreVMatchBanalExterieur";
	public final static String TABLE_FIELD_NOMBRE_VICTOIRES_IMPORTANT = "NombreVMatchsImportants";
	
	public final static String TABLE_FIELD_SERIE_D_EN_COURS = "SerieDEnCours";
	public final static String TABLE_FIELD_SERIE_D_EN_COURS_DOMICILE = "SerieDEnCoursDomicile";
	public final static String TABLE_FIELD_SERIE_D_EN_COURS_EXTERIEUR = "SerieDEnCoursExterieur";
	public final static String TABLE_FIELD_SERIE_N_EN_COURS = "SerieNEnCours";
	public final static String TABLE_FIELD_SERIE_N_EN_COURS_DOMICILE = "SerieNEnCoursDomicile";
	public final static String TABLE_FIELD_SERIE_N_EN_COURS_EXTERIEUR = "SerieNEnCoursExterieur";
	public final static String TABLE_FIELD_SERIE_V_EN_COURS = "SerieVEnCours";
	public final static String TABLE_FIELD_SERIE_V_EN_COURS_DOMICILE = "SerieVEnCoursDomicile";
	public final static String TABLE_FIELD_SERIE_V_EN_COURS_EXTERIEUR = "SerieVEnCoursExterieur";
	
	public enum EquipesLigue1 {
		MHSC, OGCN, PSG, LOSC, FCN, ASM, OM, SRFC, SB29, FCGB, OL, ASSE, SCO, RCS, AFC, SDR, FCM, DFCO, NO, TFC;
	}
	
	public static List<EquipesLigue1> listerEquipesLigue1DepuisEnumeration() {
		int i = 0;
		List<EquipesLigue1> liste = new ArrayList<EquipesLigue1>();
		
		EquipesLigue1[] tableauDesEquipes = EquipesLigue1.values();
		int valuesNumber = tableauDesEquipes.length;
		for (EquipesLigue1 equipe : tableauDesEquipes ) {
		  if (i == valuesNumber-2) {
		    break;
		  }
		  liste.add(equipe);
		}
		
		return liste;
	}
	
	public static void main(String[] args) {
		listerEquipesLigue1DepuisEnumeration();
	}
	
	public static void resetAllSeason() throws VdocHelperException {
		
		List<EquipesLigue1> listeDesEquipesDeLigue1 = listerEquipesLigue1DepuisEnumeration();
		
		for (EquipesLigue1 equipe : listeDesEquipesDeLigue1) {
			
			setResourceEquipe(equipe.toString(), TABLE_FIELD_NOMBRE_MATCHS_JOUES, 0);
			setResourceEquipe(equipe.toString(), TABLE_FIELD_NOMBRE_POINTS, 0);
			setResourceEquipe(equipe.toString(), TABLE_FIELD_NOMBRE_POINTS_DOMICILE, 0);
			setResourceEquipe(equipe.toString(), TABLE_FIELD_NOMBRE_POINTS_EXTERIEUR, 0);
			setResourceEquipe(equipe.toString(), TABLE_FIELD_STRING_MATCH_PRECEDENT_5, "O");
			setResourceEquipe(equipe.toString(), TABLE_FIELD_STRING_MATCH_PRECEDENT_5_DOMICILE, "O");
			setResourceEquipe(equipe.toString(), TABLE_FIELD_STRING_MATCH_PRECEDENT_5_EXTERIEUR, "O");
			setResourceEquipe(equipe.toString(), TABLE_FIELD_STRING_MATCH_PRECEDENT_4, "O");
			setResourceEquipe(equipe.toString(), TABLE_FIELD_STRING_MATCH_PRECEDENT_4_DOMICILE, "O");
			setResourceEquipe(equipe.toString(), TABLE_FIELD_STRING_MATCH_PRECEDENT_4_EXTERIEUR, "O");
			setResourceEquipe(equipe.toString(), TABLE_FIELD_STRING_MATCH_PRECEDENT_3, "O");
			setResourceEquipe(equipe.toString(), TABLE_FIELD_STRING_MATCH_PRECEDENT_3_DOMICILE, "O");
			setResourceEquipe(equipe.toString(), TABLE_FIELD_STRING_MATCH_PRECEDENT_3_EXTERIEUR, "O");
			setResourceEquipe(equipe.toString(), TABLE_FIELD_STRING_MATCH_PRECEDENT_2, "O");
			setResourceEquipe(equipe.toString(), TABLE_FIELD_STRING_MATCH_PRECEDENT_2_DOMICILE, "O");
			setResourceEquipe(equipe.toString(), TABLE_FIELD_STRING_MATCH_PRECEDENT_2_EXTERIEUR, "O");
			setResourceEquipe(equipe.toString(), TABLE_FIELD_STRING_MATCH_PRECEDENT_1, "O");
			setResourceEquipe(equipe.toString(), TABLE_FIELD_STRING_MATCH_PRECEDENT_1_DOMICILE, "O");
			setResourceEquipe(equipe.toString(), TABLE_FIELD_STRING_MATCH_PRECEDENT_1_EXTERIEUR, "O");
			setResourceEquipe(equipe.toString(), TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_CLASSEMENT_INFERIEUR, 0);
			setResourceEquipe(equipe.toString(), TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_CLASSEMENT_INFERIEUR_DOMICILE, 0);
			setResourceEquipe(equipe.toString(), TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_CLASSEMENT_INFERIEUR_EXTERIEUR, 0);
			setResourceEquipe(equipe.toString(), TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_CLASSEMENT_SUPERIEUR, 0);
			setResourceEquipe(equipe.toString(), TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_CLASSEMENT_SUPERIEUR_DOMICILE, 0);
			setResourceEquipe(equipe.toString(), TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_CLASSEMENT_SUPERIEUR_EXTERIEUR, 0);
			setResourceEquipe(equipe.toString(), TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_IMPORTANT_DOMICILE, 0);
			setResourceEquipe(equipe.toString(), TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_IMPORTANT_EXTERIEUR, 0);
			setResourceEquipe(equipe.toString(), TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_BANAL, 0);
			setResourceEquipe(equipe.toString(), TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_BANAL_DOMICILE, 0);
			setResourceEquipe(equipe.toString(), TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_BANAL_EXTERIEUR, 0);
			setResourceEquipe(equipe.toString(), TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_IMPORTANT, 0);
			setResourceEquipe(equipe.toString(), TABLE_FIELD_NOMBRE_MATCHS_JOUES_DOMICILE, 0);
			setResourceEquipe(equipe.toString(), TABLE_FIELD_NOMBRE_MATCHS_JOUES_EXTERIEUR, 0);
			setResourceEquipe(equipe.toString(), TABLE_FIELD_NOMBRE_MATCHS_GAGNES, 0);
			setResourceEquipe(equipe.toString(), TABLE_FIELD_NOMBRE_MATCHS_GAGNES_DOMICILE, 0);
			setResourceEquipe(equipe.toString(), TABLE_FIELD_NOMBRE_MATCHS_GAGNES_EXTERIEUR, 0);
			setResourceEquipe(equipe.toString(), TABLE_FIELD_NOMBRE_MATCHS_JOUES_BANAL, 0);
			setResourceEquipe(equipe.toString(), TABLE_FIELD_NOMBRE_MATCHS_JOUES_BANAL_DOMICILE, 0);
			setResourceEquipe(equipe.toString(), TABLE_FIELD_NOMBRE_MATCHS_JOUES_BANAL_EXTERIEUR, 0);
			setResourceEquipe(equipe.toString(), TABLE_FIELD_NOMBRE_MATCHS_JOUES_CLASSEMENT_INF, 0);
			setResourceEquipe(equipe.toString(), TABLE_FIELD_NOMBRE_MATCHS_JOUES_CLASSEMENT_INF_DOMICILE, 0);
			setResourceEquipe(equipe.toString(), TABLE_FIELD_NOMBRE_MATCHS_JOUES_CLASSEMENT_INF_EXTERIEUR, 0);
			setResourceEquipe(equipe.toString(), TABLE_FIELD_NOMBRE_MATCHS_JOUES_CLASSEMENT_SUP, 0);
			setResourceEquipe(equipe.toString(), TABLE_FIELD_NOMBRE_MATCHS_JOUES_CLASSEMENT_SUP_EXTERIEUR, 0);
			setResourceEquipe(equipe.toString(), TABLE_FIELD_NOMBRE_MATCHS_JOUES_CLASSEMENT_SUP_DOMICILE, 0);
			setResourceEquipe(equipe.toString(), TABLE_FIELD_NOMBRE_MATCHS_JOUES_IMPORTANTS, 0);
			setResourceEquipe(equipe.toString(), TABLE_FIELD_NOMBRE_MATCHS_JOUES_IMPORTANTS_DOMICILE, 0);
			setResourceEquipe(equipe.toString(), TABLE_FIELD_NOMBRE_MATCHS_JOUES_IMPORTANTS_EXTERIEUR, 0);
			setResourceEquipe(equipe.toString(), TABLE_FIELD_NOMBRE_MATCHS_NULS, 0);
			setResourceEquipe(equipe.toString(), TABLE_FIELD_NOMBRE_MATCHS_NULS_DOMICILE, 0);
			setResourceEquipe(equipe.toString(), TABLE_FIELD_NOMBRE_MATCHS_NULS_EXTERIEUR, 0);
			setResourceEquipe(equipe.toString(), TABLE_FIELD_NOMBRE_MATCHS_PERDUS, 0);
			setResourceEquipe(equipe.toString(), TABLE_FIELD_NOMBRE_MATCHS_PERDUS_DOMICILE, 0);
			setResourceEquipe(equipe.toString(), TABLE_FIELD_NOMBRE_MATCHS_PERDUS_EXTERIEUR, 0);
			setResourceEquipe(equipe.toString(), TABLE_FIELD_NOMBRE_MATCHS_NULS_CLASSEMENT_INF, 0);
			setResourceEquipe(equipe.toString(), TABLE_FIELD_NOMBRE_MATCHS_NULS_CLASSEMENT_INF_DOMICILE, 0);
			setResourceEquipe(equipe.toString(), TABLE_FIELD_NOMBRE_MATCHS_NULS_CLASSEMENT_INF_EXTERIEUR, 0);
			setResourceEquipe(equipe.toString(), TABLE_FIELD_NOMBRE_MATCHS_NULS_CLASSEMENT_SUP, 0);
			setResourceEquipe(equipe.toString(), TABLE_FIELD_NOMBRE_MATCHS_NULS_CLASSEMENT_SUP_DOMICILE, 0);
			setResourceEquipe(equipe.toString(), TABLE_FIELD_NOMBRE_MATCHS_NULS_CLASSEMENT_SUP_EXTERIEUR, 0);
			setResourceEquipe(equipe.toString(), TABLE_FIELD_NOMBRE_MATCHS_NULS_IMPORTANT_DOMICILE, 0);
			setResourceEquipe(equipe.toString(), TABLE_FIELD_NOMBRE_MATCHS_NULS_IMPORTANT_EXTERIEUR, 0);
			setResourceEquipe(equipe.toString(), TABLE_FIELD_NOMBRE_MATCHS_NULS_BANAL, 0);
			setResourceEquipe(equipe.toString(), TABLE_FIELD_NOMBRE_MATCHS_NULS_BANAL_DOMICILE, 0);
			setResourceEquipe(equipe.toString(), TABLE_FIELD_NOMBRE_MATCHS_NULS_BANAL_EXTERIEUR, 0);
			setResourceEquipe(equipe.toString(), TABLE_FIELD_NOMBRE_MATCHS_NULS_IMPORTANT, 0);
			setResourceEquipe(equipe.toString(), TABLE_FIELD_NOMBRE_VICTOIRES_CLASSEMENT_INF, 0);
			setResourceEquipe(equipe.toString(), TABLE_FIELD_NOMBRE_VICTOIRES_CLASSEMENT_INF_DOMICILE, 0);
			setResourceEquipe(equipe.toString(), TABLE_FIELD_NOMBRE_VICTOIRES_CLASSEMENT_INF_EXTERIEUR, 0);
			setResourceEquipe(equipe.toString(), TABLE_FIELD_NOMBRE_VICTOIRES_CLASSEMENT_SUP, 0);
			setResourceEquipe(equipe.toString(), TABLE_FIELD_NOMBRE_VICTOIRES_CLASSEMENT_SUP_DOMICILE, 0);
			setResourceEquipe(equipe.toString(), TABLE_FIELD_NOMBRE_VICTOIRES_CLASSEMENT_SUP_EXTERIEUR, 0);
			setResourceEquipe(equipe.toString(), TABLE_FIELD_NOMBRE_VICTOIRES_IMPORTANT_DOMICILE, 0);
			setResourceEquipe(equipe.toString(), TABLE_FIELD_NOMBRE_VICTOIRES_IMPORTANT_EXTERIEUR, 0);
			setResourceEquipe(equipe.toString(), TABLE_FIELD_NOMBRE_VICTOIRES_BANAL, 0);
			setResourceEquipe(equipe.toString(), TABLE_FIELD_NOMBRE_VICTOIRES_BANAL_DOMICILE, 0);
			setResourceEquipe(equipe.toString(), TABLE_FIELD_NOMBRE_VICTOIRES_BANAL_EXTERIEUR, 0);
			setResourceEquipe(equipe.toString(), TABLE_FIELD_NOMBRE_VICTOIRES_IMPORTANT, 0);
			setResourceEquipe(equipe.toString(), TABLE_FIELD_SERIE_D_EN_COURS, 0);
			setResourceEquipe(equipe.toString(), TABLE_FIELD_SERIE_D_EN_COURS_DOMICILE, 0);
			setResourceEquipe(equipe.toString(), TABLE_FIELD_SERIE_D_EN_COURS_EXTERIEUR, 0);
			setResourceEquipe(equipe.toString(), TABLE_FIELD_SERIE_N_EN_COURS, 0);
			setResourceEquipe(equipe.toString(), TABLE_FIELD_SERIE_N_EN_COURS_DOMICILE, 0);
			setResourceEquipe(equipe.toString(), TABLE_FIELD_SERIE_N_EN_COURS_EXTERIEUR, 0);
			setResourceEquipe(equipe.toString(), TABLE_FIELD_SERIE_V_EN_COURS, 0);
			setResourceEquipe(equipe.toString(), TABLE_FIELD_SERIE_V_EN_COURS_DOMICILE, 0);
			setResourceEquipe(equipe.toString(), TABLE_FIELD_SERIE_V_EN_COURS_EXTERIEUR, 0);
			
		}
	}

	public static IStorageResource getResourceEquipe(String surnom) throws VdocHelperException {

		Map<String, Object> equalsConstraints = new HashMap<String, Object>();
		equalsConstraints.put("surnom", surnom);

		IDirectoryModule dm = Modules.getDirectoryModule();
		IProjectModule pm = Modules.getProjectModule();
		IWorkflowModule wm = Modules.getWorkflowModule();

		IStorageResource resourceEquipe = VdocHelper.getDataUniverseResource(dm, pm, wm, "DefaultOrganization",
				"ApplicationLigue1", "ReservoirDeDonneesEquipes", "tableEquipes", equalsConstraints);
		if (resourceEquipe != null) {
			return resourceEquipe;
		}
		Modules.releaseModule(wm);
		Modules.releaseModule(pm);
		Modules.releaseModule(dm);
		return null;
	}

	public static void setResourceEquipe(String surnom, String statAModifier, Object newValue)
			throws VdocHelperException {

		Map<String, Object> equalsConstraints = new HashMap<String, Object>();
		equalsConstraints.put("surnom", surnom);

		IDirectoryModule dm = Modules.getDirectoryModule();
		IProjectModule pm = Modules.getProjectModule();
		IWorkflowModule wm = Modules.getWorkflowModule();
		IContext iContext = wm.getLoggedOnUserContext();

		IStorageResource resourceEquipe = VdocHelper.getDataUniverseResource(dm, pm, wm, "DefaultOrganization",
				"ApplicationLigue1", "ReservoirDeDonneesEquipes", "tableEquipes", equalsConstraints);
		if (resourceEquipe != null) {
			resourceEquipe.setValue(statAModifier, newValue);
			resourceEquipe.save(iContext);
		}
		Modules.releaseModule(wm);
		Modules.releaseModule(pm);
		Modules.releaseModule(dm);
	}

	public static String getStatString(String surnom, String stat) throws VdocHelperException {

		IStorageResource resourceEquipe = getResourceEquipe(surnom);

		if (resourceEquipe != null) {
			String classement = (String) resourceEquipe.getValue(stat);
			if (classement != null) {
				return classement;
			}
		}
		return null;
	}

	public static int getStatInt(String surnom, String stat) throws VdocHelperException {

		IStorageResource resourceEquipe = getResourceEquipe(surnom);

		if (resourceEquipe != null) {
			Long nbMatchsJoues = (Long) resourceEquipe.getValue(stat);
			if (nbMatchsJoues.compareTo(0L) == 0 || nbMatchsJoues.equals(null)
					|| nbMatchsJoues.toString().equals(null)) {
				return 0;
			}
			return nbMatchsJoues.intValue();
		}
		return 0;
	}

	public static void setNombreMatchsJouesPlusUn(String surnom) throws VdocHelperException {

		setResourceEquipe(surnom, TABLE_FIELD_NOMBRE_MATCHS_JOUES, getStatInt(surnom, TABLE_FIELD_NOMBRE_MATCHS_JOUES) + 1);

	}

	public static boolean setResultat(String equipe1, String equipe2, boolean e1mieuxClassee, boolean importantE1,
			boolean importantE2, boolean victoireE1, boolean matchNul, boolean victoireE2) throws VdocHelperException {

		// Les deux équipes saisies évoluent en Ligue 1
		// Elles jouent toutes les deux un match
		setNombreMatchsJouesPlusUn(equipe1);
		setNombreMatchsJouesPlusUn(equipe2);

		// E1 joue à domicile
		// E2 joue à l'extérieur
		setNombreMatchsJouesDomicilePlusUn(equipe1);
		setNombreMatchsJouesExterieurPlusUn(equipe2);

		// si E1 est mieux classée :
		// E1 joue contre equipe moins bien classée
		// E2 joue contre equipe mieux classée
		if (e1mieuxClassee) {
			setNombreMatchsJouesContreEquipeMieuxClasseePlusUn(equipe2);
			setNombreMatchsJouesContreEquipeMoinsBienClasseePlusUn(equipe1);
			setNombreMatchsJouesContreEquipeMieuxClasseeExterieurPlusUn(equipe2);
			setNombreMatchsJouesContreEquipeMoinsBienClasseeDomicilePlusUn(equipe1);
		}
		// Si E2 est mieux classée :
		// E1 joue contre une équipe mieux classée
		// E2 joue contre une équipe moins bien classée
		else {
			setNombreMatchsJouesContreEquipeMieuxClasseePlusUn(equipe1);
			setNombreMatchsJouesContreEquipeMoinsBienClasseePlusUn(equipe2);
			setNombreMatchsJouesContreEquipeMieuxClasseeDomicilePlusUn(equipe1);
			setNombreMatchsJouesContreEquipeMoinsBienClasseeExterieurPlusUn(equipe2);
		}

		// Si c'est un match important pour E1 :
		// E1 joue un match important
		// E1 joue un match important à domicile
		if (importantE1) {
			setNombreMatchsJouesImportantsPlusUn(equipe1);
			setNombreMatchsJouesImportantsDomicilePlusUn(equipe1);
		}
		// Si c'est un match banal pour E1 :
		// E1 joue un match banal
		// E1 joue un match banal à domicile
		else {
			setNombreMatchsJouesBanalPlusUn(equipe1);
			setNombreMatchsJouesBanalDomicilePlusUn(equipe1);
		}

		// Si c'est un match important pour E2 :
		// E2 joue un match important
		// E2 joue un match important à l'extérieur
		if (importantE2) {
			setNombreMatchsJouesImportantsPlusUn(equipe2);
			setNombreMatchsJouesImportantsExterieurPlusUn(equipe2);
		}
		// Si c'est un match banal pour E2 :
		// E2 joue un match banal
		// E2 joue un match banal à l'extérieur
		else {
			setNombreMatchsJouesBanalPlusUn(equipe2);
			setNombreMatchsJouesBanalExterieurPlusUn(equipe2);
		}

		// Si E1 gagne :
		// ajout 3 points aux classements domicile et général
		// gérer les séries des deux équipes (Dom, Ext, Gen)
		// E1 nombre de matchs gagnés +1 Dom et Gen
		// E2 nombre de matchs perdus +1 Ext et Gen
		if (victoireE1) {
			add3points(equipe1);
			add3pointsDomicile(equipe1);
			// gère les VNDVV, effectue un roulement
			addSerieEnCours(equipe1, "V");
			addSerieEnCours(equipe2, "D");
			addSerieEnCoursDomicile(equipe1, "V");
			addSerieEnCoursExterieur(equipe2, "D");
			// gere les totaux V:3 N:1 D:1
			setSerieVEnCours(equipe1);
			setSerieDEnCours(equipe2);
			setSerieVenCoursDomicile(equipe1);
			setSerieDenCoursExterieur(equipe2);
			setNombreMatchsGagnesPlusUn(equipe1);
			setNombreMatchsGagnesDomicilePlusUn(equipe1);
			setNombreMatchsPerdusPlusUn(equipe2);
			setNombreMatchsPerdusExterieurPlusUn(equipe2);

			// Si E1 gagne et est mieux classée :
			// E1 nombre matchs gagnés contre moins bien classée +1 gen et dom
			// E2 nombre matchs perdus contre mieux classée +1 gen et ext
			if (e1mieuxClassee) {
				setNombreMatchsGagnesContreEquipeMoinsBienClasseePlusUn(equipe1);
				setNombreMatchsPerdusContreEquipeMieuxClasseePlusUn(equipe2);
				setNombreMatchsGagnesContreEquipeMoinsBienClasseeDomicilePlusUn(equipe1);
				setNombreMatchsPerdusContreEquipeMieuxClasseeExterieurPlusUn(equipe2);
			}
			// Si E1 gagne et est moins bien classée :
			// E1 nombre matchs gagnés contre mieux classée +1 gen et dom
			// E2 nombre matchs perdus contre moins bien classée +1 gen et ext
			else {
				setNombreMatchsGagnesContreEquipeMieuxClasseePlusUn(equipe1);
				setNombreMatchsPerdusContreEquipeMoinsBienClasseePlusUn(equipe2);
				setNombreMatchsGagnesContreEquipeMieuxClasseeDomicilePlusUn(equipe1);
				setNombreMatchsPerdusContreEquipeMoinsBienClasseeExterieurPlusUn(equipe2);
			}

			// Si E1 gagne et le match est important pour lui :
			// E1 nombre de matchs gagnés importants +1 gen et dom
			if (importantE1) {
				setNombreMatchsGagnesImportantsPlusUn(equipe1);
				setNombreMatchsGagnesImportantsDomicilePlusUn(equipe1);
			}
			// Si E1 gagne et le match est banal pour lui:
			// E1 nombre de matchs gagnés banal +1 gen et dom
			else {
				setNombreMatchsGagnesBanalPlusUn(equipe1);
				setNombreMatchsGagnesBanalDomicilePlusUn(equipe1);
			}

			// Si E1 gagne et le match est important pour E2:
			// E2 nombre de matchs perdus importants +1 gen et ext
			if (importantE2) {
				setNombreMatchsPerdusImportantsPlusUn(equipe2);
				setNombreMatchsPerdusImportantsExterieurPlusUn(equipe2);
			}
			// Si E1 gagne et le match est banal pour E2 :
			// E2 nombre de matchs perdus banal +1 gen et ext
			else {
				setNombreMatchsPerdusBanalPlusUn(equipe2);
				setNombreMatchsPerdusBanalExterieurPlusUn(equipe2);
			}
		}
		// E1 n'a pas gagné
		else {
			if(victoireE2) {
				add3points(equipe2);
				add3pointsExterieur(equipe2);
				// gère les VNDVV
				addSerieEnCours(equipe1, "D");
				addSerieEnCours(equipe2, "V");
				addSerieEnCoursDomicile(equipe1, "D");
				addSerieEnCoursExterieur(equipe2, "V");
				// gere les V:3 N:1 D:1
				setSerieDEnCours(equipe1);
				setSerieVEnCours(equipe2);
				setSerieDenCoursDomicile(equipe1);
				setSerieVenCoursExterieur(equipe2);
				setNombreMatchsGagnesPlusUn(equipe2);
				setNombreMatchsGagnesExterieurPlusUn(equipe2);
				setNombreMatchsPerdusPlusUn(equipe1);
				setNombreMatchsPerdusDomicilePlusUn(equipe1);

				// Si E2 gagne et est mieux classée :
				// E2 nombre matchs gagnés contre moins bien classée +1 gen et ext
				// E1 nombre matchs perdus contre mieux classée +1 gen et dom
				if (!e1mieuxClassee) {
					setNombreMatchsGagnesContreEquipeMoinsBienClasseePlusUn(equipe2);
					setNombreMatchsPerdusContreEquipeMieuxClasseePlusUn(equipe1);
					setNombreMatchsGagnesContreEquipeMoinsBienClasseeExterieurPlusUn(equipe2);
					setNombreMatchsPerdusContreEquipeMieuxClasseeDomicilePlusUn(equipe1);
				}
				// Si E2 gagne et est moins bien classée :
				// E2 nombre matchs gagnés contre mieux classée +1 gen et ext
				// E1 nombre matchs perdus contre moins bien classée +1 gen et dom
				else {
					setNombreMatchsGagnesContreEquipeMieuxClasseePlusUn(equipe2);
					setNombreMatchsPerdusContreEquipeMoinsBienClasseePlusUn(equipe1);
					setNombreMatchsGagnesContreEquipeMieuxClasseeExterieurPlusUn(equipe2);
					setNombreMatchsPerdusContreEquipeMoinsBienClasseeDomicilePlusUn(equipe1);
				}

				// Si E2 gagne et le match est important pour lui :
				// E2 nombre de matchs gagnés importants +1 gen et ext
				if (importantE2) {
					setNombreMatchsGagnesImportantsPlusUn(equipe2);
					setNombreMatchsGagnesImportantsExterieurPlusUn(equipe2);
				}
				// Si E2 gagne et le match est banal pour lui:
				// E2 nombre de matchs gagnés banal +1 gen et ext
				else {
					setNombreMatchsGagnesBanalPlusUn(equipe2);
					setNombreMatchsGagnesBanalExterieurPlusUn(equipe2);
				}

				// Si E2 gagne et le match est important pour E1:
				// E1 nombre de matchs perdus importants +1 gen et dom
				if (importantE1) {
					setNombreMatchsPerdusImportantsPlusUn(equipe1);
					setNombreMatchsPerdusImportantsDomicilePlusUn(equipe1);
				}
				// Si E2 gagne et le match est banal pour E1 :
				// E1 nombre de matchs perdus banal +1 gen et dom
				else {
					setNombreMatchsPerdusBanalPlusUn(equipe1);
					setNombreMatchsPerdusBanalDomicilePlusUn(equipe1);
				}
			}
			// Match nul
			else {
				add1point(equipe1);
				add1point(equipe2);
				add1pointDomicile(equipe1);
				add1pointExterieur(equipe2);
				// gère les VNDVV
				addSerieEnCours(equipe1, "N");
				addSerieEnCours(equipe2, "N");
				addSerieEnCoursDomicile(equipe1, "N");
				addSerieEnCoursExterieur(equipe2, "N");
				// gere les V:3 N:1 D:1
				setSerieNEnCours(equipe1);
				setSerieNEnCours(equipe2);
				setSerieNenCoursDomicile(equipe1);
				setSerieNenCoursExterieur(equipe2);
				setNombreMatchsNulsPlusUn(equipe1);
				setNombreMatchsNulsPlusUn(equipe2);
				setNombreMatchsNulsExterieurPlusUn(equipe2);
				setNombreMatchsNulsDomicilePlusUn(equipe1);

				// Si E2 est mieux classée :
				// E2 nombre matchs nuls contre moins bien classée +1 gen et ext
				// E1 nombre matchs nuls contre mieux classée +1 gen et dom
				if (!e1mieuxClassee) {
					setNombreMatchsNulsContreEquipeMoinsBienClasseePlusUn(equipe2);
					setNombreMatchsNulsContreEquipeMieuxClasseePlusUn(equipe1);
					setNombreMatchsNulsContreEquipeMoinsBienClasseeExterieurPlusUn(equipe2);
					setNombreMatchsNulsContreEquipeMieuxClasseeDomicilePlusUn(equipe1);
				}
				// Si E2 est moins bien classée :
				// E2 nombre matchs nuls contre mieux classée +1 gen et ext
				// E1 nombre matchs nuls contre moins bien classée +1 gen et dom
				else {
					setNombreMatchsNulsContreEquipeMieuxClasseePlusUn(equipe2);
					setNombreMatchsNulsContreEquipeMoinsBienClasseePlusUn(equipe1);
					setNombreMatchsNulsContreEquipeMieuxClasseeExterieurPlusUn(equipe2);
					setNombreMatchsNulsContreEquipeMoinsBienClasseeDomicilePlusUn(equipe1);
				}

				// Si le match est important pour E2 :
				// E2 nombre de matchs nuls importants +1 gen et ext
				if (importantE2) {
					setNombreMatchsNulsImportantsPlusUn(equipe2);
					setNombreMatchsNulsImportantsExterieurPlusUn(equipe2);
				}
				// Si le match est banal pour E2:
				// E2 nombre de matchs nuls banal +1 gen et ext
				else {
					setNombreMatchsNulsBanalPlusUn(equipe2);
					setNombreMatchsNulsBanalExterieurPlusUn(equipe2);
				}

				// Si le match est important pour E1:
				// E1 nombre de matchs nuls importants +1 gen et dom
				if (importantE1) {
					setNombreMatchsNulsImportantsPlusUn(equipe1);
					setNombreMatchsNulsImportantsDomicilePlusUn(equipe1);
				}
				// Si le match est banal pour E1 :
				// E1 nombre de matchs nuls banal +1 gen et dom
				else {
					setNombreMatchsNulsBanalPlusUn(equipe1);
					setNombreMatchsNulsBanalDomicilePlusUn(equipe1);
				}
			}
			
		}
		boolean verifE1 = verif(equipe1);
		boolean verifE2 = verif(equipe2);
		
		return (verifE1 && verifE2);
	}
	
	// LES SERIES
	
	/**
	 * Prend en compte le dernier résultat et modifie les 5 derniers résultats de l'équipe
	 * exemple : "VVDVV" + "N" ==> "VDVVN"
	 * on décale vers la gauche
	 * @param equipe
	 * @throws VdocHelperException 
	 */
	private static void addSerieEnCoursExterieur(String equipe, String string) throws VdocHelperException {

		setResourceEquipe(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_5_EXTERIEUR, getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_4_EXTERIEUR));
		setResourceEquipe(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_4_EXTERIEUR, getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_3_EXTERIEUR));
		setResourceEquipe(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_3_EXTERIEUR, getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_2_EXTERIEUR));
		setResourceEquipe(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_2_EXTERIEUR, getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_1_EXTERIEUR));
		setResourceEquipe(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_1_EXTERIEUR, string);
	}

	private static void addSerieEnCoursDomicile(String equipe, String dernierResultat) throws VdocHelperException {
		
		setResourceEquipe(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_5_DOMICILE, getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_4_DOMICILE));
		setResourceEquipe(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_4_DOMICILE, getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_3_DOMICILE));
		setResourceEquipe(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_3_DOMICILE, getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_2_DOMICILE));
		setResourceEquipe(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_2_DOMICILE, getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_1_DOMICILE));
		setResourceEquipe(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_1_DOMICILE, dernierResultat);

	}

	private static void addSerieEnCours(String equipe, String string) throws VdocHelperException {
		
		setResourceEquipe(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_5, getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_4));
		setResourceEquipe(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_4, getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_3));
		setResourceEquipe(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_3, getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_2));
		setResourceEquipe(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_2, getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_1));
		setResourceEquipe(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_1, string);

	}

	/**
	 * Analyse les 5 derniers résultats et compte le nombre de nuls
	 * Exemple : "VVDND" --> 1
	 * Cette méthode est appelée après modification d'un résultat de match
	 * @param equipe
	 */
	private static void setSerieNEnCours(String equipe) throws VdocHelperException {
		
		int countNombreMatchsNulsSurLes5DerniersMatchs = 0;
		
		if(getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_1).equals("N"))
			countNombreMatchsNulsSurLes5DerniersMatchs ++;
		if(getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_2).equals("N"))
			countNombreMatchsNulsSurLes5DerniersMatchs ++;
		if(getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_3).equals("N"))
			countNombreMatchsNulsSurLes5DerniersMatchs ++;
		if(getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_4).equals("N"))
			countNombreMatchsNulsSurLes5DerniersMatchs ++;
		if(getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_5).equals("N"))
			countNombreMatchsNulsSurLes5DerniersMatchs ++;
		
		setResourceEquipe(equipe, TABLE_FIELD_SERIE_N_EN_COURS, countNombreMatchsNulsSurLes5DerniersMatchs);
		
	}

	private static void setSerieDEnCours(String equipe) throws VdocHelperException {
		
		int countNombreDefaitesSurLes5DerniersMatchs = 0;
		
		if(getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_1).equals("D"))
			countNombreDefaitesSurLes5DerniersMatchs ++;
		if(getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_2).equals("D"))
			countNombreDefaitesSurLes5DerniersMatchs ++;
		if(getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_3).equals("D"))
			countNombreDefaitesSurLes5DerniersMatchs ++;
		if(getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_4).equals("D"))
			countNombreDefaitesSurLes5DerniersMatchs ++;
		if(getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_5).equals("D"))
			countNombreDefaitesSurLes5DerniersMatchs ++;
		
		setResourceEquipe(equipe, TABLE_FIELD_SERIE_D_EN_COURS, countNombreDefaitesSurLes5DerniersMatchs);
	}

	private static void setSerieVEnCours(String equipe) throws VdocHelperException {
		
		int countNombreVictoiresSurLes5DerniersMatchs = 0;
		
		if(getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_1).equals("V"))
			countNombreVictoiresSurLes5DerniersMatchs ++;
		if(getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_2).equals("V"))
			countNombreVictoiresSurLes5DerniersMatchs ++;
		if(getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_3).equals("V"))
			countNombreVictoiresSurLes5DerniersMatchs ++;
		if(getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_4).equals("V"))
			countNombreVictoiresSurLes5DerniersMatchs ++;
		if(getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_5).equals("V"))
			countNombreVictoiresSurLes5DerniersMatchs ++;
		
		setResourceEquipe(equipe, TABLE_FIELD_SERIE_V_EN_COURS, countNombreVictoiresSurLes5DerniersMatchs);
		
	}
	
	private static void setSerieNenCoursExterieur(String equipe) throws VdocHelperException {
		
		int countNombreMatchsNulsSurLes5DerniersMatchsExterieurs = 0;
		
		if(getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_1_EXTERIEUR).equals("N"))
			countNombreMatchsNulsSurLes5DerniersMatchsExterieurs ++;
		if(getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_2_EXTERIEUR).equals("N"))
			countNombreMatchsNulsSurLes5DerniersMatchsExterieurs ++;
		if(getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_3_EXTERIEUR).equals("N"))
			countNombreMatchsNulsSurLes5DerniersMatchsExterieurs ++;
		if(getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_4_EXTERIEUR).equals("N"))
			countNombreMatchsNulsSurLes5DerniersMatchsExterieurs ++;
		if(getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_5_EXTERIEUR).equals("N"))
			countNombreMatchsNulsSurLes5DerniersMatchsExterieurs ++;
		
		setResourceEquipe(equipe, TABLE_FIELD_SERIE_N_EN_COURS_EXTERIEUR, countNombreMatchsNulsSurLes5DerniersMatchsExterieurs);
		
	}

	private static void setSerieNenCoursDomicile(String equipe) throws VdocHelperException {
		
		int countNombreMatchsNulsSurLes5DerniersMatchsDomicile = 0;
		
		if(getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_1_DOMICILE).equals("N"))
			countNombreMatchsNulsSurLes5DerniersMatchsDomicile ++;
		if(getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_2_DOMICILE).equals("N"))
			countNombreMatchsNulsSurLes5DerniersMatchsDomicile ++;
		if(getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_3_DOMICILE).equals("N"))
			countNombreMatchsNulsSurLes5DerniersMatchsDomicile ++;
		if(getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_4_DOMICILE).equals("N"))
			countNombreMatchsNulsSurLes5DerniersMatchsDomicile ++;
		if(getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_5_DOMICILE).equals("N"))
			countNombreMatchsNulsSurLes5DerniersMatchsDomicile ++;
		
		setResourceEquipe(equipe, TABLE_FIELD_SERIE_N_EN_COURS_DOMICILE, countNombreMatchsNulsSurLes5DerniersMatchsDomicile);
		
	}
	
	private static void setSerieVenCoursExterieur(String equipe) throws VdocHelperException {
		
		int countNombreVictoiresSurLes5DerniersMatchsExterieur = 0;
		
		if(getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_1_EXTERIEUR).equals("V"))
			countNombreVictoiresSurLes5DerniersMatchsExterieur ++;
		if(getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_2_EXTERIEUR).equals("V"))
			countNombreVictoiresSurLes5DerniersMatchsExterieur ++;
		if(getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_3_EXTERIEUR).equals("V"))
			countNombreVictoiresSurLes5DerniersMatchsExterieur ++;
		if(getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_4_EXTERIEUR).equals("V"))
			countNombreVictoiresSurLes5DerniersMatchsExterieur ++;
		if(getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_5_EXTERIEUR).equals("V"))
			countNombreVictoiresSurLes5DerniersMatchsExterieur ++;
		
		setResourceEquipe(equipe, TABLE_FIELD_SERIE_V_EN_COURS_EXTERIEUR, countNombreVictoiresSurLes5DerniersMatchsExterieur);
		
	}

	private static void setSerieDenCoursDomicile(String equipe) throws VdocHelperException {
		
		int countNombreDefaiteSurLes5DerniersMatchsDomicile = 0;
		
		if(getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_1_DOMICILE).equals("D"))
			countNombreDefaiteSurLes5DerniersMatchsDomicile ++;
		if(getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_2_DOMICILE).equals("D"))
			countNombreDefaiteSurLes5DerniersMatchsDomicile ++;
		if(getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_3_DOMICILE).equals("D"))
			countNombreDefaiteSurLes5DerniersMatchsDomicile ++;
		if(getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_4_DOMICILE).equals("D"))
			countNombreDefaiteSurLes5DerniersMatchsDomicile ++;
		if(getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_5_DOMICILE).equals("D"))
			countNombreDefaiteSurLes5DerniersMatchsDomicile ++;
		
		setResourceEquipe(equipe, TABLE_FIELD_SERIE_D_EN_COURS_DOMICILE, countNombreDefaiteSurLes5DerniersMatchsDomicile);
		
	}
	
	private static void setSerieDenCoursExterieur(String equipe) throws VdocHelperException {
		
		int countNombreDefaitesSurLes5DerniersMatchsExterieurs = 0;
		
		if(getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_1_EXTERIEUR).equals("D"))
			countNombreDefaitesSurLes5DerniersMatchsExterieurs ++;
		if(getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_2_EXTERIEUR).equals("D"))
			countNombreDefaitesSurLes5DerniersMatchsExterieurs ++;
		if(getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_3_EXTERIEUR).equals("D"))
			countNombreDefaitesSurLes5DerniersMatchsExterieurs ++;
		if(getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_4_EXTERIEUR).equals("D"))
			countNombreDefaitesSurLes5DerniersMatchsExterieurs ++;
		if(getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_5_EXTERIEUR).equals("D"))
			countNombreDefaitesSurLes5DerniersMatchsExterieurs ++;
		
		setResourceEquipe(equipe, TABLE_FIELD_SERIE_D_EN_COURS_EXTERIEUR, countNombreDefaitesSurLes5DerniersMatchsExterieurs);

	}

	private static void setSerieVenCoursDomicile(String equipe) throws VdocHelperException {
		
		int countNombreVictoiresSurLes5DerniersMatchsDomicile = 0;
		
		if(getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_1_DOMICILE).equals("V"))
			countNombreVictoiresSurLes5DerniersMatchsDomicile ++;
		if(getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_2_DOMICILE).equals("V"))
			countNombreVictoiresSurLes5DerniersMatchsDomicile ++;
		if(getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_3_DOMICILE).equals("V"))
			countNombreVictoiresSurLes5DerniersMatchsDomicile ++;
		if(getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_4_DOMICILE).equals("V"))
			countNombreVictoiresSurLes5DerniersMatchsDomicile ++;
		if(getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_5_DOMICILE).equals("V"))
			countNombreVictoiresSurLes5DerniersMatchsDomicile ++;
		
		setResourceEquipe(equipe, TABLE_FIELD_SERIE_V_EN_COURS_DOMICILE, countNombreVictoiresSurLes5DerniersMatchsDomicile);

	}
	
	// LES SET NOMBRES +1 ET ADDPOINTS
	
	private static void setNombreMatchsJouesContreEquipeMoinsBienClasseeExterieurPlusUn(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_MATCHS_JOUES_CLASSEMENT_INF_EXTERIEUR, getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_JOUES_CLASSEMENT_INF_EXTERIEUR) + 1);
	}

	private static void setNombreMatchsJouesContreEquipeMieuxClasseeDomicilePlusUn(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_MATCHS_JOUES_CLASSEMENT_SUP_DOMICILE, getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_JOUES_CLASSEMENT_SUP_DOMICILE) + 1);
	}

	private static void setNombreMatchsJouesContreEquipeMoinsBienClasseeDomicilePlusUn(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_MATCHS_JOUES_CLASSEMENT_INF_DOMICILE, getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_JOUES_CLASSEMENT_INF_DOMICILE) + 1);
	}

	private static void setNombreMatchsJouesContreEquipeMieuxClasseeExterieurPlusUn(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_MATCHS_JOUES_CLASSEMENT_SUP_EXTERIEUR, getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_JOUES_CLASSEMENT_SUP_EXTERIEUR) + 1);
	}

	private static void setNombreMatchsNulsBanalDomicilePlusUn(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS_BANAL_DOMICILE, getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS_BANAL_DOMICILE) + 1);
	}

	private static void setNombreMatchsNulsImportantsDomicilePlusUn(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS_IMPORTANT_DOMICILE, getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS_IMPORTANT_DOMICILE) + 1);
	}

	private static void setNombreMatchsNulsBanalExterieurPlusUn(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS_BANAL_EXTERIEUR, getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS_BANAL_EXTERIEUR) + 1);
	}
	
	private static void setNombreMatchsNulsBanalPlusUn(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS_BANAL, getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS_BANAL) + 1);		
	}

	private static void setNombreMatchsNulsImportantsExterieurPlusUn(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS_IMPORTANT_EXTERIEUR, getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS_IMPORTANT_EXTERIEUR) + 1);
	}

	private static void setNombreMatchsNulsImportantsPlusUn(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS_IMPORTANT, getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS_IMPORTANT) + 1);	
	}

	private static void setNombreMatchsNulsContreEquipeMoinsBienClasseeDomicilePlusUn(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS_CLASSEMENT_INF_DOMICILE, getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS_CLASSEMENT_INF_DOMICILE) + 1);	
	}

	private static void setNombreMatchsNulsContreEquipeMieuxClasseeExterieurPlusUn(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS_CLASSEMENT_SUP_EXTERIEUR, getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS_CLASSEMENT_SUP_EXTERIEUR) + 1);
	}

	private static void setNombreMatchsNulsContreEquipeMieuxClasseeDomicilePlusUn(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS_CLASSEMENT_SUP_DOMICILE, getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS_CLASSEMENT_SUP_DOMICILE) + 1);
	}

	private static void setNombreMatchsNulsContreEquipeMoinsBienClasseeExterieurPlusUn(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS_CLASSEMENT_INF_EXTERIEUR, getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS_CLASSEMENT_INF_EXTERIEUR) + 1);
	}

	private static void setNombreMatchsNulsContreEquipeMieuxClasseePlusUn(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS_CLASSEMENT_SUP, getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS_CLASSEMENT_SUP) + 1);		
	}

	private static void setNombreMatchsNulsContreEquipeMoinsBienClasseePlusUn(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS_CLASSEMENT_INF, getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS_CLASSEMENT_INF) + 1);
	}

	private static void setNombreMatchsNulsDomicilePlusUn(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS_DOMICILE, getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS_DOMICILE) + 1);	
	}

	private static void setNombreMatchsNulsExterieurPlusUn(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS_EXTERIEUR, getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS_EXTERIEUR) + 1);	
	}

	private static void setNombreMatchsNulsPlusUn(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS, getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS) + 1);	
	}
	
	private static void add1pointExterieur(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_POINTS_EXTERIEUR, getStatInt(equipe, TABLE_FIELD_NOMBRE_POINTS_EXTERIEUR) + 1);	
	}

	private static void add1pointDomicile(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_POINTS_DOMICILE, getStatInt(equipe, TABLE_FIELD_NOMBRE_POINTS_DOMICILE) + 1);	
	}

	private static void add1point(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_POINTS, getStatInt(equipe, TABLE_FIELD_NOMBRE_POINTS) + 1);	
	}

	private static void setNombreMatchsPerdusBanalDomicilePlusUn(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_BANAL_DOMICILE, getStatInt(equipe, TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_BANAL_DOMICILE) + 1);	
	}

	private static void setNombreMatchsPerdusImportantsDomicilePlusUn(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_IMPORTANT_DOMICILE, getStatInt(equipe, TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_IMPORTANT_DOMICILE) + 1);	
	}

	private static void setNombreMatchsGagnesBanalExterieurPlusUn(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_VICTOIRES_BANAL_EXTERIEUR, getStatInt(equipe, TABLE_FIELD_NOMBRE_VICTOIRES_BANAL_EXTERIEUR) + 1);	
	}

	private static void setNombreMatchsGagnesImportantsExterieurPlusUn(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_VICTOIRES_IMPORTANT_EXTERIEUR, getStatInt(equipe, TABLE_FIELD_NOMBRE_VICTOIRES_IMPORTANT_EXTERIEUR) + 1);
		
	}

	private static void setNombreMatchsPerdusContreEquipeMoinsBienClasseeDomicilePlusUn(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_CLASSEMENT_INFERIEUR_DOMICILE, getStatInt(equipe, TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_CLASSEMENT_INFERIEUR_DOMICILE) + 1);	
	}

	private static void setNombreMatchsGagnesContreEquipeMieuxClasseeExterieurPlusUn(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_VICTOIRES_CLASSEMENT_SUP_EXTERIEUR, getStatInt(equipe, TABLE_FIELD_NOMBRE_VICTOIRES_CLASSEMENT_SUP_EXTERIEUR) + 1);
		
	}

	private static void setNombreMatchsPerdusContreEquipeMieuxClasseeDomicilePlusUn(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_CLASSEMENT_SUPERIEUR_DOMICILE, getStatInt(equipe, TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_CLASSEMENT_SUPERIEUR_DOMICILE) + 1);	
	}

	private static void setNombreMatchsGagnesContreEquipeMoinsBienClasseeExterieurPlusUn(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_VICTOIRES_CLASSEMENT_INF_EXTERIEUR, getStatInt(equipe, TABLE_FIELD_NOMBRE_VICTOIRES_CLASSEMENT_INF_EXTERIEUR) + 1);	
	}

	private static void setNombreMatchsPerdusDomicilePlusUn(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_MATCHS_PERDUS_DOMICILE, getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_PERDUS_DOMICILE) + 1);
	}

	private static void setNombreMatchsGagnesExterieurPlusUn(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_MATCHS_GAGNES_EXTERIEUR, getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_GAGNES_EXTERIEUR) + 1);
	}

	private static void add3pointsExterieur(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_POINTS_EXTERIEUR, getStatInt(equipe, TABLE_FIELD_NOMBRE_POINTS_EXTERIEUR) + 3);	
	}

	private static void setNombreMatchsPerdusBanalExterieurPlusUn(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_BANAL_EXTERIEUR, getStatInt(equipe, TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_BANAL_EXTERIEUR) + 1);	
	}

	private static void setNombreMatchsPerdusBanalPlusUn(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_BANAL, getStatInt(equipe, TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_BANAL) + 1);	
	}

	private static void setNombreMatchsPerdusImportantsExterieurPlusUn(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_IMPORTANT_EXTERIEUR, getStatInt(equipe, TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_IMPORTANT_EXTERIEUR) + 1);	
	}

	private static void setNombreMatchsPerdusImportantsPlusUn(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_IMPORTANT, getStatInt(equipe, TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_IMPORTANT) + 1);
	}

	private static void setNombreMatchsGagnesBanalDomicilePlusUn(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_VICTOIRES_BANAL_DOMICILE, getStatInt(equipe, TABLE_FIELD_NOMBRE_VICTOIRES_BANAL_DOMICILE) + 1);
	}

	private static void setNombreMatchsGagnesBanalPlusUn(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_VICTOIRES_BANAL, getStatInt(equipe, TABLE_FIELD_NOMBRE_VICTOIRES_BANAL) + 1);
	}

	private static void setNombreMatchsGagnesImportantsDomicilePlusUn(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_VICTOIRES_IMPORTANT_DOMICILE, getStatInt(equipe, TABLE_FIELD_NOMBRE_VICTOIRES_IMPORTANT_DOMICILE) + 1);
	}

	private static void setNombreMatchsGagnesImportantsPlusUn(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_VICTOIRES_IMPORTANT, getStatInt(equipe, TABLE_FIELD_NOMBRE_VICTOIRES_IMPORTANT) + 1);
	}

	private static void setNombreMatchsPerdusContreEquipeMoinsBienClasseeExterieurPlusUn(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_CLASSEMENT_INFERIEUR_EXTERIEUR, getStatInt(equipe, TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_CLASSEMENT_INFERIEUR_EXTERIEUR) + 1);
	}

	private static void setNombreMatchsPerdusContreEquipeMoinsBienClasseePlusUn(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_CLASSEMENT_INFERIEUR, getStatInt(equipe, TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_CLASSEMENT_INFERIEUR) + 1);
	}

	private static void setNombreMatchsGagnesContreEquipeMieuxClasseeDomicilePlusUn(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_VICTOIRES_CLASSEMENT_SUP_DOMICILE, getStatInt(equipe, TABLE_FIELD_NOMBRE_VICTOIRES_CLASSEMENT_SUP_DOMICILE) + 1);
	}

	private static void setNombreMatchsGagnesContreEquipeMieuxClasseePlusUn(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_VICTOIRES_CLASSEMENT_SUP, getStatInt(equipe, TABLE_FIELD_NOMBRE_VICTOIRES_CLASSEMENT_SUP) + 1);
	}

	private static void add3pointsDomicile(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_POINTS_DOMICILE, getStatInt(equipe, TABLE_FIELD_NOMBRE_POINTS_DOMICILE) + 3);
	}

	private static void add3points(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_POINTS, getStatInt(equipe, TABLE_FIELD_NOMBRE_POINTS) + 3);
	}

	private static void setNombreMatchsPerdusContreEquipeMieuxClasseeExterieurPlusUn(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_CLASSEMENT_SUPERIEUR_EXTERIEUR, getStatInt(equipe, TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_CLASSEMENT_SUPERIEUR_EXTERIEUR) + 1);
	}

	private static void setNombreMatchsGagnesContreEquipeMoinsBienClasseeDomicilePlusUn(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_VICTOIRES_CLASSEMENT_INF_DOMICILE, getStatInt(equipe, TABLE_FIELD_NOMBRE_VICTOIRES_CLASSEMENT_INF_DOMICILE) + 1);
	}

	private static void setNombreMatchsPerdusContreEquipeMieuxClasseePlusUn(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_CLASSEMENT_SUPERIEUR, getStatInt(equipe, TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_CLASSEMENT_SUPERIEUR) + 1);
	}

	private static void setNombreMatchsGagnesContreEquipeMoinsBienClasseePlusUn(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_VICTOIRES_CLASSEMENT_INF, getStatInt(equipe, TABLE_FIELD_NOMBRE_VICTOIRES_CLASSEMENT_INF) + 1);
	}

	private static void setNombreMatchsPerdusExterieurPlusUn(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_MATCHS_PERDUS_EXTERIEUR, getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_PERDUS_EXTERIEUR) + 1);
	}

	private static void setNombreMatchsPerdusPlusUn(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_MATCHS_PERDUS, getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_PERDUS) + 1);
	}

	private static void setNombreMatchsGagnesDomicilePlusUn(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_MATCHS_GAGNES_DOMICILE, getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_GAGNES_DOMICILE) + 1);
	}

	private static void setNombreMatchsGagnesPlusUn(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_MATCHS_GAGNES, getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_GAGNES) + 1);
	}

	private static void setNombreMatchsJouesBanalExterieurPlusUn(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_MATCHS_JOUES_BANAL_EXTERIEUR, getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_JOUES_BANAL_EXTERIEUR) + 1);
	}

	private static void setNombreMatchsJouesImportantsExterieurPlusUn(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_MATCHS_JOUES_IMPORTANTS_EXTERIEUR, getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_JOUES_IMPORTANTS_EXTERIEUR) + 1);
	}

	private static void setNombreMatchsJouesBanalDomicilePlusUn(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_MATCHS_JOUES_BANAL_DOMICILE, getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_JOUES_BANAL_DOMICILE) + 1);
	}

	private static void setNombreMatchsJouesBanalPlusUn(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_MATCHS_JOUES_BANAL, getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_JOUES_BANAL) + 1);
	}

	private static void setNombreMatchsJouesImportantsDomicilePlusUn(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_MATCHS_JOUES_IMPORTANTS_DOMICILE, getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_JOUES_IMPORTANTS_DOMICILE) + 1);
	}

	private static void setNombreMatchsJouesImportantsPlusUn(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_MATCHS_JOUES_IMPORTANTS, getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_JOUES_IMPORTANTS) + 1);
	}

	private static void setNombreMatchsJouesContreEquipeMoinsBienClasseePlusUn(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_MATCHS_JOUES_CLASSEMENT_INF, getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_JOUES_CLASSEMENT_INF) + 1);
	}

	private static void setNombreMatchsJouesContreEquipeMieuxClasseePlusUn(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_MATCHS_JOUES_CLASSEMENT_SUP, getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_JOUES_CLASSEMENT_SUP) + 1);
	}

	private static void setNombreMatchsJouesExterieurPlusUn(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_MATCHS_JOUES_EXTERIEUR, getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_JOUES_EXTERIEUR) + 1);
	}

	private static void setNombreMatchsJouesDomicilePlusUn(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_MATCHS_JOUES_DOMICILE, getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_JOUES_DOMICILE) + 1);
	}
	
	public static boolean verifGenerale() throws NumberFormatException, VdocHelperException {
		
		List<EquipesLigue1> equipesDeLigue1 = listerEquipesLigue1DepuisEnumeration();
		
		for(EquipesLigue1 equipe : equipesDeLigue1) {
			if(!verif(equipe.toString()))
				return false;
		}
		return true;	
	}

	public static boolean verif(String equipe) throws NumberFormatException, VdocHelperException {
		
		if(getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_GAGNES)*3 + getStatInt(equipe,TABLE_FIELD_NOMBRE_MATCHS_NULS) != getStatInt(equipe, TABLE_FIELD_NOMBRE_POINTS))
			return false;
		if(getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_GAGNES) + getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS) + getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_PERDUS) != getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_JOUES))
			return false;
		if(getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_GAGNES_DOMICILE) + getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_GAGNES_EXTERIEUR) != getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_GAGNES))
			return false;
		if(getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_GAGNES_DOMICILE)*3 + getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS_DOMICILE) != getStatInt(equipe, TABLE_FIELD_NOMBRE_POINTS_DOMICILE))
			return false;
		if(getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_GAGNES_EXTERIEUR)*3 + getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS_EXTERIEUR) != getStatInt(equipe, TABLE_FIELD_NOMBRE_POINTS_EXTERIEUR))
			return false;
		if(getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_JOUES_DOMICILE) + getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_JOUES_EXTERIEUR) != getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_JOUES))
			return false;
		if(getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS_DOMICILE) + getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS_EXTERIEUR) != getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS))
			return false;
		if(getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_PERDUS_DOMICILE) + getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_PERDUS_EXTERIEUR) != getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_PERDUS))
			return false;
		if(getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_GAGNES_DOMICILE) + getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS_DOMICILE) + getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_PERDUS_DOMICILE) != getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_JOUES_DOMICILE))
			return false;
		if(getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_GAGNES_EXTERIEUR) + getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS_EXTERIEUR) + getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_PERDUS_EXTERIEUR) != getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_JOUES_EXTERIEUR))
			return false;
		if(!verifForme(equipe))
			return false;
		if(getStatInt(equipe, TABLE_FIELD_NOMBRE_VICTOIRES_CLASSEMENT_SUP) + getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS_CLASSEMENT_SUP) + getStatInt(equipe, TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_CLASSEMENT_SUPERIEUR) != getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_JOUES_CLASSEMENT_SUP))
			return false;
		if(getStatInt(equipe, TABLE_FIELD_NOMBRE_VICTOIRES_CLASSEMENT_INF) + getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS_CLASSEMENT_INF) + getStatInt(equipe, TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_CLASSEMENT_INFERIEUR) != getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_JOUES_CLASSEMENT_INF))
			return false;
		if(getStatInt(equipe, TABLE_FIELD_NOMBRE_VICTOIRES_CLASSEMENT_SUP_DOMICILE) + getStatInt(equipe, TABLE_FIELD_NOMBRE_VICTOIRES_CLASSEMENT_SUP_EXTERIEUR) != getStatInt(equipe, TABLE_FIELD_NOMBRE_VICTOIRES_CLASSEMENT_SUP))
			return false;
		if(getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS_CLASSEMENT_SUP_DOMICILE) + getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS_CLASSEMENT_SUP_EXTERIEUR) != getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS_CLASSEMENT_SUP))
			return false;
		if(getStatInt(equipe, TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_CLASSEMENT_SUPERIEUR_DOMICILE) + getStatInt(equipe, TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_CLASSEMENT_SUPERIEUR_EXTERIEUR) != getStatInt(equipe, TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_CLASSEMENT_SUPERIEUR))
			return false;
		if(getStatInt(equipe, TABLE_FIELD_NOMBRE_VICTOIRES_CLASSEMENT_INF_DOMICILE) + getStatInt(equipe, TABLE_FIELD_NOMBRE_VICTOIRES_CLASSEMENT_INF_EXTERIEUR) != getStatInt(equipe, TABLE_FIELD_NOMBRE_VICTOIRES_CLASSEMENT_INF))
			return false;
		if(getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS_CLASSEMENT_INF_DOMICILE) + getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS_CLASSEMENT_INF_EXTERIEUR) != getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS_CLASSEMENT_INF))
			return false;
		if(getStatInt(equipe, TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_CLASSEMENT_INFERIEUR_DOMICILE) + getStatInt(equipe, TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_CLASSEMENT_INFERIEUR_EXTERIEUR) != getStatInt(equipe, TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_CLASSEMENT_INFERIEUR))
			return false;
		if(getStatInt(equipe, TABLE_FIELD_NOMBRE_VICTOIRES_CLASSEMENT_SUP) + getStatInt(equipe, TABLE_FIELD_NOMBRE_VICTOIRES_CLASSEMENT_INF) != getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_GAGNES))
			return false;
		if(getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS_CLASSEMENT_SUP) + getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS_CLASSEMENT_INF) != getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS))
			return false;
		if(getStatInt(equipe, TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_CLASSEMENT_SUPERIEUR) + getStatInt(equipe, TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_CLASSEMENT_INFERIEUR) != getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_PERDUS))
			return false;
		if(getStatInt(equipe, TABLE_FIELD_NOMBRE_VICTOIRES_IMPORTANT) + getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS_IMPORTANT) + getStatInt(equipe, TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_IMPORTANT) != getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_JOUES_IMPORTANTS))
			return false;
		if(getStatInt(equipe, TABLE_FIELD_NOMBRE_VICTOIRES_BANAL) + getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS_BANAL) + getStatInt(equipe, TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_BANAL) != getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_JOUES_BANAL))
			return false;
		if(getStatInt(equipe, TABLE_FIELD_NOMBRE_VICTOIRES_IMPORTANT_DOMICILE) + getStatInt(equipe, TABLE_FIELD_NOMBRE_VICTOIRES_IMPORTANT_EXTERIEUR) != getStatInt(equipe, TABLE_FIELD_NOMBRE_VICTOIRES_IMPORTANT))
			return false;
		if(getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS_IMPORTANT_DOMICILE) + getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS_IMPORTANT_EXTERIEUR) != getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS_IMPORTANT))
			return false;
		if(getStatInt(equipe, TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_IMPORTANT_DOMICILE) + getStatInt(equipe, TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_IMPORTANT_EXTERIEUR) != getStatInt(equipe, TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_IMPORTANT))
			return false;
		if(getStatInt(equipe, TABLE_FIELD_NOMBRE_VICTOIRES_BANAL_DOMICILE) + getStatInt(equipe, TABLE_FIELD_NOMBRE_VICTOIRES_BANAL_EXTERIEUR) != getStatInt(equipe, TABLE_FIELD_NOMBRE_VICTOIRES_BANAL))
			return false;
		if(getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS_BANAL_DOMICILE) + getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS_BANAL_EXTERIEUR) != getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS_BANAL))
			return false;
		if(getStatInt(equipe, TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_BANAL_DOMICILE) + getStatInt(equipe, TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_BANAL_EXTERIEUR) != getStatInt(equipe, TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_BANAL))
			return false;
		if(getStatInt(equipe, TABLE_FIELD_NOMBRE_VICTOIRES_IMPORTANT) + getStatInt(equipe, TABLE_FIELD_NOMBRE_VICTOIRES_BANAL) != getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_GAGNES))
			return false;
		if(getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS_IMPORTANT) + getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS_BANAL) != getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS))
			return false;
		if(getStatInt(equipe, TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_IMPORTANT) + getStatInt(equipe, TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_BANAL) != getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_PERDUS))
			return false;
		
		return true;
	}

	
	private static boolean verifForme(String equipe) throws VdocHelperException {
		
		int countFormeV = 0;
		int countFormeN = 0;
		int countFormeD = 0;
		
		if(getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_1).equals("V")) {
			countFormeV ++;
		}
		else {
			if(getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_1).equals("N")) {
				countFormeN ++;
			}
			else {
				if(getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_1).equals("D")) {
					countFormeD ++;
				}
			}
		}
		
		if(getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_2).equals("V")) {
			countFormeV ++;
		}
		else {
			if(getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_2).equals("N")) {
				countFormeN ++;
			}
			else {
				if(getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_2).equals("D")) {
					countFormeD ++;
				}
			}
		}
		
		if(getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_3).equals("V")) {
			countFormeV ++;
		}
		else {
			if(getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_3).equals("N")) {
				countFormeN ++;
			}
			else {
				if(getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_3).equals("D")) {
					countFormeD ++;
				}
			}
		}
		
		if(getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_4).equals("V")) {
			countFormeV ++;
		}
		else {
			if(getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_4).equals("N")) {
				countFormeN ++;
			}
			else {
				if(getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_4).equals("D")) {
					countFormeD ++;
				}
			}
		}
		
		if(getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_5).equals("V")) {
			countFormeV ++;
		}
		else {
			if(getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_5).equals("N")) {
				countFormeN ++;
			}
			else {
				if(getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_5).equals("D")) {
					countFormeD ++;
				}
			}
		}
		
		if(getStatInt(equipe, TABLE_FIELD_SERIE_V_EN_COURS) != countFormeV)
			return false;
		if(getStatInt(equipe, TABLE_FIELD_SERIE_N_EN_COURS) != countFormeN)
			return false;
		if(getStatInt(equipe, TABLE_FIELD_SERIE_D_EN_COURS) != countFormeD)
			return false;
		
		int countFormeVDom = 0;
		int countFormeNDom = 0;
		int countFormeDDom = 0;
		
		if(getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_1_DOMICILE).equals("V")) {
			countFormeVDom ++;
		}
		else {
			if(getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_1_DOMICILE).equals("N")) {
				countFormeNDom ++;
			}
			else {
				if(getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_1_DOMICILE).equals("D")) {
					countFormeDDom ++;
				}
			}
		}
		
		if(getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_2_DOMICILE).equals("V")) {
			countFormeVDom ++;
		}
		else {
			if(getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_2_DOMICILE).equals("N")) {
				countFormeNDom ++;
			}
			else {
				if(getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_2_DOMICILE).equals("D")) {
					countFormeDDom ++;
				}
			}
		}
		
		if(getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_3_DOMICILE).equals("V")) {
			countFormeVDom ++;
		}
		else {
			if(getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_3_DOMICILE).equals("N")) {
				countFormeNDom ++;
			}
			else {
				if(getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_3_DOMICILE).equals("D")) {
					countFormeDDom ++;
				}
			}
		}
		
		if(getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_4_DOMICILE).equals("V")) {
			countFormeVDom ++;
		}
		else {
			if(getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_4_DOMICILE).equals("N")) {
				countFormeNDom ++;
			}
			else {
				if(getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_4_DOMICILE).equals("D")) {
					countFormeDDom ++;
				}
			}
		}
		
		if(getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_5_DOMICILE).equals("V")) {
			countFormeVDom ++;
		}
		else {
			if(getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_5_DOMICILE).equals("N")) {
				countFormeNDom ++;
			}
			else {
				if(getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_5_DOMICILE).equals("D")) {
					countFormeDDom ++;
				}
			}
		}
		
		if(getStatInt(equipe, TABLE_FIELD_SERIE_V_EN_COURS_DOMICILE) != countFormeVDom)
			return false;
		if(getStatInt(equipe, TABLE_FIELD_SERIE_N_EN_COURS_DOMICILE) != countFormeNDom)
			return false;
		if(getStatInt(equipe, TABLE_FIELD_SERIE_D_EN_COURS_DOMICILE) != countFormeDDom)
			return false;
		
		int countFormeVExt = 0;
		int countFormeNExt = 0;
		int countFormeDExt = 0;
		
		if(getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_1_EXTERIEUR).equals("V")) {
			countFormeVExt ++;
		}
		else {
			if(getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_1_EXTERIEUR).equals("N")) {
				countFormeNExt ++;
			}
			else {
				if(getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_1_EXTERIEUR).equals("D")) {
					countFormeDExt ++;
				}
			}
		}
		
		if(getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_2_EXTERIEUR).equals("V")) {
			countFormeVExt ++;
		}
		else {
			if(getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_2_EXTERIEUR).equals("N")) {
				countFormeNExt ++;
			}
			else {
				if(getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_2_EXTERIEUR).equals("D")) {
					countFormeDExt ++;
				}
			}
		}
		
		if(getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_3_EXTERIEUR).equals("V")) {
			countFormeVExt ++;
		}
		else {
			if(getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_3_EXTERIEUR).equals("N")) {
				countFormeNExt ++;
			}
			else {
				if(getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_3_EXTERIEUR).equals("D")) {
					countFormeDExt ++;
				}
			}
		}
		
		if(getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_4_EXTERIEUR).equals("V")) {
			countFormeVExt ++;
		}
		else {
			if(getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_4_EXTERIEUR).equals("N")) {
				countFormeNExt ++;
			}
			else {
				if(getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_4_EXTERIEUR).equals("D")) {
					countFormeDExt ++;
				}
			}
		}
		
		if(getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_5_EXTERIEUR).equals("V")) {
			countFormeVExt ++;
		}
		else {
			if(getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_5_EXTERIEUR).equals("N")) {
				countFormeNExt ++;
			}
			else {
				if(getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_5_EXTERIEUR).equals("D")) {
					countFormeDExt ++;
				}
			}
		}
		
		if(getStatInt(equipe, TABLE_FIELD_SERIE_V_EN_COURS_EXTERIEUR) != countFormeVExt)
			return false;
		if(getStatInt(equipe, TABLE_FIELD_SERIE_N_EN_COURS_EXTERIEUR) != countFormeNExt)
			return false;
		if(getStatInt(equipe, TABLE_FIELD_SERIE_D_EN_COURS_EXTERIEUR) != countFormeDExt)
			return false;
		
		return true;
	}

}
