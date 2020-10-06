package com.doandgo.ligue1.matchs;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import com.axemble.vdoc.sdk.document.extensions.BaseDocumentExtension;
import com.axemble.vdoc.sdk.interfaces.IAction;
import com.axemble.vdoc.sdk.interfaces.IStorageResource;
import com.axemble.vdoc.sdk.interfaces.IWorkflowInstance;
import com.doandgo.ligue1.utils.UtilitaireLigue1;
import com.doandgo.moovapps.exceptions.VdocHelperException;

/**
 * Classe qui génère le rapport du match dans le dossier "C:/perso/Ligue1"
 * 
 * @author Thomas CHARMES
 *
 */
public class GenerateRapportStatistiques extends BaseDocumentExtension {

	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private static final Float coefficientA = 0.5F;
	private static final Float coefficientB = 1F;
	private static final Float coefficientC = 1.5F;
	private static final Float coefficientD = 2F;
	@SuppressWarnings("unused")
	private static final Float coefficientE = 2.5F;
	private static final Float coefficientF = 3F;

	@Override
	public boolean onBeforeSubmit(IAction action) {

		if (action.getName().equals(UtilitaireLigue1.FORM_BUTTON_GENERER_RAPPORT_STATISTIQUES)) {

			IWorkflowInstance wi = getWorkflowInstance();
			IStorageResource statistiques = getStatistiquesFromMatch(wi);
			try {
				generateRapport(statistiques, wi);
				wi.setValue(UtilitaireLigue1.FORM_FIELD_CHECK_BOX_COMPTABILISE_RAPPORT_STATISTIQUES_JOURNEE, false);
				wi.save(getWorkflowModule().getSysadminContext());
				getResourceController().alert("Le rapport des statistiques du match a bien été publié");
			} catch (IOException | VdocHelperException e) {
				e.getMessage();
				e.printStackTrace();
			}
		}

		return super.onBeforeSubmit(action);
	}

	private void generateRapport(IStorageResource statistiques, IWorkflowInstance wi)
			throws IOException, VdocHelperException {

		String match = (String) statistiques.getValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_MATCH);

		XWPFDocument document = new XWPFDocument();
		File rapport_match = new File("C:\\perso\\Ligue1\\rapport_" + match + ".docx");
		FileOutputStream out = new FileOutputStream(rapport_match);

		TreeMap<String, String> mapTreeStatsGenerales = getTreeMapStatsVictoires(statistiques, match);
		String[] teams = match.split("-");
		String homeTeam = teams[0];
		String awayTeam = teams[1];
		String generalStandingOfHomeTeam;
		String generalStandingOfAwayTeam;
		try {
			generalStandingOfHomeTeam = getGeneralStandingAndPointsNumber(homeTeam);
			generalStandingOfAwayTeam = getGeneralStandingAndPointsNumber(awayTeam);

			writeMapInWordDocument(
					"Stats des 5 dernières confrontations entre " + homeTeam + " (" + generalStandingOfHomeTeam
							+ ") et " + awayTeam + " (" + generalStandingOfAwayTeam + ")",
					mapTreeStatsGenerales, document);
		} catch (NullStatException e) {
			getResourceController().alert(
					"Erreur à la récupération du classement et du nombre de points d'une équipe : " + e.getMessage());
			e.printStackTrace();
		}

		IStorageResource confrontation = UtilitaireLigue1.getResourceConfrontation(match);

		document.createParagraph().createRun()
				.setText("Match le plus récent " + match + "--> "
						+ confrontation.getValue(UtilitaireLigue1.TABLE_CONFRONTATIONS_FIELD_RECENT_1) + "\n" + "----- "
						+ confrontation.getValue(UtilitaireLigue1.TABLE_CONFRONTATIONS_FIELD_RECENT_2) + "\n" + "----- "
						+ confrontation.getValue(UtilitaireLigue1.TABLE_CONFRONTATIONS_FIELD_RECENT_3) + "\n" + "----- "
						+ confrontation.getValue(UtilitaireLigue1.TABLE_CONFRONTATIONS_FIELD_RECENT_4) + "\n" + "----- "
						+ confrontation.getValue(UtilitaireLigue1.TABLE_CONFRONTATIONS_FIELD_RECENT_5)
						+ " <-- Match le plus ancien");

		Map<String, String> mapStatsLDEM = getTreeMapStatsLDEM(statistiques, match);
		writeMapInWordDocument("" + match, mapStatsLDEM, document);

		TreeMap<String, String> mapTreeStatsMoyennes = getTreeMapStatsMoyennes(statistiques, match);
		writeMapInWordDocument("Statistiques des moyennes de buts du match lors des dernières confrontations " + match,
				mapTreeStatsMoyennes, document);

		TreeMap<String, String> mapTreeStatsNombreDeButsMatch = getTreeMapStatsNombreButsMatch(statistiques, match, wi);
		writeMapInWordDocument("Statistiques sur le nombre de buts du match lors des dernières confrontations " + match,
				mapTreeStatsNombreDeButsMatch, document);

		TreeMap<String, String> mapTreeStatsNombreDeButsE1 = getTreeMapStatsNombreButsE1(statistiques, match);
		writeMapInWordDocument(
				"Statistiques sur le nombre de buts de l'équipe à domicile lors des dernières confrontations ",
				mapTreeStatsNombreDeButsE1, document);

		TreeMap<String, String> mapTreeStatsNombreDeButsE2 = getTreeMapStatsNombreButsE2(statistiques, match);
		writeMapInWordDocument(
				"Statistiques sur le nombre de buts de l'équipe à l'extérieur lors des dernières confrontations ",
				mapTreeStatsNombreDeButsE2, document);

		TreeMap<String, String> mapTreeStatsNombreExactDeButsE1 = getTreeMapStatsNombreExactButsE1(statistiques, match);
		writeMapInWordDocument(
				"Statistiques sur le nombre exact de buts marqués lors des dernières confrontations par l'équipe à domicile ",
				mapTreeStatsNombreExactDeButsE1, document);

		TreeMap<String, String> mapTreeStatsNombreExactDeButsE2 = getTreeMapStatsNombreExactButsE2(statistiques, match);
		writeMapInWordDocument(
				"Statistiques sur le nombre exact de buts marqués lors des dernières confrontations par l'équipe à l'extérieur ",
				mapTreeStatsNombreExactDeButsE2, document);

		TreeMap<String, String> mapTreeStatsEcartButE1 = getTreeMapStatsEcartButsE1(statistiques, match);
		writeMapInWordDocument(
				"Statistiques sur l'écart de score en faveur de l'équipe à domicile lors des dernières confrontations ",
				mapTreeStatsEcartButE1, document);

		TreeMap<String, String> mapTreeStatsEcartButE2 = getTreeMapStatsEcartButsE2(statistiques, match);
		writeMapInWordDocument(
				"Statistiques sur l'écart de score en faveur de l'équipe à l'extérieur lors des dernières confrontations ",
				mapTreeStatsEcartButE2, document);

		Boolean e1MieuxClassee = (Boolean) wi
				.getValue(UtilitaireLigue1.FORM_FIELD_CHECK_BOX_EQUIPE_DOMICILE_MIEUX_CLASSEE);
		Boolean e1MatchImportant = (Boolean) wi
				.getValue(UtilitaireLigue1.FORM_FIELD_CHECK_BOX_IMPORTANT_EQUIPE_DOMICILE);
		Boolean e2MatchImportant = (Boolean) wi
				.getValue(UtilitaireLigue1.FORM_FIELD_CHECK_BOX_IMPORTANT_EQUIPE_EXTERIEUR);
		String formE1 = (String) wi.getValue(UtilitaireLigue1.FORM_FIELD_SURNOM_EQUIPE_DOMICILE);
		String formE2 = (String) wi.getValue(UtilitaireLigue1.FORM_FIELD_SURNOM_EQUIPE_EXTERIEUR);

		if (UtilitaireLigue1.isEmpty(formE1) || UtilitaireLigue1.isEmpty(formE2)) {
			getResourceController().alert("Au moins l'une des deux équipes n'est pas renseignée");
		}

		writeHomeTeamStatsFromTableEquipes(document, formE1, e1MieuxClassee, e1MatchImportant);

		writeAwayTeamStatsFromTableEquipes(document, formE2, e1MieuxClassee, e2MatchImportant);

		TreeMap<String, String> mapTreeStatsPronostics = getTreeMapStatsPronostics(statistiques, match, wi);
		writeMapInWordDocument("Probabilités du résultat : ", mapTreeStatsPronostics, document);

