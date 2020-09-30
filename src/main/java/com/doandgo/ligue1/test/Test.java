package com.doandgo.ligue1.test;

public class Test {

	public static void main(String[] args) {

		String score = "0-4";
		String[] scoreTableau = score.split("-");
		String nbButsEquipeDomicileString = scoreTableau[0];
		String nbButsEquipeExterieurString = scoreTableau[1];

		Long nbButsMarquesEquipeDomicile = Long.parseLong(nbButsEquipeDomicileString);
		Long nbButsMarquesEquipeExterieur = Long.parseLong(nbButsEquipeExterieurString);
		Long test = null;

		if (nbButsMarquesEquipeDomicile != null && nbButsMarquesEquipeExterieur != null) {

			Long differenceEnFaveurEquipeDomicile = nbButsMarquesEquipeDomicile - nbButsMarquesEquipeExterieur;

			int differenceEquipe1Globale = 4;

			test = Long.valueOf(differenceEquipe1Globale) + differenceEnFaveurEquipeDomicile;
		}
		System.out.println(test);
	}

}
