package com.doandgo.ligue1.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

/**
 * Opérations de base sur les tables
 * 
 * @author Thomas CHARMES
 *
 */
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
	public final static String FORM_FIELD_CHECK_BOX_COMPTABILISE = "Comptabilise";
	public final static String FORM_FIELD_SCORE = "Score";
	public final static String FORM_BUTTON_GENERER_RAPPORT_STATISTIQUES = "Statistiques";
	public final static String FORM_FIELD_CHECK_BOX_COMPTABILISE_RAPPORT_STATISTIQUES_JOURNEE = "RapportStatistiques";

	public final static String TABLE_FIELD_NOMBRE_MATCHS_JOUES = "nombreMatchsJoues";
	public final static String TABLE_FIELD_STRING_CLASSEMENT = "classement";
	public final static String TABLE_FIELD_DIFFERENCE_DE_BUTS = "GoalAverage";
	public final static String TABLE_FIELD_STRING_CLASSEMENT_DOMICILE = "classementDomicile";
	public final static String TABLE_FIELD_DIFFERENCE_DE_BUTS_DOMICILE = "HomeGoalAverage";
	public final static String TABLE_FIELD_STRING_CLASSEMENT_EXTERIEUR = "classementExterieur";
	public final static String TABLE_FIELD_DIFFERENCE_DE_BUTS_EXTERIEUR = "AwayGoalAverage";
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
	
	public static final String TABLE_CONFRONTATIONS_FIELD_RECENT_5 = "Recent5";
	public static final String TABLE_CONFRONTATIONS_FIELD_RECENT_4 = "Recent4";
	public static final String TABLE_CONFRONTATIONS_FIELD_RECENT_3 = "Recent3";
	public static final String TABLE_CONFRONTATIONS_FIELD_RECENT_2 = "Recent2";
	public static final String TABLE_CONFRONTATIONS_FIELD_RECENT_1 = "Recent1";
	public static final String TABLE_CONFRONTATIONS_FIELD_MATCH = "match";
	public static final String TABLE_CONFRONTATIONS_FIELD_DATE_LAST_MODIFICATION = "sys_ModificationDate";
	
	public static final String TABLE_STATISTIQUES_FIELD_MATCH = "match";
	public static final String TABLE_STATISTIQUES_FIELD_MOYENNE_BUTS_DOMICILE = "MoyButsEquipeDom";
	public static final String TABLE_STATISTIQUES_FIELD_MOYENNE_BUTS_EXTERIEUR = "MoyButsEquipeExt";
	public static final String TABLE_STATISTIQUES_FIELD_MOYENNE_BUTS_MATCH = "MoyButsMatch";
	public static final String TABLE_STATISTIQUES_FIELD_POURCENTAGE_NULS = "Nul";
	public static final String TABLE_STATISTIQUES_FIELD_POURCENTAGE_LDEM = "LDEM";
	public static final String TABLE_STATISTIQUES_FIELD_POURCENTAGE_VICTOIRE_DOMICILE = "VEquipeDom";
	public static final String TABLE_STATISTIQUES_FIELD_POURCENTAGE_VICTOIRE_EXTERIEUR = "VEquipeExt";
	
	public static final String TABLE_STATISTIQUES_FIELD_POURCENTAGE_MATCH_PLUS_0_5_BUTS = "Plus05";
	public static final String TABLE_STATISTIQUES_FIELD_POURCENTAGE_MATCH_PLUS_1_5_BUTS = "Plus15";
	public static final String TABLE_STATISTIQUES_FIELD_POURCENTAGE_MATCH_PLUS_2_5_BUTS = "Plus25";
	public static final String TABLE_STATISTIQUES_FIELD_POURCENTAGE_MATCH_PLUS_3_5_BUTS = "Plus35";
	public static final String TABLE_STATISTIQUES_FIELD_POURCENTAGE_MATCH_PLUS_4_5_BUTS = "Plus45";
	public static final String TABLE_STATISTIQUES_FIELD_POURCENTAGE_MATCH_MOINS_0_5_BUTS = "Moins05";
	public static final String TABLE_STATISTIQUES_FIELD_POURCENTAGE_MATCH_MOINS_1_5_BUTS = "Moins15";
	public static final String TABLE_STATISTIQUES_FIELD_POURCENTAGE_MATCH_MOINS_2_5_BUTS = "Moins25";
	public static final String TABLE_STATISTIQUES_FIELD_POURCENTAGE_MATCH_MOINS_3_5_BUTS = "Moins35";
	public static final String TABLE_STATISTIQUES_FIELD_POURCENTAGE_MATCH_MOINS_4_5_BUTS = "Moins45";
	public static final String TABLE_STATISTIQUES_FIELD_POURCENTAGE_DOMICILE_PLUS_0_5_BUTS = "E1Plus05";
	public static final String TABLE_STATISTIQUES_FIELD_POURCENTAGE_DOMICILE_PLUS_1_5_BUTS = "E1Plus15";
	public static final String TABLE_STATISTIQUES_FIELD_POURCENTAGE_DOMICILE_PLUS_2_5_BUTS = "E1Plus25";
	public static final String TABLE_STATISTIQUES_FIELD_POURCENTAGE_DOMICILE_PLUS_3_5_BUTS = "E1Plus35";
	public static final String TABLE_STATISTIQUES_FIELD_POURCENTAGE_DOMICILE_PLUS_4_5_BUTS = "E1Plus45";
	public static final String TABLE_STATISTIQUES_FIELD_POURCENTAGE_DOMICILE_MOINS_0_5_BUTS = "E1Moins05";
	public static final String TABLE_STATISTIQUES_FIELD_POURCENTAGE_DOMICILE_MOINS_1_5_BUTS = "E1Moins15";
	public static final String TABLE_STATISTIQUES_FIELD_POURCENTAGE_DOMICILE_MOINS_2_5_BUTS = "E1Moins25";
	public static final String TABLE_STATISTIQUES_FIELD_POURCENTAGE_DOMICILE_MOINS_3_5_BUTS = "E1Moins35";
	public static final String TABLE_STATISTIQUES_FIELD_POURCENTAGE_DOMICILE_MOINS_4_5_BUTS = "E1Moins45";
	public static final String TABLE_STATISTIQUES_FIELD_POURCENTAGE_EXTERIEUR_PLUS_0_5_BUTS = "E2Plus05";
	public static final String TABLE_STATISTIQUES_FIELD_POURCENTAGE_EXTERIEUR_PLUS_1_5_BUTS = "E2Plus15";
	public static final String TABLE_STATISTIQUES_FIELD_POURCENTAGE_EXTERIEUR_PLUS_2_5_BUTS = "E2Plus25";
	public static final String TABLE_STATISTIQUES_FIELD_POURCENTAGE_EXTERIEUR_PLUS_3_5_BUTS = "E2Plus35";
	public static final String TABLE_STATISTIQUES_FIELD_POURCENTAGE_EXTERIEUR_PLUS_4_5_BUTS = "E2Plus45";
	public static final String TABLE_STATISTIQUES_FIELD_POURCENTAGE_EXTERIEUR_MOINS_0_5_BUTS = "E2Moins05";
	public static final String TABLE_STATISTIQUES_FIELD_POURCENTAGE_EXTERIEUR_MOINS_1_5_BUTS = "E2Moins15";
	public static final String TABLE_STATISTIQUES_FIELD_POURCENTAGE_EXTERIEUR_MOINS_2_5_BUTS = "E2Moins25";
	public static final String TABLE_STATISTIQUES_FIELD_POURCENTAGE_EXTERIEUR_MOINS_3_5_BUTS = "E2Moins35";
	public static final String TABLE_STATISTIQUES_FIELD_POURCENTAGE_EXTERIEUR_MOINS_4_5_BUTS = "E2Moins45";
	
	public static final String TABLE_STATISTIQUES_FIELD_POURCENTAGE_ECART_BUT_MOINS_1 = "EcartButMoins1";
	public static final String TABLE_STATISTIQUES_FIELD_POURCENTAGE_ECART_BUT_MOINS_2 = "EcartButMoins2";
	public static final String TABLE_STATISTIQUES_FIELD_POURCENTAGE_ECART_BUT_MOINS_3 = "EcartButMoins3";
	public static final String TABLE_STATISTIQUES_FIELD_POURCENTAGE_ECART_BUT_MOINS_4 = "EcartButMoins4";
	public static final String TABLE_STATISTIQUES_FIELD_POURCENTAGE_ECART_BUT_MOINS_5 = "EcartButMoins5";
	public static final String TABLE_STATISTIQUES_FIELD_POURCENTAGE_ECART_BUT_MOINS_6 = "EcartButMoins6";
	public static final String TABLE_STATISTIQUES_FIELD_POURCENTAGE_ECART_BUT_MOINS_7 = "EcartButMoins7";
	public static final String TABLE_STATISTIQUES_FIELD_POURCENTAGE_ECART_BUT_MOINS_8 = "EcartButMoins8";
	public static final String TABLE_STATISTIQUES_FIELD_POURCENTAGE_ECART_BUT_MOINS_9 = "EcartButMoins9";
	public static final String TABLE_STATISTIQUES_FIELD_POURCENTAGE_ECART_BUT_PLUS_1 = "EcartButPlus1";
	public static final String TABLE_STATISTIQUES_FIELD_POURCENTAGE_ECART_BUT_PLUS_2 = "EcartButPlus2";
	public static final String TABLE_STATISTIQUES_FIELD_POURCENTAGE_ECART_BUT_PLUS_3 = "EcartButPlus3";
	public static final String TABLE_STATISTIQUES_FIELD_POURCENTAGE_ECART_BUT_PLUS_4 = "EcartButPlus4";
	public static final String TABLE_STATISTIQUES_FIELD_POURCENTAGE_ECART_BUT_PLUS_5 = "EcartButPlus5";
	public static final String TABLE_STATISTIQUES_FIELD_POURCENTAGE_ECART_BUT_PLUS_6 = "EcartButPlus6";
	public static final String TABLE_STATISTIQUES_FIELD_POURCENTAGE_ECART_BUT_PLUS_7 = "EcartButPlus7";
	public static final String TABLE_STATISTIQUES_FIELD_POURCENTAGE_ECART_BUT_PLUS_8 = "EcartButPlus8";
	public static final String TABLE_STATISTIQUES_FIELD_POURCENTAGE_ECART_BUT_PLUS_9 = "EcartButPlus9";
	
	public static final String TABLE_STATISTIQUES_FIELD_POURCENTAGE_EQUIPE_DOMICILE_0_BUT_MARQUE = "E1Exactement0ButMarque";
	public static final String TABLE_STATISTIQUES_FIELD_POURCENTAGE_EQUIPE_DOMICILE_1_BUT_MARQUE = "E1Exactement1ButMarque";
	public static final String TABLE_STATISTIQUES_FIELD_POURCENTAGE_EQUIPE_DOMICILE_2_BUTS_MARQUES = "E1Exactement2ButsMarques";
	public static final String TABLE_STATISTIQUES_FIELD_POURCENTAGE_EQUIPE_DOMICILE_3_BUTS_MARQUES = "E1Exactement3ButsMarques";
	public static final String TABLE_STATISTIQUES_FIELD_POURCENTAGE_EQUIPE_DOMICILE_4_BUTS_MARQUES = "E1Exactement4ButsMarques";
	public static final String TABLE_STATISTIQUES_FIELD_POURCENTAGE_EQUIPE_DOMICILE_5_BUTS_MARQUES = "E1Exactement5ButsMarques";
	public static final String TABLE_STATISTIQUES_FIELD_POURCENTAGE_EQUIPE_EXTERIEUR_0_BUT_MARQUE = "E2Exactement0ButMarque";
	public static final String TABLE_STATISTIQUES_FIELD_POURCENTAGE_EQUIPE_EXTERIEUR_1_BUT_MARQUE = "E2Exactement1ButMarque";
	public static final String TABLE_STATISTIQUES_FIELD_POURCENTAGE_EQUIPE_EXTERIEUR_2_BUTS_MARQUES = "E2Exactement2ButsMarques";
	public static final String TABLE_STATISTIQUES_FIELD_POURCENTAGE_EQUIPE_EXTERIEUR_3_BUTS_MARQUES = "E2Exactement3ButsMarques";
	public static final String TABLE_STATISTIQUES_FIELD_POURCENTAGE_EQUIPE_EXTERIEUR_4_BUTS_MARQUES = "E2Exactement4ButsMarques";
	public static final String TABLE_STATISTIQUES_FIELD_POURCENTAGE_EQUIPE_EXTERIEUR_5_BUTS_MARQUES = "E2Exactement5ButsMarques";
	
	public static final String TABLE_STATISTIQUES_FIELD_POURCENTAGE_EQUIPE_DOMICILE_0_BUT_ENCAISSE = "E1Exactement0ButEncaisse";
	public static final String TABLE_STATISTIQUES_FIELD_POURCENTAGE_EQUIPE_DOMICILE_1_BUT_ENCAISSE = "E1Exactement1ButEncaisse";
	public static final String TABLE_STATISTIQUES_FIELD_POURCENTAGE_EQUIPE_DOMICILE_2_BUTS_ENCAISSES = "E1Exactement2ButsEncaisses";
	public static final String TABLE_STATISTIQUES_FIELD_POURCENTAGE_EQUIPE_DOMICILE_3_BUTS_ENCAISSES = "E1Exactement3ButsEncaisses";
	public static final String TABLE_STATISTIQUES_FIELD_POURCENTAGE_EQUIPE_DOMICILE_4_BUTS_ENCAISSES = "E1Exactement4ButsEncaisses";
	public static final String TABLE_STATISTIQUES_FIELD_POURCENTAGE_EQUIPE_DOMICILE_5_BUTS_ENCAISSES = "E1Exactement5ButsEncaisses";
	public static final String TABLE_STATISTIQUES_FIELD_POURCENTAGE_EQUIPE_EXTERIEUR_0_BUT_ENCAISSE = "E2Exactement0ButEncaisse";
	public static final String TABLE_STATISTIQUES_FIELD_POURCENTAGE_EQUIPE_EXTERIEUR_1_BUT_ENCAISSE = "E2Exactement1ButEncaisse";
	public static final String TABLE_STATISTIQUES_FIELD_POURCENTAGE_EQUIPE_EXTERIEUR_2_BUTS_ENCAISSES = "E2Exactement2ButsEncaisses";
	public static final String TABLE_STATISTIQUES_FIELD_POURCENTAGE_EQUIPE_EXTERIEUR_3_BUTS_ENCAISSES = "E2Exactement3ButsEncaisses";
	public static final String TABLE_STATISTIQUES_FIELD_POURCENTAGE_EQUIPE_EXTERIEUR_4_BUTS_ENCAISSES = "E2Exactement4ButsEncaisses";
	public static final String TABLE_STATISTIQUES_FIELD_POURCENTAGE_EQUIPE_EXTERIEUR_5_BUTS_ENCAISSES = "E2Exactement5ButsEncaisses";
	

	public final static String ORGANIZATION_NAME = "DefaultOrganization";
	public final static String APPLICATION_NAME = "ApplicationLigue1";
	public final static String DATA_RESERVOIR_EQUIPES_NAME = "ReservoirDeDonneesEquipes";
	public final static String TABLE_EQUIPES_NAME = "tableEquipes";
	public final static String TABLE_STATISTIQUES_NAME = "StatistiquesMatchs";
	public final static String TABLE_CONFRONTATIONS_NAME = "tableConfrontations";
	public final static String GROUP_PROCESS_NAME = "GroupeDeProcessusLigue1";
	public final static String PROCESS_NAME = "match";
	public final static String VERSION_PROCESS_NAME = "match_1.0";

	public enum EquipesLigue1 {
		MHSC, OGCN, PSG, LOSC, FCN, ASM, OM, SRFC, SB29, FCGB, OL, ASSE, SCO, RCS, FCL, SDR, FCM, DFCO, NO, RCL;
	}

	public enum RivauxRCL {
		PSG, LOSC, ASM, OM, OL, ASSE;
	}

	public enum RivauxNO {
		MHSC, OGCN, PSG, ASM, OM, OL, ASSE;
	}

	public enum RivauxDFCO {
		PSG, LOSC, ASM, OM, OL, ASSE;
	}

	public enum RivauxFCM {
		PSG, LOSC, ASM, OM, OL, ASSE, RCS;
	}

	public enum RivauxSDR {
		PSG, LOSC, ASM, OM, OL, ASSE;
	}

	public enum RivauxFCL {
		PSG, LOSC, ASM, OM, SRFC, SB29, OL, ASSE;
	}

	public enum RivauxRCS {
		PSG, LOSC, ASM, OM, OL, ASSE, FCM;
	}

	public enum RivauxSCO {
		PSG, LOSC, ASM, OM, OL, ASSE;
	}

	public enum RivauxASSE {
		PSG, LOSC, ASM, OM, FCGB, OL;
	}

	public enum RivauxOL {
		PSG, LOSC, ASM, OM, FCGB, ASSE;
	}

	public enum RivauxFCGB {
		OGCN, PSG, ASM, OM, OL, ASSE;
	}

	public enum RivauxSB29 {
		PSG, ASM, OM, SRFC, OL, ASSE, FCL;
	}

	public enum RivauxSRFC {
		PSG, LOSC, FCN, ASM, OM, SB29, OL, ASSE, FCL;
	}

	public enum RivauxOM {
		OGCN, PSG, LOSC, ASM, FCGB, OL, ASSE;
	}

	public enum RivauxASM {
		OGCN, PSG, OM, FCGB, OL, ASSE;
	}

	public enum RivauxFCN {
		PSG, LOSC, ASM, OM, SRFC, OL, ASSE;
	}

	public enum RivauxLOSC {
		PSG, ASM, OM, OL, ASSE, RCL;
	}

	public enum RivauxPSG {
		LOSC, ASM, OM, OL, ASSE;
	}

	public enum RivauxOGCN {
		PSG, ASM, OM, FCGB, OL, ASSE;
	}

	public enum RivauxMHSC {
		PSG, ASM, OM, OL, ASSE, NO;
	}

	public static List<EquipesLigue1> listerEquipesLigue1DepuisEnumeration() {
		int i = 0;
		List<EquipesLigue1> liste = new ArrayList<EquipesLigue1>();

		EquipesLigue1[] tableauDesEquipes = EquipesLigue1.values();
		int valuesNumber = tableauDesEquipes.length;
		for (EquipesLigue1 equipe : tableauDesEquipes) {
			if (i == valuesNumber - 2) {
				break;
			}
			liste.add(equipe);
		}

		return liste;
	}

	public static void main(String[] args) {
		listerEquipesLigue1DepuisEnumeration();
	}

	public static boolean isScoreWellFormed(String score) {
		String regex = "^[0-9]-[0-9]$";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(score);
		return m.matches();
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

		IStorageResource resourceEquipe = VdocHelper.getDataUniverseResource(dm, pm, wm, ORGANIZATION_NAME,
				APPLICATION_NAME, DATA_RESERVOIR_EQUIPES_NAME, TABLE_EQUIPES_NAME, equalsConstraints);
		if (resourceEquipe != null) {
			return resourceEquipe;
		}
		Modules.releaseModule(wm);
		Modules.releaseModule(pm);
		Modules.releaseModule(dm);
		return null;
	}
	
	public static IStorageResource getResourceStatistique(String match) throws VdocHelperException {

		Map<String, Object> equalsConstraints = new HashMap<String, Object>();
		equalsConstraints.put("match", match);

		IDirectoryModule dm = Modules.getDirectoryModule();
		IProjectModule pm = Modules.getProjectModule();
		IWorkflowModule wm = Modules.getWorkflowModule();

		IStorageResource resourceStatistique = VdocHelper.getDataUniverseResource(dm, pm, wm, ORGANIZATION_NAME,
				APPLICATION_NAME, DATA_RESERVOIR_EQUIPES_NAME, TABLE_STATISTIQUES_NAME, equalsConstraints);
		if (resourceStatistique != null) {
			return resourceStatistique;
		}
		Modules.releaseModule(wm);
		Modules.releaseModule(pm);
		Modules.releaseModule(dm);
		return null;
	}

	public static IStorageResource getResourceConfrontation(String match) throws VdocHelperException {

		Map<String, Object> equalsConstraints = new HashMap<String, Object>();
		equalsConstraints.put("match", match);

		IDirectoryModule dm = Modules.getDirectoryModule();
		IProjectModule pm = Modules.getProjectModule();
		IWorkflowModule wm = Modules.getWorkflowModule();

		IStorageResource confrontation = VdocHelper.getDataUniverseResource(dm, pm, wm, ORGANIZATION_NAME,
				APPLICATION_NAME, DATA_RESERVOIR_EQUIPES_NAME, TABLE_CONFRONTATIONS_NAME, equalsConstraints);
		if (confrontation != null) {
			return confrontation;
		}
		Modules.releaseModule(wm);
		Modules.releaseModule(pm);
		Modules.releaseModule(dm);
		return null;
	}
	
	public static Collection<IStorageResource> getResourcesConfrontations() throws VdocHelperException {

		Map<String, Object> equalsConstraints = new HashMap<String, Object>();

		IDirectoryModule dm = Modules.getDirectoryModule();
		IProjectModule pm = Modules.getProjectModule();
		IWorkflowModule wm = Modules.getWorkflowModule();

		Collection<IStorageResource> confrontations = VdocHelper.getDataUniverseResources(dm, pm, wm, ORGANIZATION_NAME,
				APPLICATION_NAME, DATA_RESERVOIR_EQUIPES_NAME, TABLE_CONFRONTATIONS_NAME, equalsConstraints);
		if (confrontations != null) {
			return confrontations;
		}
		Modules.releaseModule(wm);
		Modules.releaseModule(pm);
		Modules.releaseModule(dm);
		return null;
	}
	
	public static Collection<IStorageResource> getResourcesEquipes() throws VdocHelperException {

		Map<String, Object> equalsConstraints = new HashMap<String, Object>();

		IDirectoryModule dm = Modules.getDirectoryModule();
		IProjectModule pm = Modules.getProjectModule();
		IWorkflowModule wm = Modules.getWorkflowModule();

		Collection<IStorageResource> equipes = VdocHelper.getDataUniverseResources(dm, pm, wm, ORGANIZATION_NAME,
				APPLICATION_NAME, DATA_RESERVOIR_EQUIPES_NAME, TABLE_EQUIPES_NAME, equalsConstraints);
		if (equipes != null) {
			return equipes;
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

		IStorageResource resourceEquipe = VdocHelper.getDataUniverseResource(dm, pm, wm, ORGANIZATION_NAME,
				APPLICATION_NAME, DATA_RESERVOIR_EQUIPES_NAME, TABLE_EQUIPES_NAME, equalsConstraints);
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

	/** 
	 * @param surnom
	 * @param stat : le nom système du champ de la table "Equipes"
	 * @return
	 * @throws VdocHelperException
	 */
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
	
	/** 
	 * @param match
	 * @param stat : le nom système du champ de la table "confrontations"
	 * @return
	 * @throws VdocHelperException
	 */
	public static Float getStatConfrontation(String match, String stat) throws VdocHelperException {

		IStorageResource resourceConfrontation = getResourceConfrontation(match);

		if (resourceConfrontation != null) {
			Float nbMatchsJoues = (Float) resourceConfrontation.getValue(stat);
			if (nbMatchsJoues.compareTo(0F) == 0 || nbMatchsJoues.equals(null)
					|| nbMatchsJoues.toString().equals(null)) {
				return 0F;
			}
			return nbMatchsJoues.floatValue();
		}
		return 0F;
	}

	public static void setNombreMatchsJouesPlusUn(String surnom) throws VdocHelperException {

		setResourceEquipe(surnom, TABLE_FIELD_NOMBRE_MATCHS_JOUES,
				getStatInt(surnom, TABLE_FIELD_NOMBRE_MATCHS_JOUES) + 1);

	}

	public static boolean setResultat(String equipe1, String equipe2, boolean e1mieuxClassee, boolean importantE1,
			boolean importantE2, boolean victoireE1, boolean matchNul, boolean victoireE2, String score) throws VdocHelperException {

		setDifferencesDeButsDansLesTroisClassements(equipe1, equipe2, score);
		
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
			if (victoireE2) {
				add3points(equipe2);
				add3pointsExterieur(equipe2);
				// gère les VNDVV
				addSerieEnCours(equipe1, "D");
				addSerieEnCours(equipe2, "V");
				addSerieEnCoursDomicile(equipe1, "D");
				addSerieEnCoursExterieur(equipe2, "V");
				// gere les V:3 N:1 D:1
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
		setSerieEnCours(equipe1);
		setSerieEnCours(equipe2);
		setSerieEnCoursDomicile(equipe1);
		setSerieEnCoursExterieur(equipe2);
		
		boolean verifE1 = verif(equipe1);
		boolean verifE2 = verif(equipe2);

		return (verifE1 && verifE2);
	}

	private static void setDifferencesDeButsDansLesTroisClassements(String equipe1, String equipe2, String score) {
		
		String[] scoreTableau = score.split("-");
		String nbButsEquipeDomicileString = scoreTableau[0];
		String nbButsEquipeExterieurString = scoreTableau[1];
		
		Long nbButsMarquesEquipeDomicile = Long.parseLong(nbButsEquipeDomicileString);
		Long nbButsMarquesEquipeExterieur = Long.parseLong(nbButsEquipeExterieurString);
		
		if (nbButsMarquesEquipeDomicile != null && nbButsMarquesEquipeExterieur != null) {
			
			Long differenceEnFaveurEquipeDomicile = nbButsMarquesEquipeDomicile - nbButsMarquesEquipeExterieur;
			Long differenceEnFaveurEquipeExterieur = nbButsMarquesEquipeExterieur - nbButsMarquesEquipeDomicile;
			
			updateGlobalGoalAverage(TABLE_FIELD_DIFFERENCE_DE_BUTS, equipe1, equipe2, differenceEnFaveurEquipeDomicile, differenceEnFaveurEquipeExterieur);
			updateHomeAndAwayGoalAverage(TABLE_FIELD_DIFFERENCE_DE_BUTS_DOMICILE, equipe1, TABLE_FIELD_DIFFERENCE_DE_BUTS_EXTERIEUR, equipe2, differenceEnFaveurEquipeDomicile, differenceEnFaveurEquipeExterieur);
		}
		
	}

	private static void updateHomeAndAwayGoalAverage(String tableFieldDifferenceDeButsDomicile, String equipe1,
			String tableFieldDifferenceDeButsExterieur, String equipe2, Long differenceEnFaveurEquipeDomicile,
			Long differenceEnFaveurEquipeExterieur) {
		
		try {
			int differenceEquipe1ClassementDomicile = getStatInt(equipe1, tableFieldDifferenceDeButsDomicile);
			setResourceEquipe(equipe1, tableFieldDifferenceDeButsDomicile, Long.valueOf(differenceEquipe1ClassementDomicile) + differenceEnFaveurEquipeDomicile);
			int differenceEquipe2ClassementExterieur = getStatInt(equipe2, tableFieldDifferenceDeButsExterieur);
			setResourceEquipe(equipe2, tableFieldDifferenceDeButsExterieur, Long.valueOf(differenceEquipe2ClassementExterieur) + differenceEnFaveurEquipeExterieur);
			
		} catch (VdocHelperException e) {
			e.printStackTrace();
		}
	}

	private static void updateGlobalGoalAverage(String field, String equipe1, String equipe2, Long differenceEnFaveurEquipeDomicile,
			Long differenceEnFaveurEquipeExterieur) {
		
		try {
			int differenceEquipe1 = getStatInt(equipe1, field);
			setResourceEquipe(equipe1, field, Long.valueOf(differenceEquipe1) + differenceEnFaveurEquipeDomicile);
			int differenceEquipe2 = getStatInt(equipe2, field);
			setResourceEquipe(equipe2, field, Long.valueOf(differenceEquipe2) + differenceEnFaveurEquipeExterieur);
			
		} catch (VdocHelperException e) {
			e.printStackTrace();
		}
	}

	// LES SERIES

	/**
	 * Prend en compte le dernier résultat et modifie les 5 derniers résultats de
	 * l'équipe exemple : "VVDVV" + "N" ==> "VDVVN" on décale vers la gauche
	 * 
	 * @param equipe
	 * @throws VdocHelperException
	 */
	private static void addSerieEnCoursExterieur(String equipe, String string) throws VdocHelperException {

		setResourceEquipe(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_5_EXTERIEUR,
				getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_4_EXTERIEUR));
		setResourceEquipe(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_4_EXTERIEUR,
				getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_3_EXTERIEUR));
		setResourceEquipe(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_3_EXTERIEUR,
				getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_2_EXTERIEUR));
		setResourceEquipe(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_2_EXTERIEUR,
				getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_1_EXTERIEUR));
		setResourceEquipe(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_1_EXTERIEUR, string);
	}

	private static void addSerieEnCoursDomicile(String equipe, String dernierResultat) throws VdocHelperException {

		setResourceEquipe(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_5_DOMICILE,
				getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_4_DOMICILE));
		setResourceEquipe(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_4_DOMICILE,
				getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_3_DOMICILE));
		setResourceEquipe(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_3_DOMICILE,
				getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_2_DOMICILE));
		setResourceEquipe(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_2_DOMICILE,
				getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_1_DOMICILE));
		setResourceEquipe(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_1_DOMICILE, dernierResultat);

	}

	private static void addSerieEnCours(String equipe, String string) throws VdocHelperException {

		setResourceEquipe(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_5,
				getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_4));
		setResourceEquipe(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_4,
				getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_3));
		setResourceEquipe(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_3,
				getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_2));
		setResourceEquipe(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_2,
				getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_1));
		setResourceEquipe(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_1, string);

	}

	private static void setSerieEnCours(String equipe) throws VdocHelperException {

		Long countNombreVictoiresConsecutives = 0L;
		Long countNombreNulsConsecutifs = 0L;
		Long countNombreDefaitesConsecutives = 0L;
		Boolean isAWinningStreakSeries = false;
		Boolean isADrawStreakSeries = false;
		Boolean isALoosingStreakSeries = false;
		
		if (getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_1).equals("V")) {
			isAWinningStreakSeries = true;
			countNombreVictoiresConsecutives++;
			setResourceEquipe(equipe, TABLE_FIELD_SERIE_N_EN_COURS, 0L);
			setResourceEquipe(equipe, TABLE_FIELD_SERIE_D_EN_COURS, 0L);
		}
		
		if (getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_1).equals("N")) {
			isADrawStreakSeries = true;
			countNombreNulsConsecutifs++;
			setResourceEquipe(equipe, TABLE_FIELD_SERIE_V_EN_COURS, 0L);
			setResourceEquipe(equipe, TABLE_FIELD_SERIE_D_EN_COURS, 0L);
		}
		
		if (getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_1).equals("D")) {
			isALoosingStreakSeries = true;
			countNombreDefaitesConsecutives++;
			setResourceEquipe(equipe, TABLE_FIELD_SERIE_V_EN_COURS, 0L);
			setResourceEquipe(equipe, TABLE_FIELD_SERIE_N_EN_COURS, 0L);
		}
		
		if (isAWinningStreakSeries) {
			updateSerie(equipe, TABLE_FIELD_SERIE_V_EN_COURS, countNombreVictoiresConsecutives, "V");
		}
		
		if (isADrawStreakSeries) {
			updateSerie(equipe, TABLE_FIELD_SERIE_N_EN_COURS, countNombreNulsConsecutifs, "N");
		}
		
		if (isALoosingStreakSeries) {
			updateSerie(equipe, TABLE_FIELD_SERIE_D_EN_COURS, countNombreDefaitesConsecutives, "D");
		}
	}
	
	private static void setSerieEnCoursDomicile(String equipe) throws VdocHelperException {

		Long countNombreVictoiresConsecutives = 0L;
		Long countNombreNulsConsecutifs = 0L;
		Long countNombreDefaitesConsecutives = 0L;
		Boolean isAWinningStreakSeries = false;
		Boolean isADrawStreakSeries = false;
		Boolean isALoosingStreakSeries = false;
		
		if (getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_1_DOMICILE).equals("V")) {
			isAWinningStreakSeries = true;
			countNombreVictoiresConsecutives++;
			setResourceEquipe(equipe, TABLE_FIELD_SERIE_N_EN_COURS_DOMICILE, 0L);
			setResourceEquipe(equipe, TABLE_FIELD_SERIE_D_EN_COURS_DOMICILE, 0L);
		}
		
		if (getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_1_DOMICILE).equals("N")) {
			isADrawStreakSeries = true;
			countNombreNulsConsecutifs++;
			setResourceEquipe(equipe, TABLE_FIELD_SERIE_V_EN_COURS_DOMICILE, 0L);
			setResourceEquipe(equipe, TABLE_FIELD_SERIE_D_EN_COURS_DOMICILE, 0L);
		}
		
		if (getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_1_DOMICILE).equals("D")) {
			isALoosingStreakSeries = true;
			countNombreDefaitesConsecutives++;
			setResourceEquipe(equipe, TABLE_FIELD_SERIE_V_EN_COURS_DOMICILE, 0L);
			setResourceEquipe(equipe, TABLE_FIELD_SERIE_N_EN_COURS_DOMICILE, 0L);
		}
		
		if (isAWinningStreakSeries) {
			updateSerie(equipe, TABLE_FIELD_SERIE_V_EN_COURS_DOMICILE, countNombreVictoiresConsecutives, "V");
		}
		
		if (isADrawStreakSeries) {
			updateSerie(equipe, TABLE_FIELD_SERIE_N_EN_COURS_DOMICILE, countNombreNulsConsecutifs, "N");
		}
		
		if (isALoosingStreakSeries) {
			updateSerie(equipe, TABLE_FIELD_SERIE_D_EN_COURS_DOMICILE, countNombreDefaitesConsecutives, "D");
		}
	}
	
	private static void setSerieEnCoursExterieur(String equipe) throws VdocHelperException {

		Long countNombreVictoiresConsecutives = 0L;
		Long countNombreNulsConsecutifs = 0L;
		Long countNombreDefaitesConsecutives = 0L;
		Boolean isAWinningStreakSeries = false;
		Boolean isADrawStreakSeries = false;
		Boolean isALoosingStreakSeries = false;
		
		if (getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_1_EXTERIEUR).equals("V")) {
			isAWinningStreakSeries = true;
			countNombreVictoiresConsecutives++;
			setResourceEquipe(equipe, TABLE_FIELD_SERIE_N_EN_COURS_EXTERIEUR, 0L);
			setResourceEquipe(equipe, TABLE_FIELD_SERIE_D_EN_COURS_EXTERIEUR, 0L);
		}
		
		if (getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_1_EXTERIEUR).equals("N")) {
			isADrawStreakSeries = true;
			countNombreNulsConsecutifs++;
			setResourceEquipe(equipe, TABLE_FIELD_SERIE_V_EN_COURS_EXTERIEUR, 0L);
			setResourceEquipe(equipe, TABLE_FIELD_SERIE_D_EN_COURS_EXTERIEUR, 0L);
		}
		
		if (getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_1_EXTERIEUR).equals("D")) {
			isALoosingStreakSeries = true;
			countNombreDefaitesConsecutives++;
			setResourceEquipe(equipe, TABLE_FIELD_SERIE_V_EN_COURS_EXTERIEUR, 0L);
			setResourceEquipe(equipe, TABLE_FIELD_SERIE_N_EN_COURS_EXTERIEUR, 0L);
		}
		
		if (isAWinningStreakSeries) {
			updateSerie(equipe, TABLE_FIELD_SERIE_V_EN_COURS_EXTERIEUR, countNombreVictoiresConsecutives, "V");
		}
		
		if (isADrawStreakSeries) {
			updateSerie(equipe, TABLE_FIELD_SERIE_N_EN_COURS_EXTERIEUR, countNombreNulsConsecutifs, "N");
		}
		
		if (isALoosingStreakSeries) {
			updateSerie(equipe, TABLE_FIELD_SERIE_D_EN_COURS_EXTERIEUR, countNombreDefaitesConsecutives, "D");
		}
	}

	private static void updateSerie(String equipe, String field, Long serieEnCours, String resultat) throws VdocHelperException {
		
		Boolean isSeriesAlive = true;
		if (getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_2).equals(resultat)) {
			serieEnCours++;
		}
		else {
			isSeriesAlive = false;
		}
		if (isSeriesAlive && getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_3).equals(resultat)) {
			serieEnCours++;
		}
		else {
			isSeriesAlive = false;
		}
		if (isSeriesAlive && getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_4).equals(resultat)) {
			serieEnCours++;
		}
		else {
			isSeriesAlive = false;
		}
		if (isSeriesAlive && getStatString(equipe, TABLE_FIELD_STRING_MATCH_PRECEDENT_5).equals(resultat)) {
			serieEnCours++;
		}
		else {
			isSeriesAlive = false;
		}
		setResourceEquipe(equipe, field, serieEnCours);
		
	}

	// LES SET NOMBRES +1 ET ADDPOINTS

	private static void setNombreMatchsJouesContreEquipeMoinsBienClasseeExterieurPlusUn(String equipe)
			throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_MATCHS_JOUES_CLASSEMENT_INF_EXTERIEUR,
				getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_JOUES_CLASSEMENT_INF_EXTERIEUR) + 1);
	}

	private static void setNombreMatchsJouesContreEquipeMieuxClasseeDomicilePlusUn(String equipe)
			throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_MATCHS_JOUES_CLASSEMENT_SUP_DOMICILE,
				getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_JOUES_CLASSEMENT_SUP_DOMICILE) + 1);
	}

	private static void setNombreMatchsJouesContreEquipeMoinsBienClasseeDomicilePlusUn(String equipe)
			throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_MATCHS_JOUES_CLASSEMENT_INF_DOMICILE,
				getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_JOUES_CLASSEMENT_INF_DOMICILE) + 1);
	}

	private static void setNombreMatchsJouesContreEquipeMieuxClasseeExterieurPlusUn(String equipe)
			throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_MATCHS_JOUES_CLASSEMENT_SUP_EXTERIEUR,
				getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_JOUES_CLASSEMENT_SUP_EXTERIEUR) + 1);
	}

	private static void setNombreMatchsNulsBanalDomicilePlusUn(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS_BANAL_DOMICILE,
				getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS_BANAL_DOMICILE) + 1);
	}

	private static void setNombreMatchsNulsImportantsDomicilePlusUn(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS_IMPORTANT_DOMICILE,
				getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS_IMPORTANT_DOMICILE) + 1);
	}

	private static void setNombreMatchsNulsBanalExterieurPlusUn(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS_BANAL_EXTERIEUR,
				getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS_BANAL_EXTERIEUR) + 1);
	}

	private static void setNombreMatchsNulsBanalPlusUn(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS_BANAL,
				getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS_BANAL) + 1);
	}

	private static void setNombreMatchsNulsImportantsExterieurPlusUn(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS_IMPORTANT_EXTERIEUR,
				getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS_IMPORTANT_EXTERIEUR) + 1);
	}

	private static void setNombreMatchsNulsImportantsPlusUn(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS_IMPORTANT,
				getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS_IMPORTANT) + 1);
	}

	private static void setNombreMatchsNulsContreEquipeMoinsBienClasseeDomicilePlusUn(String equipe)
			throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS_CLASSEMENT_INF_DOMICILE,
				getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS_CLASSEMENT_INF_DOMICILE) + 1);
	}

	private static void setNombreMatchsNulsContreEquipeMieuxClasseeExterieurPlusUn(String equipe)
			throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS_CLASSEMENT_SUP_EXTERIEUR,
				getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS_CLASSEMENT_SUP_EXTERIEUR) + 1);
	}

	private static void setNombreMatchsNulsContreEquipeMieuxClasseeDomicilePlusUn(String equipe)
			throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS_CLASSEMENT_SUP_DOMICILE,
				getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS_CLASSEMENT_SUP_DOMICILE) + 1);
	}

	private static void setNombreMatchsNulsContreEquipeMoinsBienClasseeExterieurPlusUn(String equipe)
			throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS_CLASSEMENT_INF_EXTERIEUR,
				getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS_CLASSEMENT_INF_EXTERIEUR) + 1);
	}

	private static void setNombreMatchsNulsContreEquipeMieuxClasseePlusUn(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS_CLASSEMENT_SUP,
				getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS_CLASSEMENT_SUP) + 1);
	}

	private static void setNombreMatchsNulsContreEquipeMoinsBienClasseePlusUn(String equipe)
			throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS_CLASSEMENT_INF,
				getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS_CLASSEMENT_INF) + 1);
	}

	private static void setNombreMatchsNulsDomicilePlusUn(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS_DOMICILE,
				getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS_DOMICILE) + 1);
	}

	private static void setNombreMatchsNulsExterieurPlusUn(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS_EXTERIEUR,
				getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS_EXTERIEUR) + 1);
	}

	private static void setNombreMatchsNulsPlusUn(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS,
				getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS) + 1);
	}

	private static void add1pointExterieur(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_POINTS_EXTERIEUR,
				getStatInt(equipe, TABLE_FIELD_NOMBRE_POINTS_EXTERIEUR) + 1);
	}

	private static void add1pointDomicile(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_POINTS_DOMICILE,
				getStatInt(equipe, TABLE_FIELD_NOMBRE_POINTS_DOMICILE) + 1);
	}

	private static void add1point(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_POINTS, getStatInt(equipe, TABLE_FIELD_NOMBRE_POINTS) + 1);
	}

	private static void setNombreMatchsPerdusBanalDomicilePlusUn(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_BANAL_DOMICILE,
				getStatInt(equipe, TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_BANAL_DOMICILE) + 1);
	}

	private static void setNombreMatchsPerdusImportantsDomicilePlusUn(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_IMPORTANT_DOMICILE,
				getStatInt(equipe, TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_IMPORTANT_DOMICILE) + 1);
	}

	private static void setNombreMatchsGagnesBanalExterieurPlusUn(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_VICTOIRES_BANAL_EXTERIEUR,
				getStatInt(equipe, TABLE_FIELD_NOMBRE_VICTOIRES_BANAL_EXTERIEUR) + 1);
	}

	private static void setNombreMatchsGagnesImportantsExterieurPlusUn(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_VICTOIRES_IMPORTANT_EXTERIEUR,
				getStatInt(equipe, TABLE_FIELD_NOMBRE_VICTOIRES_IMPORTANT_EXTERIEUR) + 1);

	}

	private static void setNombreMatchsPerdusContreEquipeMoinsBienClasseeDomicilePlusUn(String equipe)
			throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_CLASSEMENT_INFERIEUR_DOMICILE,
				getStatInt(equipe, TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_CLASSEMENT_INFERIEUR_DOMICILE) + 1);
	}

	private static void setNombreMatchsGagnesContreEquipeMieuxClasseeExterieurPlusUn(String equipe)
			throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_VICTOIRES_CLASSEMENT_SUP_EXTERIEUR,
				getStatInt(equipe, TABLE_FIELD_NOMBRE_VICTOIRES_CLASSEMENT_SUP_EXTERIEUR) + 1);

	}

	private static void setNombreMatchsPerdusContreEquipeMieuxClasseeDomicilePlusUn(String equipe)
			throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_CLASSEMENT_SUPERIEUR_DOMICILE,
				getStatInt(equipe, TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_CLASSEMENT_SUPERIEUR_DOMICILE) + 1);
	}

	private static void setNombreMatchsGagnesContreEquipeMoinsBienClasseeExterieurPlusUn(String equipe)
			throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_VICTOIRES_CLASSEMENT_INF_EXTERIEUR,
				getStatInt(equipe, TABLE_FIELD_NOMBRE_VICTOIRES_CLASSEMENT_INF_EXTERIEUR) + 1);
	}

	private static void setNombreMatchsPerdusDomicilePlusUn(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_MATCHS_PERDUS_DOMICILE,
				getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_PERDUS_DOMICILE) + 1);
	}

	private static void setNombreMatchsGagnesExterieurPlusUn(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_MATCHS_GAGNES_EXTERIEUR,
				getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_GAGNES_EXTERIEUR) + 1);
	}

	private static void add3pointsExterieur(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_POINTS_EXTERIEUR,
				getStatInt(equipe, TABLE_FIELD_NOMBRE_POINTS_EXTERIEUR) + 3);
	}

	private static void setNombreMatchsPerdusBanalExterieurPlusUn(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_BANAL_EXTERIEUR,
				getStatInt(equipe, TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_BANAL_EXTERIEUR) + 1);
	}

	private static void setNombreMatchsPerdusBanalPlusUn(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_BANAL,
				getStatInt(equipe, TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_BANAL) + 1);
	}

	private static void setNombreMatchsPerdusImportantsExterieurPlusUn(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_IMPORTANT_EXTERIEUR,
				getStatInt(equipe, TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_IMPORTANT_EXTERIEUR) + 1);
	}

	private static void setNombreMatchsPerdusImportantsPlusUn(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_IMPORTANT,
				getStatInt(equipe, TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_IMPORTANT) + 1);
	}

	private static void setNombreMatchsGagnesBanalDomicilePlusUn(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_VICTOIRES_BANAL_DOMICILE,
				getStatInt(equipe, TABLE_FIELD_NOMBRE_VICTOIRES_BANAL_DOMICILE) + 1);
	}

	private static void setNombreMatchsGagnesBanalPlusUn(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_VICTOIRES_BANAL,
				getStatInt(equipe, TABLE_FIELD_NOMBRE_VICTOIRES_BANAL) + 1);
	}

	private static void setNombreMatchsGagnesImportantsDomicilePlusUn(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_VICTOIRES_IMPORTANT_DOMICILE,
				getStatInt(equipe, TABLE_FIELD_NOMBRE_VICTOIRES_IMPORTANT_DOMICILE) + 1);
	}

	private static void setNombreMatchsGagnesImportantsPlusUn(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_VICTOIRES_IMPORTANT,
				getStatInt(equipe, TABLE_FIELD_NOMBRE_VICTOIRES_IMPORTANT) + 1);
	}

	private static void setNombreMatchsPerdusContreEquipeMoinsBienClasseeExterieurPlusUn(String equipe)
			throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_CLASSEMENT_INFERIEUR_EXTERIEUR,
				getStatInt(equipe, TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_CLASSEMENT_INFERIEUR_EXTERIEUR) + 1);
	}

	private static void setNombreMatchsPerdusContreEquipeMoinsBienClasseePlusUn(String equipe)
			throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_CLASSEMENT_INFERIEUR,
				getStatInt(equipe, TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_CLASSEMENT_INFERIEUR) + 1);
	}

	private static void setNombreMatchsGagnesContreEquipeMieuxClasseeDomicilePlusUn(String equipe)
			throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_VICTOIRES_CLASSEMENT_SUP_DOMICILE,
				getStatInt(equipe, TABLE_FIELD_NOMBRE_VICTOIRES_CLASSEMENT_SUP_DOMICILE) + 1);
	}

	private static void setNombreMatchsGagnesContreEquipeMieuxClasseePlusUn(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_VICTOIRES_CLASSEMENT_SUP,
				getStatInt(equipe, TABLE_FIELD_NOMBRE_VICTOIRES_CLASSEMENT_SUP) + 1);
	}

	private static void add3pointsDomicile(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_POINTS_DOMICILE,
				getStatInt(equipe, TABLE_FIELD_NOMBRE_POINTS_DOMICILE) + 3);
	}

	private static void add3points(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_POINTS, getStatInt(equipe, TABLE_FIELD_NOMBRE_POINTS) + 3);
	}

	private static void setNombreMatchsPerdusContreEquipeMieuxClasseeExterieurPlusUn(String equipe)
			throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_CLASSEMENT_SUPERIEUR_EXTERIEUR,
				getStatInt(equipe, TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_CLASSEMENT_SUPERIEUR_EXTERIEUR) + 1);
	}

	private static void setNombreMatchsGagnesContreEquipeMoinsBienClasseeDomicilePlusUn(String equipe)
			throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_VICTOIRES_CLASSEMENT_INF_DOMICILE,
				getStatInt(equipe, TABLE_FIELD_NOMBRE_VICTOIRES_CLASSEMENT_INF_DOMICILE) + 1);
	}

	private static void setNombreMatchsPerdusContreEquipeMieuxClasseePlusUn(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_CLASSEMENT_SUPERIEUR,
				getStatInt(equipe, TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_CLASSEMENT_SUPERIEUR) + 1);
	}

	private static void setNombreMatchsGagnesContreEquipeMoinsBienClasseePlusUn(String equipe)
			throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_VICTOIRES_CLASSEMENT_INF,
				getStatInt(equipe, TABLE_FIELD_NOMBRE_VICTOIRES_CLASSEMENT_INF) + 1);
	}

	private static void setNombreMatchsPerdusExterieurPlusUn(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_MATCHS_PERDUS_EXTERIEUR,
				getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_PERDUS_EXTERIEUR) + 1);
	}

	private static void setNombreMatchsPerdusPlusUn(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_MATCHS_PERDUS,
				getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_PERDUS) + 1);
	}

	private static void setNombreMatchsGagnesDomicilePlusUn(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_MATCHS_GAGNES_DOMICILE,
				getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_GAGNES_DOMICILE) + 1);
	}

	private static void setNombreMatchsGagnesPlusUn(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_MATCHS_GAGNES,
				getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_GAGNES) + 1);
	}

	private static void setNombreMatchsJouesBanalExterieurPlusUn(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_MATCHS_JOUES_BANAL_EXTERIEUR,
				getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_JOUES_BANAL_EXTERIEUR) + 1);
	}

	private static void setNombreMatchsJouesImportantsExterieurPlusUn(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_MATCHS_JOUES_IMPORTANTS_EXTERIEUR,
				getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_JOUES_IMPORTANTS_EXTERIEUR) + 1);
	}

	private static void setNombreMatchsJouesBanalDomicilePlusUn(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_MATCHS_JOUES_BANAL_DOMICILE,
				getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_JOUES_BANAL_DOMICILE) + 1);
	}

	private static void setNombreMatchsJouesBanalPlusUn(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_MATCHS_JOUES_BANAL,
				getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_JOUES_BANAL) + 1);
	}

	private static void setNombreMatchsJouesImportantsDomicilePlusUn(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_MATCHS_JOUES_IMPORTANTS_DOMICILE,
				getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_JOUES_IMPORTANTS_DOMICILE) + 1);
	}

	private static void setNombreMatchsJouesImportantsPlusUn(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_MATCHS_JOUES_IMPORTANTS,
				getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_JOUES_IMPORTANTS) + 1);
	}

	private static void setNombreMatchsJouesContreEquipeMoinsBienClasseePlusUn(String equipe)
			throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_MATCHS_JOUES_CLASSEMENT_INF,
				getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_JOUES_CLASSEMENT_INF) + 1);
	}

	private static void setNombreMatchsJouesContreEquipeMieuxClasseePlusUn(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_MATCHS_JOUES_CLASSEMENT_SUP,
				getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_JOUES_CLASSEMENT_SUP) + 1);
	}

	private static void setNombreMatchsJouesExterieurPlusUn(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_MATCHS_JOUES_EXTERIEUR,
				getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_JOUES_EXTERIEUR) + 1);
	}

	private static void setNombreMatchsJouesDomicilePlusUn(String equipe) throws VdocHelperException {
		setResourceEquipe(equipe, TABLE_FIELD_NOMBRE_MATCHS_JOUES_DOMICILE,
				getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_JOUES_DOMICILE) + 1);
	}

	public static boolean verifGenerale() throws NumberFormatException, VdocHelperException {

		List<EquipesLigue1> equipesDeLigue1 = listerEquipesLigue1DepuisEnumeration();

		for (EquipesLigue1 equipe : equipesDeLigue1) {
			if (!verif(equipe.toString()))
				return false;
		}
		return true;
	}

	public static boolean verif(String equipe) throws NumberFormatException, VdocHelperException {

		if (getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_GAGNES) * 3
				+ getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS) != getStatInt(equipe, TABLE_FIELD_NOMBRE_POINTS))
			return false;
		if (getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_GAGNES) + getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS)
				+ getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_PERDUS) != getStatInt(equipe,
						TABLE_FIELD_NOMBRE_MATCHS_JOUES))
			return false;
		if (getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_GAGNES_DOMICILE) + getStatInt(equipe,
				TABLE_FIELD_NOMBRE_MATCHS_GAGNES_EXTERIEUR) != getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_GAGNES))
			return false;
		if (getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_GAGNES_DOMICILE) * 3 + getStatInt(equipe,
				TABLE_FIELD_NOMBRE_MATCHS_NULS_DOMICILE) != getStatInt(equipe, TABLE_FIELD_NOMBRE_POINTS_DOMICILE))
			return false;
		if (getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_GAGNES_EXTERIEUR) * 3 + getStatInt(equipe,
				TABLE_FIELD_NOMBRE_MATCHS_NULS_EXTERIEUR) != getStatInt(equipe, TABLE_FIELD_NOMBRE_POINTS_EXTERIEUR))
			return false;
		if (getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_JOUES_DOMICILE) + getStatInt(equipe,
				TABLE_FIELD_NOMBRE_MATCHS_JOUES_EXTERIEUR) != getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_JOUES))
			return false;
		if (getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS_DOMICILE) + getStatInt(equipe,
				TABLE_FIELD_NOMBRE_MATCHS_NULS_EXTERIEUR) != getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS))
			return false;
		if (getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_PERDUS_DOMICILE) + getStatInt(equipe,
				TABLE_FIELD_NOMBRE_MATCHS_PERDUS_EXTERIEUR) != getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_PERDUS))
			return false;
		if (getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_GAGNES_DOMICILE)
				+ getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS_DOMICILE)
				+ getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_PERDUS_DOMICILE) != getStatInt(equipe,
						TABLE_FIELD_NOMBRE_MATCHS_JOUES_DOMICILE))
			return false;
		if (getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_GAGNES_EXTERIEUR)
				+ getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS_EXTERIEUR)
				+ getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_PERDUS_EXTERIEUR) != getStatInt(equipe,
						TABLE_FIELD_NOMBRE_MATCHS_JOUES_EXTERIEUR))
			return false;
		if (getStatInt(equipe, TABLE_FIELD_NOMBRE_VICTOIRES_CLASSEMENT_SUP)
				+ getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS_CLASSEMENT_SUP)
				+ getStatInt(equipe, TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_CLASSEMENT_SUPERIEUR) != getStatInt(equipe,
						TABLE_FIELD_NOMBRE_MATCHS_JOUES_CLASSEMENT_SUP))
			return false;
		if (getStatInt(equipe, TABLE_FIELD_NOMBRE_VICTOIRES_CLASSEMENT_INF)
				+ getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS_CLASSEMENT_INF)
				+ getStatInt(equipe, TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_CLASSEMENT_INFERIEUR) != getStatInt(equipe,
						TABLE_FIELD_NOMBRE_MATCHS_JOUES_CLASSEMENT_INF))
			return false;
		if (getStatInt(equipe, TABLE_FIELD_NOMBRE_VICTOIRES_CLASSEMENT_SUP_DOMICILE)
				+ getStatInt(equipe, TABLE_FIELD_NOMBRE_VICTOIRES_CLASSEMENT_SUP_EXTERIEUR) != getStatInt(equipe,
						TABLE_FIELD_NOMBRE_VICTOIRES_CLASSEMENT_SUP))
			return false;
		if (getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS_CLASSEMENT_SUP_DOMICILE)
				+ getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS_CLASSEMENT_SUP_EXTERIEUR) != getStatInt(equipe,
						TABLE_FIELD_NOMBRE_MATCHS_NULS_CLASSEMENT_SUP))
			return false;
		if (getStatInt(equipe, TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_CLASSEMENT_SUPERIEUR_DOMICILE)
				+ getStatInt(equipe, TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_CLASSEMENT_SUPERIEUR_EXTERIEUR) != getStatInt(
						equipe, TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_CLASSEMENT_SUPERIEUR))
			return false;
		if (getStatInt(equipe, TABLE_FIELD_NOMBRE_VICTOIRES_CLASSEMENT_INF_DOMICILE)
				+ getStatInt(equipe, TABLE_FIELD_NOMBRE_VICTOIRES_CLASSEMENT_INF_EXTERIEUR) != getStatInt(equipe,
						TABLE_FIELD_NOMBRE_VICTOIRES_CLASSEMENT_INF))
			return false;
		if (getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS_CLASSEMENT_INF_DOMICILE)
				+ getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS_CLASSEMENT_INF_EXTERIEUR) != getStatInt(equipe,
						TABLE_FIELD_NOMBRE_MATCHS_NULS_CLASSEMENT_INF))
			return false;
		if (getStatInt(equipe, TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_CLASSEMENT_INFERIEUR_DOMICILE)
				+ getStatInt(equipe, TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_CLASSEMENT_INFERIEUR_EXTERIEUR) != getStatInt(
						equipe, TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_CLASSEMENT_INFERIEUR))
			return false;
		if (getStatInt(equipe, TABLE_FIELD_NOMBRE_VICTOIRES_CLASSEMENT_SUP) + getStatInt(equipe,
				TABLE_FIELD_NOMBRE_VICTOIRES_CLASSEMENT_INF) != getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_GAGNES))
			return false;
		if (getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS_CLASSEMENT_SUP) + getStatInt(equipe,
				TABLE_FIELD_NOMBRE_MATCHS_NULS_CLASSEMENT_INF) != getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS))
			return false;
		if (getStatInt(equipe, TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_CLASSEMENT_SUPERIEUR)
				+ getStatInt(equipe, TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_CLASSEMENT_INFERIEUR) != getStatInt(equipe,
						TABLE_FIELD_NOMBRE_MATCHS_PERDUS))
			return false;
		if (getStatInt(equipe, TABLE_FIELD_NOMBRE_VICTOIRES_IMPORTANT)
				+ getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS_IMPORTANT)
				+ getStatInt(equipe, TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_IMPORTANT) != getStatInt(equipe,
						TABLE_FIELD_NOMBRE_MATCHS_JOUES_IMPORTANTS))
			return false;
		if (getStatInt(equipe, TABLE_FIELD_NOMBRE_VICTOIRES_BANAL)
				+ getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS_BANAL)
				+ getStatInt(equipe, TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_BANAL) != getStatInt(equipe,
						TABLE_FIELD_NOMBRE_MATCHS_JOUES_BANAL))
			return false;
		if (getStatInt(equipe, TABLE_FIELD_NOMBRE_VICTOIRES_IMPORTANT_DOMICILE)
				+ getStatInt(equipe, TABLE_FIELD_NOMBRE_VICTOIRES_IMPORTANT_EXTERIEUR) != getStatInt(equipe,
						TABLE_FIELD_NOMBRE_VICTOIRES_IMPORTANT))
			return false;
		if (getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS_IMPORTANT_DOMICILE)
				+ getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS_IMPORTANT_EXTERIEUR) != getStatInt(equipe,
						TABLE_FIELD_NOMBRE_MATCHS_NULS_IMPORTANT))
			return false;
		if (getStatInt(equipe, TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_IMPORTANT_DOMICILE)
				+ getStatInt(equipe, TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_IMPORTANT_EXTERIEUR) != getStatInt(equipe,
						TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_IMPORTANT))
			return false;
		if (getStatInt(equipe, TABLE_FIELD_NOMBRE_VICTOIRES_BANAL_DOMICILE) + getStatInt(equipe,
				TABLE_FIELD_NOMBRE_VICTOIRES_BANAL_EXTERIEUR) != getStatInt(equipe, TABLE_FIELD_NOMBRE_VICTOIRES_BANAL))
			return false;
		if (getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS_BANAL_DOMICILE)
				+ getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS_BANAL_EXTERIEUR) != getStatInt(equipe,
						TABLE_FIELD_NOMBRE_MATCHS_NULS_BANAL))
			return false;
		if (getStatInt(equipe, TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_BANAL_DOMICILE)
				+ getStatInt(equipe, TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_BANAL_EXTERIEUR) != getStatInt(equipe,
						TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_BANAL))
			return false;
		if (getStatInt(equipe, TABLE_FIELD_NOMBRE_VICTOIRES_IMPORTANT) + getStatInt(equipe,
				TABLE_FIELD_NOMBRE_VICTOIRES_BANAL) != getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_GAGNES))
			return false;
		if (getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS_IMPORTANT) + getStatInt(equipe,
				TABLE_FIELD_NOMBRE_MATCHS_NULS_BANAL) != getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_NULS))
			return false;
		if (getStatInt(equipe, TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_IMPORTANT) + getStatInt(equipe,
				TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_BANAL) != getStatInt(equipe, TABLE_FIELD_NOMBRE_MATCHS_PERDUS))
			return false;

		return true;
	}

	public static String getEquipeMieuxClassee(String e1, String e2) {
		String equipeMieuxClassee = null;
		try {
			IStorageResource equipe1 = getResourceEquipe(e1);
			IStorageResource equipe2 = getResourceEquipe(e2);

			if (equipe1 != null && equipe2 != null) {

				String c1 = (String) equipe1.getValue("classement");
				String c2 = (String) equipe2.getValue("classement");

				if (!isEmpty(c1) && !isEmpty(c2)) {
					if (Integer.parseInt(c1) < Integer.parseInt(c2)) {
						equipeMieuxClassee = e1;
					} else {
						equipeMieuxClassee = e2;
					}
				}
			}

		} catch (VdocHelperException e) {
			e.printStackTrace();
		}
		return equipeMieuxClassee;
	}
	
	public static boolean isEmpty(String string) {
		return (string == null || string.isEmpty());
	}

	public static boolean getMatchImportant(String e1, String e2) {

		if (e1.equalsIgnoreCase("MHSC")) {
			RivauxMHSC[] rivauxMHSCs = RivauxMHSC.values();
			for (RivauxMHSC rivauxMHSC : rivauxMHSCs) {
				if (rivauxMHSC.toString().equalsIgnoreCase(e2)) {
					return true;
				}
			}
		}

		if (e1.equalsIgnoreCase("PSG")) {
			RivauxPSG[] rivauxPSGs = RivauxPSG.values();
			for (RivauxPSG rivauxPSG : rivauxPSGs) {
				if (rivauxPSG.toString().equalsIgnoreCase(e2)) {
					return true;
				}
			}
		}

		if (e1.equalsIgnoreCase("OGCN")) {
			RivauxOGCN[] rivauxOGCNs = RivauxOGCN.values();
			for (RivauxOGCN rivauxOGCN : rivauxOGCNs) {
				if (rivauxOGCN.toString().equalsIgnoreCase(e2)) {
					return true;
				}
			}
		}

		if (e1.equalsIgnoreCase("LOSC")) {
			RivauxLOSC[] rivauxLOSCs = RivauxLOSC.values();
			for (RivauxLOSC rivauxLOSC : rivauxLOSCs) {
				if (rivauxLOSC.toString().equalsIgnoreCase(e2)) {
					return true;
				}
			}
		}

		if (e1.equalsIgnoreCase("FCN")) {
			RivauxFCN[] rivauxFCNs = RivauxFCN.values();
			for (RivauxFCN rivauxFCN : rivauxFCNs) {
				if (rivauxFCN.toString().equalsIgnoreCase(e2)) {
					return true;
				}
			}
		}

		if (e1.equalsIgnoreCase("ASM")) {
			RivauxASM[] rivauxASMs = RivauxASM.values();
			for (RivauxASM rivauxASM : rivauxASMs) {
				if (rivauxASM.toString().equalsIgnoreCase(e2)) {
					return true;
				}
			}
		}

		if (e1.equalsIgnoreCase("OM")) {
			RivauxOM[] rivauxOMs = RivauxOM.values();
			for (RivauxOM rivauxOM : rivauxOMs) {
				if (rivauxOM.toString().equalsIgnoreCase(e2)) {
					return true;
				}
			}
		}

		if (e1.equalsIgnoreCase("SRFC")) {
			RivauxSRFC[] rivauxSRFCs = RivauxSRFC.values();
			for (RivauxSRFC rivauxSRFC : rivauxSRFCs) {
				if (rivauxSRFC.toString().equalsIgnoreCase(e2)) {
					return true;
				}
			}
		}

		if (e1.equalsIgnoreCase("SB29")) {
			RivauxSB29[] rivauxSB29s = RivauxSB29.values();
			for (RivauxSB29 rivauxSB29 : rivauxSB29s) {
				if (rivauxSB29.toString().equalsIgnoreCase(e2)) {
					return true;
				}
			}
		}

		if (e1.equalsIgnoreCase("FCGB")) {
			RivauxFCGB[] rivauxFCGBs = RivauxFCGB.values();
			for (RivauxFCGB rivauxFCGB : rivauxFCGBs) {
				if (rivauxFCGB.toString().equalsIgnoreCase(e2)) {
					return true;
				}
			}
		}

		if (e1.equalsIgnoreCase("OL")) {
			RivauxOL[] rivauxOLs = RivauxOL.values();
			for (RivauxOL rivauxOL : rivauxOLs) {
				if (rivauxOL.toString().equalsIgnoreCase(e2)) {
					return true;
				}
			}
		}

		if (e1.equalsIgnoreCase("ASSE")) {
			RivauxASSE[] rivauxASSEs = RivauxASSE.values();
			for (RivauxASSE rivauxASSE : rivauxASSEs) {
				if (rivauxASSE.toString().equalsIgnoreCase(e2)) {
					return true;
				}
			}
		}

		if (e1.equalsIgnoreCase("SCO")) {
			RivauxSCO[] rivauxSCOs = RivauxSCO.values();
			for (RivauxSCO rivauxSCO : rivauxSCOs) {
				if (rivauxSCO.toString().equalsIgnoreCase(e2)) {
					return true;
				}
			}
		}

		if (e1.equalsIgnoreCase("RCS")) {
			RivauxRCS[] rivauxRCSs = RivauxRCS.values();
			for (RivauxRCS rivauxRCS : rivauxRCSs) {
				if (rivauxRCS.toString().equalsIgnoreCase(e2)) {
					return true;
				}
			}
		}

		if (e1.equalsIgnoreCase("FCL")) {
			RivauxFCL[] rivauxFCLs = RivauxFCL.values();
			for (RivauxFCL rivauxFCL : rivauxFCLs) {
				if (rivauxFCL.toString().equalsIgnoreCase(e2)) {
					return true;
				}
			}
		}

		if (e1.equalsIgnoreCase("SDR")) {
			RivauxSDR[] rivauxSDRs = RivauxSDR.values();
			for (RivauxSDR rivauxSDR : rivauxSDRs) {
				if (rivauxSDR.toString().equalsIgnoreCase(e2)) {
					return true;
				}
			}
		}

		if (e1.equalsIgnoreCase("FCM")) {
			RivauxFCM[] rivauxFCMs = RivauxFCM.values();
			for (RivauxFCM rivauxFCM : rivauxFCMs) {
				if (rivauxFCM.toString().equalsIgnoreCase(e2)) {
					return true;
				}
			}
		}

		if (e1.equalsIgnoreCase("DFCO")) {
			RivauxDFCO[] rivauxDFCOs = RivauxDFCO.values();
			for (RivauxDFCO rivauxDFCO : rivauxDFCOs) {
				if (rivauxDFCO.toString().equalsIgnoreCase(e2)) {
					return true;
				}
			}
		}

		if (e1.equalsIgnoreCase("NO")) {
			RivauxNO[] rivauxNOs = RivauxNO.values();
			for (RivauxNO rivauxNO : rivauxNOs) {
				if (rivauxNO.toString().equalsIgnoreCase(e2)) {
					return true;
				}
			}
		}

		if (e1.equalsIgnoreCase("RCL")) {
			RivauxRCL[] rivauxRCLs = RivauxRCL.values();
			for (RivauxRCL rivauxRCL : rivauxRCLs) {
				if (rivauxRCL.toString().equalsIgnoreCase(e2)) {
					return true;
				}
			}
		}

		return false;
	}

}
