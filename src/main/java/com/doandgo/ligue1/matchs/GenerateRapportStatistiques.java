package com.doandgo.ligue1.matchs;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import com.axemble.vdoc.sdk.document.extensions.BaseDocumentExtension;
import com.axemble.vdoc.sdk.interfaces.IAction;
import com.axemble.vdoc.sdk.interfaces.IStorageResource;
import com.axemble.vdoc.sdk.interfaces.IWorkflowInstance;
import com.doandgo.commons.utils.StringUtils;
import com.doandgo.ligue1.utils.UtilitaireLigue1;
import com.doandgo.moovapps.exceptions.VdocHelperException;

public class GenerateRapportStatistiques extends BaseDocumentExtension {

	private static final long serialVersionUID = 1L;

	@Override
	public boolean onBeforeSubmit(IAction action) {

		if (action.getName().equals(UtilitaireLigue1.FORM_BUTTON_GENERER_RAPPORT_STATISTIQUES)) {

			IWorkflowInstance wi = getWorkflowInstance();
			IStorageResource statistiques = getStatistiquesFromMatch(wi);
			try {
				generateRapport(statistiques);
				wi.setValue(UtilitaireLigue1.FORM_FIELD_CHECK_BOX_COMPTABILISE_RAPPORT_STATISTIQUES_JOURNEE, false);
				wi.save(getWorkflowModule().getSysadminContext());
				getResourceController().alert("Le rapport des statistiques du match a bien été publié");
			} catch (IOException e) {
				e.getMessage();
				e.printStackTrace();
			}
		}

		return super.onBeforeSubmit(action);
	}

	private void generateRapport(IStorageResource statistiques) throws IOException {

		String match = (String) statistiques.getValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_MATCH);
//		String[] score = match.split("-");
//		
//		String surnomE1 = (String) getWorkflowInstance().getValue(UtilitaireLigue1.FORM_FIELD_SURNOM_EQUIPE_DOMICILE);
//
//		if (surnomE1.equals(score[1])) {
//			match = score[1] + "-" + score[0];
//		}
		
		XWPFDocument document = new XWPFDocument();
		File rapport_match = new File("C:\\perso\\Ligue1\\rapport_" + match + ".docx");
		FileOutputStream out = new FileOutputStream(rapport_match);

		TreeMap<String, String> mapTreeStatsGenerales = getTreeMapStatsVictoires(statistiques, match);
		writeMapInWordDocument("Statistiques des résultats du match lors des dernières confrontations " + match,
				mapTreeStatsGenerales, document);

		TreeMap<String, String> mapTreeStatsMoyennes = getTreeMapStatsMoyennes(statistiques, match);
		writeMapInWordDocument("Statistiques des moyennes de buts du match lors des dernières confrontations " + match,
				mapTreeStatsMoyennes, document);

		Map<String, String> mapStatsLDEM = getTreeMapStatsLDEM(statistiques, match);
		writeMapInWordDocument("" + match, mapStatsLDEM, document);

		TreeMap<String, String> mapTreeStatsNombreDeButsMatch = getTreeMapStatsNombreButsMatch(statistiques, match);
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

		// TODO Ajouter les statistiques de la table "Equipes" selon le classement,
		// l'importance et domicile ou extérieur
		// Pour chaque équipe

		document.write(out);
		out.close();
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

		stats.put("Pourcentage de matchs avec exactement 0 but marqué pour l'équipe " + e2, E2marque0 + "%");
		stats.put("Pourcentage de matchs avec exactement 1 but marqué pour l'équipe " + e2, E2marque1 + "%");
		stats.put("Pourcentage de matchs avec exactement 2 buts marqués pour l'équipe " + e2, E2marque2 + "%");
		stats.put("Pourcentage de matchs avec exactement 3 buts marqués pour l'équipe " + e2, E2marque3 + "%");
		stats.put("Pourcentage de matchs avec exactement 4 buts marqués pour l'équipe " + e2, E2marque4 + "%");
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

		stats.put("Pourcentage de matchs avec exactement 0 but marqué pour l'équipe " + e1, E1marque0 + "%");
		stats.put("Pourcentage de matchs avec exactement 1 but marqué pour l'équipe " + e1, E1marque1 + "%");
		stats.put("Pourcentage de matchs avec exactement 2 buts marqués pour l'équipe " + e1, E1marque2 + "%");
		stats.put("Pourcentage de matchs avec exactement 3 buts marqués pour l'équipe " + e1, E1marque3 + "%");
		stats.put("Pourcentage de matchs avec exactement 4 buts marqués pour l'équipe " + e1, E1marque4 + "%");
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

		stats.put("Pourcentage de matchs remporté par exactement 1 but d'écart par l'équipe " + e2, E2par1 + "%");
		stats.put("Pourcentage de matchs remporté par exactement 2 buts d'écart par l'équipe " + e2, E2par2 + "%");
		stats.put("Pourcentage de matchs remporté par exactement 3 buts d'écart par l'équipe " + e2, E2par3 + "%");
		stats.put("Pourcentage de matchs remporté par exactement 4 buts d'écart par l'équipe " + e2, E2par4 + "%");
		stats.put("Pourcentage de matchs remporté par exactement 5 buts d'écart par l'équipe " + e2, E2par5 + "%");
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