		document.write(out);
		out.close();
	}

	private String getGeneralStandingAndPointsNumber(String team) throws NullStatException {

		String resultat = "";
		String classement = null;
		int nbPoints = -1;
		try {
			classement = UtilitaireLigue1.getStatString(team, UtilitaireLigue1.TABLE_FIELD_STRING_CLASSEMENT);
			nbPoints = UtilitaireLigue1.getStatInt(team, UtilitaireLigue1.TABLE_FIELD_NOMBRE_POINTS);
		} catch (VdocHelperException e) {
			return null;
		}

		if (classement == null || nbPoints == -1) {
			throw new NullStatException(
					"Erreur lors de la récupération du classement ou du nombre de points de l'équipe " + team);
		}

		if (classement.equals("1")) {
			resultat = classement + "er : " + nbPoints + " pts";
		} else {
			resultat = classement + "eme : " + nbPoints + " pts";
		}

		return resultat;
	}

	private void writeHomeTeamStatsFromTableEquipes(XWPFDocument document, String team1,
			Boolean homeTeamHasBestStanding, Boolean isImportantForTeam1) {

		try {
			IStorageResource equipe = UtilitaireLigue1.getResourceEquipe(team1);
			if (equipe == null) {
				getResourceController().alert(
						"Erreur à la récupération de la resource équipe de la méthode writeHomeTeamStatsFromTableEquipes()");
				return;
			}

			// Stats globales

			Long nbMatchsGagnes = (Long) equipe.getValue(UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_GAGNES);
			Long nbMatchsJoues = (Long) equipe.getValue(UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_JOUES);
			Long nbMatchsNuls = (Long) equipe.getValue(UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_NULS);
			Long nbMatchsPerdus = (Long) equipe.getValue(UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_PERDUS);
			Long serieD = (Long) equipe.getValue(UtilitaireLigue1.TABLE_FIELD_SERIE_D_EN_COURS);
			Long serieN = (Long) equipe.getValue(UtilitaireLigue1.TABLE_FIELD_SERIE_N_EN_COURS);
			Long serieV = (Long) equipe.getValue(UtilitaireLigue1.TABLE_FIELD_SERIE_V_EN_COURS);

			Float pourcentageVictoires = null;
			Float pourcentageNuls = null;
			Float pourcentageDefaites = null;

			XWPFRun run = document.createParagraph().createRun();
			run.setBold(true);
			run.setFontSize(14);
			run.setText("Statistiques générales de l'équipe " + team1);

			if (nbMatchsJoues != 0L) {
				pourcentageVictoires = (nbMatchsGagnes.floatValue() / nbMatchsJoues.floatValue()) * 100;
				pourcentageNuls = (nbMatchsNuls.floatValue() / nbMatchsJoues.floatValue()) * 100;
				pourcentageDefaites = (nbMatchsPerdus.floatValue() / nbMatchsJoues.floatValue()) * 100;

				document.createParagraph().createRun()
						.setText("Pourcentage de victoires : " + pourcentageVictoires + "%");
				document.createParagraph().createRun().setText("Pourcentage de matchs nuls : " + pourcentageNuls + "%");
				document.createParagraph().createRun()
						.setText("Pourcentage de défaites : " + pourcentageDefaites + "%");
			} else {
				document.createParagraph().createRun().setText("L'équipe " + team1
						+ "  va jouer son premier match de la saison, aucune donnée n'est disponible pour le moment.");
			}

			if (serieV > 0L)
				document.createParagraph().createRun().setText(
						"Nombre de victoires sur les 5 derniers matchs de l'équipe " + team1 + " : " + serieV + " V");
			if (serieN > 0L)
				document.createParagraph().createRun().setText(
						"Nombre de matchs nuls sur les 5 derniers matchs de l'équipe " + team1 + " : " + serieN + " N");
			if (serieD > 0L)
				document.createParagraph().createRun().setText(
						"Nombre de défaites sur les 5 derniers matchs de l'équipe " + team1 + " : " + serieD + " D");

			// Stats domicile

			Float pourcentageVictoiresDomicile = null;
			Float pourcentageNulsDomicile = null;
			Float pourcentageDefaitesDomicile = null;

			Long nbMatchsGagnesDomicile = (Long) equipe
					.getValue(UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_GAGNES_DOMICILE);
			Long nbMatchsJouesDomicile = (Long) equipe
					.getValue(UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_JOUES_DOMICILE);
			Long nbMatchsNulsDomicile = (Long) equipe
					.getValue(UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_NULS_DOMICILE);
			Long nbMatchsPerdusDomicile = (Long) equipe
					.getValue(UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_PERDUS_DOMICILE);
			Long serieDDomicile = (Long) equipe.getValue(UtilitaireLigue1.TABLE_FIELD_SERIE_D_EN_COURS_DOMICILE);
			Long serieNDomicile = (Long) equipe.getValue(UtilitaireLigue1.TABLE_FIELD_SERIE_N_EN_COURS_DOMICILE);
			Long serieVDomicile = (Long) equipe.getValue(UtilitaireLigue1.TABLE_FIELD_SERIE_V_EN_COURS_DOMICILE);

			XWPFRun runDom = document.createParagraph().createRun();
			runDom.setBold(true);
			runDom.setFontSize(14);
			runDom.setText("Statistiques à domicile de l'équipe " + team1);

			if (nbMatchsJouesDomicile != 0L) {
				pourcentageVictoiresDomicile = (nbMatchsGagnesDomicile.floatValue()
						/ nbMatchsJouesDomicile.floatValue()) * 100;
				pourcentageNulsDomicile = (nbMatchsNulsDomicile.floatValue() / nbMatchsJouesDomicile.floatValue())
						* 100;
				pourcentageDefaitesDomicile = (nbMatchsPerdusDomicile.floatValue() / nbMatchsJouesDomicile.floatValue())
						* 100;

				document.createParagraph().createRun()
						.setText("Pourcentage de victoires à domicile : " + pourcentageVictoiresDomicile + "%");
				document.createParagraph().createRun()
						.setText("Pourcentage de matchs nuls à domicile : " + pourcentageNulsDomicile + "%");
				document.createParagraph().createRun()
						.setText("Pourcentage de défaites à domicile : " + pourcentageDefaitesDomicile + "%");
			} else {
				document.createParagraph().createRun().setText("L'équipe " + team1
						+ " va jouer son premier match de la saison à domicile, aucune donnée n'est disponible pour le moment.");
			}

			if (serieVDomicile > 0L)
				document.createParagraph().createRun()
						.setText("Nombre de victoires à domicile sur les 5 derniers matchs de l'équipe " + team1 + " : "
								+ serieVDomicile + " V");
			if (serieNDomicile > 0L)
				document.createParagraph().createRun()
						.setText("Nombre de matchs nuls à domicile sur les 5 derniers matchs de l'équipe " + team1
								+ " : " + serieNDomicile + " N");
			if (serieDDomicile > 0L)
				document.createParagraph().createRun()
						.setText("Nombre de défaites à domicile sur les 5 derniers matchs de l'équipe " + team1 + " : "
								+ serieDDomicile + " D");

			Long nbDefaitesContreClassementInf;
			Long nbDefaitesContreClassementInfDomicile;
			Long nbMatchsJouesContreClassementInf;
			Long nbMatchsJouesContreClassementInfDomicile;
			Long nbMatchsNulsContreClassementInf;
			Long nbMatchsNulsContreClassementInfDomicile;
			Long nbVictoiresContreClassementInf;
			Long nbVictoiresContreClassementInfDomicile;

			Float pourcentageDefaitesContreClassementInf = null;
			Float pourcentageDefaitesContreClassementInfDomicile = null;
			Float pourcentageNulsContreClassementInf = null;
			Float pourcentageNulsContreClassementInfDomicile = null;
			Float pourcentageVictoiresContreClassementInf = null;
			Float pourcentageVictoiresContreClassementInfDomicile = null;

			Long nbDefaitesContreClassementSup;
			Long nbDefaitesContreClassementSupDomicile;
			Long nbMatchsJouesContreClassementSup;
			Long nbMatchsJouesContreClassementSupDomicile;
			Long nbMatchsNulsContreClassementSup;
			Long nbMatchsNulsContreClassementSupDomicile;
			Long nbVictoiresContreClassementSup;
			Long nbVictoiresContreClassementSupDomicile;

			Float pourcentageDefaitesContreClassementSup = null;
			Float pourcentageDefaitesContreClassementSupDomicile = null;
			Float pourcentageNulsContreClassementSup = null;
			Float pourcentageNulsContreClassementSupDomicile = null;
			Float pourcentageVictoiresContreClassementSup = null;
			Float pourcentageVictoiresContreClassementSupDomicile = null;

			Long nbDefaitesContreImportant;
			Long nbDefaitesContreImportantDomicile;
			Long nbMatchsJouesContreImportant;
			Long nbMatchsJouesContreImportantDomicile;
			Long nbMatchsNulsContreImportant;
			Long nbMatchsNulsContreImportantDomicile;
			Long nbVictoiresContreImportant;
			Long nbVictoiresContreImportantDomicile;

			Float pourcentageDefaitesContreImportant = null;
			Float pourcentageDefaitesContreImportantDomicile = null;
			Float pourcentageNulsContreImportant = null;
			Float pourcentageNulsContreImportantDomicile = null;
			Float pourcentageVictoiresContreImportant = null;
			Float pourcentageVictoiresContreImportantDomicile = null;

			Long nbDefaitesContreBanal;
			Long nbDefaitesContreBanalDomicile;
			Long nbMatchsJouesContreBanal;
			Long nbMatchsJouesContreBanalDomicile;
			Long nbMatchsNulsContreBanal;
			Long nbMatchsNulsContreBanalDomicile;
			Long nbVictoiresContreBanal;
			Long nbVictoiresContreBanalDomicile;

			Float pourcentageDefaitesContreBanal = null;
			Float pourcentageDefaitesContreBanalDomicile = null;
			Float pourcentageNulsContreBanal = null;
			Float pourcentageNulsContreBanalDomicile = null;
			Float pourcentageVictoiresContreBanal = null;
			Float pourcentageVictoiresContreBanalDomicile = null;

			if (homeTeamHasBestStanding) {

				nbDefaitesContreClassementInf = (Long) equipe
						.getValue(UtilitaireLigue1.TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_CLASSEMENT_INFERIEUR);
				nbDefaitesContreClassementInfDomicile = (Long) equipe
						.getValue(UtilitaireLigue1.TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_CLASSEMENT_INFERIEUR_DOMICILE);
				nbMatchsJouesContreClassementInf = (Long) equipe
						.getValue(UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_JOUES_CLASSEMENT_INF);
				nbMatchsJouesContreClassementInfDomicile = (Long) equipe
						.getValue(UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_JOUES_CLASSEMENT_INF_DOMICILE);
				nbMatchsNulsContreClassementInf = (Long) equipe
						.getValue(UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_NULS_CLASSEMENT_INF);
				nbMatchsNulsContreClassementInfDomicile = (Long) equipe
						.getValue(UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_NULS_CLASSEMENT_INF_DOMICILE);
				nbVictoiresContreClassementInf = (Long) equipe
						.getValue(UtilitaireLigue1.TABLE_FIELD_NOMBRE_VICTOIRES_CLASSEMENT_INF);
				nbVictoiresContreClassementInfDomicile = (Long) equipe
						.getValue(UtilitaireLigue1.TABLE_FIELD_NOMBRE_VICTOIRES_CLASSEMENT_INF_DOMICILE);

				XWPFRun runClassementInf = document.createParagraph().createRun();
				runClassementInf.setBold(true);
				runClassementInf.setFontSize(14);
				runClassementInf.setText("Statistiques de l'équipe " + team1 + " contre une équipe moins bien classée");

				if (nbMatchsJouesContreClassementInf != 0L) {
					pourcentageVictoiresContreClassementInf = (nbVictoiresContreClassementInf.floatValue()
							/ nbMatchsJouesContreClassementInf.floatValue()) * 100;
					pourcentageNulsContreClassementInf = (nbMatchsNulsContreClassementInf.floatValue()
							/ nbMatchsJouesContreClassementInf.floatValue()) * 100;
					pourcentageDefaitesContreClassementInf = (nbDefaitesContreClassementInf.floatValue()
							/ nbMatchsJouesContreClassementInf.floatValue()) * 100;

					document.createParagraph().createRun()
							.setText("Pourcentage de victoires contre une équipe moins bien classée : "
									+ pourcentageVictoiresContreClassementInf + "%");
					document.createParagraph().createRun()
							.setText("Pourcentage de matchs nuls contre une équipe moins bien classée : "
									+ pourcentageNulsContreClassementInf + "%");
					document.createParagraph().createRun()
							.setText("Pourcentage de défaites contre une équipe moins bien classée : "
									+ pourcentageDefaitesContreClassementInf + "%");
				} else {
					document.createParagraph().createRun().setText("L'équipe " + team1
							+ " va jouer son premier match de la saison contre une équipe moins bien classée qu'elle, aucune donnée n'est disponible pour le moment.");
				}

				XWPFRun runClassementInfDom = document.createParagraph().createRun();
				runClassementInfDom.setBold(true);
				runClassementInfDom.setFontSize(14);
				runClassementInfDom.setText(
						"Statistiques de l'équipe " + team1 + " à domicile contre une équipe moins bien classée");

				if (nbMatchsJouesContreClassementInfDomicile != 0L) {
					pourcentageVictoiresContreClassementInfDomicile = (nbVictoiresContreClassementInfDomicile
							.floatValue() / nbMatchsJouesContreClassementInfDomicile.floatValue()) * 100;
					pourcentageNulsContreClassementInfDomicile = (nbMatchsNulsContreClassementInfDomicile.floatValue()
							/ nbMatchsJouesContreClassementInfDomicile.floatValue()) * 100;
					pourcentageDefaitesContreClassementInfDomicile = (nbDefaitesContreClassementInfDomicile.floatValue()
							/ nbMatchsJouesContreClassementInfDomicile.floatValue()) * 100;

					document.createParagraph().createRun()
							.setText("Pourcentage de victoires à domicile contre une équipe moins bien classée : "
									+ pourcentageVictoiresContreClassementInfDomicile + "%");
					document.createParagraph().createRun()
							.setText("Pourcentage de matchs nuls à domicile contre une équipe moins bien classée : "
									+ pourcentageNulsContreClassementInfDomicile + "%");
					document.createParagraph().createRun()
							.setText("Pourcentage de défaites à domicile contre une équipe moins bien classée : "
									+ pourcentageDefaitesContreClassementInfDomicile + "%");
				} else {
					document.createParagraph().createRun().setText("L'équipe " + team1
							+ " va jouer son premier match de la saison à domicile contre une équipe moins bien classée qu'elle, aucune donnée n'est disponible pour le moment.");
				}

			} else {
				nbDefaitesContreClassementSup = (Long) equipe
						.getValue(UtilitaireLigue1.TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_CLASSEMENT_SUPERIEUR);
				nbDefaitesContreClassementSupDomicile = (Long) equipe
						.getValue(UtilitaireLigue1.TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_CLASSEMENT_SUPERIEUR_DOMICILE);
				nbMatchsJouesContreClassementSup = (Long) equipe
						.getValue(UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_JOUES_CLASSEMENT_SUP);
				nbMatchsJouesContreClassementSupDomicile = (Long) equipe
						.getValue(UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_JOUES_CLASSEMENT_SUP_DOMICILE);
				nbMatchsNulsContreClassementSup = (Long) equipe
						.getValue(UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_NULS_CLASSEMENT_SUP);
				nbMatchsNulsContreClassementSupDomicile = (Long) equipe
						.getValue(UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_NULS_CLASSEMENT_SUP_DOMICILE);
				nbVictoiresContreClassementSup = (Long) equipe
						.getValue(UtilitaireLigue1.TABLE_FIELD_NOMBRE_VICTOIRES_CLASSEMENT_SUP);
				nbVictoiresContreClassementSupDomicile = (Long) equipe
						.getValue(UtilitaireLigue1.TABLE_FIELD_NOMBRE_VICTOIRES_CLASSEMENT_SUP_DOMICILE);

				XWPFRun runClassementSup = document.createParagraph().createRun();
				runClassementSup.setBold(true);
				runClassementSup.setFontSize(14);
				runClassementSup.setText("Statistiques de l'équipe " + team1 + " contre une équipe mieux classée");

				if (nbMatchsJouesContreClassementSup != 0L) {
					pourcentageVictoiresContreClassementSup = (nbVictoiresContreClassementSup.floatValue()
							/ nbMatchsJouesContreClassementSup.floatValue()) * 100;
					pourcentageNulsContreClassementSup = (nbMatchsNulsContreClassementSup.floatValue()
							/ nbMatchsJouesContreClassementSup.floatValue()) * 100;
					pourcentageDefaitesContreClassementSup = (nbDefaitesContreClassementSup.floatValue()
							/ nbMatchsJouesContreClassementSup.floatValue()) * 100;

					document.createParagraph().createRun()
							.setText("Pourcentage de victoires contre une équipe mieux classée : "
									+ pourcentageVictoiresContreClassementSup + "%");
					document.createParagraph().createRun()
							.setText("Pourcentage de matchs nuls contre une équipe mieux classée : "
									+ pourcentageNulsContreClassementSup + "%");
					document.createParagraph().createRun()
							.setText("Pourcentage de défaites contre une équipe mieux classée : "
									+ pourcentageDefaitesContreClassementSup + "%");
				} else {
					document.createParagraph().createRun().setText("L'équipe " + team1
							+ " va jouer son premier match de la saison contre une équipe mieux classée qu'elle, aucune donnée n'est disponible pour le moment.");
				}

				XWPFRun runClassementSupDomicile = document.createParagraph().createRun();
				runClassementSupDomicile.setBold(true);
				runClassementSupDomicile.setFontSize(14);
				runClassementSupDomicile
						.setText("Statistiques de l'équipe " + team1 + " à domicile contre une équipe mieux classée");

				if (nbMatchsJouesContreClassementSupDomicile != 0L) {
					pourcentageVictoiresContreClassementSupDomicile = (nbVictoiresContreClassementSupDomicile
							.floatValue() / nbMatchsJouesContreClassementSupDomicile.floatValue()) * 100;
					pourcentageNulsContreClassementSupDomicile = (nbMatchsNulsContreClassementSupDomicile.floatValue()
							/ nbMatchsJouesContreClassementSupDomicile.floatValue()) * 100;
					pourcentageDefaitesContreClassementSupDomicile = (nbDefaitesContreClassementSupDomicile.floatValue()
							/ nbMatchsJouesContreClassementSupDomicile.floatValue()) * 100;

					document.createParagraph().createRun()
							.setText("Pourcentage de victoires à domicile contre une équipe mieux classée : "
									+ pourcentageVictoiresContreClassementSupDomicile + "%");
					document.createParagraph().createRun()
							.setText("Pourcentage de matchs nuls à domicile contre une équipe mieux classée : "
									+ pourcentageNulsContreClassementSupDomicile + "%");
					document.createParagraph().createRun()
							.setText("Pourcentage de défaites à domicile contre une équipe mieux classée : "
									+ pourcentageDefaitesContreClassementSupDomicile + "%");
				} else {
					document.createParagraph().createRun().setText("L'équipe " + team1
							+ " va jouer son premier match de la saison à domicile contre une équipe mieux classée qu'elle, aucune donnée n'est disponible pour le moment.");
				}
			}

			if (isImportantForTeam1) {

				nbDefaitesContreImportant = (Long) equipe
						.getValue(UtilitaireLigue1.TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_IMPORTANT);
				nbDefaitesContreImportantDomicile = (Long) equipe
						.getValue(UtilitaireLigue1.TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_IMPORTANT_DOMICILE);
				nbMatchsJouesContreImportant = (Long) equipe
						.getValue(UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_JOUES_IMPORTANTS);
				nbMatchsJouesContreImportantDomicile = (Long) equipe
						.getValue(UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_JOUES_IMPORTANTS_DOMICILE);
				nbMatchsNulsContreImportant = (Long) equipe
						.getValue(UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_NULS_IMPORTANT);
				nbMatchsNulsContreImportantDomicile = (Long) equipe
						.getValue(UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_NULS_IMPORTANT_DOMICILE);
				nbVictoiresContreImportant = (Long) equipe
						.getValue(UtilitaireLigue1.TABLE_FIELD_NOMBRE_VICTOIRES_IMPORTANT);
				nbVictoiresContreImportantDomicile = (Long) equipe
						.getValue(UtilitaireLigue1.TABLE_FIELD_NOMBRE_VICTOIRES_IMPORTANT_DOMICILE);

				XWPFRun runImportant = document.createParagraph().createRun();
				runImportant.setBold(true);
				runImportant.setFontSize(14);
				runImportant.setText("Statistiques de l'équipe " + team1 + " dans un match important");

				if (nbMatchsJouesContreImportant != 0L) {
					pourcentageVictoiresContreImportant = (nbVictoiresContreImportant.floatValue()
							/ nbMatchsJouesContreImportant.floatValue()) * 100;
					pourcentageNulsContreImportant = (nbMatchsNulsContreImportant.floatValue()
							/ nbMatchsJouesContreImportant.floatValue()) * 100;
					pourcentageDefaitesContreImportant = (nbDefaitesContreImportant.floatValue()
							/ nbMatchsJouesContreImportant.floatValue()) * 100;

					document.createParagraph().createRun().setText("Pourcentage de victoires dans un match important : "
							+ pourcentageVictoiresContreImportant + "%");
					document.createParagraph().createRun()
							.setText("Pourcentage de matchs nuls dans un match important : "
									+ pourcentageNulsContreImportant + "%");
					document.createParagraph().createRun().setText("Pourcentage de défaites dans un match important : "
							+ pourcentageDefaitesContreImportant + "%");
				} else {
					document.createParagraph().createRun().setText("L'équipe " + team1
							+ "  va jouer son premier match important de la saison, aucune donnée n'est disponible pour le moment.");
				}

				XWPFRun runImportantDomicile = document.createParagraph().createRun();
				runImportantDomicile.setBold(true);
				runImportantDomicile.setFontSize(14);
				runImportantDomicile
						.setText("Statistiques de l'équipe " + team1 + " à domicile dans un match important");

				if (nbMatchsJouesContreImportantDomicile != 0L) {
					pourcentageVictoiresContreImportantDomicile = (nbVictoiresContreImportantDomicile.floatValue()
							/ nbMatchsJouesContreImportantDomicile.floatValue()) * 100;
					pourcentageNulsContreImportantDomicile = (nbMatchsNulsContreImportantDomicile.floatValue()
							/ nbMatchsJouesContreImportantDomicile.floatValue()) * 100;
					pourcentageDefaitesContreImportantDomicile = (nbDefaitesContreImportantDomicile.floatValue()
							/ nbMatchsJouesContreImportantDomicile.floatValue()) * 100;

					document.createParagraph().createRun()
							.setText("Pourcentage de victoires à domicile dans un match important : "
									+ pourcentageVictoiresContreImportantDomicile + "%");
					document.createParagraph().createRun()
							.setText("Pourcentage de matchs nuls à domicile dans un match important : "
									+ pourcentageNulsContreImportantDomicile + "%");
					document.createParagraph().createRun()
							.setText("Pourcentage de défaites à domicile dans un match important : "
									+ pourcentageDefaitesContreImportantDomicile + "%");
				} else {
					document.createParagraph().createRun().setText("L'équipe " + team1
							+ " va jouer son premier match important de la saison à domicile, aucune donnée n'est disponible pour le moment.");
				}

			} else {

				nbDefaitesContreBanal = (Long) equipe
						.getValue(UtilitaireLigue1.TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_BANAL);
				nbDefaitesContreBanalDomicile = (Long) equipe
						.getValue(UtilitaireLigue1.TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_BANAL_DOMICILE);
				nbMatchsJouesContreBanal = (Long) equipe
						.getValue(UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_JOUES_BANAL);
				nbMatchsJouesContreBanalDomicile = (Long) equipe
						.getValue(UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_JOUES_BANAL_DOMICILE);
				nbMatchsNulsContreBanal = (Long) equipe.getValue(UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_NULS_BANAL);
				nbMatchsNulsContreBanalDomicile = (Long) equipe
						.getValue(UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_NULS_BANAL_DOMICILE);
				nbVictoiresContreBanal = (Long) equipe.getValue(UtilitaireLigue1.TABLE_FIELD_NOMBRE_VICTOIRES_BANAL);
				nbVictoiresContreBanalDomicile = (Long) equipe
						.getValue(UtilitaireLigue1.TABLE_FIELD_NOMBRE_VICTOIRES_BANAL_DOMICILE);

				XWPFRun runBanal = document.createParagraph().createRun();
				runBanal.setBold(true);
				runBanal.setFontSize(14);
				runBanal.setText(
						"Statistiques de l'équipe " + team1 + " dans un match qui n'est pas considéré comme important");

				if (nbMatchsJouesContreBanal != 0L) {
					pourcentageVictoiresContreBanal = (nbVictoiresContreBanal.floatValue()
							/ nbMatchsJouesContreBanal.floatValue()) * 100;
					pourcentageNulsContreBanal = (nbMatchsNulsContreBanal.floatValue()
							/ nbMatchsJouesContreBanal.floatValue()) * 100;
					pourcentageDefaitesContreBanal = (nbDefaitesContreBanal.floatValue()
							/ nbMatchsJouesContreBanal.floatValue()) * 100;

					document.createParagraph().createRun().setText("Pourcentage de victoires dans un match classique : "
							+ pourcentageVictoiresContreBanal + "%");
					document.createParagraph().createRun().setText(
							"Pourcentage de matchs nuls dans un match classique : " + pourcentageNulsContreBanal + "%");
					document.createParagraph().createRun().setText("Pourcentage de défaites dans un match classique : "
							+ pourcentageDefaitesContreBanal + "%");
				} else {
					document.createParagraph().createRun().setText("L'équipe " + team1
							+ " va jouer son premier match classique de la saison, aucune donnée n'est disponible pour le moment.");
				}

				XWPFRun runBanalDomicile = document.createParagraph().createRun();
				runBanalDomicile.setBold(true);
				runBanalDomicile.setFontSize(14);
				runBanalDomicile.setText("Statistiques de l'équipe " + team1
						+ " à domicile dans un match qui n'est pas considéré comme important");

				if (nbMatchsJouesContreBanalDomicile != 0L) {
					pourcentageVictoiresContreBanalDomicile = (nbVictoiresContreBanalDomicile.floatValue()
							/ nbMatchsJouesContreBanalDomicile.floatValue()) * 100;
					pourcentageNulsContreBanalDomicile = (nbMatchsNulsContreBanalDomicile.floatValue()
							/ nbMatchsJouesContreBanalDomicile.floatValue()) * 100;
					pourcentageDefaitesContreBanalDomicile = (nbDefaitesContreBanalDomicile.floatValue()
							/ nbMatchsJouesContreBanalDomicile.floatValue()) * 100;

					document.createParagraph().createRun()
							.setText("Pourcentage de victoires à domicile dans un match classique : "
									+ pourcentageVictoiresContreBanalDomicile + "%");
					document.createParagraph().createRun()
							.setText("Pourcentage de matchs nuls à domicile dans un match classique : "
									+ pourcentageNulsContreBanalDomicile + "%");
					document.createParagraph().createRun()
							.setText("Pourcentage de défaites à domicile dans un match classique : "
									+ pourcentageDefaitesContreBanalDomicile + "%");
				} else {
					document.createParagraph().createRun().setText("L'équipe " + team1
							+ " va jouer son premier match classique de la saison à domicile, aucune donnée n'est disponible pour le moment.");
				}

			}

		} catch (VdocHelperException e) {
			getResourceController().alert(
					"Erreur à la récupération de la resource équipe de la méthode writeHomeTeamStatsFromTableEquipes()");
			e.getMessage();
			e.printStackTrace();
		}

	}

	private void writeAwayTeamStatsFromTableEquipes(XWPFDocument document, String team2,
			Boolean homeTeamHasBestStanding, Boolean isImportantForTeam2) {

		try {
			IStorageResource equipe = UtilitaireLigue1.getResourceEquipe(team2);
			if (equipe == null) {
				getResourceController().alert(
						"Erreur à la récupération de la resource équipe de la méthode writeAwayTeamStatsFromTableEquipes()");
				return;
			}

			// Stats globales
			Long nbMatchsGagnes = (Long) equipe.getValue(UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_GAGNES);
			Long nbMatchsJoues = (Long) equipe.getValue(UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_JOUES);
			Long nbMatchsNuls = (Long) equipe.getValue(UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_NULS);
			Long nbMatchsPerdus = (Long) equipe.getValue(UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_PERDUS);
			Long serieD = (Long) equipe.getValue(UtilitaireLigue1.TABLE_FIELD_SERIE_D_EN_COURS);
			Long serieN = (Long) equipe.getValue(UtilitaireLigue1.TABLE_FIELD_SERIE_N_EN_COURS);
			Long serieV = (Long) equipe.getValue(UtilitaireLigue1.TABLE_FIELD_SERIE_V_EN_COURS);

			Float pourcentageVictoires = null;
			Float pourcentageNuls = null;
			Float pourcentageDefaites = null;

			XWPFRun run = document.createParagraph().createRun();
			run.setBold(true);
			run.setFontSize(14);
			run.setText("Statistiques générales de l'équipe " + team2);

			if (nbMatchsJoues != 0L) {
				pourcentageVictoires = (nbMatchsGagnes.floatValue() / nbMatchsJoues.floatValue()) * 100;
				pourcentageNuls = (nbMatchsNuls.floatValue() / nbMatchsJoues.floatValue()) * 100;
				pourcentageDefaites = (nbMatchsPerdus.floatValue() / nbMatchsJoues.floatValue()) * 100;

				document.createParagraph().createRun()
						.setText("Pourcentage de victoires : " + pourcentageVictoires + "%");
				document.createParagraph().createRun().setText("Pourcentage de matchs nuls : " + pourcentageNuls + "%");
				document.createParagraph().createRun()
						.setText("Pourcentage de défaites : " + pourcentageDefaites + "%");
			} else {
				document.createParagraph().createRun().setText("L'équipe " + team2
						+ " va jouer son premier match de la saison, aucune donnée n'est disponible pour le moment.");
			}

			if (serieV > 0L)
				document.createParagraph().createRun().setText(
						"Nombre de victoires de l'équipe " + team2 + " sur les 5 derniers matchs : " + serieV + " V");
			if (serieN > 0L)
				document.createParagraph().createRun().setText(
						"Nombre de matchs nuls de l'équipe " + team2 + " sur les 5 derniers matchs : " + serieN + " N");
			if (serieD > 0L)
				document.createParagraph().createRun().setText(
						"Nombre de défaites de l'équipe " + team2 + " sur les 5 derniers matchs : " + serieD + " D");

			// Stats extérieur

			Long nbMatchsGagnesExterieur = (Long) equipe
					.getValue(UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_GAGNES_EXTERIEUR);
			Long nbMatchsJouesExterieur = (Long) equipe
					.getValue(UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_JOUES_EXTERIEUR);
			Long nbMatchsNulsExterieur = (Long) equipe
					.getValue(UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_NULS_EXTERIEUR);
			Long nbMatchsPerdusExterieur = (Long) equipe
					.getValue(UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_PERDUS_EXTERIEUR);
			Long serieDExterieur = (Long) equipe.getValue(UtilitaireLigue1.TABLE_FIELD_SERIE_D_EN_COURS_EXTERIEUR);
			Long serieNExterieur = (Long) equipe.getValue(UtilitaireLigue1.TABLE_FIELD_SERIE_N_EN_COURS_EXTERIEUR);
			Long serieVExterieur = (Long) equipe.getValue(UtilitaireLigue1.TABLE_FIELD_SERIE_V_EN_COURS_EXTERIEUR);

			Float pourcentageVictoiresExterieur = null;
			Float pourcentageNulsExterieur = null;
			Float pourcentageDefaitesExterieur = null;

			XWPFRun runDom = document.createParagraph().createRun();
			runDom.setBold(true);
			runDom.setFontSize(14);
			runDom.setText("Statistiques à l'extérieur de l'équipe " + team2);

			if (nbMatchsJouesExterieur != 0L) {
				pourcentageVictoiresExterieur = (nbMatchsGagnesExterieur.floatValue()
						/ nbMatchsJouesExterieur.floatValue()) * 100;
				pourcentageNulsExterieur = (nbMatchsNulsExterieur.floatValue() / nbMatchsJouesExterieur.floatValue())
						* 100;
				pourcentageDefaitesExterieur = (nbMatchsPerdusExterieur.floatValue()
						/ nbMatchsJouesExterieur.floatValue()) * 100;

				document.createParagraph().createRun()
						.setText("Pourcentage de victoires à l'extérieur : " + pourcentageVictoiresExterieur + "%");
				document.createParagraph().createRun()
						.setText("Pourcentage de matchs nuls à l'extérieur : " + pourcentageNulsExterieur + "%");
				document.createParagraph().createRun()
						.setText("Pourcentage de défaites à l'extérieur : " + pourcentageDefaitesExterieur + "%");
			} else {
				document.createParagraph().createRun().setText("L'équipe " + team2
						+ " va jouer son premier match de la saison à l'extérieur, aucune donnée n'est disponible pour le moment.");
			}

			if (serieVExterieur > 0L)
				document.createParagraph().createRun().setText("Nombre de victoires de l'équipe " + team2
						+ " sur les 5 derniers matchs à domicile : " + serieVExterieur + " V");
			if (serieNExterieur > 0L)
				document.createParagraph().createRun().setText("Nombre de matchs nuls de l'équipe " + team2
						+ " sur les 5 derniers matchs à domicile : " + serieNExterieur + " N");
			if (serieDExterieur > 0L)
				document.createParagraph().createRun().setText("Nombre de défaites de l'équipe " + team2
						+ " sur les 5 derniers matchs à domicile : " + serieDExterieur + " D");

			Long nbDefaitesContreClassementInf;
			Long nbDefaitesContreClassementInfExterieur;
			Long nbMatchsJouesContreClassementInf;
			Long nbMatchsJouesContreClassementInfExterieur;
			Long nbMatchsNulsContreClassementInf;
			Long nbMatchsNulsContreClassementInfExterieur;
			Long nbVictoiresContreClassementInf;
			Long nbVictoiresContreClassementInfExterieur;

			Float pourcentageDefaitesContreClassementInf = null;
			Float pourcentageDefaitesContreClassementInfExterieur = null;
			Float pourcentageNulsContreClassementInf = null;
			Float pourcentageNulsContreClassementInfExterieur = null;
			Float pourcentageVictoiresContreClassementInf = null;
			Float pourcentageVictoiresContreClassementInfExterieur = null;

			Long nbDefaitesContreClassementSup;
			Long nbDefaitesContreClassementSupExterieur;
			Long nbMatchsJouesContreClassementSup;
			Long nbMatchsJouesContreClassementSupExterieur;
			Long nbMatchsNulsContreClassementSup;
			Long nbMatchsNulsContreClassementSupExterieur;
			Long nbVictoiresContreClassementSup;
			Long nbVictoiresContreClassementSupExterieur;

			Float pourcentageDefaitesContreClassementSup = null;
			Float pourcentageDefaitesContreClassementSupExterieur = null;
			Float pourcentageNulsContreClassementSup = null;
			Float pourcentageNulsContreClassementSupExterieur = null;
			Float pourcentageVictoiresContreClassementSup = null;
			Float pourcentageVictoiresContreClassementSupExterieur = null;

			Long nbDefaitesContreImportant;
			Long nbDefaitesContreImportantExterieur;
			Long nbMatchsJouesContreImportant;
			Long nbMatchsJouesContreImportantExterieur;
			Long nbMatchsNulsContreImportant;
			Long nbMatchsNulsContreImportantExterieur;
			Long nbVictoiresContreImportant;
			Long nbVictoiresContreImportantExterieur;

			Float pourcentageDefaitesContreImportant = null;
			Float pourcentageDefaitesContreImportantExterieur = null;
			Float pourcentageNulsContreImportant = null;
			Float pourcentageNulsContreImportantExterieur = null;
			Float pourcentageVictoiresContreImportant = null;
			Float pourcentageVictoiresContreImportantExterieur = null;

			Long nbDefaitesContreBanal;
			Long nbDefaitesContreBanalExterieur;
			Long nbMatchsJouesContreBanal;
			Long nbMatchsJouesContreBanalExterieur;
			Long nbMatchsNulsContreBanal;
			Long nbMatchsNulsContreBanalExterieur;
			Long nbVictoiresContreBanal;
			Long nbVictoiresContreBanalExterieur;

			Float pourcentageDefaitesContreBanal = null;
			Float pourcentageDefaitesContreBanalExterieur = null;
			Float pourcentageNulsContreBanal = null;
			Float pourcentageNulsContreBanalExterieur = null;
			Float pourcentageVictoiresContreBanal = null;
			Float pourcentageVictoiresContreBanalExterieur = null;

			if (homeTeamHasBestStanding) {

				nbDefaitesContreClassementSup = (Long) equipe
						.getValue(UtilitaireLigue1.TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_CLASSEMENT_SUPERIEUR);
				nbDefaitesContreClassementSupExterieur = (Long) equipe
						.getValue(UtilitaireLigue1.TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_CLASSEMENT_SUPERIEUR_EXTERIEUR);
				nbMatchsJouesContreClassementSup = (Long) equipe
						.getValue(UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_JOUES_CLASSEMENT_SUP);
				nbMatchsJouesContreClassementSupExterieur = (Long) equipe
						.getValue(UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_JOUES_CLASSEMENT_SUP_EXTERIEUR);
				nbMatchsNulsContreClassementSup = (Long) equipe
						.getValue(UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_NULS_CLASSEMENT_SUP);
				nbMatchsNulsContreClassementSupExterieur = (Long) equipe
						.getValue(UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_NULS_CLASSEMENT_SUP_EXTERIEUR);
				nbVictoiresContreClassementSup = (Long) equipe
						.getValue(UtilitaireLigue1.TABLE_FIELD_NOMBRE_VICTOIRES_CLASSEMENT_SUP);
				nbVictoiresContreClassementSupExterieur = (Long) equipe
						.getValue(UtilitaireLigue1.TABLE_FIELD_NOMBRE_VICTOIRES_CLASSEMENT_SUP_EXTERIEUR);

				XWPFRun runClassementSup = document.createParagraph().createRun();
				runClassementSup.setBold(true);
				runClassementSup.setFontSize(14);
				runClassementSup.setText("Statistiques de l'équipe " + team2 + " contre une équipe mieux classée");

				if (nbMatchsJouesContreClassementSup != 0L) {
					pourcentageVictoiresContreClassementSup = (nbVictoiresContreClassementSup.floatValue()
							/ nbMatchsJouesContreClassementSup.floatValue()) * 100;
					pourcentageNulsContreClassementSup = (nbMatchsNulsContreClassementSup.floatValue()
							/ nbMatchsJouesContreClassementSup.floatValue()) * 100;
					pourcentageDefaitesContreClassementSup = (nbDefaitesContreClassementSup.floatValue()
							/ nbMatchsJouesContreClassementSup.floatValue()) * 100;

					document.createParagraph().createRun()
							.setText("Pourcentage de victoires contre une équipe mieux classée : "
									+ pourcentageVictoiresContreClassementSup + "%");
					document.createParagraph().createRun()
							.setText("Pourcentage de matchs nuls contre une équipe mieux classée : "
									+ pourcentageNulsContreClassementSup + "%");
					document.createParagraph().createRun()
							.setText("Pourcentage de défaites contre une équipe mieux classée : "
									+ pourcentageDefaitesContreClassementSup + "%");
				} else {
					document.createParagraph().createRun().setText("L'équipe " + team2
							+ " va jouer son premier match de la saison contre une équipe mieux classée qu'elle, aucune donnée n'est disponible pour le moment.");
				}

				XWPFRun runClassementSupExterieur = document.createParagraph().createRun();
				runClassementSupExterieur.setBold(true);
				runClassementSupExterieur.setFontSize(14);
				runClassementSupExterieur.setText(
						"Statistiques de l'équipe " + team2 + " à l'extérieur contre une équipe mieux classée");

				if (nbMatchsJouesContreClassementSupExterieur != 0L) {
					pourcentageVictoiresContreClassementSupExterieur = (nbVictoiresContreClassementSupExterieur
							.floatValue() / nbMatchsJouesContreClassementSupExterieur.floatValue()) * 100;
					pourcentageNulsContreClassementSupExterieur = (nbMatchsNulsContreClassementSupExterieur.floatValue()
							/ nbMatchsJouesContreClassementSupExterieur.floatValue()) * 100;
					pourcentageDefaitesContreClassementSupExterieur = (nbDefaitesContreClassementSupExterieur
							.floatValue() / nbMatchsJouesContreClassementSupExterieur.floatValue()) * 100;

					document.createParagraph().createRun()
							.setText("Pourcentage de victoires à l'extérieur contre une équipe mieux classée : "
									+ pourcentageVictoiresContreClassementSupExterieur + "%");
					document.createParagraph().createRun()
							.setText("Pourcentage de matchs nuls à l'extérieur contre une équipe mieux classée : "
									+ pourcentageNulsContreClassementSupExterieur + "%");
					document.createParagraph().createRun()
							.setText("Pourcentage de défaites à l'extérieur contre une équipe mieux classée : "
									+ pourcentageDefaitesContreClassementSupExterieur + "%");
				} else {
					document.createParagraph().createRun().setText("L'équipe " + team2
							+ " va jouer son premier match de la saison à l'extérieur contre une équipe mieux classée qu'elle, aucune donnée n'est disponible pour le moment.");
				}

			} else {

				nbDefaitesContreClassementInf = (Long) equipe
						.getValue(UtilitaireLigue1.TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_CLASSEMENT_INFERIEUR);
				nbDefaitesContreClassementInfExterieur = (Long) equipe
						.getValue(UtilitaireLigue1.TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_CLASSEMENT_INFERIEUR_EXTERIEUR);
				nbMatchsJouesContreClassementInf = (Long) equipe
						.getValue(UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_JOUES_CLASSEMENT_INF);
				nbMatchsJouesContreClassementInfExterieur = (Long) equipe
						.getValue(UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_JOUES_CLASSEMENT_INF_EXTERIEUR);
				nbMatchsNulsContreClassementInf = (Long) equipe
						.getValue(UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_NULS_CLASSEMENT_INF);
				nbMatchsNulsContreClassementInfExterieur = (Long) equipe
						.getValue(UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_NULS_CLASSEMENT_INF_EXTERIEUR);
				nbVictoiresContreClassementInf = (Long) equipe
						.getValue(UtilitaireLigue1.TABLE_FIELD_NOMBRE_VICTOIRES_CLASSEMENT_INF);
				nbVictoiresContreClassementInfExterieur = (Long) equipe
						.getValue(UtilitaireLigue1.TABLE_FIELD_NOMBRE_VICTOIRES_CLASSEMENT_INF_EXTERIEUR);

				XWPFRun runClassementInf = document.createParagraph().createRun();
				runClassementInf.setBold(true);
				runClassementInf.setFontSize(14);
				runClassementInf.setText("Statistiques de l'équipe " + team2 + " contre une équipe moins bien classée");

				if (nbMatchsJouesContreClassementInf != 0L) {
					pourcentageVictoiresContreClassementInf = (nbVictoiresContreClassementInf.floatValue()
							/ nbMatchsJouesContreClassementInf.floatValue()) * 100;
					pourcentageNulsContreClassementInf = (nbMatchsNulsContreClassementInf.floatValue()
							/ nbMatchsJouesContreClassementInf.floatValue()) * 100;
					pourcentageDefaitesContreClassementInf = (nbDefaitesContreClassementInf.floatValue()
							/ nbMatchsJouesContreClassementInf.floatValue()) * 100;

					document.createParagraph().createRun()
							.setText("Pourcentage de victoires contre une équipe moins bien classée : "
									+ pourcentageVictoiresContreClassementInf + "%");
					document.createParagraph().createRun()
							.setText("Pourcentage de matchs nuls contre une équipe moins bien classée : "
									+ pourcentageNulsContreClassementInf + "%");
					document.createParagraph().createRun()
							.setText("Pourcentage de défaites contre une équipe moins bien classée : "
									+ pourcentageDefaitesContreClassementInf + "%");
				} else {
					document.createParagraph().createRun().setText("L'équipe " + team2
							+ " va jouer son premier match de la saison contre une équipe moins bien classée qu'elle, aucune donnée n'est disponible pour le moment.");
				}

				XWPFRun runClassementInfExt = document.createParagraph().createRun();
				runClassementInfExt.setBold(true);
				runClassementInfExt.setFontSize(14);
				runClassementInfExt.setText(
						"Statistiques de l'équipe " + team2 + " à l'extérieur contre une équipe moins bien classée");

				if (nbMatchsJouesContreClassementInfExterieur != 0L) {
					pourcentageVictoiresContreClassementInfExterieur = (nbVictoiresContreClassementInfExterieur
							.floatValue() / nbMatchsJouesContreClassementInfExterieur.floatValue()) * 100;
					pourcentageNulsContreClassementInfExterieur = (nbMatchsNulsContreClassementInfExterieur.floatValue()
							/ nbMatchsJouesContreClassementInfExterieur.floatValue()) * 100;
					pourcentageDefaitesContreClassementInfExterieur = (nbDefaitesContreClassementInfExterieur
							.floatValue() / nbMatchsJouesContreClassementInfExterieur.floatValue()) * 100;

					document.createParagraph().createRun()
							.setText("Pourcentage de victoires à l'extérieur contre une équipe moins bien classée : "
									+ pourcentageVictoiresContreClassementInfExterieur + "%");
					document.createParagraph().createRun()
							.setText("Pourcentage de matchs nuls à l'extérieur contre une équipe moins bien classée : "
									+ pourcentageNulsContreClassementInfExterieur + "%");
					document.createParagraph().createRun()
							.setText("Pourcentage de défaites à l'extérieur contre une équipe moins bien classée : "
									+ pourcentageDefaitesContreClassementInfExterieur + "%");
				} else {
					document.createParagraph().createRun().setText("L'équipe " + team2
							+ " va jouer son premier match de la saison à l'extérieur contre une équipe moins bien classée qu'elle, aucune donnée n'est disponible pour le moment.");
				}

			}

			if (isImportantForTeam2) {

				nbDefaitesContreImportant = (Long) equipe
						.getValue(UtilitaireLigue1.TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_IMPORTANT);
				nbDefaitesContreImportantExterieur = (Long) equipe
						.getValue(UtilitaireLigue1.TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_IMPORTANT_EXTERIEUR);
				nbMatchsJouesContreImportant = (Long) equipe
						.getValue(UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_JOUES_IMPORTANTS);
				nbMatchsJouesContreImportantExterieur = (Long) equipe
						.getValue(UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_JOUES_IMPORTANTS_EXTERIEUR);
				nbMatchsNulsContreImportant = (Long) equipe
						.getValue(UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_NULS_IMPORTANT);
				nbMatchsNulsContreImportantExterieur = (Long) equipe
						.getValue(UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_NULS_IMPORTANT_EXTERIEUR);
				nbVictoiresContreImportant = (Long) equipe
						.getValue(UtilitaireLigue1.TABLE_FIELD_NOMBRE_VICTOIRES_IMPORTANT);
				nbVictoiresContreImportantExterieur = (Long) equipe
						.getValue(UtilitaireLigue1.TABLE_FIELD_NOMBRE_VICTOIRES_IMPORTANT_EXTERIEUR);

				XWPFRun runImportant = document.createParagraph().createRun();
				runImportant.setBold(true);
				runImportant.setFontSize(14);
				runImportant.setText("Statistiques de l'équipe " + team2 + " dans un match important");

				if (nbMatchsJouesContreImportant != 0L) {
					pourcentageVictoiresContreImportant = (nbVictoiresContreImportant.floatValue()
							/ nbMatchsJouesContreImportant.floatValue()) * 100;
					pourcentageNulsContreImportant = (nbMatchsNulsContreImportant.floatValue()
							/ nbMatchsJouesContreImportant.floatValue()) * 100;
					pourcentageDefaitesContreImportant = (nbDefaitesContreImportant.floatValue()
							/ nbMatchsJouesContreImportant.floatValue()) * 100;

					document.createParagraph().createRun().setText("Pourcentage de victoires dans un match important : "
							+ pourcentageVictoiresContreImportant + "%");
					document.createParagraph().createRun()
							.setText("Pourcentage de matchs nuls dans un match important : "
									+ pourcentageNulsContreImportant + "%");
					document.createParagraph().createRun().setText("Pourcentage de défaites dans un match important : "
							+ pourcentageDefaitesContreImportant + "%");
				} else {
					document.createParagraph().createRun().setText("L'équipe " + team2
							+ " va jouer son premier match important de la saison, aucune donnée n'est disponible pour le moment.");
				}

				XWPFRun runImportantExterieur = document.createParagraph().createRun();
				runImportantExterieur.setBold(true);
				runImportantExterieur.setFontSize(14);
				runImportantExterieur
						.setText("Statistiques de l'équipe " + team2 + " à l'extérieur dans un match important");

				if (nbMatchsJouesContreImportantExterieur != 0L) {
					pourcentageVictoiresContreImportantExterieur = (nbVictoiresContreImportantExterieur.floatValue()
							/ nbMatchsJouesContreImportantExterieur.floatValue()) * 100;
					pourcentageNulsContreImportantExterieur = (nbMatchsNulsContreImportantExterieur.floatValue()
							/ nbMatchsJouesContreImportantExterieur.floatValue()) * 100;
					pourcentageDefaitesContreImportantExterieur = (nbDefaitesContreImportantExterieur.floatValue()
							/ nbMatchsJouesContreImportantExterieur.floatValue()) * 100;

					document.createParagraph().createRun()
							.setText("Pourcentage de victoires à l'extérieur dans un match important : "
									+ pourcentageVictoiresContreImportantExterieur + "%");
					document.createParagraph().createRun()
							.setText("Pourcentage de matchs nuls à l'extérieur dans un match important : "
									+ pourcentageNulsContreImportantExterieur + "%");
					document.createParagraph().createRun()
							.setText("Pourcentage de défaites à l'extérieur dans un match important : "
									+ pourcentageDefaitesContreImportantExterieur + "%");
				} else {
					document.createParagraph().createRun().setText("L'équipe " + team2
							+ " va jouer son premier match important de la saison à l'extérieur, aucune donnée n'est disponible pour le moment.");
				}

			} else {

				nbDefaitesContreBanal = (Long) equipe
						.getValue(UtilitaireLigue1.TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_BANAL);
				nbDefaitesContreBanalExterieur = (Long) equipe
						.getValue(UtilitaireLigue1.TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_BANAL_EXTERIEUR);
				nbMatchsJouesContreBanal = (Long) equipe
						.getValue(UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_JOUES_BANAL);
				nbMatchsJouesContreBanalExterieur = (Long) equipe
						.getValue(UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_JOUES_BANAL_EXTERIEUR);
				nbMatchsNulsContreBanal = (Long) equipe.getValue(UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_NULS_BANAL);
				nbMatchsNulsContreBanalExterieur = (Long) equipe
						.getValue(UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_NULS_BANAL_EXTERIEUR);
				nbVictoiresContreBanal = (Long) equipe.getValue(UtilitaireLigue1.TABLE_FIELD_NOMBRE_VICTOIRES_BANAL);
				nbVictoiresContreBanalExterieur = (Long) equipe
						.getValue(UtilitaireLigue1.TABLE_FIELD_NOMBRE_VICTOIRES_BANAL_EXTERIEUR);

				XWPFRun runBanal = document.createParagraph().createRun();
				runBanal.setBold(true);
				runBanal.setFontSize(14);
				runBanal.setText(
						"Statistiques de l'équipe " + team2 + " dans un match qui n'est pas considéré comme important");

				if (nbMatchsJouesContreBanal != 0L) {
					pourcentageVictoiresContreBanal = (nbVictoiresContreBanal.floatValue()
							/ nbMatchsJouesContreBanal.floatValue()) * 100;
					pourcentageNulsContreBanal = (nbMatchsNulsContreBanal.floatValue()
							/ nbMatchsJouesContreBanal.floatValue()) * 100;
					pourcentageDefaitesContreBanal = (nbDefaitesContreBanal.floatValue()
							/ nbMatchsJouesContreBanal.floatValue()) * 100;

					document.createParagraph().createRun().setText("Pourcentage de victoires dans un match classique : "
							+ pourcentageVictoiresContreBanal + "%");
					document.createParagraph().createRun().setText(
							"Pourcentage de matchs nuls dans un match classique : " + pourcentageNulsContreBanal + "%");
					document.createParagraph().createRun().setText("Pourcentage de défaites dans un match classique : "
							+ pourcentageDefaitesContreBanal + "%");
				} else {
					document.createParagraph().createRun().setText("L'équipe " + team2
							+ " va jouer son premier match classique de la saison, aucune donnée n'est disponible pour le moment.");
				}

				XWPFRun runBanalExterieur = document.createParagraph().createRun();
				runBanalExterieur.setBold(true);
				runBanalExterieur.setFontSize(14);
				runBanalExterieur.setText("Statistiques de l'équipe " + team2
						+ " à l'extérieur dans un match qui n'est pas considéré comme important");

				if (nbMatchsJouesContreBanalExterieur != 0L) {
					pourcentageVictoiresContreBanalExterieur = (nbVictoiresContreBanalExterieur.floatValue()
							/ nbMatchsJouesContreBanalExterieur.floatValue()) * 100;
					pourcentageNulsContreBanalExterieur = (nbMatchsNulsContreBanalExterieur.floatValue()
							/ nbMatchsJouesContreBanalExterieur.floatValue()) * 100;
					pourcentageDefaitesContreBanalExterieur = (nbDefaitesContreBanalExterieur.floatValue()
							/ nbMatchsJouesContreBanalExterieur.floatValue()) * 100;

					document.createParagraph().createRun()
							.setText("Pourcentage de victoires à l'extérieur dans un match classique : "
									+ pourcentageVictoiresContreBanalExterieur + "%");
					document.createParagraph().createRun()
							.setText("Pourcentage de matchs nuls à l'extérieur dans un match classique : "
									+ pourcentageNulsContreBanalExterieur + "%");
					document.createParagraph().createRun()
							.setText("Pourcentage de défaites à l'extérieur dans un match classique : "
									+ pourcentageDefaitesContreBanalExterieur + "%");
				} else {
					document.createParagraph().createRun().setText("L'équipe " + team2
							+ " va jouer son premier match classique de la saison à l'extérieur, aucune donnée n'est disponible pour le moment.");
				}

			}

		} catch (VdocHelperException e) {
			getResourceController().alert(
					"Erreur à la récupération de la resource équipe de la méthode writeHomeTeamStatsFromTableEquipes()");
			e.getMessage();
			e.printStackTrace();
		}

	}

	private TreeMap<String, String> getTreeMapStatsNombreExactButsE2(IStorageResource statistiques, String match) {

		Map<String, String> stats = new HashMap<String, String>();

		String[] score = match.split("-");
		String e2 = score[1];

		Float E2marque0 = (Float) statistiques
				.getValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_EQUIPE_EXTERIEUR_0_BUT_MARQUE);
		Float E2marque1 = (Float) statistiques
				.getValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_EQUIPE_EXTERIEUR_1_BUT_MARQUE);
		Float E2marque2 = (Float) statistiques
				.getValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_EQUIPE_EXTERIEUR_2_BUTS_MARQUES);
		Float E2marque3 = (Float) statistiques
				.getValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_EQUIPE_EXTERIEUR_3_BUTS_MARQUES);
		Float E2marque4 = (Float) statistiques
				.getValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_EQUIPE_EXTERIEUR_4_BUTS_MARQUES);
		Float E2marque5 = (Float) statistiques
				.getValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_EQUIPE_EXTERIEUR_5_BUTS_MARQUES);

		if (E2marque0 != 0F)
			stats.put("Pourcentage de matchs avec exactement 0 but marqué pour l'équipe " + e2, E2marque0 + "%");
		if (E2marque1 != 0F)
			stats.put("Pourcentage de matchs avec exactement 1 but marqué pour l'équipe " + e2, E2marque1 + "%");
		if (E2marque2 != 0F)
			stats.put("Pourcentage de matchs avec exactement 2 buts marqués pour l'équipe " + e2, E2marque2 + "%");
		if (E2marque3 != 0F)
			stats.put("Pourcentage de matchs avec exactement 3 buts marqués pour l'équipe " + e2, E2marque3 + "%");
		if (E2marque4 != 0F)
			stats.put("Pourcentage de matchs avec exactement 4 buts marqués pour l'équipe " + e2, E2marque4 + "%");
		if (E2marque5 != 0F)
			stats.put("Pourcentage de matchs avec exactement 5 buts marqués pour l'équipe " + e2, E2marque5 + "%");

		PourcentageComparator comparator = new PourcentageComparator(stats);
		TreeMap<String, String> mapTree = new TreeMap<String, String>(comparator);
		mapTree.putAll(stats);

		return mapTree;
	}

	private TreeMap<String, String> getTreeMapStatsNombreExactButsE1(IStorageResource statistiques, String match) {

		Map<String, String> stats = new HashMap<String, String>();

		String[] score = match.split("-");
		String e1 = score[0];

		Float E1marque0 = (Float) statistiques
				.getValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_EQUIPE_DOMICILE_0_BUT_MARQUE);
		Float E1marque1 = (Float) statistiques
				.getValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_EQUIPE_DOMICILE_1_BUT_MARQUE);
		Float E1marque2 = (Float) statistiques
				.getValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_EQUIPE_DOMICILE_2_BUTS_MARQUES);
		Float E1marque3 = (Float) statistiques
				.getValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_EQUIPE_DOMICILE_3_BUTS_MARQUES);
		Float E1marque4 = (Float) statistiques
				.getValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_EQUIPE_DOMICILE_4_BUTS_MARQUES);
		Float E1marque5 = (Float) statistiques
				.getValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_EQUIPE_DOMICILE_5_BUTS_MARQUES);

		if (E1marque0 != 0F)
			stats.put("Pourcentage de matchs avec exactement 0 but marqué pour l'équipe " + e1, E1marque0 + "%");
		if (E1marque1 != 0F)
			stats.put("Pourcentage de matchs avec exactement 1 but marqué pour l'équipe " + e1, E1marque1 + "%");
		if (E1marque2 != 0F)
			stats.put("Pourcentage de matchs avec exactement 2 buts marqués pour l'équipe " + e1, E1marque2 + "%");
		if (E1marque3 != 0F)
			stats.put("Pourcentage de matchs avec exactement 3 buts marqués pour l'équipe " + e1, E1marque3 + "%");
		if (E1marque4 != 0F)
			stats.put("Pourcentage de matchs avec exactement 4 buts marqués pour l'équipe " + e1, E1marque4 + "%");
		if (E1marque5 != 0F)
			stats.put("Pourcentage de matchs avec exactement 5 buts marqués pour l'équipe " + e1, E1marque5 + "%");

		PourcentageComparator comparator = new PourcentageComparator(stats);
		TreeMap<String, String> mapTree = new TreeMap<String, String>(comparator);
		mapTree.putAll(stats);

		return mapTree;
	}

	private TreeMap<String, String> getTreeMapStatsEcartButsE2(IStorageResource statistiques, String match) {

		Map<String, String> stats = new HashMap<String, String>();

		String[] score = match.split("-");
		String e2 = score[1];

		Float E2par1 = (Float) statistiques
				.getValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_ECART_BUT_MOINS_1);
		Float E2par2 = (Float) statistiques
				.getValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_ECART_BUT_MOINS_2);
		Float E2par3 = (Float) statistiques
				.getValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_ECART_BUT_MOINS_3);
		Float E2par4 = (Float) statistiques
				.getValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_ECART_BUT_MOINS_4);
		Float E2par5 = (Float) statistiques
				.getValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_ECART_BUT_MOINS_5);
		Float E2par6 = (Float) statistiques
				.getValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_ECART_BUT_MOINS_6);

		if (E2par1 != 0F)
			stats.put("Pourcentage de matchs remporté par exactement 1 but d'écart par l'équipe " + e2, E2par1 + "%");
		if (E2par2 != 0F)
			stats.put("Pourcentage de matchs remporté par exactement 2 buts d'écart par l'équipe " + e2, E2par2 + "%");
		if (E2par3 != 0F)
			stats.put("Pourcentage de matchs remporté par exactement 3 buts d'écart par l'équipe " + e2, E2par3 + "%");
		if (E2par4 != 0F)
			stats.put("Pourcentage de matchs remporté par exactement 4 buts d'écart par l'équipe " + e2, E2par4 + "%");
		if (E2par5 != 0F)
			stats.put("Pourcentage de matchs remporté par exactement 5 buts d'écart par l'équipe " + e2, E2par5 + "%");
		if (E2par6 != 0F)
			stats.put("Pourcentage de matchs remporté par exactement 6 buts d'écart par l'équipe " + e2, E2par6 + "%");

		PourcentageComparator comparator = new PourcentageComparator(stats);
		TreeMap<String, String> mapTree = new TreeMap<String, String>(comparator);
		mapTree.putAll(stats);

		return mapTree;
	}

	private TreeMap<String, String> getTreeMapStatsEcartButsE1(IStorageResource statistiques, String match) {

		Map<String, String> stats = new HashMap<String, String>();

		String[] score = match.split("-");
		String e1 = score[0];

		Float E1par1 = (Float) statistiques
				.getValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_ECART_BUT_PLUS_1);
		Float E1par2 = (Float) statistiques
				.getValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_ECART_BUT_PLUS_2);
		Float E1par3 = (Float) statistiques
				.getValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_ECART_BUT_PLUS_3);
		Float E1par4 = (Float) statistiques
				.getValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_ECART_BUT_PLUS_4);
		Float E1par5 = (Float) statistiques
				.getValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_ECART_BUT_PLUS_5);
		Float E1par6 = (Float) statistiques
				.getValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_ECART_BUT_PLUS_6);

		if (E1par1 != 0F)
			stats.put("Pourcentage de matchs remporté par exactement 1 but d'écart par l'équipe " + e1, E1par1 + "%");
		if (E1par2 != 0F)
			stats.put("Pourcentage de matchs remporté par exactement 2 buts d'écart par l'équipe " + e1, E1par2 + "%");
		if (E1par3 != 0F)
			stats.put("Pourcentage de matchs remporté par exactement 3 buts d'écart par l'équipe " + e1, E1par3 + "%");
		if (E1par4 != 0F)
			stats.put("Pourcentage de matchs remporté par exactement 4 buts d'écart par l'équipe " + e1, E1par4 + "%");
		if (E1par5 != 0F)
			stats.put("Pourcentage de matchs remporté par exactement 5 buts d'écart par l'équipe " + e1, E1par5 + "%");
		if (E1par6 != 0F)
			stats.put("Pourcentage de matchs remporté par exactement 6 buts d'écart par l'équipe " + e1, E1par6 + "%");

		PourcentageComparator comparator = new PourcentageComparator(stats);
		TreeMap<String, String> mapTree = new TreeMap<String, String>(comparator);
		mapTree.putAll(stats);

		return mapTree;

	}

	private TreeMap<String, String> getTreeMapStatsNombreButsE2(IStorageResource statistiques, String match) {

		Map<String, String> stats = new HashMap<String, String>();

		String[] score = match.split("-");
		String e2 = score[1];

		Float E2plus05 = (Float) statistiques
				.getValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_EXTERIEUR_PLUS_0_5_BUTS);
		Float E2plus15 = (Float) statistiques
				.getValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_EXTERIEUR_PLUS_1_5_BUTS);
		Float E2plus25 = (Float) statistiques
				.getValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_EXTERIEUR_PLUS_2_5_BUTS);
		Float E2plus35 = (Float) statistiques
				.getValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_EXTERIEUR_PLUS_3_5_BUTS);
		Float E2plus45 = (Float) statistiques
				.getValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_EXTERIEUR_PLUS_4_5_BUTS);

		Float E2moins05 = (Float) statistiques
				.getValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_EXTERIEUR_MOINS_0_5_BUTS);
		Float E2moins15 = (Float) statistiques
				.getValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_EXTERIEUR_MOINS_1_5_BUTS);
		Float E2moins25 = (Float) statistiques
				.getValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_EXTERIEUR_MOINS_2_5_BUTS);
		Float E2moins35 = (Float) statistiques
				.getValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_EXTERIEUR_MOINS_3_5_BUTS);
		Float E2moins45 = (Float) statistiques
				.getValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_EXTERIEUR_MOINS_4_5_BUTS);

		if (E2plus05 != 0F)
			stats.put("Pourcentage de matchs avec plus de 0,5 buts pour l'équipe " + e2, E2plus05 + "%");
		if (E2plus15 != 0F)
			stats.put("Pourcentage de matchs avec plus de 1,5 buts pour l'équipe " + e2, E2plus15 + "%");
		if (E2plus25 != 0F)
			stats.put("Pourcentage de matchs avec plus de 2,5 buts pour l'équipe " + e2, E2plus25 + "%");
		if (E2plus35 != 0F)
			stats.put("Pourcentage de matchs avec plus de 3,5 buts pour l'équipe " + e2, E2plus35 + "%");
		if (E2plus45 != 0F)
			stats.put("Pourcentage de matchs avec plus de 4,5 buts pour l'équipe " + e2, E2plus45 + "%");
		if (E2moins05 != 0F)
			stats.put("Pourcentage de matchs avec moins de 0,5 buts pour l'équipe " + e2, E2moins05 + "%");
		if (E2moins15 != 0F)
			stats.put("Pourcentage de matchs avec moins de 1,5 buts pour l'équipe " + e2, E2moins15 + "%");
		if (E2moins25 != 0F)
			stats.put("Pourcentage de matchs avec moins de 2,5 buts pour l'équipe " + e2, E2moins25 + "%");
		if (E2moins35 != 0F)
			stats.put("Pourcentage de matchs avec moins de 3,5 buts pour l'équipe " + e2, E2moins35 + "%");
		if (E2moins45 != 0F)
			stats.put("Pourcentage de matchs avec moins de 4,5 buts pour l'équipe " + e2, E2moins45 + "%");

		PourcentageComparator comparator = new PourcentageComparator(stats);
		TreeMap<String, String> mapTree = new TreeMap<String, String>(comparator);
		mapTree.putAll(stats);

		return mapTree;
	}

	private TreeMap<String, String> getTreeMapStatsNombreButsE1(IStorageResource statistiques, String match) {

		Map<String, String> stats = new HashMap<String, String>();

		String[] score = match.split("-");
		String e1 = score[0];

		Float E1plus05 = (Float) statistiques
				.getValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_DOMICILE_PLUS_0_5_BUTS);
		Float E1plus15 = (Float) statistiques
				.getValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_DOMICILE_PLUS_1_5_BUTS);
		Float E1plus25 = (Float) statistiques
				.getValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_DOMICILE_PLUS_2_5_BUTS);
		Float E1plus35 = (Float) statistiques
				.getValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_DOMICILE_PLUS_3_5_BUTS);
		Float E1plus45 = (Float) statistiques
				.getValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_DOMICILE_PLUS_4_5_BUTS);

		Float E1moins05 = (Float) statistiques
				.getValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_DOMICILE_MOINS_0_5_BUTS);
		Float E1moins15 = (Float) statistiques
				.getValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_DOMICILE_MOINS_1_5_BUTS);
		Float E1moins25 = (Float) statistiques
				.getValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_DOMICILE_MOINS_2_5_BUTS);
		Float E1moins35 = (Float) statistiques
				.getValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_DOMICILE_MOINS_3_5_BUTS);
		Float E1moins45 = (Float) statistiques
				.getValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_DOMICILE_MOINS_4_5_BUTS);

		if (E1plus05 != 0F)
			stats.put("Pourcentage de matchs avec plus de 0,5 buts pour l'équipe " + e1, E1plus05 + "%");
		if (E1plus15 != 0F)
			stats.put("Pourcentage de matchs avec plus de 1,5 buts pour l'équipe " + e1, E1plus15 + "%");
		if (E1plus25 != 0F)
			stats.put("Pourcentage de matchs avec plus de 2,5 buts pour l'équipe " + e1, E1plus25 + "%");
		if (E1plus35 != 0F)
			stats.put("Pourcentage de matchs avec plus de 3,5 buts pour l'équipe " + e1, E1plus35 + "%");
		if (E1plus45 != 0F)
			stats.put("Pourcentage de matchs avec plus de 4,5 buts pour l'équipe " + e1, E1plus45 + "%");
		if (E1moins05 != 0F)
			stats.put("Pourcentage de matchs avec moins de 0,5 buts pour l'équipe " + e1, E1moins05 + "%");
		if (E1moins15 != 0F)
			stats.put("Pourcentage de matchs avec moins de 1,5 buts pour l'équipe " + e1, E1moins15 + "%");
		if (E1moins25 != 0F)
			stats.put("Pourcentage de matchs avec moins de 2,5 buts pour l'équipe " + e1, E1moins25 + "%");
		if (E1moins35 != 0F)
			stats.put("Pourcentage de matchs avec moins de 3,5 buts pour l'équipe " + e1, E1moins35 + "%");
		if (E1moins45 != 0F)
			stats.put("Pourcentage de matchs avec moins de 4,5 buts pour l'équipe " + e1, E1moins45 + "%");

		PourcentageComparator comparator = new PourcentageComparator(stats);
		TreeMap<String, String> mapTree = new TreeMap<String, String>(comparator);
		mapTree.putAll(stats);

		return mapTree;

	}

	private TreeMap<String, String> getTreeMapStatsNombreButsMatch(IStorageResource statistiques, String match,
			IWorkflowInstance wi) {

		Map<String, String> stats = new HashMap<String, String>();

		Float plus05 = (Float) statistiques
				.getValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_MATCH_PLUS_0_5_BUTS);
		Float plus15 = (Float) statistiques
				.getValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_MATCH_PLUS_1_5_BUTS);
		Float plus25 = (Float) statistiques
				.getValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_MATCH_PLUS_2_5_BUTS);
		Float plus35 = (Float) statistiques
				.getValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_MATCH_PLUS_3_5_BUTS);
		Float plus45 = (Float) statistiques
				.getValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_MATCH_PLUS_4_5_BUTS);

		Float moins05 = (Float) statistiques
				.getValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_MATCH_MOINS_0_5_BUTS);
		Float moins15 = (Float) statistiques
				.getValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_MATCH_MOINS_1_5_BUTS);
		Float moins25 = (Float) statistiques
				.getValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_MATCH_MOINS_2_5_BUTS);
		Float moins35 = (Float) statistiques
				.getValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_MATCH_MOINS_3_5_BUTS);
		Float moins45 = (Float) statistiques
				.getValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_MATCH_MOINS_4_5_BUTS);

		if (plus05 != 0F)
			stats.put("Pourcentage de matchs avec plus de 0,5 buts", plus05 + "%");
		if (plus15 != 0F)
			stats.put("Pourcentage de matchs avec plus de 1,5 buts", plus15 + "%");
		if (plus25 != 0F)
			stats.put("Pourcentage de matchs avec plus de 2,5 buts", plus25 + "%");
		if (plus35 != 0F)
			stats.put("Pourcentage de matchs avec plus de 3,5 buts", plus35 + "%");
		if (plus45 != 0F)
			stats.put("Pourcentage de matchs avec plus de 4,5 buts", plus45 + "%");
		if (moins05 != 0F)
			stats.put("Pourcentage de matchs avec moins de 0,5 buts", moins05 + "%");
		if (moins15 != 0F)
			stats.put("Pourcentage de matchs avec moins de 1,5 buts", moins15 + "%");
		if (moins25 != 0F)
			stats.put("Pourcentage de matchs avec moins de 2,5 buts", moins25 + "%");
		if (moins35 != 0F)
			stats.put("Pourcentage de matchs avec moins de 3,5 buts", moins35 + "%");
		if (moins45 != 0F)
			stats.put("Pourcentage de matchs avec moins de 4,5 buts", moins45 + "%");

		PourcentageComparator comparator = new PourcentageComparator(stats);
		TreeMap<String, String> mapTree = new TreeMap<String, String>(comparator);
		mapTree.putAll(stats);

		return mapTree;
	}

	private Map<String, String> getTreeMapStatsLDEM(IStorageResource statistiques, String match) {

		Map<String, String> stats = new HashMap<String, String>();

		String[] teams = match.split("-");
		String homeTeam = teams[0];
		String awayTeam = teams[1];
		Float pourcentageLDEM = (Float) statistiques
				.getValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_LDEM);
		String classementDomicile = "";
		String classementExterieur = "";
		try {
			classementDomicile = UtilitaireLigue1.getStatString(homeTeam,
					UtilitaireLigue1.TABLE_FIELD_STRING_CLASSEMENT_DOMICILE);
			classementExterieur = UtilitaireLigue1.getStatString(awayTeam,
					UtilitaireLigue1.TABLE_FIELD_STRING_CLASSEMENT_EXTERIEUR);
		} catch (VdocHelperException e) {
			getResourceController().alert("Erreur à la récupération du classement domicile et exterieur des equipes "
					+ homeTeam + " et " + awayTeam);
			e.printStackTrace();
		}

		stats.put("Pourcentage de matchs où les deux équipes marquent", pourcentageLDEM + "%");
		stats.put("Classement à domicile de l'équipe " + homeTeam, classementDomicile);
		stats.put("Classement à l'extérieur de l'équipe " + awayTeam, classementExterieur);

		return stats;
	}

	private TreeMap<String, String> getTreeMapStatsVictoires(IStorageResource statistiques, String match) {

		Map<String, String> stats = new HashMap<String, String>();

		String[] score = match.split("-");
		String e1 = score[0];
		String e2 = score[1];

		Float pourcentageVDOM = (Float) statistiques
				.getValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_VICTOIRE_DOMICILE);
		Float pourcentageNul = (Float) statistiques
				.getValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_NULS);
		Float pourcentageVEXT = (Float) statistiques
				.getValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_VICTOIRE_EXTERIEUR);

		stats.put("Pourcentage de victoires de l'équipe " + e2, pourcentageVEXT + "%");
		stats.put("Pourcentage de matchs nuls", pourcentageNul + "%");
		stats.put("Pourcentage de victoires de l'équipe " + e1, pourcentageVDOM + "%");

		PourcentageComparator comparator = new PourcentageComparator(stats);
		TreeMap<String, String> mapTree = new TreeMap<String, String>(comparator);
		mapTree.putAll(stats);

		return mapTree;
	}

	private TreeMap<String, String> getTreeMapStatsPronostics(IStorageResource statistiques, String match,
			IWorkflowInstance wi) throws VdocHelperException {

		String[] score = match.split("-");
		String e1 = score[0];
		String e2 = score[1];

		Map<String, String> stats = new HashMap<String, String>();

		Float totalPointsE1 = getProbabilityPointsForHomeTeam(statistiques, match, wi);
		Float totalPointsNul = getDrawProbabilityPoints(statistiques, match, wi);
		Float totalPointsE2 = getProbabilityPointsForAwayTeam(statistiques, match, wi);

		Float total = totalPointsE1 + totalPointsE2 + totalPointsNul;

		Float probabiliteVictoireEquipeDomicile = 0F;
		Float probabiliteMatchNul = 0F;
		Float probabiliteVictoireEquipeExterieur = 0F;

		if (total != 0F) {
			probabiliteVictoireEquipeDomicile = (totalPointsE1 / total) * 100;
			probabiliteMatchNul = (totalPointsNul / total) * 100;
			probabiliteVictoireEquipeExterieur = (totalPointsE2 / total) * 100;
		}

		stats.put("Probabilité de victoire de l'équipe " + e1, probabiliteVictoireEquipeDomicile + "%");
		stats.put("Pourcentage de matchs nuls", probabiliteMatchNul + "%");
		stats.put("Pourcentage de victoires de l'équipe " + e2, probabiliteVictoireEquipeExterieur + "%");

		PourcentageComparator comparator = new PourcentageComparator(stats);
		TreeMap<String, String> mapTree = new TreeMap<String, String>(comparator);
		mapTree.putAll(stats);

		return mapTree;
	}

	private Float getProbabilityPointsForAwayTeam(IStorageResource statistiques, String match, IWorkflowInstance wi)
			throws VdocHelperException {

		Float totalPoints = 0F;

		String[] score = match.split("-");
		String e1 = score[0];
		String e2 = score[1];

		// 50%
		Float averagePercentageOfAwayTeamVictoriesInChampionship = getAveragePercentageInChampionship(
				UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_GAGNES_EXTERIEUR,
				UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_JOUES_EXTERIEUR);

		Float pourcentageVEXTConfrontations = ((Float) statistiques
				.getValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_VICTOIRE_EXTERIEUR));

		String classementE1 = (String) UtilitaireLigue1.getStatString(e1,
				UtilitaireLigue1.TABLE_FIELD_STRING_CLASSEMENT);
		String classementE2 = (String) UtilitaireLigue1.getStatString(e2,
				UtilitaireLigue1.TABLE_FIELD_STRING_CLASSEMENT);
		Float nbPointsRapportesParClassement = 0F;
		if (!UtilitaireLigue1.isEmpty(classementE2) && !UtilitaireLigue1.isEmpty(classementE1)) {
			// e2 mieux classée que e1
			if (Float.parseFloat(classementE2) < Float.parseFloat(classementE1)) {
				nbPointsRapportesParClassement = (Float.parseFloat(classementE1) - Float.parseFloat(classementE2));
			}
		}

		String classementE1Domicile = (String) UtilitaireLigue1.getStatString(e1,
				UtilitaireLigue1.TABLE_FIELD_STRING_CLASSEMENT_DOMICILE);
		String classementE2Exterieur = (String) UtilitaireLigue1.getStatString(e2,
				UtilitaireLigue1.TABLE_FIELD_STRING_CLASSEMENT_EXTERIEUR);
		Float nbPointsRapportesParClassementsDomEtExt = 0F;
		if (!UtilitaireLigue1.isEmpty(classementE2Exterieur) && !UtilitaireLigue1.isEmpty(classementE1Domicile)) {
			// e2 mieux classée que e1
			if (Float.parseFloat(classementE2Exterieur) < Float.parseFloat(classementE1Domicile)) {
				nbPointsRapportesParClassementsDomEtExt = (Float.parseFloat(classementE1Domicile)
						- Float.parseFloat(classementE2Exterieur));
			}
		}

		Float nbPointsForme = 0F;
		String formeE1 = getFormeActuelle(e1);
		String formeE2 = getFormeActuelle(e2);
		if (!UtilitaireLigue1.isEmpty(formeE1) && !UtilitaireLigue1.isEmpty(formeE2)) {
			if (formeE2.equals("V")) {
				int serieVe2 = UtilitaireLigue1.getStatInt(e2, UtilitaireLigue1.TABLE_FIELD_SERIE_V_EN_COURS);
				if (formeE1.equals("V")) {
					int serieVe1 = UtilitaireLigue1.getStatInt(e1, UtilitaireLigue1.TABLE_FIELD_SERIE_V_EN_COURS);
					if (serieVe2 > serieVe1) {
						nbPointsForme = (float) (serieVe2 - serieVe1) * 5;
					}
				} else {
					if (formeE1.equals("N")) {
						nbPointsForme = (float) serieVe2 * 10;
					} else {
						nbPointsForme = (float) serieVe2 * 15;
					}
				}
			}
		}

		Float nbPointsFormeDomExt = 0F;
		String formeE1Dom = getFormeActuelleDomicile(e1);
		String formeE2Ext = getFormeActuelleExterieur(e2);
		if (!UtilitaireLigue1.isEmpty(formeE1Dom) && !UtilitaireLigue1.isEmpty(formeE2Ext)) {
			if (formeE2Ext.equals("V")) {
				int serieVe2 = UtilitaireLigue1.getStatInt(e2, UtilitaireLigue1.TABLE_FIELD_SERIE_V_EN_COURS_EXTERIEUR);
				if (formeE1Dom.equals("V")) {
					int serieVe1 = UtilitaireLigue1.getStatInt(e1,
							UtilitaireLigue1.TABLE_FIELD_SERIE_V_EN_COURS_EXTERIEUR);
					if (serieVe2 > serieVe1) {
						nbPointsFormeDomExt = (float) (serieVe2 - serieVe1) * 5;
					}
				} else {
					if (formeE1.equals("N")) {
						nbPointsFormeDomExt = (float) serieVe2 * 10;
					} else {
						nbPointsFormeDomExt = (float) serieVe2 * 15;
					}
				}
			}
		}

		Float forme5DerniersMatchsE2 = getFormePointsForTeam(e2);
		Float forme5DerniersMatchsE1 = getFormePointsForTeam(e1);
		Float forme5DerniersMatchs = 0F;
		if (forme5DerniersMatchsE2 > forme5DerniersMatchsE1) {
			forme5DerniersMatchs = forme5DerniersMatchsE2 - forme5DerniersMatchsE1;
		}

		Float forme5DerniersMatchsE2Exterieur = getFormePointsExterieur(e2);
		Float forme5DerniersMatchsE1Domicile = getFormePointsDomicile(e1);
		Float forme5DerniersMatchsDomExt = 0F;
		if (forme5DerniersMatchsE2Exterieur > forme5DerniersMatchsE1Domicile) {
			forme5DerniersMatchsDomExt = forme5DerniersMatchsE2Exterieur - forme5DerniersMatchsE1Domicile;
		}

		Float pointsResultats = 0F;
		Float pourcentageDefaitesE1 = 0F;
		Float pourcentageVictoiresE2 = 0F;

		int jouesE1 = UtilitaireLigue1.getStatInt(e1, UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_JOUES);
		int perdusE1 = UtilitaireLigue1.getStatInt(e1, UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_PERDUS);
		if (jouesE1 > 0) {
			pourcentageDefaitesE1 = (float) ((perdusE1 / jouesE1)) * 100;
		}
		int jouesE2 = UtilitaireLigue1.getStatInt(e2, UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_JOUES);
		int gagnesE2 = UtilitaireLigue1.getStatInt(e2, UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_GAGNES);
		if (jouesE2 > 0) {
			pourcentageVictoiresE2 = (float) ((gagnesE2 / jouesE2)) * 100;
		}
		pointsResultats = (pourcentageDefaitesE1 + pourcentageVictoiresE2) / 2;

		Float pointsResultatsDomExt = 0F;
		Float pourcentageDefaitesE1Dom = 0F;
		Float pourcentageVictoiresE2Ext = 0F;

		int jouesE1Dom = UtilitaireLigue1.getStatInt(e1, UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_JOUES_DOMICILE);
		int perdusE1Dom = UtilitaireLigue1.getStatInt(e1, UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_PERDUS_DOMICILE);
		if (jouesE1Dom > 0) {
			pourcentageDefaitesE1Dom = (float) ((perdusE1Dom / jouesE1Dom)) * 100;
		}
		int jouesE2Ext = UtilitaireLigue1.getStatInt(e2, UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_JOUES_EXTERIEUR);
		int gagnesE2Ext = UtilitaireLigue1.getStatInt(e2, UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_GAGNES_EXTERIEUR);
		if (jouesE2Ext > 0) {
			pourcentageVictoiresE2Ext = (float) ((gagnesE2Ext / jouesE2Ext)) * 100;
		}
		pointsResultatsDomExt = (pourcentageDefaitesE1Dom + pourcentageVictoiresE2Ext) / 2;

		Boolean e1MieuxClassee = (Boolean) wi
				.getValue(UtilitaireLigue1.FORM_FIELD_CHECK_BOX_EQUIPE_DOMICILE_MIEUX_CLASSEE);
		Boolean e1MatchImportant = (Boolean) wi
				.getValue(UtilitaireLigue1.FORM_FIELD_CHECK_BOX_IMPORTANT_EQUIPE_DOMICILE);
		Boolean e2MatchImportant = (Boolean) wi
				.getValue(UtilitaireLigue1.FORM_FIELD_CHECK_BOX_IMPORTANT_EQUIPE_EXTERIEUR);

		Float pointsResultatsImportance = 0F;
		Float pointsResultatsImportanceDomExt = 0F;

		if (e2MatchImportant) {
			if (e1MatchImportant) {
				Float pourcentageDefaitesE1Importance = 0F;
				Float pourcentageVictoiresE2Importance = 0F;

				int jouesE1Importance = UtilitaireLigue1.getStatInt(e1,
						UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_JOUES_IMPORTANTS);
				int perdusE1Importance = UtilitaireLigue1.getStatInt(e1,
						UtilitaireLigue1.TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_IMPORTANT);
				if (jouesE1Importance > 0) {
					pourcentageDefaitesE1Importance = (float) ((perdusE1Importance / jouesE1Importance)) * 100;
				}
				int jouesE2Importance = UtilitaireLigue1.getStatInt(e2,
						UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_JOUES_IMPORTANTS);
				int gagnesE2Importance = UtilitaireLigue1.getStatInt(e2,
						UtilitaireLigue1.TABLE_FIELD_NOMBRE_VICTOIRES_IMPORTANT);
				if (jouesE2Importance > 0) {
					pourcentageVictoiresE2Importance = (float) ((gagnesE2Importance / jouesE2Importance)) * 100;
				}
				pointsResultatsImportance = (pourcentageDefaitesE1Importance + pourcentageVictoiresE2Importance) / 2;

				Float pourcentageDefaitesE1DomImportance = 0F;
				Float pourcentageVictoiresE2ExtImportance = 0F;

				int jouesE1DomImportance = UtilitaireLigue1.getStatInt(e1,
						UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_JOUES_IMPORTANTS_DOMICILE);
				int perdusE1DomImportance = UtilitaireLigue1.getStatInt(e1,
						UtilitaireLigue1.TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_IMPORTANT_DOMICILE);
				if (jouesE1DomImportance > 0) {
					pourcentageDefaitesE1DomImportance = (float) ((perdusE1DomImportance / jouesE1DomImportance)) * 100;
				}
				int jouesE2ExtImportance = UtilitaireLigue1.getStatInt(e2,
						UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_JOUES_IMPORTANTS_EXTERIEUR);
				int gagnesE2ExtImportance = UtilitaireLigue1.getStatInt(e2,
						UtilitaireLigue1.TABLE_FIELD_NOMBRE_VICTOIRES_IMPORTANT_EXTERIEUR);
				if (jouesE2ExtImportance > 0) {
					pourcentageVictoiresE2ExtImportance = (float) ((gagnesE2ExtImportance / jouesE2ExtImportance))
							* 100;
				}
				pointsResultatsImportanceDomExt = (pourcentageDefaitesE1DomImportance
						+ pourcentageVictoiresE2ExtImportance) / 2;
			} else {
				Float pourcentageDefaitesE1ImportanceNon = 0F;
				Float pourcentageVictoiresE2Importance = 0F;

				int jouesE1ImportanceNon = UtilitaireLigue1.getStatInt(e1,
						UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_JOUES_BANAL);
				int perdusE1ImportanceNon = UtilitaireLigue1.getStatInt(e1,
						UtilitaireLigue1.TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_BANAL);
				if (jouesE1ImportanceNon > 0) {
					pourcentageDefaitesE1ImportanceNon = (float) ((perdusE1ImportanceNon / jouesE1ImportanceNon)) * 100;
				}
				int jouesE2Importance = UtilitaireLigue1.getStatInt(e2,
						UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_JOUES_IMPORTANTS);
				int gagnesE2Importance = UtilitaireLigue1.getStatInt(e2,
						UtilitaireLigue1.TABLE_FIELD_NOMBRE_VICTOIRES_IMPORTANT);
				if (jouesE2Importance > 0) {
					pourcentageVictoiresE2Importance = (float) ((gagnesE2Importance / jouesE2Importance)) * 100;
				}
				pointsResultatsImportance = (pourcentageDefaitesE1ImportanceNon + pourcentageVictoiresE2Importance) / 2;

				Float pourcentageDefaitesE1DomImportanceNon = 0F;
				Float pourcentageVictoiresE2ExtImportance = 0F;

				int jouesE1DomImportanceNon = UtilitaireLigue1.getStatInt(e1,
						UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_JOUES_BANAL_DOMICILE);
				int perdusE1DomImportanceNon = UtilitaireLigue1.getStatInt(e1,
						UtilitaireLigue1.TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_BANAL_DOMICILE);
				if (jouesE1DomImportanceNon > 0) {
					pourcentageDefaitesE1DomImportanceNon = (float) ((perdusE1DomImportanceNon
							/ jouesE1DomImportanceNon)) * 100;
				}
				int jouesE2ExtImportance = UtilitaireLigue1.getStatInt(e2,
						UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_JOUES_IMPORTANTS_EXTERIEUR);
				int gagnesE2ExtImportance = UtilitaireLigue1.getStatInt(e2,
						UtilitaireLigue1.TABLE_FIELD_NOMBRE_VICTOIRES_IMPORTANT_EXTERIEUR);
				if (jouesE2ExtImportance > 0) {
					pourcentageVictoiresE2ExtImportance = (float) ((gagnesE2ExtImportance / jouesE2ExtImportance))
							* 100;
				}
				pointsResultatsImportanceDomExt = (pourcentageDefaitesE1DomImportanceNon
						+ pourcentageVictoiresE2ExtImportance) / 2;
			}
		} else {
			if (e1MatchImportant) {
				Float pourcentageDefaitesE1Importance = 0F;
				Float pourcentageVictoiresE2ImportanceNon = 0F;

				int jouesE1Importance = UtilitaireLigue1.getStatInt(e1,
						UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_JOUES_IMPORTANTS);
				int perdusE1Importance = UtilitaireLigue1.getStatInt(e1,
						UtilitaireLigue1.TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_IMPORTANT);
				if (jouesE1Importance > 0) {
					pourcentageDefaitesE1Importance = (float) ((perdusE1Importance / jouesE1Importance)) * 100;
				}
				int jouesE2ImportanceNon = UtilitaireLigue1.getStatInt(e2,
						UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_JOUES_BANAL);
				int gagnesE2ImportanceNon = UtilitaireLigue1.getStatInt(e2,
						UtilitaireLigue1.TABLE_FIELD_NOMBRE_VICTOIRES_BANAL);
				if (jouesE2ImportanceNon > 0) {
					pourcentageVictoiresE2ImportanceNon = (float) ((gagnesE2ImportanceNon / jouesE2ImportanceNon))
							* 100;
				}
				pointsResultatsImportance = (pourcentageDefaitesE1Importance + pourcentageVictoiresE2ImportanceNon) / 2;

				Float pourcentageDefaitesE1DomImportance = 0F;
				Float pourcentageVictoiresE2ExtImportanceNon = 0F;

				int jouesE1DomImportance = UtilitaireLigue1.getStatInt(e1,
						UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_JOUES_IMPORTANTS_DOMICILE);
				int perdusE1DomImportance = UtilitaireLigue1.getStatInt(e1,
						UtilitaireLigue1.TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_IMPORTANT_DOMICILE);
				if (jouesE1DomImportance > 0) {
					pourcentageDefaitesE1DomImportance = (float) ((perdusE1DomImportance / jouesE1DomImportance)) * 100;
				}
				int jouesE2ExtImportanceNon = UtilitaireLigue1.getStatInt(e2,
						UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_JOUES_BANAL_EXTERIEUR);
				int gagnesE2ExtImportanceNon = UtilitaireLigue1.getStatInt(e2,
						UtilitaireLigue1.TABLE_FIELD_NOMBRE_VICTOIRES_BANAL_EXTERIEUR);
				if (jouesE2ExtImportanceNon > 0) {
					pourcentageVictoiresE2ExtImportanceNon = (float) ((gagnesE2ExtImportanceNon
							/ jouesE2ExtImportanceNon)) * 100;
				}
				pointsResultatsImportanceDomExt = (pourcentageDefaitesE1DomImportance
						+ pourcentageVictoiresE2ExtImportanceNon) / 2;
			} else {
				Float pourcentageDefaitesE1ImportanceNon = 0F;
				Float pourcentageVictoiresE2ImportanceNon = 0F;

				int jouesE1ImportanceNon = UtilitaireLigue1.getStatInt(e1,
						UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_JOUES_BANAL);
				int perdusE1ImportanceNon = UtilitaireLigue1.getStatInt(e1,
						UtilitaireLigue1.TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_BANAL);
				if (jouesE1ImportanceNon > 0) {
					pourcentageDefaitesE1ImportanceNon = (float) ((perdusE1ImportanceNon / jouesE1ImportanceNon)) * 100;
				}
				int jouesE2ImportanceNon = UtilitaireLigue1.getStatInt(e2,
						UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_JOUES_BANAL);
				int gagnesE2ImportanceNon = UtilitaireLigue1.getStatInt(e2,
						UtilitaireLigue1.TABLE_FIELD_NOMBRE_VICTOIRES_BANAL);
				if (jouesE2ImportanceNon > 0) {
					pourcentageVictoiresE2ImportanceNon = (float) ((gagnesE2ImportanceNon / jouesE2ImportanceNon))
							* 100;
				}
				pointsResultatsImportance = (pourcentageDefaitesE1ImportanceNon + pourcentageVictoiresE2ImportanceNon)
						/ 2;

				Float pourcentageDefaitesE1DomImportanceNon = 0F;
				Float pourcentageVictoiresE2ExtImportanceNon = 0F;

				int jouesE1DomImportanceNon = UtilitaireLigue1.getStatInt(e1,
						UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_JOUES_BANAL_DOMICILE);
				int perdusE1DomImportanceNon = UtilitaireLigue1.getStatInt(e1,
						UtilitaireLigue1.TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_BANAL_DOMICILE);
				if (jouesE1DomImportanceNon > 0) {
					pourcentageDefaitesE1DomImportanceNon = (float) ((perdusE1DomImportanceNon
							/ jouesE1DomImportanceNon)) * 100;
				}
				int jouesE2ExtImportanceNon = UtilitaireLigue1.getStatInt(e2,
						UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_JOUES_BANAL_EXTERIEUR);
				int gagnesE2ExtImportanceNon = UtilitaireLigue1.getStatInt(e2,
						UtilitaireLigue1.TABLE_FIELD_NOMBRE_VICTOIRES_BANAL_EXTERIEUR);
				if (jouesE2ExtImportanceNon > 0) {
					pourcentageVictoiresE2ExtImportanceNon = (float) ((gagnesE2ExtImportanceNon
							/ jouesE2ExtImportanceNon)) * 100;
				}
				pointsResultatsImportanceDomExt = (pourcentageDefaitesE1DomImportanceNon
						+ pourcentageVictoiresE2ExtImportanceNon) / 2;

			}
		}

		Float pointsResultatsClassement = 0F;
		Float pointsResultatsClassementDomExt = 0F;

		if (e1MieuxClassee) {

			Float pourcentageDefaitesE1ContreClassementInferieur = 0F;
			Float pourcentageVictoiresE2ContreClassementSuperieur = 0F;

			int jouesE1ContreClassementInferieur = UtilitaireLigue1.getStatInt(e1,
					UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_JOUES_CLASSEMENT_INF);
			int perdusE1ContreClassementInferieur = UtilitaireLigue1.getStatInt(e1,
					UtilitaireLigue1.TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_CLASSEMENT_INFERIEUR);
			if (jouesE1ContreClassementInferieur > 0) {
				pourcentageDefaitesE1ContreClassementInferieur = (float) ((perdusE1ContreClassementInferieur
						/ jouesE1ContreClassementInferieur)) * 100;
			}
			int jouesE2ContreClassementSuperieur = UtilitaireLigue1.getStatInt(e2,
					UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_JOUES_CLASSEMENT_SUP);
			int gagnesE2ContreClassementSuperieur = UtilitaireLigue1.getStatInt(e2,
					UtilitaireLigue1.TABLE_FIELD_NOMBRE_VICTOIRES_CLASSEMENT_SUP);
			if (jouesE2ContreClassementSuperieur > 0) {
				pourcentageVictoiresE2ContreClassementSuperieur = (float) ((gagnesE2ContreClassementSuperieur
						/ jouesE2ContreClassementSuperieur)) * 100;
			}
			pointsResultatsClassement = (pourcentageDefaitesE1ContreClassementInferieur
					+ pourcentageVictoiresE2ContreClassementSuperieur) / 2;

			Float pourcentageDefaitesE1ContreClassementInferieurDomicile = 0F;
			Float pourcentageVictoiresE2ContreClassementSuperieurExterieur = 0F;

			int jouesE1ContreClassementInferieurDomicile = UtilitaireLigue1.getStatInt(e1,
					UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_JOUES_CLASSEMENT_INF_DOMICILE);
			int perdusE1ContreClassementInferieurDomicile = UtilitaireLigue1.getStatInt(e1,
					UtilitaireLigue1.TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_CLASSEMENT_INFERIEUR_DOMICILE);
			if (jouesE1ContreClassementInferieurDomicile > 0) {
				pourcentageDefaitesE1ContreClassementInferieurDomicile = (float) ((perdusE1ContreClassementInferieurDomicile
						/ jouesE1ContreClassementInferieurDomicile)) * 100;
			}
			int jouesE2ContreClassementSuperieurExterieur = UtilitaireLigue1.getStatInt(e2,
					UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_JOUES_CLASSEMENT_SUP_EXTERIEUR);
			int gagnesE2ContreClassementSuperieurExterieur = UtilitaireLigue1.getStatInt(e2,
					UtilitaireLigue1.TABLE_FIELD_NOMBRE_VICTOIRES_CLASSEMENT_SUP_EXTERIEUR);
			if (jouesE2ContreClassementSuperieurExterieur > 0) {
				pourcentageVictoiresE2ContreClassementSuperieurExterieur = (float) ((gagnesE2ContreClassementSuperieurExterieur
						/ jouesE2ContreClassementSuperieurExterieur)) * 100;
			}
			pointsResultatsClassementDomExt = (pourcentageDefaitesE1ContreClassementInferieurDomicile
					+ pourcentageVictoiresE2ContreClassementSuperieurExterieur) / 2;

		} else {
			Float pourcentageDefaitesE1ContreClassementSuperieur = 0F;
			Float pourcentageVictoiresE2ContreClassementInferieur = 0F;

			int jouesE1ContreClassementSuperieur = UtilitaireLigue1.getStatInt(e1,
					UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_JOUES_CLASSEMENT_SUP);
			int perdusE1ContreClassementSuperieur = UtilitaireLigue1.getStatInt(e1,
					UtilitaireLigue1.TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_CLASSEMENT_SUPERIEUR);
			if (jouesE1ContreClassementSuperieur > 0) {
				pourcentageDefaitesE1ContreClassementSuperieur = (float) ((perdusE1ContreClassementSuperieur
						/ jouesE1ContreClassementSuperieur)) * 100;
			}
			int jouesE2ContreClassementInferieur = UtilitaireLigue1.getStatInt(e2,
					UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_JOUES_CLASSEMENT_INF);
			int gagnesE2ContreClassementInferieur = UtilitaireLigue1.getStatInt(e2,
					UtilitaireLigue1.TABLE_FIELD_NOMBRE_VICTOIRES_CLASSEMENT_INF);
			if (jouesE2ContreClassementInferieur > 0) {
				pourcentageVictoiresE2ContreClassementInferieur = (float) ((gagnesE2ContreClassementInferieur
						/ jouesE2ContreClassementInferieur)) * 100;
			}
			pointsResultatsClassement = (pourcentageDefaitesE1ContreClassementSuperieur
					+ pourcentageVictoiresE2ContreClassementInferieur) / 2;

			Float pourcentageDefaitesE1ContreClassementSuperieurDomicile = 0F;
			Float pourcentageVictoiresE2ContreClassementInferieurExterieur = 0F;

			int jouesE1ContreClassementSuperieurDomicile = UtilitaireLigue1.getStatInt(e1,
					UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_JOUES_CLASSEMENT_SUP_DOMICILE);
			int perdusE1ContreClassementSuperieurDomicile = UtilitaireLigue1.getStatInt(e1,
					UtilitaireLigue1.TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_CLASSEMENT_SUPERIEUR_DOMICILE);
			if (jouesE1ContreClassementSuperieurDomicile > 0) {
				pourcentageDefaitesE1ContreClassementSuperieurDomicile = (float) ((perdusE1ContreClassementSuperieurDomicile
						/ jouesE1ContreClassementSuperieurDomicile)) * 100;
			}
			int jouesE2ContreClassementInferieurExterieur = UtilitaireLigue1.getStatInt(e2,
					UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_JOUES_CLASSEMENT_INF_EXTERIEUR);
			int gagnesE2ContreClassementInferieurExterieur = UtilitaireLigue1.getStatInt(e2,
					UtilitaireLigue1.TABLE_FIELD_NOMBRE_VICTOIRES_CLASSEMENT_INF_EXTERIEUR);
			if (jouesE2ContreClassementInferieurExterieur > 0) {
				pourcentageVictoiresE2ContreClassementInferieurExterieur = (float) ((gagnesE2ContreClassementInferieurExterieur
						/ jouesE2ContreClassementInferieurExterieur)) * 100;
			}
			pointsResultatsClassementDomExt = (pourcentageDefaitesE1ContreClassementSuperieurDomicile
					+ pourcentageVictoiresE2ContreClassementInferieurExterieur) / 2;

		}

		totalPoints = coefficientB * averagePercentageOfAwayTeamVictoriesInChampionship
				+ (coefficientF * pourcentageVEXTConfrontations) + (coefficientC * nbPointsRapportesParClassement)
				+ (coefficientC * nbPointsRapportesParClassementsDomEtExt) + (coefficientC * nbPointsForme)
				+ (coefficientC * forme5DerniersMatchs) + (coefficientC * forme5DerniersMatchsDomExt)
				+ (coefficientD * pointsResultats) + (coefficientD * pointsResultatsDomExt)
				+ (coefficientC * nbPointsFormeDomExt) + (coefficientD * pointsResultatsImportance)
				+ (coefficientD * pointsResultatsImportanceDomExt) + (coefficientD * pointsResultatsClassement)
				+ (coefficientD * pointsResultatsClassementDomExt);

		return totalPoints;
	}

	private Float getFormePointsDomicile(String e) throws VdocHelperException {

		Float forme = 0F;

		String precedent1 = UtilitaireLigue1.getStatString(e,
				UtilitaireLigue1.TABLE_FIELD_STRING_MATCH_PRECEDENT_1_DOMICILE);
		String precedent2 = UtilitaireLigue1.getStatString(e,
				UtilitaireLigue1.TABLE_FIELD_STRING_MATCH_PRECEDENT_2_DOMICILE);
		String precedent3 = UtilitaireLigue1.getStatString(e,
				UtilitaireLigue1.TABLE_FIELD_STRING_MATCH_PRECEDENT_3_DOMICILE);
		String precedent4 = UtilitaireLigue1.getStatString(e,
				UtilitaireLigue1.TABLE_FIELD_STRING_MATCH_PRECEDENT_4_DOMICILE);
		String precedent5 = UtilitaireLigue1.getStatString(e,
				UtilitaireLigue1.TABLE_FIELD_STRING_MATCH_PRECEDENT_5_DOMICILE);

		forme = getNombreForme("V", precedent1, precedent2, precedent3, precedent4, precedent5) * 10F
				+ getNombreForme("N", precedent1, precedent2, precedent3, precedent4, precedent5) * 5F;

		return forme;
	}

	private Float getFormePointsExterieur(String e) throws VdocHelperException {

		Float forme = 0F;

		String precedent1 = UtilitaireLigue1.getStatString(e,
				UtilitaireLigue1.TABLE_FIELD_STRING_MATCH_PRECEDENT_1_EXTERIEUR);
		String precedent2 = UtilitaireLigue1.getStatString(e,
				UtilitaireLigue1.TABLE_FIELD_STRING_MATCH_PRECEDENT_2_EXTERIEUR);
		String precedent3 = UtilitaireLigue1.getStatString(e,
				UtilitaireLigue1.TABLE_FIELD_STRING_MATCH_PRECEDENT_3_EXTERIEUR);
		String precedent4 = UtilitaireLigue1.getStatString(e,
				UtilitaireLigue1.TABLE_FIELD_STRING_MATCH_PRECEDENT_4_EXTERIEUR);
		String precedent5 = UtilitaireLigue1.getStatString(e,
				UtilitaireLigue1.TABLE_FIELD_STRING_MATCH_PRECEDENT_5_EXTERIEUR);

		forme = getNombreForme("V", precedent1, precedent2, precedent3, precedent4, precedent5) * 10F
				+ getNombreForme("N", precedent1, precedent2, precedent3, precedent4, precedent5) * 5F;

		return forme;
	}

	private Float getFormePointsForTeam(String e) throws VdocHelperException {

		Float forme = 0F;

		String precedent1 = UtilitaireLigue1.getStatString(e, UtilitaireLigue1.TABLE_FIELD_STRING_MATCH_PRECEDENT_1);
		String precedent2 = UtilitaireLigue1.getStatString(e, UtilitaireLigue1.TABLE_FIELD_STRING_MATCH_PRECEDENT_2);
		String precedent3 = UtilitaireLigue1.getStatString(e, UtilitaireLigue1.TABLE_FIELD_STRING_MATCH_PRECEDENT_3);
		String precedent4 = UtilitaireLigue1.getStatString(e, UtilitaireLigue1.TABLE_FIELD_STRING_MATCH_PRECEDENT_4);
		String precedent5 = UtilitaireLigue1.getStatString(e, UtilitaireLigue1.TABLE_FIELD_STRING_MATCH_PRECEDENT_5);

		forme = getNombreForme("V", precedent1, precedent2, precedent3, precedent4, precedent5) * 10F
				+ getNombreForme("N", precedent1, precedent2, precedent3, precedent4, precedent5) * 5F;

		return forme;
	}
	
	private Float getFormePointsForDraw(String e1, String e2) throws VdocHelperException {

			Float forme = 0F;

			String e1precedent1 = UtilitaireLigue1.getStatString(e1, UtilitaireLigue1.TABLE_FIELD_STRING_MATCH_PRECEDENT_1);
			String e1precedent2 = UtilitaireLigue1.getStatString(e1, UtilitaireLigue1.TABLE_FIELD_STRING_MATCH_PRECEDENT_2);
			String e1precedent3 = UtilitaireLigue1.getStatString(e1, UtilitaireLigue1.TABLE_FIELD_STRING_MATCH_PRECEDENT_3);
			String e1precedent4 = UtilitaireLigue1.getStatString(e1, UtilitaireLigue1.TABLE_FIELD_STRING_MATCH_PRECEDENT_4);
			String e1precedent5 = UtilitaireLigue1.getStatString(e1, UtilitaireLigue1.TABLE_FIELD_STRING_MATCH_PRECEDENT_5);
			
			String e2precedent1 = UtilitaireLigue1.getStatString(e2, UtilitaireLigue1.TABLE_FIELD_STRING_MATCH_PRECEDENT_1);
			String e2precedent2 = UtilitaireLigue1.getStatString(e2, UtilitaireLigue1.TABLE_FIELD_STRING_MATCH_PRECEDENT_2);
			String e2precedent3 = UtilitaireLigue1.getStatString(e2, UtilitaireLigue1.TABLE_FIELD_STRING_MATCH_PRECEDENT_3);
			String e2precedent4 = UtilitaireLigue1.getStatString(e2, UtilitaireLigue1.TABLE_FIELD_STRING_MATCH_PRECEDENT_4);
			String e2precedent5 = UtilitaireLigue1.getStatString(e2, UtilitaireLigue1.TABLE_FIELD_STRING_MATCH_PRECEDENT_5);

			forme = getNombreForme("N", e1precedent1, e1precedent2, e1precedent3, e1precedent4, e1precedent5) * 10F
					+ getNombreForme("N", e2precedent1, e2precedent2, e2precedent3, e2precedent4, e2precedent5) * 10F;

			return forme;
		
	}
	
	private float getFormePointsForDrawDomExt(String e1, String e2) throws VdocHelperException {
		
		Float forme = 0F;

		String e1precedent1 = UtilitaireLigue1.getStatString(e1, UtilitaireLigue1.TABLE_FIELD_STRING_MATCH_PRECEDENT_1_DOMICILE);
		String e1precedent2 = UtilitaireLigue1.getStatString(e1, UtilitaireLigue1.TABLE_FIELD_STRING_MATCH_PRECEDENT_2_DOMICILE);
		String e1precedent3 = UtilitaireLigue1.getStatString(e1, UtilitaireLigue1.TABLE_FIELD_STRING_MATCH_PRECEDENT_3_DOMICILE);
		String e1precedent4 = UtilitaireLigue1.getStatString(e1, UtilitaireLigue1.TABLE_FIELD_STRING_MATCH_PRECEDENT_4_DOMICILE);
		String e1precedent5 = UtilitaireLigue1.getStatString(e1, UtilitaireLigue1.TABLE_FIELD_STRING_MATCH_PRECEDENT_5_DOMICILE);
		
		String e2precedent1 = UtilitaireLigue1.getStatString(e2, UtilitaireLigue1.TABLE_FIELD_STRING_MATCH_PRECEDENT_1_EXTERIEUR);
		String e2precedent2 = UtilitaireLigue1.getStatString(e2, UtilitaireLigue1.TABLE_FIELD_STRING_MATCH_PRECEDENT_2_EXTERIEUR);
		String e2precedent3 = UtilitaireLigue1.getStatString(e2, UtilitaireLigue1.TABLE_FIELD_STRING_MATCH_PRECEDENT_3_EXTERIEUR);
		String e2precedent4 = UtilitaireLigue1.getStatString(e2, UtilitaireLigue1.TABLE_FIELD_STRING_MATCH_PRECEDENT_4_EXTERIEUR);
		String e2precedent5 = UtilitaireLigue1.getStatString(e2, UtilitaireLigue1.TABLE_FIELD_STRING_MATCH_PRECEDENT_5_EXTERIEUR);

		forme = getNombreForme("N", e1precedent1, e1precedent2, e1precedent3, e1precedent4, e1precedent5) * 10F
				+ getNombreForme("N", e2precedent1, e2precedent2, e2precedent3, e2precedent4, e2precedent5) * 10F;

		return forme;
		
	}

	private int getNombreForme(String forme, String precedent1, String precedent2, String precedent3, String precedent4,
			String precedent5) {

		int total = 0;
		if (precedent1.equals(forme))
			total++;
		if (precedent2.equals(forme))
			total++;
		if (precedent3.equals(forme))
			total++;
		if (precedent4.equals(forme))
			total++;
		if (precedent5.equals(forme))
			total++;
		return total;
	}

	private String getFormeActuelleExterieur(String e) {

		try {
			int serieV = UtilitaireLigue1.getStatInt(e, UtilitaireLigue1.TABLE_FIELD_SERIE_V_EN_COURS_EXTERIEUR);
			if (serieV > 0)
				return "V";
			int serieN = UtilitaireLigue1.getStatInt(e, UtilitaireLigue1.TABLE_FIELD_SERIE_N_EN_COURS_EXTERIEUR);
			if (serieN > 0)
				return "N";
			int serieD = UtilitaireLigue1.getStatInt(e, UtilitaireLigue1.TABLE_FIELD_SERIE_D_EN_COURS_EXTERIEUR);
			if (serieD > 0)
				return "D";

		} catch (VdocHelperException e1) {
			e1.printStackTrace();
		}
		return null;
	}

	private String getFormeActuelleDomicile(String e) {

		try {
			int serieV = UtilitaireLigue1.getStatInt(e, UtilitaireLigue1.TABLE_FIELD_SERIE_V_EN_COURS_DOMICILE);
			if (serieV > 0)
				return "V";
			int serieN = UtilitaireLigue1.getStatInt(e, UtilitaireLigue1.TABLE_FIELD_SERIE_N_EN_COURS_DOMICILE);
			if (serieN > 0)
				return "N";
			int serieD = UtilitaireLigue1.getStatInt(e, UtilitaireLigue1.TABLE_FIELD_SERIE_D_EN_COURS_DOMICILE);
			if (serieD > 0)
				return "D";

		} catch (VdocHelperException e1) {
			e1.printStackTrace();
		}
		return null;
	}

	private String getFormeActuelle(String e) {

		try {
			int serieV = UtilitaireLigue1.getStatInt(e, UtilitaireLigue1.TABLE_FIELD_SERIE_V_EN_COURS);
			if (serieV > 0)
				return "V";
			int serieN = UtilitaireLigue1.getStatInt(e, UtilitaireLigue1.TABLE_FIELD_SERIE_N_EN_COURS);
			if (serieN > 0)
				return "N";
			int serieD = UtilitaireLigue1.getStatInt(e, UtilitaireLigue1.TABLE_FIELD_SERIE_D_EN_COURS);
			if (serieD > 0)
				return "D";

		} catch (VdocHelperException e1) {
			e1.printStackTrace();
		}
		return null;
	}

	private Float getDrawProbabilityPoints(IStorageResource statistiques, String match, IWorkflowInstance wi) throws VdocHelperException {

		Float totalPoints = 0F;

		String[] score = match.split("-");
		String e1 = score[0];
		String e2 = score[1];

		Float averagePercentageOfDrawsInChampionship = getAveragePercentageInChampionship(
				UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_NULS, UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_JOUES);

		Float pourcentageNulsConfrontations = ((Float) statistiques
				.getValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_NULS));

		String classementE1 = (String) UtilitaireLigue1.getStatString(e1,
				UtilitaireLigue1.TABLE_FIELD_STRING_CLASSEMENT);
		String classementE2 = (String) UtilitaireLigue1.getStatString(e2,
				UtilitaireLigue1.TABLE_FIELD_STRING_CLASSEMENT);
		Float nbPointsRapportesParClassement = 0F;
		if (!UtilitaireLigue1.isEmpty(classementE2) && !UtilitaireLigue1.isEmpty(classementE1)) {
			// il n'y a pas plus de 3 places d'écart au classement
			if (Math.abs(Long.parseLong(classementE1) - Long.parseLong(classementE2)) <= 3) {
				nbPointsRapportesParClassement = Math
						.abs(Float.parseFloat(classementE2) - Float.parseFloat(classementE1)) * 10;
			}
		}

		String classementE1Domicile = (String) UtilitaireLigue1.getStatString(e1,
				UtilitaireLigue1.TABLE_FIELD_STRING_CLASSEMENT_DOMICILE);
		String classementE2Exterieur = (String) UtilitaireLigue1.getStatString(e2,
				UtilitaireLigue1.TABLE_FIELD_STRING_CLASSEMENT_EXTERIEUR);
		Float nbPointsRapportesParClassementsDomEtExt = 0F;
		if (!UtilitaireLigue1.isEmpty(classementE2Exterieur) && !UtilitaireLigue1.isEmpty(classementE1Domicile)) {
			// il n'y a pas plus de 3 places d'écart au classement
			if (Math.abs(Long.parseLong(classementE1Domicile) - Long.parseLong(classementE2Exterieur)) <= 3) {
				nbPointsRapportesParClassement = Math
						.abs(Float.parseFloat(classementE2Exterieur) - Float.parseFloat(classementE1Domicile)) * 10;
			}
		}

		Float nbPointsForme = 0F;
		String formeE1 = getFormeActuelle(e1);
		String formeE2 = getFormeActuelle(e2);
		if (!UtilitaireLigue1.isEmpty(formeE1) && !UtilitaireLigue1.isEmpty(formeE2)) {
			if (formeE1.equals("N")) {
				int serieNe1 = UtilitaireLigue1.getStatInt(e1, UtilitaireLigue1.TABLE_FIELD_SERIE_N_EN_COURS);
				if (formeE2.equals("N")) {
					int serieNe2 = UtilitaireLigue1.getStatInt(e2, UtilitaireLigue1.TABLE_FIELD_SERIE_N_EN_COURS);
					nbPointsForme = (float) (serieNe1 + serieNe2) * 5;
				}
			}
		}

		Float nbPointsFormeDomExt = 0F;
		String formeE1Dom = getFormeActuelleDomicile(e1);
		String formeE2Ext = getFormeActuelleExterieur(e2);
		if (!UtilitaireLigue1.isEmpty(formeE1Dom) && !UtilitaireLigue1.isEmpty(formeE2Ext)) {
			if (formeE1Dom.equals("N")) {
				int serieNe1 = UtilitaireLigue1.getStatInt(e1, UtilitaireLigue1.TABLE_FIELD_SERIE_N_EN_COURS_DOMICILE);
				if (formeE2Ext.equals("N")) {
					int serieNe2 = UtilitaireLigue1.getStatInt(e2, UtilitaireLigue1.TABLE_FIELD_SERIE_N_EN_COURS_EXTERIEUR);
					nbPointsFormeDomExt = (float) (serieNe1 + serieNe2) * 5;
				}
			}
		}

		Float forme5DerniersMatchsDraw = getFormePointsForDraw(e1, e2);

		Float forme5DerniersMatchsDrawDomExt = getFormePointsForDrawDomExt(e1, e2);

		Float pointsResultats = 0F;
		Float pourcentageNulsE2 = 0F;
		Float pourcentageNulsE1 = 0F;

		int jouesE2 = UtilitaireLigue1.getStatInt(e2, UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_JOUES);
		int nulsE2 = UtilitaireLigue1.getStatInt(e2, UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_NULS);
		if (jouesE2 > 0) {
			pourcentageNulsE2 = (float) ((nulsE2 / jouesE2)) * 100;
		}
		int jouesE1 = UtilitaireLigue1.getStatInt(e1, UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_JOUES);
		int nulsE1 = UtilitaireLigue1.getStatInt(e1, UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_NULS);
		if (jouesE1 > 0) {
			pourcentageNulsE1 = (float) ((nulsE1 / jouesE1)) * 100;
		}
		pointsResultats = (pourcentageNulsE2 + pourcentageNulsE1) / 2;

		Float pointsResultatsDomExt = 0F;
		Float pourcentageNulsE2Ext = 0F;
		Float pourcentageNulsE1Dom = 0F;

		int jouesE2Ext = UtilitaireLigue1.getStatInt(e2, UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_JOUES_EXTERIEUR);
		int nulsE2Ext = UtilitaireLigue1.getStatInt(e2, UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_NULS_EXTERIEUR);
		if (jouesE2Ext > 0) {
			pourcentageNulsE2Ext = (float) ((nulsE2Ext / jouesE2Ext)) * 100;
		}
		int jouesE1Dom = UtilitaireLigue1.getStatInt(e1, UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_JOUES_DOMICILE);
		int nulsE1Dom = UtilitaireLigue1.getStatInt(e1, UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_NULS_DOMICILE);
		if (jouesE1Dom > 0) {
			pourcentageNulsE1Dom = (float) ((nulsE1Dom / jouesE1Dom)) * 100;
		}
		pointsResultatsDomExt = (pourcentageNulsE2Ext + pourcentageNulsE1Dom) / 2;

		Boolean e1MieuxClassee = (Boolean) wi
				.getValue(UtilitaireLigue1.FORM_FIELD_CHECK_BOX_EQUIPE_DOMICILE_MIEUX_CLASSEE);
		Boolean e1MatchImportant = (Boolean) wi
				.getValue(UtilitaireLigue1.FORM_FIELD_CHECK_BOX_IMPORTANT_EQUIPE_DOMICILE);
		Boolean e2MatchImportant = (Boolean) wi
				.getValue(UtilitaireLigue1.FORM_FIELD_CHECK_BOX_IMPORTANT_EQUIPE_EXTERIEUR);

		Float pointsResultatsImportance = 0F;
		Float pointsResultatsImportanceDomExt = 0F;

		if (e2MatchImportant) {
			if (e1MatchImportant) {
				Float pourcentageNulsE2Importance = 0F;
				Float pourcentageNulsE1Importance = 0F;

				int jouesE2Importance = UtilitaireLigue1.getStatInt(e2,
						UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_JOUES_IMPORTANTS);
				int nulsE2Importance = UtilitaireLigue1.getStatInt(e2,
						UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_NULS_IMPORTANT);
				if (jouesE2Importance > 0) {
					pourcentageNulsE2Importance = (float) ((nulsE2Importance / jouesE2Importance)) * 100;
				}
				int jouesE1Importance = UtilitaireLigue1.getStatInt(e1,
						UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_JOUES_IMPORTANTS);
				int nulsE1Importance = UtilitaireLigue1.getStatInt(e1,
						UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_NULS_IMPORTANT);
				if (jouesE1Importance > 0) {
					pourcentageNulsE1Importance = (float) ((nulsE1Importance / jouesE1Importance)) * 100;
				}
				pointsResultatsImportance = (pourcentageNulsE2Importance + pourcentageNulsE1Importance) / 2;

				Float pourcentageNulsE2ExtImportance = 0F;
				Float pourcentageNulsE1DomImportance = 0F;

				int jouesE2ExtImportance = UtilitaireLigue1.getStatInt(e2,
						UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_JOUES_IMPORTANTS_EXTERIEUR);
				int nulsE2ExtImportance = UtilitaireLigue1.getStatInt(e2,
						UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_NULS_IMPORTANT_EXTERIEUR);
				if (jouesE2ExtImportance > 0) {
					pourcentageNulsE2ExtImportance = (float) ((nulsE2ExtImportance / jouesE2ExtImportance)) * 100;
				}
				int jouesE1DomImportance = UtilitaireLigue1.getStatInt(e1,
						UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_JOUES_IMPORTANTS_DOMICILE);
				int nulsE1DomImportance = UtilitaireLigue1.getStatInt(e1,
						UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_NULS_IMPORTANT_DOMICILE);
				if (jouesE1DomImportance > 0) {
					pourcentageNulsE1DomImportance = (float) ((nulsE1DomImportance / jouesE1DomImportance))
							* 100;
				}
				pointsResultatsImportanceDomExt = (pourcentageNulsE2ExtImportance
						+ pourcentageNulsE1DomImportance) / 2;
			} else {
				Float pourcentageNulsE2ImportanceNon = 0F;
				Float pourcentageNulsE1Importance = 0F;

				int jouesE2ImportanceNon = UtilitaireLigue1.getStatInt(e2,
						UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_JOUES_BANAL);
				int nulsE2ImportanceNon = UtilitaireLigue1.getStatInt(e2,
						UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_NULS_BANAL);
				if (jouesE2ImportanceNon > 0) {
					pourcentageNulsE2ImportanceNon = (float) ((nulsE2ImportanceNon / jouesE2ImportanceNon)) * 100;
				}
				int jouesE1Importance = UtilitaireLigue1.getStatInt(e1,
						UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_JOUES_IMPORTANTS);
				int nulsE1Importance = UtilitaireLigue1.getStatInt(e1,
						UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_NULS_IMPORTANT);
				if (jouesE1Importance > 0) {
					pourcentageNulsE1Importance = (float) ((nulsE1Importance / jouesE1Importance)) * 100;
				}
				pointsResultatsImportance = (pourcentageNulsE2ImportanceNon + pourcentageNulsE1Importance) / 2;

				Float pourcentageNulsE2ExtImportanceNon = 0F;
				Float pourcentageNulsE1DomImportance = 0F;

				int jouesE2ExtImportanceNon = UtilitaireLigue1.getStatInt(e2,
						UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_JOUES_BANAL_EXTERIEUR);
				int nulsE2ExtImportanceNon = UtilitaireLigue1.getStatInt(e2,
						UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_NULS_BANAL_EXTERIEUR);
				if (jouesE2ExtImportanceNon > 0) {
					pourcentageNulsE2ExtImportanceNon = (float) ((nulsE2ExtImportanceNon
							/ jouesE2ExtImportanceNon)) * 100;
				}
				int jouesE1DomImportance = UtilitaireLigue1.getStatInt(e1,
						UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_JOUES_IMPORTANTS_DOMICILE);
				int nulsE1DomImportance = UtilitaireLigue1.getStatInt(e1,
						UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_NULS_IMPORTANT_DOMICILE);
				if (jouesE1DomImportance > 0) {
					pourcentageNulsE1DomImportance = (float) ((nulsE1DomImportance / jouesE1DomImportance))
							* 100;
				}
				pointsResultatsImportanceDomExt = (pourcentageNulsE2ExtImportanceNon
						+ pourcentageNulsE1DomImportance) / 2;
			}
		} else {
			if (e1MatchImportant) {
				Float pourcentageNulsE2Importance = 0F;
				Float pourcentageNulsE1ImportanceNon = 0F;

				int jouesE2Importance = UtilitaireLigue1.getStatInt(e2,
						UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_JOUES_IMPORTANTS);
				int nulsE2Importance = UtilitaireLigue1.getStatInt(e2,
						UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_NULS_IMPORTANT);
				if (jouesE2Importance > 0) {
					pourcentageNulsE2Importance = (float) ((nulsE2Importance / jouesE2Importance)) * 100;
				}
				int jouesE1ImportanceNon = UtilitaireLigue1.getStatInt(e1,
						UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_JOUES_BANAL);
				int nulsE1ImportanceNon = UtilitaireLigue1.getStatInt(e1,
						UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_NULS_BANAL);
				if (jouesE1ImportanceNon > 0) {
					pourcentageNulsE1ImportanceNon = (float) ((nulsE1ImportanceNon / jouesE1ImportanceNon))
							* 100;
				}
				pointsResultatsImportance = (pourcentageNulsE2Importance + pourcentageNulsE1ImportanceNon) / 2;

				Float pourcentageNulsE2ExtImportance = 0F;
				Float pourcentageNulsE1DomImportanceNon = 0F;

				int jouesE2ExtImportance = UtilitaireLigue1.getStatInt(e2,
						UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_JOUES_IMPORTANTS_EXTERIEUR);
				int nulsE2ExtImportance = UtilitaireLigue1.getStatInt(e2,
						UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_NULS_IMPORTANT_EXTERIEUR);
				if (jouesE2ExtImportance > 0) {
					pourcentageNulsE2ExtImportance = (float) ((nulsE2ExtImportance / jouesE2ExtImportance)) * 100;
				}
				int jouesE1DomImportanceNon = UtilitaireLigue1.getStatInt(e1,
						UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_JOUES_BANAL_DOMICILE);
				int nulsE1DomImportanceNon = UtilitaireLigue1.getStatInt(e1,
						UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_NULS_BANAL_DOMICILE);
				if (jouesE1DomImportanceNon > 0) {
					pourcentageNulsE1DomImportanceNon = (float) ((nulsE1DomImportanceNon
							/ jouesE1DomImportanceNon)) * 100;
				}
				pointsResultatsImportanceDomExt = (pourcentageNulsE2ExtImportance
						+ pourcentageNulsE1DomImportanceNon) / 2;
			} else {
				Float pourcentageNulsE2ImportanceNon = 0F;
				Float pourcentageNulsE1ImportanceNon = 0F;

				int jouesE2ImportanceNon = UtilitaireLigue1.getStatInt(e2,
						UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_JOUES_BANAL);
				int nulsE2ImportanceNon = UtilitaireLigue1.getStatInt(e2,
						UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_NULS_BANAL);
				if (jouesE2ImportanceNon > 0) {
					pourcentageNulsE2ImportanceNon = (float) ((nulsE2ImportanceNon / jouesE2ImportanceNon)) * 100;
				}
				int jouesE1ImportanceNon = UtilitaireLigue1.getStatInt(e1,
						UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_JOUES_BANAL);
				int nulsE1ImportanceNon = UtilitaireLigue1.getStatInt(e1,
						UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_NULS_BANAL);
				if (jouesE1ImportanceNon > 0) {
					pourcentageNulsE1ImportanceNon = (float) ((nulsE1ImportanceNon / jouesE1ImportanceNon))
							* 100;
				}
				pointsResultatsImportance = (pourcentageNulsE2ImportanceNon + pourcentageNulsE1ImportanceNon)
						/ 2;

				Float pourcentageNulsE2ExtImportanceNon = 0F;
				Float pourcentageNulsE1DomImportanceNon = 0F;

				int jouesE2ExtImportanceNon = UtilitaireLigue1.getStatInt(e2,
						UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_JOUES_BANAL_EXTERIEUR);
				int nuls2ExtImportanceNon = UtilitaireLigue1.getStatInt(e2,
						UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_NULS_BANAL_EXTERIEUR);
				if (jouesE2ExtImportanceNon > 0) {
					pourcentageNulsE2ExtImportanceNon = (float) ((nuls2ExtImportanceNon
							/ jouesE2ExtImportanceNon)) * 100;
				}
				int jouesE1DomImportanceNon = UtilitaireLigue1.getStatInt(e1,
						UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_JOUES_BANAL_DOMICILE);
				int nulsE1DomImportanceNon = UtilitaireLigue1.getStatInt(e1,
						UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_NULS_BANAL_DOMICILE);
				if (jouesE1DomImportanceNon > 0) {
					pourcentageNulsE1DomImportanceNon = (float) ((nulsE1DomImportanceNon
							/ jouesE1DomImportanceNon)) * 100;
				}
				pointsResultatsImportanceDomExt = (pourcentageNulsE2ExtImportanceNon
						+ pourcentageNulsE1DomImportanceNon) / 2;

			}
		}

		Float pointsResultatsClassement = 0F;
		Float pointsResultatsClassementDomExt = 0F;

		if (e1MieuxClassee) {

			Float pourcentageNulsE2ContreClassementSuperieur = 0F;
			Float pourcentageNulsE1ContreClassementInferieur = 0F;

			int jouesE2ContreClassementSuperieur = UtilitaireLigue1.getStatInt(e2,
					UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_JOUES_CLASSEMENT_SUP);
			int nulsE2ContreClassementSuperieur = UtilitaireLigue1.getStatInt(e2,
					UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_NULS_CLASSEMENT_SUP);
			if (jouesE2ContreClassementSuperieur > 0) {
				pourcentageNulsE2ContreClassementSuperieur = (float) ((nulsE2ContreClassementSuperieur
						/ jouesE2ContreClassementSuperieur)) * 100;
			}
			int jouesE1ContreClassementInferieur = UtilitaireLigue1.getStatInt(e1,
					UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_JOUES_CLASSEMENT_INF);
			int nulsE1ContreClassementInferieur = UtilitaireLigue1.getStatInt(e1,
					UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_NULS_CLASSEMENT_INF);
			if (jouesE1ContreClassementInferieur > 0) {
				pourcentageNulsE1ContreClassementInferieur = (float) ((nulsE1ContreClassementInferieur
						/ jouesE1ContreClassementInferieur)) * 100;
			}
			pointsResultatsClassement = (pourcentageNulsE2ContreClassementSuperieur
					+ pourcentageNulsE1ContreClassementInferieur) / 2;

			Float pourcentageNulsE2ContreClassementSuperieurExterieur = 0F;
			Float pourcentageNulsE1ContreClassementInferieurDomicile = 0F;

			int jouesE2ContreClassementSuperieurExterieur = UtilitaireLigue1.getStatInt(e2,
					UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_JOUES_CLASSEMENT_SUP_EXTERIEUR);
			int nulsE2ContreClassementSuperieurExterieur = UtilitaireLigue1.getStatInt(e2,
					UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_NULS_CLASSEMENT_SUP_EXTERIEUR);
			if (jouesE2ContreClassementSuperieurExterieur > 0) {
				pourcentageNulsE2ContreClassementSuperieurExterieur = (float) ((nulsE2ContreClassementSuperieurExterieur
						/ jouesE2ContreClassementSuperieurExterieur)) * 100;
			}
			int jouesE1ContreClassementInferieurDomicile = UtilitaireLigue1.getStatInt(e1,
					UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_JOUES_CLASSEMENT_INF_DOMICILE);
			int nulsE1ContreClassementInferieurDomicile = UtilitaireLigue1.getStatInt(e1,
					UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_NULS_CLASSEMENT_INF_DOMICILE);
			if (jouesE1ContreClassementInferieurDomicile > 0) {
				pourcentageNulsE1ContreClassementInferieurDomicile = (float) ((nulsE1ContreClassementInferieurDomicile
						/ jouesE1ContreClassementInferieurDomicile)) * 100;
			}
			pointsResultatsClassementDomExt = (pourcentageNulsE2ContreClassementSuperieurExterieur
					+ pourcentageNulsE1ContreClassementInferieurDomicile) / 2;

		} else {
			Float pourcentageNulsE2ContreClassementInferieur = 0F;
			Float pourcentageNulsE1ContreClassementSuperieur = 0F;

			int jouesE2ContreClassementInferieur = UtilitaireLigue1.getStatInt(e2,
					UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_JOUES_CLASSEMENT_INF);
			int nulsE2ContreClassementInferieur = UtilitaireLigue1.getStatInt(e2,
					UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_NULS_CLASSEMENT_INF);
			if (jouesE2ContreClassementInferieur > 0) {
				pourcentageNulsE2ContreClassementInferieur = (float) ((nulsE2ContreClassementInferieur
						/ jouesE2ContreClassementInferieur)) * 100;
			}
			int jouesE1ContreClassementSuperieur = UtilitaireLigue1.getStatInt(e1,
					UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_JOUES_CLASSEMENT_SUP);
			int nulsE1ContreClassementSuperieur = UtilitaireLigue1.getStatInt(e1,
					UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_NULS_CLASSEMENT_SUP);
			if (jouesE1ContreClassementSuperieur > 0) {
				pourcentageNulsE1ContreClassementSuperieur = (float) ((nulsE1ContreClassementSuperieur
						/ jouesE1ContreClassementSuperieur)) * 100;
			}
			pointsResultatsClassement = (pourcentageNulsE2ContreClassementInferieur
					+ pourcentageNulsE1ContreClassementSuperieur) / 2;

			Float pourcentageNulsE2ContreClassementInferieurExterieur = 0F;
			Float pourcentageNulsE1ContreClassementSuperieurDomicile = 0F;

			int jouesE2ContreClassementInferieurExterieur = UtilitaireLigue1.getStatInt(e2,
					UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_JOUES_CLASSEMENT_INF_EXTERIEUR);
			int nulsE2ContreClassementInferieurExterieur = UtilitaireLigue1.getStatInt(e2,
					UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_NULS_CLASSEMENT_INF_EXTERIEUR);
			if (jouesE2ContreClassementInferieurExterieur > 0) {
				pourcentageNulsE2ContreClassementInferieurExterieur = (float) ((nulsE2ContreClassementInferieurExterieur
						/ jouesE2ContreClassementInferieurExterieur)) * 100;
			}
			int jouesE1ContreClassementSuperieurDomicile = UtilitaireLigue1.getStatInt(e1,
					UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_JOUES_CLASSEMENT_SUP_DOMICILE);
			int nulsE1ContreClassementSuperieurDomicile = UtilitaireLigue1.getStatInt(e1,
					UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_NULS_CLASSEMENT_SUP_DOMICILE);
			if (jouesE1ContreClassementSuperieurDomicile > 0) {
				pourcentageNulsE1ContreClassementSuperieurDomicile = (float) ((nulsE1ContreClassementSuperieurDomicile
						/ jouesE1ContreClassementSuperieurDomicile)) * 100;
			}
			pointsResultatsClassementDomExt = (pourcentageNulsE2ContreClassementInferieurExterieur
					+ pourcentageNulsE1ContreClassementSuperieurDomicile) / 2;

		}

		totalPoints = coefficientB * averagePercentageOfDrawsInChampionship
				+ (coefficientF * pourcentageNulsConfrontations) + (coefficientC * nbPointsRapportesParClassement)
				+ (coefficientC * nbPointsRapportesParClassementsDomEtExt) + (coefficientC * nbPointsForme)
				+ (coefficientC * forme5DerniersMatchsDraw) + (coefficientC * forme5DerniersMatchsDrawDomExt)
				+ (coefficientD * pointsResultats) + (coefficientD * pointsResultatsDomExt)
				+ (coefficientC * nbPointsFormeDomExt) + (coefficientD * pointsResultatsImportance)
				+ (coefficientD * pointsResultatsImportanceDomExt) + +(coefficientD * pointsResultatsClassement)
				+ (coefficientD * pointsResultatsClassementDomExt);

		return totalPoints;
	}

	private Float getProbabilityPointsForHomeTeam(IStorageResource statistiques, String match, IWorkflowInstance wi)
			throws VdocHelperException {

		Float totalPoints = 0F;

		String[] score = match.split("-");
		String e1 = score[0];
		String e2 = score[1];

		Float averagePercentageOfHomeTeamVictoriesInChampionship = getAveragePercentageInChampionship(
				UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_GAGNES_DOMICILE,
				UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_JOUES_DOMICILE);

		Float pourcentageVDOMConfrontations = ((Float) statistiques
				.getValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_VICTOIRE_DOMICILE));

		String classementE1 = (String) UtilitaireLigue1.getStatString(e1,
				UtilitaireLigue1.TABLE_FIELD_STRING_CLASSEMENT);
		String classementE2 = (String) UtilitaireLigue1.getStatString(e2,
				UtilitaireLigue1.TABLE_FIELD_STRING_CLASSEMENT);
		Float nbPointsRapportesParClassement = 0F;
		if (!UtilitaireLigue1.isEmpty(classementE2) && !UtilitaireLigue1.isEmpty(classementE1)) {
			// e1 mieux classée que e2
			if (Float.parseFloat(classementE1) < Float.parseFloat(classementE2)) {
				nbPointsRapportesParClassement = (Float.parseFloat(classementE2) - Float.parseFloat(classementE1));
			}
		}

		String classementE1Domicile = (String) UtilitaireLigue1.getStatString(e1,
				UtilitaireLigue1.TABLE_FIELD_STRING_CLASSEMENT_DOMICILE);
		String classementE2Exterieur = (String) UtilitaireLigue1.getStatString(e2,
				UtilitaireLigue1.TABLE_FIELD_STRING_CLASSEMENT_EXTERIEUR);
		Float nbPointsRapportesParClassementsDomEtExt = 0F;
		if (!UtilitaireLigue1.isEmpty(classementE2Exterieur) && !UtilitaireLigue1.isEmpty(classementE1Domicile)) {
			// e1 mieux classée que e2
			if (Float.parseFloat(classementE2Exterieur) > Float.parseFloat(classementE1Domicile)) {
				nbPointsRapportesParClassementsDomEtExt = Float.parseFloat(classementE2Exterieur)
						- (Float.parseFloat(classementE1Domicile));
			}
		}

		Float nbPointsForme = 0F;
		String formeE1 = getFormeActuelle(e1);
		String formeE2 = getFormeActuelle(e2);
		if (!UtilitaireLigue1.isEmpty(formeE1) && !UtilitaireLigue1.isEmpty(formeE2)) {
			if (formeE1.equals("V")) {
				int serieVe1 = UtilitaireLigue1.getStatInt(e1, UtilitaireLigue1.TABLE_FIELD_SERIE_V_EN_COURS);
				if (formeE2.equals("V")) {
					int serieVe2 = UtilitaireLigue1.getStatInt(e2, UtilitaireLigue1.TABLE_FIELD_SERIE_V_EN_COURS);
					if (serieVe1 > serieVe2) {
						nbPointsForme = (float) (serieVe1 - serieVe2) * 5;
					}
				} else {
					if (formeE2.equals("N")) {
						nbPointsForme = (float) serieVe1 * 10;
					} else {
						nbPointsForme = (float) serieVe1 * 15;
					}
				}
			}
		}

		Float nbPointsFormeDomExt = 0F;
		String formeE1Dom = getFormeActuelleDomicile(e1);
		String formeE2Ext = getFormeActuelleExterieur(e2);
		if (!UtilitaireLigue1.isEmpty(formeE1Dom) && !UtilitaireLigue1.isEmpty(formeE2Ext)) {
			if (formeE1Dom.equals("V")) {
				int serieVe1 = UtilitaireLigue1.getStatInt(e1, UtilitaireLigue1.TABLE_FIELD_SERIE_V_EN_COURS_DOMICILE);
				if (formeE2Ext.equals("V")) {
					int serieVe2 = UtilitaireLigue1.getStatInt(e2,
							UtilitaireLigue1.TABLE_FIELD_SERIE_V_EN_COURS_EXTERIEUR);
					if (serieVe1 > serieVe2) {
						nbPointsFormeDomExt = (float) (serieVe1 - serieVe2) * 5;
					}
				} else {
					if (formeE2Ext.equals("N")) {
						nbPointsFormeDomExt = (float) serieVe1 * 10;
					} else {
						nbPointsFormeDomExt = (float) serieVe1 * 15;
					}
				}
			}
		}

		Float forme5DerniersMatchsE2 = getFormePointsForTeam(e2);
		Float forme5DerniersMatchsE1 = getFormePointsForTeam(e1);
		Float forme5DerniersMatchs = 0F;
		if (forme5DerniersMatchsE2 < forme5DerniersMatchsE1) {
			forme5DerniersMatchs = forme5DerniersMatchsE1 - forme5DerniersMatchsE2;
		}

		Float forme5DerniersMatchsE2Exterieur = getFormePointsExterieur(e2);
		Float forme5DerniersMatchsE1Domicile = getFormePointsDomicile(e1);
		Float forme5DerniersMatchsDomExt = 0F;
		if (forme5DerniersMatchsE2Exterieur < forme5DerniersMatchsE1Domicile) {
			forme5DerniersMatchsDomExt = forme5DerniersMatchsE1Domicile - forme5DerniersMatchsE2Exterieur;
		}

		Float pointsResultats = 0F;
		Float pourcentageDefaitesE2 = 0F;
		Float pourcentageVictoiresE1 = 0F;

		int jouesE2 = UtilitaireLigue1.getStatInt(e2, UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_JOUES);
		int perdusE2 = UtilitaireLigue1.getStatInt(e2, UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_PERDUS);
		if (jouesE2 > 0) {
			pourcentageDefaitesE2 = (float) ((perdusE2 / jouesE2)) * 100;
		}
		int jouesE1 = UtilitaireLigue1.getStatInt(e1, UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_JOUES);
		int gagnesE1 = UtilitaireLigue1.getStatInt(e1, UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_GAGNES);
		if (jouesE1 > 0) {
			pourcentageVictoiresE1 = (float) ((gagnesE1 / jouesE1)) * 100;
		}
		pointsResultats = (pourcentageDefaitesE2 + pourcentageVictoiresE1) / 2;

		Float pointsResultatsDomExt = 0F;
		Float pourcentageDefaitesE2Ext = 0F;
		Float pourcentageVictoiresE1Dom = 0F;

		int jouesE2Ext = UtilitaireLigue1.getStatInt(e2, UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_JOUES_EXTERIEUR);
		int perdusE2Ext = UtilitaireLigue1.getStatInt(e2, UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_PERDUS_EXTERIEUR);
		if (jouesE2Ext > 0) {
			pourcentageDefaitesE2Ext = (float) ((perdusE2Ext / jouesE2Ext)) * 100;
		}
		int jouesE1Dom = UtilitaireLigue1.getStatInt(e1, UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_JOUES_DOMICILE);
		int gagnesE1Dom = UtilitaireLigue1.getStatInt(e1, UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_GAGNES_DOMICILE);
		if (jouesE1Dom > 0) {
			pourcentageVictoiresE1Dom = (float) ((gagnesE1Dom / jouesE1Dom)) * 100;
		}
		pointsResultatsDomExt = (pourcentageDefaitesE2Ext + pourcentageVictoiresE1Dom) / 2;

		Boolean e1MieuxClassee = (Boolean) wi
				.getValue(UtilitaireLigue1.FORM_FIELD_CHECK_BOX_EQUIPE_DOMICILE_MIEUX_CLASSEE);
		Boolean e1MatchImportant = (Boolean) wi
				.getValue(UtilitaireLigue1.FORM_FIELD_CHECK_BOX_IMPORTANT_EQUIPE_DOMICILE);
		Boolean e2MatchImportant = (Boolean) wi
				.getValue(UtilitaireLigue1.FORM_FIELD_CHECK_BOX_IMPORTANT_EQUIPE_EXTERIEUR);

		Float pointsResultatsImportance = 0F;
		Float pointsResultatsImportanceDomExt = 0F;

		if (e2MatchImportant) {
			if (e1MatchImportant) {
				Float pourcentageDefaitesE2Importance = 0F;
				Float pourcentageVictoiresE1Importance = 0F;

				int jouesE2Importance = UtilitaireLigue1.getStatInt(e2,
						UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_JOUES_IMPORTANTS);
				int perdusE2Importance = UtilitaireLigue1.getStatInt(e2,
						UtilitaireLigue1.TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_IMPORTANT);
				if (jouesE2Importance > 0) {
					pourcentageDefaitesE2Importance = (float) ((perdusE2Importance / jouesE2Importance)) * 100;
				}
				int jouesE1Importance = UtilitaireLigue1.getStatInt(e1,
						UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_JOUES_IMPORTANTS);
				int gagnesE1Importance = UtilitaireLigue1.getStatInt(e1,
						UtilitaireLigue1.TABLE_FIELD_NOMBRE_VICTOIRES_IMPORTANT);
				if (jouesE1Importance > 0) {
					pourcentageVictoiresE1Importance = (float) ((gagnesE1Importance / jouesE1Importance)) * 100;
				}
				pointsResultatsImportance = (pourcentageDefaitesE2Importance + pourcentageVictoiresE1Importance) / 2;

				Float pourcentageDefaitesE2ExtImportance = 0F;
				Float pourcentageVictoiresE1DomImportance = 0F;

				int jouesE2ExtImportance = UtilitaireLigue1.getStatInt(e2,
						UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_JOUES_IMPORTANTS_EXTERIEUR);
				int perdusE2ExtImportance = UtilitaireLigue1.getStatInt(e2,
						UtilitaireLigue1.TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_IMPORTANT_EXTERIEUR);
				if (jouesE2ExtImportance > 0) {
					pourcentageDefaitesE2ExtImportance = (float) ((perdusE2ExtImportance / jouesE2ExtImportance)) * 100;
				}
				int jouesE1DomImportance = UtilitaireLigue1.getStatInt(e1,
						UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_JOUES_IMPORTANTS_DOMICILE);
				int gagnesE1DomImportance = UtilitaireLigue1.getStatInt(e1,
						UtilitaireLigue1.TABLE_FIELD_NOMBRE_VICTOIRES_IMPORTANT_DOMICILE);
				if (jouesE1DomImportance > 0) {
					pourcentageVictoiresE1DomImportance = (float) ((gagnesE1DomImportance / jouesE1DomImportance))
							* 100;
				}
				pointsResultatsImportanceDomExt = (pourcentageDefaitesE2ExtImportance
						+ pourcentageVictoiresE1DomImportance) / 2;
			} else {
				Float pourcentageDefaitesE2ImportanceNon = 0F;
				Float pourcentageVictoiresE1Importance = 0F;

				int jouesE2ImportanceNon = UtilitaireLigue1.getStatInt(e2,
						UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_JOUES_BANAL);
				int perdusE2ImportanceNon = UtilitaireLigue1.getStatInt(e2,
						UtilitaireLigue1.TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_BANAL);
				if (jouesE2ImportanceNon > 0) {
					pourcentageDefaitesE2ImportanceNon = (float) ((perdusE2ImportanceNon / jouesE2ImportanceNon)) * 100;
				}
				int jouesE1Importance = UtilitaireLigue1.getStatInt(e1,
						UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_JOUES_IMPORTANTS);
				int gagnesE1Importance = UtilitaireLigue1.getStatInt(e1,
						UtilitaireLigue1.TABLE_FIELD_NOMBRE_VICTOIRES_IMPORTANT);
				if (jouesE1Importance > 0) {
					pourcentageVictoiresE1Importance = (float) ((gagnesE1Importance / jouesE1Importance)) * 100;
				}
				pointsResultatsImportance = (pourcentageDefaitesE2ImportanceNon + pourcentageVictoiresE1Importance) / 2;

				Float pourcentageDefaitesE2ExtImportanceNon = 0F;
				Float pourcentageVictoiresE1DomImportance = 0F;

				int jouesE2ExtImportanceNon = UtilitaireLigue1.getStatInt(e2,
						UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_JOUES_BANAL_EXTERIEUR);
				int perdusE2ExtImportanceNon = UtilitaireLigue1.getStatInt(e2,
						UtilitaireLigue1.TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_BANAL_EXTERIEUR);
				if (jouesE2ExtImportanceNon > 0) {
					pourcentageDefaitesE2ExtImportanceNon = (float) ((perdusE2ExtImportanceNon
							/ jouesE2ExtImportanceNon)) * 100;
				}
				int jouesE1DomImportance = UtilitaireLigue1.getStatInt(e1,
						UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_JOUES_IMPORTANTS_DOMICILE);
				int gagnesE1DomImportance = UtilitaireLigue1.getStatInt(e1,
						UtilitaireLigue1.TABLE_FIELD_NOMBRE_VICTOIRES_IMPORTANT_DOMICILE);
				if (jouesE1DomImportance > 0) {
					pourcentageVictoiresE1DomImportance = (float) ((gagnesE1DomImportance / jouesE1DomImportance))
							* 100;
				}
				pointsResultatsImportanceDomExt = (pourcentageDefaitesE2ExtImportanceNon
						+ pourcentageVictoiresE1DomImportance) / 2;
			}
		} else {
			if (e1MatchImportant) {
				Float pourcentageDefaitesE2Importance = 0F;
				Float pourcentageVictoiresE1ImportanceNon = 0F;

				int jouesE2Importance = UtilitaireLigue1.getStatInt(e2,
						UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_JOUES_IMPORTANTS);
				int perdusE2Importance = UtilitaireLigue1.getStatInt(e2,
						UtilitaireLigue1.TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_IMPORTANT);
				if (jouesE2Importance > 0) {
					pourcentageDefaitesE2Importance = (float) ((perdusE2Importance / jouesE2Importance)) * 100;
				}
				int jouesE1ImportanceNon = UtilitaireLigue1.getStatInt(e1,
						UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_JOUES_BANAL);
				int gagnesE1ImportanceNon = UtilitaireLigue1.getStatInt(e1,
						UtilitaireLigue1.TABLE_FIELD_NOMBRE_VICTOIRES_BANAL);
				if (jouesE1ImportanceNon > 0) {
					pourcentageVictoiresE1ImportanceNon = (float) ((gagnesE1ImportanceNon / jouesE1ImportanceNon))
							* 100;
				}
				pointsResultatsImportance = (pourcentageDefaitesE2Importance + pourcentageVictoiresE1ImportanceNon) / 2;

				Float pourcentageDefaitesE2ExtImportance = 0F;
				Float pourcentageVictoiresE1DomImportanceNon = 0F;

				int jouesE2ExtImportance = UtilitaireLigue1.getStatInt(e2,
						UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_JOUES_IMPORTANTS_EXTERIEUR);
				int perdusE2ExtImportance = UtilitaireLigue1.getStatInt(e2,
						UtilitaireLigue1.TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_IMPORTANT_EXTERIEUR);
				if (jouesE2ExtImportance > 0) {
					pourcentageDefaitesE2ExtImportance = (float) ((perdusE2ExtImportance / jouesE2ExtImportance)) * 100;
				}
				int jouesE1DomImportanceNon = UtilitaireLigue1.getStatInt(e1,
						UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_JOUES_BANAL_DOMICILE);
				int gagnesE1DomImportanceNon = UtilitaireLigue1.getStatInt(e1,
						UtilitaireLigue1.TABLE_FIELD_NOMBRE_VICTOIRES_BANAL_DOMICILE);
				if (jouesE1DomImportanceNon > 0) {
					pourcentageVictoiresE1DomImportanceNon = (float) ((gagnesE1DomImportanceNon
							/ jouesE1DomImportanceNon)) * 100;
				}
				pointsResultatsImportanceDomExt = (pourcentageDefaitesE2ExtImportance
						+ pourcentageVictoiresE1DomImportanceNon) / 2;
			} else {
				Float pourcentageDefaitesE2ImportanceNon = 0F;
				Float pourcentageVictoiresE1ImportanceNon = 0F;

				int jouesE2ImportanceNon = UtilitaireLigue1.getStatInt(e2,
						UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_JOUES_BANAL);
				int perdusE2ImportanceNon = UtilitaireLigue1.getStatInt(e2,
						UtilitaireLigue1.TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_BANAL);
				if (jouesE2ImportanceNon > 0) {
					pourcentageDefaitesE2ImportanceNon = (float) ((perdusE2ImportanceNon / jouesE2ImportanceNon)) * 100;
				}
				int jouesE1ImportanceNon = UtilitaireLigue1.getStatInt(e1,
						UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_JOUES_BANAL);
				int gagnesE1ImportanceNon = UtilitaireLigue1.getStatInt(e1,
						UtilitaireLigue1.TABLE_FIELD_NOMBRE_VICTOIRES_BANAL);
				if (jouesE1ImportanceNon > 0) {
					pourcentageVictoiresE1ImportanceNon = (float) ((gagnesE1ImportanceNon / jouesE1ImportanceNon))
							* 100;
				}
				pointsResultatsImportance = (pourcentageDefaitesE2ImportanceNon + pourcentageVictoiresE1ImportanceNon)
						/ 2;

				Float pourcentageDefaitesE2ExtImportanceNon = 0F;
				Float pourcentageVictoiresE1DomImportanceNon = 0F;

				int jouesE2ExtImportanceNon = UtilitaireLigue1.getStatInt(e2,
						UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_JOUES_BANAL_EXTERIEUR);
				int perdus2ExtImportanceNon = UtilitaireLigue1.getStatInt(e2,
						UtilitaireLigue1.TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_BANAL_EXTERIEUR);
				if (jouesE2ExtImportanceNon > 0) {
					pourcentageDefaitesE2ExtImportanceNon = (float) ((perdus2ExtImportanceNon
							/ jouesE2ExtImportanceNon)) * 100;
				}
				int jouesE1DomImportanceNon = UtilitaireLigue1.getStatInt(e1,
						UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_JOUES_BANAL_DOMICILE);
				int gagnesE1DomImportanceNon = UtilitaireLigue1.getStatInt(e1,
						UtilitaireLigue1.TABLE_FIELD_NOMBRE_VICTOIRES_BANAL_DOMICILE);
				if (jouesE1DomImportanceNon > 0) {
					pourcentageVictoiresE1DomImportanceNon = (float) ((gagnesE1DomImportanceNon
							/ jouesE1DomImportanceNon)) * 100;
				}
				pointsResultatsImportanceDomExt = (pourcentageDefaitesE2ExtImportanceNon
						+ pourcentageVictoiresE1DomImportanceNon) / 2;

			}
		}

		Float pointsResultatsClassement = 0F;
		Float pointsResultatsClassementDomExt = 0F;

		if (e1MieuxClassee) {

			Float pourcentageDefaitesE2ContreClassementSuperieur = 0F;
			Float pourcentageVictoiresE1ContreClassementInferieur = 0F;

			int jouesE2ContreClassementSuperieur = UtilitaireLigue1.getStatInt(e2,
					UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_JOUES_CLASSEMENT_SUP);
			int perdusE2ContreClassementSuperieur = UtilitaireLigue1.getStatInt(e2,
					UtilitaireLigue1.TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_CLASSEMENT_SUPERIEUR);
			if (jouesE2ContreClassementSuperieur > 0) {
				pourcentageDefaitesE2ContreClassementSuperieur = (float) ((perdusE2ContreClassementSuperieur
						/ jouesE2ContreClassementSuperieur)) * 100;
			}
			int jouesE1ContreClassementInferieur = UtilitaireLigue1.getStatInt(e1,
					UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_JOUES_CLASSEMENT_INF);
			int gagnesE1ContreClassementInferieur = UtilitaireLigue1.getStatInt(e1,
					UtilitaireLigue1.TABLE_FIELD_NOMBRE_VICTOIRES_CLASSEMENT_INF);
			if (jouesE1ContreClassementInferieur > 0) {
				pourcentageVictoiresE1ContreClassementInferieur = (float) ((gagnesE1ContreClassementInferieur
						/ jouesE1ContreClassementInferieur)) * 100;
			}
			pointsResultatsClassement = (pourcentageDefaitesE2ContreClassementSuperieur
					+ pourcentageVictoiresE1ContreClassementInferieur) / 2;

			Float pourcentageDefaitesE2ContreClassementSuperieurExterieur = 0F;
			Float pourcentageVictoiresE1ContreClassementInferieurDomicile = 0F;

			int jouesE2ContreClassementSuperieurExterieur = UtilitaireLigue1.getStatInt(e2,
					UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_JOUES_CLASSEMENT_SUP_EXTERIEUR);
			int perdusE2ContreClassementSuperieurExterieur = UtilitaireLigue1.getStatInt(e2,
					UtilitaireLigue1.TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_CLASSEMENT_SUPERIEUR_EXTERIEUR);
			if (jouesE2ContreClassementSuperieurExterieur > 0) {
				pourcentageDefaitesE2ContreClassementSuperieurExterieur = (float) ((perdusE2ContreClassementSuperieurExterieur
						/ jouesE2ContreClassementSuperieurExterieur)) * 100;
			}
			int jouesE1ContreClassementInferieurDomicile = UtilitaireLigue1.getStatInt(e1,
					UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_JOUES_CLASSEMENT_INF_DOMICILE);
			int gagnesE1ContreClassementInferieurDomicile = UtilitaireLigue1.getStatInt(e1,
					UtilitaireLigue1.TABLE_FIELD_NOMBRE_VICTOIRES_CLASSEMENT_INF_DOMICILE);
			if (jouesE1ContreClassementInferieurDomicile > 0) {
				pourcentageVictoiresE1ContreClassementInferieurDomicile = (float) ((gagnesE1ContreClassementInferieurDomicile
						/ jouesE1ContreClassementInferieurDomicile)) * 100;
			}
			pointsResultatsClassementDomExt = (pourcentageDefaitesE2ContreClassementSuperieurExterieur
					+ pourcentageVictoiresE1ContreClassementInferieurDomicile) / 2;

		} else {
			Float pourcentageDefaitesE2ContreClassementInferieur = 0F;
			Float pourcentageVictoiresE1ContreClassementSuperieur = 0F;

			int jouesE2ContreClassementInferieur = UtilitaireLigue1.getStatInt(e2,
					UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_JOUES_CLASSEMENT_INF);
			int perdusE2ContreClassementInferieur = UtilitaireLigue1.getStatInt(e2,
					UtilitaireLigue1.TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_CLASSEMENT_INFERIEUR);
			if (jouesE2ContreClassementInferieur > 0) {
				pourcentageDefaitesE2ContreClassementInferieur = (float) ((perdusE2ContreClassementInferieur
						/ jouesE2ContreClassementInferieur)) * 100;
			}
			int jouesE1ContreClassementSuperieur = UtilitaireLigue1.getStatInt(e1,
					UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_JOUES_CLASSEMENT_SUP);
			int gagnesE1ContreClassementSuperieur = UtilitaireLigue1.getStatInt(e1,
					UtilitaireLigue1.TABLE_FIELD_NOMBRE_VICTOIRES_CLASSEMENT_SUP);
			if (jouesE1ContreClassementSuperieur > 0) {
				pourcentageVictoiresE1ContreClassementSuperieur = (float) ((gagnesE1ContreClassementSuperieur
						/ jouesE1ContreClassementSuperieur)) * 100;
			}
			pointsResultatsClassement = (pourcentageDefaitesE2ContreClassementInferieur
					+ pourcentageVictoiresE1ContreClassementSuperieur) / 2;

			Float pourcentageDefaitesE2ContreClassementInferieurExterieur = 0F;
			Float pourcentageVictoiresE1ContreClassementSuperieurDomicile = 0F;

			int jouesE2ContreClassementInferieurExterieur = UtilitaireLigue1.getStatInt(e2,
					UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_JOUES_CLASSEMENT_INF_EXTERIEUR);
			int perdusE2ContreClassementInferieurExterieur = UtilitaireLigue1.getStatInt(e2,
					UtilitaireLigue1.TABLE_FIELD_NOMBRE_DEFAITE_CONTRE_CLASSEMENT_INFERIEUR_EXTERIEUR);
			if (jouesE2ContreClassementInferieurExterieur > 0) {
				pourcentageDefaitesE2ContreClassementInferieurExterieur = (float) ((perdusE2ContreClassementInferieurExterieur
						/ jouesE2ContreClassementInferieurExterieur)) * 100;
			}
			int jouesE1ContreClassementSuperieurDomicile = UtilitaireLigue1.getStatInt(e1,
					UtilitaireLigue1.TABLE_FIELD_NOMBRE_MATCHS_JOUES_CLASSEMENT_SUP_DOMICILE);
			int gagnesE1ContreClassementSuperieurDomicile = UtilitaireLigue1.getStatInt(e1,
					UtilitaireLigue1.TABLE_FIELD_NOMBRE_VICTOIRES_CLASSEMENT_SUP_DOMICILE);
			if (jouesE1ContreClassementSuperieurDomicile > 0) {
				pourcentageVictoiresE1ContreClassementSuperieurDomicile = (float) ((gagnesE1ContreClassementSuperieurDomicile
						/ jouesE1ContreClassementSuperieurDomicile)) * 100;
			}
			pointsResultatsClassementDomExt = (pourcentageDefaitesE2ContreClassementInferieurExterieur
					+ pourcentageVictoiresE1ContreClassementSuperieurDomicile) / 2;

		}

		totalPoints = coefficientB * averagePercentageOfHomeTeamVictoriesInChampionship
				+ (coefficientF * pourcentageVDOMConfrontations) + (coefficientC * nbPointsRapportesParClassement)
				+ (coefficientC * nbPointsRapportesParClassementsDomEtExt) + (coefficientC * nbPointsForme)
				+ (coefficientC * forme5DerniersMatchs) + (coefficientC * forme5DerniersMatchsDomExt)
				+ (coefficientD * pointsResultats) + (coefficientD * pointsResultatsDomExt)
				+ (coefficientC * nbPointsFormeDomExt) + (coefficientD * pointsResultatsImportance)
				+ (coefficientD * pointsResultatsImportanceDomExt) + +(coefficientD * pointsResultatsClassement)
				+ (coefficientD * pointsResultatsClassementDomExt);

		return totalPoints;
	}

	private Float getAveragePercentageInChampionship(String evaluatedValue, String total) {

		Float pourcentageTotal = 0F;
		try {
			Collection<IStorageResource> equipes = UtilitaireLigue1.getResourcesEquipes();
			for (IStorageResource equipe : equipes) {
				Long nbMatchsPrisEnCompte = (Long) equipe.getValue(evaluatedValue);
				Long nbTotalMatchs = (Long) equipe.getValue(total);
				if (nbTotalMatchs != 0L) {
					pourcentageTotal = pourcentageTotal
							+ (nbMatchsPrisEnCompte.floatValue() / nbTotalMatchs.floatValue()) * 100;
				}
			}
		} catch (VdocHelperException e) {
			e.printStackTrace();
		}
		return pourcentageTotal / 20;
	}

	private TreeMap<String, String> getTreeMapStatsMoyennes(IStorageResource statistiques, String match) {

		Map<String, String> stats = new HashMap<String, String>();

		String[] score = match.split("-");
		String e1 = score[0];
		String e2 = score[1];

		Float moyenneButsDomicile = (Float) statistiques
				.getValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_MOYENNE_BUTS_DOMICILE);
		Float moyenneButsMatch = (Float) statistiques
				.getValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_MOYENNE_BUTS_MATCH);
		Float moyenneButsExtérieur = (Float) statistiques
				.getValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_MOYENNE_BUTS_EXTERIEUR);

		stats.put("Moyenne de buts par match de l'équipe " + e2, moyenneButsExtérieur + " buts");
		stats.put("Moyenne de buts par match", moyenneButsMatch + " buts");
		stats.put("Moyenne de buts par match de l'équipe " + e1, moyenneButsDomicile + " buts");

		AverageScoredGoalsComparator comparator = new AverageScoredGoalsComparator(stats);
		TreeMap<String, String> mapTree = new TreeMap<String, String>(comparator);
		mapTree.putAll(stats);

		return mapTree;
	}

	private static void writeMapInWordDocument(String titreParagraphe, Map<String, String> statsGenerales,
			XWPFDocument document) {
		XWPFParagraph paragraphServeurConfig = document.createParagraph();
		XWPFRun runServeurConfig = paragraphServeurConfig.createRun();
		runServeurConfig.setText(titreParagraphe);
		runServeurConfig.setBold(true);
		runServeurConfig.setFontSize(14);
		for (Entry<String, String> serveurConfig : statsGenerales.entrySet()) {
			document.createParagraph().createRun()
					.setText(serveurConfig.getKey() + " : " + serveurConfig.getValue() + "\n");
		}
		document.createParagraph().createRun().setText("");
	}

	private static IStorageResource getStatistiquesFromMatch(IWorkflowInstance wi) {

		String equipe1 = (String) wi.getValue(UtilitaireLigue1.FORM_FIELD_SURNOM_EQUIPE_DOMICILE);
		String equipe2 = (String) wi.getValue(UtilitaireLigue1.FORM_FIELD_SURNOM_EQUIPE_EXTERIEUR);

		if (!UtilitaireLigue1.isEmpty(equipe1) && !UtilitaireLigue1.isEmpty(equipe2)) {
			String match = equipe1 + "-" + equipe2;
			try {
				IStorageResource stat = UtilitaireLigue1.getResourceStatistique(match);
				if (stat == null) {
					match = equipe2 + "-" + equipe1;
					stat = UtilitaireLigue1.getResourceStatistique(match);
					if (stat == null) {
						throw new NullStatException("La statistique pour le match " + match
								+ " n'a pas été trouvée dans le tableau Statistiques");
					}
				}
				return stat;
			} catch (VdocHelperException | NullStatException e) {
				e.getMessage();
				e.printStackTrace();
			}

		}
		return null;
	}
}