		stats.put("Pourcentage de matchs remporté par exactement 1 but d'écart par l'équipe " + e1, E1par1 + "%");
		stats.put("Pourcentage de matchs remporté par exactement 2 buts d'écart par l'équipe " + e1, E1par2 + "%");
		stats.put("Pourcentage de matchs remporté par exactement 3 buts d'écart par l'équipe " + e1, E1par3 + "%");
		stats.put("Pourcentage de matchs remporté par exactement 4 buts d'écart par l'équipe " + e1, E1par4 + "%");
		stats.put("Pourcentage de matchs remporté par exactement 5 buts d'écart par l'équipe " + e1, E1par5 + "%");
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

		stats.put("Pourcentage de matchs avec plus de 0,5 buts pour l'équipe " + e2, E2plus05 + "%");
		stats.put("Pourcentage de matchs avec plus de 1,5 buts pour l'équipe " + e2, E2plus15 + "%");
		stats.put("Pourcentage de matchs avec plus de 2,5 buts pour l'équipe " + e2, E2plus25 + "%");
		stats.put("Pourcentage de matchs avec plus de 3,5 buts pour l'équipe " + e2, E2plus35 + "%");
		stats.put("Pourcentage de matchs avec plus de 4,5 buts pour l'équipe " + e2, E2plus45 + "%");
		stats.put("Pourcentage de matchs avec moins de 0,5 buts pour l'équipe " + e2, E2moins05 + "%");
		stats.put("Pourcentage de matchs avec moins de 1,5 buts pour l'équipe " + e2, E2moins15 + "%");
		stats.put("Pourcentage de matchs avec moins de 2,5 buts pour l'équipe " + e2, E2moins25 + "%");
		stats.put("Pourcentage de matchs avec moins de 3,5 buts pour l'équipe " + e2, E2moins35 + "%");
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

		stats.put("Pourcentage de matchs avec plus de 0,5 buts pour l'équipe " + e1, E1plus05 + "%");
		stats.put("Pourcentage de matchs avec plus de 1,5 buts pour l'équipe " + e1, E1plus15 + "%");
		stats.put("Pourcentage de matchs avec plus de 2,5 buts pour l'équipe " + e1, E1plus25 + "%");
		stats.put("Pourcentage de matchs avec plus de 3,5 buts pour l'équipe " + e1, E1plus35 + "%");
		stats.put("Pourcentage de matchs avec plus de 4,5 buts pour l'équipe " + e1, E1plus45 + "%");
		stats.put("Pourcentage de matchs avec moins de 0,5 buts pour l'équipe " + e1, E1moins05 + "%");
		stats.put("Pourcentage de matchs avec moins de 1,5 buts pour l'équipe " + e1, E1moins15 + "%");
		stats.put("Pourcentage de matchs avec moins de 2,5 buts pour l'équipe " + e1, E1moins25 + "%");
		stats.put("Pourcentage de matchs avec moins de 3,5 buts pour l'équipe " + e1, E1moins35 + "%");
		stats.put("Pourcentage de matchs avec moins de 4,5 buts pour l'équipe " + e1, E1moins45 + "%");

		PourcentageComparator comparator = new PourcentageComparator(stats);
		TreeMap<String, String> mapTree = new TreeMap<String, String>(comparator);
		mapTree.putAll(stats);

		return mapTree;

	}

	private TreeMap<String, String> getTreeMapStatsNombreButsMatch(IStorageResource statistiques, String match) {

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

		stats.put("Pourcentage de matchs avec plus de 0,5 buts", plus05 + "%");
		stats.put("Pourcentage de matchs avec plus de 1,5 buts", plus15 + "%");
		stats.put("Pourcentage de matchs avec plus de 2,5 buts", plus25 + "%");
		stats.put("Pourcentage de matchs avec plus de 3,5 buts", plus35 + "%");
		stats.put("Pourcentage de matchs avec plus de 4,5 buts", plus45 + "%");
		stats.put("Pourcentage de matchs avec moins de 0,5 buts", moins05 + "%");
		stats.put("Pourcentage de matchs avec moins de 1,5 buts", moins15 + "%");
		stats.put("Pourcentage de matchs avec moins de 2,5 buts", moins25 + "%");
		stats.put("Pourcentage de matchs avec moins de 3,5 buts", moins35 + "%");
		stats.put("Pourcentage de matchs avec moins de 4,5 buts", moins45 + "%");

		PourcentageComparator comparator = new PourcentageComparator(stats);
		TreeMap<String, String> mapTree = new TreeMap<String, String>(comparator);
		mapTree.putAll(stats);

		return mapTree;
	}

	private Map<String, String> getTreeMapStatsLDEM(IStorageResource statistiques, String match) {

		Map<String, String> stats = new HashMap<String, String>();

		Float pourcentageLDEM = (Float) statistiques
				.getValue(UtilitaireLigue1.TABLE_STATISTIQUES_FIELD_POURCENTAGE_LDEM);

		stats.put("Pourcentage de matchs où les deux équipes marquent", pourcentageLDEM + "%");

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

		MoyenneButsComparator comparator = new MoyenneButsComparator(stats);
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

		if (!StringUtils.isEmpty(equipe1) && !StringUtils.isEmpty(equipe2)) {
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
