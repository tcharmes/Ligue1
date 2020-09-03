package com.doandgo.ligue1.matchs;

import java.util.Date;

import com.doandgo.commons.utils.StringUtils;

public class Confrontation {

	String match;
	String recent1;
	String recent2;
	String recent3;
	String recent4;
	String recent5;
	Date dateLastModification;

	public Confrontation(String match, String recent1, String recent2, String recent3, String recent4, String recent5, Date dateLastModification) {
		this.match = match;
		this.recent1 = recent1;
		this.recent2 = recent2;
		this.recent3 = recent3;
		this.recent4 = recent4;
		this.recent5 = recent5;
		this.dateLastModification = dateLastModification;
	}

	public String getMatch() {
		return match;
	}

	public String getRecent1() {
		return recent1;
	}

	public String getRecent2() {
		return recent2;
	}

	public String getRecent3() {
		return recent3;
	}

	public String getRecent4() {
		return recent4;
	}

	public String getRecent5() {
		return recent5;
	}
	
	public Date getDateLastModification() {
		return dateLastModification;
	}

	public Float getNbConfrontations() {

		Float nbConfrontations = 5F;

		if (StringUtils.isEmpty(getRecent5())) {
			nbConfrontations--;
		}
		if (StringUtils.isEmpty(getRecent4())) {
			nbConfrontations--;
		}
		if (StringUtils.isEmpty(getRecent3())) {
			nbConfrontations--;
		}
		if (StringUtils.isEmpty(getRecent2())) {
			nbConfrontations--;
		}
		if (StringUtils.isEmpty(getRecent1())) {
			nbConfrontations--;
		}
		return nbConfrontations;
	}

	public String getSurnomEquipe1() {

		String[] equipes = this.match.split("-");

		if (StringUtils.isEmpty(equipes[0])) {
			return null;
		}
		return equipes[0];
	}

	public String getSurnomEquipe2() {
		String[] equipes = this.match.split("-");
		if (StringUtils.isEmpty(equipes[1])) {
			return null;
		}
		return equipes[1];
	}

	public Float getNbButsEquipe2Recent1() {
		if (StringUtils.isEmpty(getRecent1())){
			return -1F;
		}
		String[] score = this.recent1.split("-");
		for (String scoreEquipe : score) {
			if (StringUtils.isEmpty(scoreEquipe)) {
				return -1F;
			}
		}
		return Float.parseFloat(score[1]);
	}

	public Float getNbButsEquipe2Recent2() {
		if (StringUtils.isEmpty(getRecent2())){
			return -1F;
		}
		String[] score = this.recent2.split("-");
		for (String scoreEquipe : score) {
			if (StringUtils.isEmpty(scoreEquipe)) {
				return -1F;
			}
		}
		return Float.parseFloat(score[1]);
	}

	public Float getNbButsEquipe2Recent3() {
		if (StringUtils.isEmpty(getRecent3())){
			return -1F;
		}
		String[] score = this.recent3.split("-");
		for (String scoreEquipe : score) {
			if (StringUtils.isEmpty(scoreEquipe)) {
				return -1F;
			}
		}
		return Float.parseFloat(score[1]);
	}

	public Float getNbButsEquipe2Recent4() {
		if (StringUtils.isEmpty(getRecent4())){
			return -1F;
		}
		String[] score = this.recent4.split("-");
		for (String scoreEquipe : score) {
			if (StringUtils.isEmpty(scoreEquipe)) {
				return -1F;
			}
		}
		return Float.parseFloat(score[1]);
	}

	public Float getNbButsEquipe2Recent5() {
		if (StringUtils.isEmpty(getRecent5())){
			return -1F;
		}
		String[] score = this.recent5.split("-");
		for (String scoreEquipe : score) {
			if (StringUtils.isEmpty(scoreEquipe)) {
				return -1F;
			}
		}
		return Float.parseFloat(score[1]);
	}

	public Float getNbButsEquipe1Recent1() {
		if (StringUtils.isEmpty(getRecent1())){
			return -1F;
		}
		String[] score = this.recent1.split("-");
		for (String scoreEquipe : score) {
			if (StringUtils.isEmpty(scoreEquipe)) {
				return -1F;
			}
		}
		return Float.parseFloat(score[0]);
	}

	public Float getNbButsEquipe1Recent2() {
		if (StringUtils.isEmpty(getRecent2())){
			return -1F;
		}
		String[] score = this.recent2.split("-");
		for (String scoreEquipe : score) {
			if (StringUtils.isEmpty(scoreEquipe)) {
				return -1F;
			}
		}
		return Float.parseFloat(score[0]);
	}

	public Float getNbButsEquipe1Recent3() {
		if (StringUtils.isEmpty(getRecent3())){
			return -1F;
		}
		String[] score = this.recent3.split("-");
		for (String scoreEquipe : score) {
			if (StringUtils.isEmpty(scoreEquipe)) {
				return -1F;
			}
		}
		return Float.parseFloat(score[0]);
	}

	public Float getNbButsEquipe1Recent4() {
		if (StringUtils.isEmpty(getRecent4())){
			return -1F;
		}
		String[] score = this.recent4.split("-");
		for (String scoreEquipe : score) {
			if (StringUtils.isEmpty(scoreEquipe)) {
				return -1F;
			}
		}
		return Float.parseFloat(score[0]);
	}

	public Float getNbButsEquipe1Recent5() {
		if (StringUtils.isEmpty(getRecent5())){
			return -1F;
		}
		String[] score = this.recent5.split("-");
		for (String scoreEquipe : score) {
			if (StringUtils.isEmpty(scoreEquipe)) {
				return -1F;
			}
		}
		return Float.parseFloat(score[0]);
	}

	public Float getTotalButsEquipe1() {
		Float total = 0F;

		if (getNbButsEquipe1Recent1() != -1) {
			total = total + getNbButsEquipe1Recent1();
		}
		if (getNbButsEquipe1Recent2() != -1) {
			total = total + getNbButsEquipe1Recent2();
		}
		if (getNbButsEquipe1Recent3() != -1) {
			total = total + getNbButsEquipe1Recent3();
		}
		if (getNbButsEquipe1Recent4() != -1) {
			total = total + getNbButsEquipe1Recent4();
		}
		if (getNbButsEquipe1Recent5() != -1) {
			total = total + getNbButsEquipe1Recent5();
		}
		return total;
	}

	public Float getTotalButsEquipe2() {
		Float total = 0F;

		if (getNbButsEquipe2Recent1() != -1) {
			total = total + getNbButsEquipe2Recent1();
		}
		if (getNbButsEquipe2Recent2() != -1) {
			total = total + getNbButsEquipe2Recent2();
		}
		if (getNbButsEquipe2Recent3() != -1) {
			total = total + getNbButsEquipe2Recent3();
		}
		if (getNbButsEquipe2Recent4() != -1) {
			total = total + getNbButsEquipe2Recent4();
		}
		if (getNbButsEquipe2Recent5() != -1) {
			total = total + getNbButsEquipe2Recent5();
		}
		return total;
	}

	public Float getMoyenneButsParMatchEquipe1() {
		return (float) (getTotalButsEquipe1() / getNbConfrontations());
	}

	public Float getMoyenneButsParMatchEquipe2() {
		return (float) (getTotalButsEquipe2() / getNbConfrontations());
	}

	public Float getMoyenneButsParMatch() {
		return (float) ((getTotalButsEquipe1() + getTotalButsEquipe2()) / getNbConfrontations());
	}

	public Float getPourcentageNuls() {
		Float nbNuls = 0F;

		if (getNbConfrontations() == 5) {

			if (getNbButsEquipe1Recent1().equals(getNbButsEquipe2Recent1())) {
				nbNuls++;
			}
			if (getNbButsEquipe1Recent2().equals(getNbButsEquipe2Recent2())) {
				nbNuls++;
			}
			if (getNbButsEquipe1Recent3().equals(getNbButsEquipe2Recent3())) {
				nbNuls++;
			}
			if (getNbButsEquipe1Recent4().equals(getNbButsEquipe2Recent4())) {
				nbNuls++;
			}
			if (getNbButsEquipe1Recent5().equals(getNbButsEquipe2Recent5())) {
				nbNuls++;
			}
		}
		if (getNbConfrontations() == 4) {

			if (getNbButsEquipe1Recent1().equals(getNbButsEquipe2Recent1())) {
				nbNuls++;
			}
			if (getNbButsEquipe1Recent2().equals(getNbButsEquipe2Recent2())) {
				nbNuls++;
			}
			if (getNbButsEquipe1Recent3().equals(getNbButsEquipe2Recent3())) {
				nbNuls++;
			}
			if (getNbButsEquipe1Recent4().equals(getNbButsEquipe2Recent4())) {
				nbNuls++;
			}
		}
		if (getNbConfrontations() == 3) {

			if (getNbButsEquipe1Recent1().equals(getNbButsEquipe2Recent1())) {
				nbNuls++;
			}
			if (getNbButsEquipe1Recent2().equals(getNbButsEquipe2Recent2())) {
				nbNuls++;
			}
			if (getNbButsEquipe1Recent3().equals(getNbButsEquipe2Recent3())) {
				nbNuls++;
			}
		}
		if (getNbConfrontations() == 2) {

			if (getNbButsEquipe1Recent1().equals(getNbButsEquipe2Recent1())) {
				nbNuls++;
			}
			if (getNbButsEquipe1Recent2().equals(getNbButsEquipe2Recent2())) {
				nbNuls++;
			}
		}
		if (getNbConfrontations() == 1) {

			if (getNbButsEquipe1Recent1().equals(getNbButsEquipe2Recent1())) {
				nbNuls++;
			}
		}
		return pourcentage(nbNuls);
	}

	private Float pourcentage(Float nb) {
		return (float) ((nb / getNbConfrontations()) * 100);
	}

	public Float getPourcentageVictoiresEquipe1() {
		Float nbVictoiresEquipe1 = 0F;

		if (getNbConfrontations() == 5) {

			if (getNbButsEquipe1Recent1() > getNbButsEquipe2Recent1()) {
				nbVictoiresEquipe1++;
			}
			if (getNbButsEquipe1Recent2() > getNbButsEquipe2Recent2()) {
				nbVictoiresEquipe1++;
			}
			if (getNbButsEquipe1Recent3() > getNbButsEquipe2Recent3()) {
				nbVictoiresEquipe1++;
			}
			if (getNbButsEquipe1Recent4() > getNbButsEquipe2Recent4()) {
				nbVictoiresEquipe1++;
			}
			if (getNbButsEquipe1Recent5() > getNbButsEquipe2Recent5()) {
				nbVictoiresEquipe1++;
			}
		}
		if (getNbConfrontations() == 4) {

			if (getNbButsEquipe1Recent1() > getNbButsEquipe2Recent1()) {
				nbVictoiresEquipe1++;
			}
			if (getNbButsEquipe1Recent2() > getNbButsEquipe2Recent2()) {
				nbVictoiresEquipe1++;
			}
			if (getNbButsEquipe1Recent3() > getNbButsEquipe2Recent3()) {
				nbVictoiresEquipe1++;
			}
			if (getNbButsEquipe1Recent4() > getNbButsEquipe2Recent4()) {
				nbVictoiresEquipe1++;
			}
		}
		if (getNbConfrontations() == 3) {

			if (getNbButsEquipe1Recent1() > getNbButsEquipe2Recent1()) {
				nbVictoiresEquipe1++;
			}
			if (getNbButsEquipe1Recent2() > getNbButsEquipe2Recent2()) {
				nbVictoiresEquipe1++;
			}
			if (getNbButsEquipe1Recent3() > getNbButsEquipe2Recent3()) {
				nbVictoiresEquipe1++;
			}
		}
		if (getNbConfrontations() == 2) {

			if (getNbButsEquipe1Recent1() > getNbButsEquipe2Recent1()) {
				nbVictoiresEquipe1++;
			}
			if (getNbButsEquipe1Recent2() > getNbButsEquipe2Recent2()) {
				nbVictoiresEquipe1++;
			}
		}
		if (getNbConfrontations() == 1) {

			if (getNbButsEquipe1Recent1() > getNbButsEquipe2Recent1()) {
				nbVictoiresEquipe1++;
			}
		}
		return pourcentage(nbVictoiresEquipe1);
	}

	public Float getPourcentageMatchsEquipe1MarqueExactementXButs(Float nbButs) {
		Float nbMatchsEquipe1MarqueExactementXButs = 0F;

		if (getNbConfrontations() == 5) {

			if (getNbButsEquipe1Recent1().equals(nbButs)) {
				nbMatchsEquipe1MarqueExactementXButs++;
			}
			if (getNbButsEquipe1Recent2().equals(nbButs)) {
				nbMatchsEquipe1MarqueExactementXButs++;
			}
			if (getNbButsEquipe1Recent3().equals(nbButs)) {
				nbMatchsEquipe1MarqueExactementXButs++;
			}
			if (getNbButsEquipe1Recent4().equals(nbButs)) {
				nbMatchsEquipe1MarqueExactementXButs++;
			}
			if (getNbButsEquipe1Recent5().equals(nbButs)) {
				nbMatchsEquipe1MarqueExactementXButs++;
			}
		}
		if (getNbConfrontations() == 4) {

			if (getNbButsEquipe1Recent1().equals(nbButs)) {
				nbMatchsEquipe1MarqueExactementXButs++;
			}
			if (getNbButsEquipe1Recent2().equals(nbButs)) {
				nbMatchsEquipe1MarqueExactementXButs++;
			}
			if (getNbButsEquipe1Recent3().equals(nbButs)) {
				nbMatchsEquipe1MarqueExactementXButs++;
			}
			if (getNbButsEquipe1Recent4().equals(nbButs)) {
				nbMatchsEquipe1MarqueExactementXButs++;
			}
		}
		if (getNbConfrontations() == 3) {

			if (getNbButsEquipe1Recent1().equals(nbButs)) {
				nbMatchsEquipe1MarqueExactementXButs++;
			}
			if (getNbButsEquipe1Recent2().equals(nbButs)) {
				nbMatchsEquipe1MarqueExactementXButs++;
			}
			if (getNbButsEquipe1Recent3().equals(nbButs)) {
				nbMatchsEquipe1MarqueExactementXButs++;
			}
		}
		if (getNbConfrontations() == 2) {

			if (getNbButsEquipe1Recent1().equals(nbButs)) {
				nbMatchsEquipe1MarqueExactementXButs++;
			}
			if (getNbButsEquipe1Recent2().equals(nbButs)) {
				nbMatchsEquipe1MarqueExactementXButs++;
			}
		}
		if (getNbConfrontations() == 1) {

			if (getNbButsEquipe1Recent1().equals(nbButs)) {
				nbMatchsEquipe1MarqueExactementXButs++;
			}
		}
		return pourcentage(nbMatchsEquipe1MarqueExactementXButs);
	}

	public Float getPourcentageMatchsEquipe2MarqueExactementXButs(Float nbButs) {
		Float nbMatchsEquipe2MarqueExactementXButs = 0F;

		if (getNbConfrontations() == 5) {

			if (getNbButsEquipe2Recent1().equals(nbButs)) {
				nbMatchsEquipe2MarqueExactementXButs++;
			}
			if (getNbButsEquipe2Recent2().equals(nbButs)) {
				nbMatchsEquipe2MarqueExactementXButs++;
			}
			if (getNbButsEquipe2Recent3().equals(nbButs)) {
				nbMatchsEquipe2MarqueExactementXButs++;
			}
			if (getNbButsEquipe2Recent4().equals(nbButs)) {
				nbMatchsEquipe2MarqueExactementXButs++;
			}
			if (getNbButsEquipe2Recent5().equals(nbButs)) {
				nbMatchsEquipe2MarqueExactementXButs++;
			}
		}
		if (getNbConfrontations() == 4) {

			if (getNbButsEquipe2Recent1().equals(nbButs)) {
				nbMatchsEquipe2MarqueExactementXButs++;
			}
			if (getNbButsEquipe2Recent2().equals(nbButs)) {
				nbMatchsEquipe2MarqueExactementXButs++;
			}
			if (getNbButsEquipe2Recent3().equals(nbButs)) {
				nbMatchsEquipe2MarqueExactementXButs++;
			}
			if (getNbButsEquipe2Recent4().equals(nbButs)) {
				nbMatchsEquipe2MarqueExactementXButs++;
			}
		}
		if (getNbConfrontations() == 3) {

			if (getNbButsEquipe2Recent1().equals(nbButs)) {
				nbMatchsEquipe2MarqueExactementXButs++;
			}
			if (getNbButsEquipe2Recent2().equals(nbButs)) {
				nbMatchsEquipe2MarqueExactementXButs++;
			}
			if (getNbButsEquipe2Recent3().equals(nbButs)) {
				nbMatchsEquipe2MarqueExactementXButs++;
			}
		}
		if (getNbConfrontations() == 2) {

			if (getNbButsEquipe2Recent1().equals(nbButs)) {
				nbMatchsEquipe2MarqueExactementXButs++;
			}
			if (getNbButsEquipe2Recent2().equals(nbButs)) {
				nbMatchsEquipe2MarqueExactementXButs++;
			}
		}
		if (getNbConfrontations() == 1) {

			if (getNbButsEquipe2Recent1().equals(nbButs)) {
				nbMatchsEquipe2MarqueExactementXButs++;
			}
		}
		return pourcentage(nbMatchsEquipe2MarqueExactementXButs);
	}

	public Float getPourcentageMatchsEquipe1EncaisseExactementXButs(Float nbButs) {
		return getPourcentageMatchsEquipe2MarqueExactementXButs(nbButs);
	}

	public Float getPourcentageMatchsEquipe2EncaisseExactementXButs(Float nbButs) {
		return getPourcentageMatchsEquipe1MarqueExactementXButs(nbButs);
	}

	public Float getPourcentageVictoiresEquipe1ParXButs(Float ecartButs) {
		Float nbVictoiresEquipe1parXButs = 0F;

		if (getNbConfrontations() == 5) {

			if (getNbButsEquipe1Recent1() - getNbButsEquipe2Recent1() == ecartButs) {
				nbVictoiresEquipe1parXButs++;
			}
			if (getNbButsEquipe1Recent2() - getNbButsEquipe2Recent2() == ecartButs) {
				nbVictoiresEquipe1parXButs++;
			}
			if (getNbButsEquipe1Recent3() - getNbButsEquipe2Recent3() == ecartButs) {
				nbVictoiresEquipe1parXButs++;
			}
			if (getNbButsEquipe1Recent4() - getNbButsEquipe2Recent4() == ecartButs) {
				nbVictoiresEquipe1parXButs++;
			}
			if (getNbButsEquipe1Recent5() - getNbButsEquipe2Recent5() == ecartButs) {
				nbVictoiresEquipe1parXButs++;
			}
		}
		if (getNbConfrontations() == 4) {

			if (getNbButsEquipe1Recent1() - getNbButsEquipe2Recent1() == ecartButs) {
				nbVictoiresEquipe1parXButs++;
			}
			if (getNbButsEquipe1Recent2() - getNbButsEquipe2Recent2() == ecartButs) {
				nbVictoiresEquipe1parXButs++;
			}
			if (getNbButsEquipe1Recent3() - getNbButsEquipe2Recent3() == ecartButs) {
				nbVictoiresEquipe1parXButs++;
			}
			if (getNbButsEquipe1Recent4() - getNbButsEquipe2Recent4() == ecartButs) {
				nbVictoiresEquipe1parXButs++;
			}
		}
		if (getNbConfrontations() == 3) {

			if (getNbButsEquipe1Recent1() - getNbButsEquipe2Recent1() == ecartButs) {
				nbVictoiresEquipe1parXButs++;
			}
			if (getNbButsEquipe1Recent2() - getNbButsEquipe2Recent2() == ecartButs) {
				nbVictoiresEquipe1parXButs++;
			}
			if (getNbButsEquipe1Recent3() - getNbButsEquipe2Recent3() == ecartButs) {
				nbVictoiresEquipe1parXButs++;
			}
		}
		if (getNbConfrontations() == 2) {

			if (getNbButsEquipe1Recent1() - getNbButsEquipe2Recent1() == ecartButs) {
				nbVictoiresEquipe1parXButs++;
			}
			if (getNbButsEquipe1Recent2() - getNbButsEquipe2Recent2() == ecartButs) {
				nbVictoiresEquipe1parXButs++;
			}
		}
		if (getNbConfrontations() == 1) {

			if (getNbButsEquipe1Recent1() - getNbButsEquipe2Recent1() == ecartButs) {
				nbVictoiresEquipe1parXButs++;
			}
		}
		return pourcentage(nbVictoiresEquipe1parXButs);
	}

	public Float getPourcentageVictoiresEquipe2ParXButs(Float ecartButs) {
		Float nbVictoiresEquipe2parXButs = 0F;

		if (getNbConfrontations() == 5) {

			if (getNbButsEquipe2Recent1() - getNbButsEquipe1Recent1() == ecartButs) {
				nbVictoiresEquipe2parXButs++;
			}
			if (getNbButsEquipe2Recent2() - getNbButsEquipe1Recent2() == ecartButs) {
				nbVictoiresEquipe2parXButs++;
			}
			if (getNbButsEquipe2Recent3() - getNbButsEquipe1Recent3() == ecartButs) {
				nbVictoiresEquipe2parXButs++;
			}
			if (getNbButsEquipe2Recent4() - getNbButsEquipe1Recent4() == ecartButs) {
				nbVictoiresEquipe2parXButs++;
			}
			if (getNbButsEquipe2Recent5() - getNbButsEquipe1Recent5() == ecartButs) {
				nbVictoiresEquipe2parXButs++;
			}
		}
		if (getNbConfrontations() == 4) {

			if (getNbButsEquipe2Recent1() - getNbButsEquipe1Recent1() == ecartButs) {
				nbVictoiresEquipe2parXButs++;
			}
			if (getNbButsEquipe2Recent2() - getNbButsEquipe1Recent2() == ecartButs) {
				nbVictoiresEquipe2parXButs++;
			}
			if (getNbButsEquipe2Recent3() - getNbButsEquipe1Recent3() == ecartButs) {
				nbVictoiresEquipe2parXButs++;
			}
			if (getNbButsEquipe2Recent4() - getNbButsEquipe1Recent4() == ecartButs) {
				nbVictoiresEquipe2parXButs++;
			}
		}
		if (getNbConfrontations() == 3) {

			if (getNbButsEquipe2Recent1() - getNbButsEquipe1Recent1() == ecartButs) {
				nbVictoiresEquipe2parXButs++;
			}
			if (getNbButsEquipe2Recent2() - getNbButsEquipe1Recent2() == ecartButs) {
				nbVictoiresEquipe2parXButs++;
			}
			if (getNbButsEquipe2Recent3() - getNbButsEquipe1Recent3() == ecartButs) {
				nbVictoiresEquipe2parXButs++;
			}
		}
		if (getNbConfrontations() == 2) {

			if (getNbButsEquipe2Recent1() - getNbButsEquipe1Recent1() == ecartButs) {
				nbVictoiresEquipe2parXButs++;
			}
			if (getNbButsEquipe2Recent2() - getNbButsEquipe1Recent2() == ecartButs) {
				nbVictoiresEquipe2parXButs++;
			}
		}
		if (getNbConfrontations() == 1) {

			if (getNbButsEquipe2Recent1() - getNbButsEquipe1Recent1() == ecartButs) {
				nbVictoiresEquipe2parXButs++;
			}
		}
		return pourcentage(nbVictoiresEquipe2parXButs);
	}

	public Float getPourcentageVictoiresEquipe2() {
		Float nbVictoiresEquipe2 = 0F;

		if (getNbConfrontations() == 5) {

			if (getNbButsEquipe1Recent1() < getNbButsEquipe2Recent1()) {
				nbVictoiresEquipe2++;
			}
			if (getNbButsEquipe1Recent2() < getNbButsEquipe2Recent2()) {
				nbVictoiresEquipe2++;
			}
			if (getNbButsEquipe1Recent3() < getNbButsEquipe2Recent3()) {
				nbVictoiresEquipe2++;
			}
			if (getNbButsEquipe1Recent4() < getNbButsEquipe2Recent4()) {
				nbVictoiresEquipe2++;
			}
			if (getNbButsEquipe1Recent5() < getNbButsEquipe2Recent5()) {
				nbVictoiresEquipe2++;
			}
		}
		if (getNbConfrontations() == 4) {

			if (getNbButsEquipe1Recent1() < getNbButsEquipe2Recent1()) {
				nbVictoiresEquipe2++;
			}
			if (getNbButsEquipe1Recent2() < getNbButsEquipe2Recent2()) {
				nbVictoiresEquipe2++;
			}
			if (getNbButsEquipe1Recent3() < getNbButsEquipe2Recent3()) {
				nbVictoiresEquipe2++;
			}
			if (getNbButsEquipe1Recent4() < getNbButsEquipe2Recent4()) {
				nbVictoiresEquipe2++;
			}
		}
		if (getNbConfrontations() == 3) {

			if (getNbButsEquipe1Recent1() < getNbButsEquipe2Recent1()) {
				nbVictoiresEquipe2++;
			}
			if (getNbButsEquipe1Recent2() < getNbButsEquipe2Recent2()) {
				nbVictoiresEquipe2++;
			}
			if (getNbButsEquipe1Recent3() < getNbButsEquipe2Recent3()) {
				nbVictoiresEquipe2++;
			}
		}
		if (getNbConfrontations() == 2) {

			if (getNbButsEquipe1Recent1() < getNbButsEquipe2Recent1()) {
				nbVictoiresEquipe2++;
			}
			if (getNbButsEquipe1Recent2() < getNbButsEquipe2Recent2()) {
				nbVictoiresEquipe2++;
			}
		}
		if (getNbConfrontations() == 1) {

			if (getNbButsEquipe1Recent1() < getNbButsEquipe2Recent1()) {
				nbVictoiresEquipe2++;
			}
		}
		return pourcentage(nbVictoiresEquipe2);
	}

	public Float getPourcentageLDEM() {
		Float nbMatchsLDEM = 0F;

		if (getNbConfrontations() == 5) {

			if (getNbButsEquipe1Recent1() > 0 && getNbButsEquipe2Recent1() > 0) {
				nbMatchsLDEM++;
			}
			if (getNbButsEquipe1Recent2() > 0 && getNbButsEquipe2Recent2() > 0) {
				nbMatchsLDEM++;
			}
			if (getNbButsEquipe1Recent3() > 0 && getNbButsEquipe2Recent3() > 0) {
				nbMatchsLDEM++;
			}
			if (getNbButsEquipe1Recent4() > 0 && getNbButsEquipe2Recent4() > 0) {
				nbMatchsLDEM++;
			}
			if (getNbButsEquipe1Recent5() > 0 && getNbButsEquipe2Recent5() > 0) {
				nbMatchsLDEM++;
			}
		}
		if (getNbConfrontations() == 4) {

			if (getNbButsEquipe1Recent1() > 0 && getNbButsEquipe2Recent1() > 0) {
				nbMatchsLDEM++;
			}
			if (getNbButsEquipe1Recent2() > 0 && getNbButsEquipe2Recent2() > 0) {
				nbMatchsLDEM++;
			}
			if (getNbButsEquipe1Recent3() > 0 && getNbButsEquipe2Recent3() > 0) {
				nbMatchsLDEM++;
			}
			if (getNbButsEquipe1Recent4() > 0 && getNbButsEquipe2Recent4() > 0) {
				nbMatchsLDEM++;
			}
		}
		if (getNbConfrontations() == 3) {

			if (getNbButsEquipe1Recent1() > 0 && getNbButsEquipe2Recent1() > 0) {
				nbMatchsLDEM++;
			}
			if (getNbButsEquipe1Recent2() > 0 && getNbButsEquipe2Recent2() > 0) {
				nbMatchsLDEM++;
			}
			if (getNbButsEquipe1Recent3() > 0 && getNbButsEquipe2Recent3() > 0) {
				nbMatchsLDEM++;
			}
		}
		if (getNbConfrontations() == 2) {

			if (getNbButsEquipe1Recent1() > 0 && getNbButsEquipe2Recent1() > 0) {
				nbMatchsLDEM++;
			}
			if (getNbButsEquipe1Recent2() > 0 && getNbButsEquipe2Recent2() > 0) {
				nbMatchsLDEM++;
			}
		}
		if (getNbConfrontations() == 1) {

			if (getNbButsEquipe1Recent1() > 0 && getNbButsEquipe2Recent1() > 0) {
				nbMatchsLDEM++;
			}
		}
		return pourcentage(nbMatchsLDEM);
	}

	public Float getPourcentageMatchPlusBut(Float nbButs) {
		Float nbMatchs = 0F;

		if (getNbConfrontations() == 5) {

			if (getNbButsEquipe1Recent1() + getNbButsEquipe2Recent1() > nbButs) {
				nbMatchs++;
			}
			if (getNbButsEquipe1Recent2() + getNbButsEquipe2Recent2() > nbButs) {
				nbMatchs++;
			}
			if (getNbButsEquipe1Recent3() + getNbButsEquipe2Recent3() > nbButs) {
				nbMatchs++;
			}
			if (getNbButsEquipe1Recent4() + getNbButsEquipe2Recent4() > nbButs) {
				nbMatchs++;
			}
			if (getNbButsEquipe1Recent5() + getNbButsEquipe2Recent5() > nbButs) {
				nbMatchs++;
			}
		}
		if (getNbConfrontations() == 4) {

			if (getNbButsEquipe1Recent1() + getNbButsEquipe2Recent1() > nbButs) {
				nbMatchs++;
			}
			if (getNbButsEquipe1Recent2() + getNbButsEquipe2Recent2() > nbButs) {
				nbMatchs++;
			}
			if (getNbButsEquipe1Recent3() + getNbButsEquipe2Recent3() > nbButs) {
				nbMatchs++;
			}
			if (getNbButsEquipe1Recent4() + getNbButsEquipe2Recent4() > nbButs) {
				nbMatchs++;
			}
		}
		if (getNbConfrontations() == 3) {

			if (getNbButsEquipe1Recent1() + getNbButsEquipe2Recent1() > nbButs) {
				nbMatchs++;
			}
			if (getNbButsEquipe1Recent2() + getNbButsEquipe2Recent2() > nbButs) {
				nbMatchs++;
			}
			if (getNbButsEquipe1Recent3() + getNbButsEquipe2Recent3() > nbButs) {
				nbMatchs++;
			}
		}
		if (getNbConfrontations() == 2) {

			if (getNbButsEquipe1Recent1() + getNbButsEquipe2Recent1() > nbButs) {
				nbMatchs++;
			}
			if (getNbButsEquipe1Recent2() + getNbButsEquipe2Recent2() > nbButs) {
				nbMatchs++;
			}
		}
		if (getNbConfrontations() == 1) {

			if (getNbButsEquipe1Recent1() + getNbButsEquipe2Recent1() > nbButs) {
				nbMatchs++;
			}
		}
		return pourcentage(nbMatchs);
	}

	public Float getPourcentageEquipeDomicilePlusBut(Float nbButs) {
		Float nbMatchs = 0F;

		if (getNbConfrontations() == 5) {

			if (getNbButsEquipe1Recent1() > nbButs) {
				nbMatchs++;
			}
			if (getNbButsEquipe1Recent2() > nbButs) {
				nbMatchs++;
			}
			if (getNbButsEquipe1Recent3() > nbButs) {
				nbMatchs++;
			}
			if (getNbButsEquipe1Recent4() > nbButs) {
				nbMatchs++;
			}
			if (getNbButsEquipe1Recent5() > nbButs) {
				nbMatchs++;
			}
		}
		if (getNbConfrontations() == 4) {

			if (getNbButsEquipe1Recent1() > nbButs) {
				nbMatchs++;
			}
			if (getNbButsEquipe1Recent2() > nbButs) {
				nbMatchs++;
			}
			if (getNbButsEquipe1Recent3() > nbButs) {
				nbMatchs++;
			}
			if (getNbButsEquipe1Recent4() > nbButs) {
				nbMatchs++;
			}
		}
		if (getNbConfrontations() == 3) {

			if (getNbButsEquipe1Recent1() > nbButs) {
				nbMatchs++;
			}
			if (getNbButsEquipe1Recent2() > nbButs) {
				nbMatchs++;
			}
			if (getNbButsEquipe1Recent3() > nbButs) {
				nbMatchs++;
			}
		}
		if (getNbConfrontations() == 2) {

			if (getNbButsEquipe1Recent1() > nbButs) {
				nbMatchs++;
			}
			if (getNbButsEquipe1Recent2() > nbButs) {
				nbMatchs++;
			}

		}
		if (getNbConfrontations() == 1) {

			if (getNbButsEquipe1Recent1() > nbButs) {
				nbMatchs++;
			}
		}
		return pourcentage(nbMatchs);
	}

	public Float getPourcentageEquipeExterieurPlusBut(Float nbButs) {
		Float nbMatchs = 0F;

		if (getNbConfrontations() == 5) {

			if (getNbButsEquipe2Recent1() > nbButs) {
				nbMatchs++;
			}
			if (getNbButsEquipe2Recent2() > nbButs) {
				nbMatchs++;
			}
			if (getNbButsEquipe2Recent3() > nbButs) {
				nbMatchs++;
			}
			if (getNbButsEquipe2Recent4() > nbButs) {
				nbMatchs++;
			}
			if (getNbButsEquipe2Recent5() > nbButs) {
				nbMatchs++;
			}
		}
		if (getNbConfrontations() == 4) {

			if (getNbButsEquipe2Recent1() > nbButs) {
				nbMatchs++;
			}
			if (getNbButsEquipe2Recent2() > nbButs) {
				nbMatchs++;
			}
			if (getNbButsEquipe2Recent3() > nbButs) {
				nbMatchs++;
			}
			if (getNbButsEquipe2Recent4() > nbButs) {
				nbMatchs++;
			}
		}
		if (getNbConfrontations() == 3) {

			if (getNbButsEquipe2Recent1() > nbButs) {
				nbMatchs++;
			}
			if (getNbButsEquipe2Recent2() > nbButs) {
				nbMatchs++;
			}
			if (getNbButsEquipe2Recent3() > nbButs) {
				nbMatchs++;
			}
		}
		if (getNbConfrontations() == 2) {

			if (getNbButsEquipe2Recent1() > nbButs) {
				nbMatchs++;
			}
			if (getNbButsEquipe2Recent2() > nbButs) {
				nbMatchs++;
			}

		}
		if (getNbConfrontations() == 1) {

			if (getNbButsEquipe2Recent1() > nbButs) {
				nbMatchs++;
			}
		}
		return pourcentage(nbMatchs);
	}

	public Float getPourcentageEquipeDomicileMoinsBut(Float nbButs) {
		Float nbMatchs = 0F;

		if (getNbConfrontations() == 5) {

			if (getNbButsEquipe1Recent1() < nbButs) {
				nbMatchs++;
			}
			if (getNbButsEquipe1Recent2() < nbButs) {
				nbMatchs++;
			}
			if (getNbButsEquipe1Recent3() < nbButs) {
				nbMatchs++;
			}
			if (getNbButsEquipe1Recent4() < nbButs) {
				nbMatchs++;
			}
			if (getNbButsEquipe1Recent5() < nbButs) {
				nbMatchs++;
			}
		}
		if (getNbConfrontations() == 4) {

			if (getNbButsEquipe1Recent1() < nbButs) {
				nbMatchs++;
			}
			if (getNbButsEquipe1Recent2() < nbButs) {
				nbMatchs++;
			}
			if (getNbButsEquipe1Recent3() < nbButs) {
				nbMatchs++;
			}
			if (getNbButsEquipe1Recent4() < nbButs) {
				nbMatchs++;
			}
		}
		if (getNbConfrontations() == 3) {

			if (getNbButsEquipe1Recent1() < nbButs) {
				nbMatchs++;
			}
			if (getNbButsEquipe1Recent2() < nbButs) {
				nbMatchs++;
			}
			if (getNbButsEquipe1Recent3() < nbButs) {
				nbMatchs++;
			}
		}
		if (getNbConfrontations() == 2) {

			if (getNbButsEquipe1Recent1() < nbButs) {
				nbMatchs++;
			}
			if (getNbButsEquipe1Recent2() < nbButs) {
				nbMatchs++;
			}

		}
		if (getNbConfrontations() == 1) {

			if (getNbButsEquipe1Recent1() < nbButs) {
				nbMatchs++;
			}
		}
		return pourcentage(nbMatchs);
	}

	public Float getPourcentageEquipeExterieurMoinsBut(Float nbButs) {
		Float nbMatchs = 0F;

		if (getNbConfrontations() == 5) {

			if (getNbButsEquipe2Recent1() < nbButs) {
				nbMatchs++;
			}
			if (getNbButsEquipe2Recent2() < nbButs) {
				nbMatchs++;
			}
			if (getNbButsEquipe2Recent3() < nbButs) {
				nbMatchs++;
			}
			if (getNbButsEquipe2Recent4() < nbButs) {
				nbMatchs++;
			}
			if (getNbButsEquipe2Recent5() < nbButs) {
				nbMatchs++;
			}
		}
		if (getNbConfrontations() == 4) {

			if (getNbButsEquipe2Recent1() < nbButs) {
				nbMatchs++;
			}
			if (getNbButsEquipe2Recent2() < nbButs) {
				nbMatchs++;
			}
			if (getNbButsEquipe2Recent3() < nbButs) {
				nbMatchs++;
			}
			if (getNbButsEquipe2Recent4() < nbButs) {
				nbMatchs++;
			}
		}
		if (getNbConfrontations() == 3) {

			if (getNbButsEquipe2Recent1() < nbButs) {
				nbMatchs++;
			}
			if (getNbButsEquipe2Recent2() < nbButs) {
				nbMatchs++;
			}
			if (getNbButsEquipe2Recent3() < nbButs) {
				nbMatchs++;
			}
		}
		if (getNbConfrontations() == 2) {

			if (getNbButsEquipe2Recent1() < nbButs) {
				nbMatchs++;
			}
			if (getNbButsEquipe2Recent2() < nbButs) {
				nbMatchs++;
			}

		}
		if (getNbConfrontations() == 1) {

			if (getNbButsEquipe2Recent1() < nbButs) {
				nbMatchs++;
			}
		}
		return pourcentage(nbMatchs);
	}

	public Float getPourcentageMatchMoinsBut(Float nbButs) {
		Float nbMatchs = 0F;

		if (getNbConfrontations() == 5) {

			if (getNbButsEquipe1Recent1() + getNbButsEquipe2Recent1() < nbButs) {
				nbMatchs++;
			}
			if (getNbButsEquipe1Recent2() + getNbButsEquipe2Recent2() < nbButs) {
				nbMatchs++;
			}
			if (getNbButsEquipe1Recent3() + getNbButsEquipe2Recent3() < nbButs) {
				nbMatchs++;
			}
			if (getNbButsEquipe1Recent4() + getNbButsEquipe2Recent4() < nbButs) {
				nbMatchs++;
			}
			if (getNbButsEquipe1Recent5() + getNbButsEquipe2Recent5() < nbButs) {
				nbMatchs++;
			}
		}
		if (getNbConfrontations() == 4) {

			if (getNbButsEquipe1Recent1() + getNbButsEquipe2Recent1() < nbButs) {
				nbMatchs++;
			}
			if (getNbButsEquipe1Recent2() + getNbButsEquipe2Recent2() < nbButs) {
				nbMatchs++;
			}
			if (getNbButsEquipe1Recent3() + getNbButsEquipe2Recent3() < nbButs) {
				nbMatchs++;
			}
			if (getNbButsEquipe1Recent4() + getNbButsEquipe2Recent4() < nbButs) {
				nbMatchs++;
			}
		}
		if (getNbConfrontations() == 3) {

			if (getNbButsEquipe1Recent1() + getNbButsEquipe2Recent1() < nbButs) {
				nbMatchs++;
			}
			if (getNbButsEquipe1Recent2() + getNbButsEquipe2Recent2() < nbButs) {
				nbMatchs++;
			}
			if (getNbButsEquipe1Recent3() + getNbButsEquipe2Recent3() < nbButs) {
				nbMatchs++;
			}
		}
		if (getNbConfrontations() == 2) {

			if (getNbButsEquipe1Recent1() + getNbButsEquipe2Recent1() < nbButs) {
				nbMatchs++;
			}
			if (getNbButsEquipe1Recent2() + getNbButsEquipe2Recent2() < nbButs) {
				nbMatchs++;
			}
		}
		if (getNbConfrontations() == 1) {

			if (getNbButsEquipe1Recent1() + getNbButsEquipe2Recent1() < nbButs) {
				nbMatchs++;
			}
		}
		return pourcentage(nbMatchs);
	}

	public void setMatch(String match) {
		this.match = match;
	}

	public static void main(String[] args) {

		Confrontation match = new Confrontation("OM-ASSE", "2-0", "1-0", "2-0", "", "", null);

		System.out.println("match : " + match.getMatch());
		System.out.println("nb confrontations : " + match.getNbConfrontations());
		System.out.println("nb confrontations = 5 ? : ");
		System.out.println("recent1 : " + match.getRecent1());
		System.out.println("recent 1 nb buts équipe 1 : " + match.getNbButsEquipe1Recent1());
		System.out.println("recent 1 nb buts équipe 2 : " + match.getNbButsEquipe2Recent1());
		System.out.println("recent2 : " + match.getRecent2());
		System.out.println("recent 2 nb buts équipe 1 : " + match.getNbButsEquipe1Recent2());
		System.out.println("recent 2 nb buts équipe 2 : " + match.getNbButsEquipe2Recent2());
		System.out.println("recent3 : " + match.getRecent3());
		System.out.println("recent 3 nb buts équipe 1 : " + match.getNbButsEquipe1Recent3());
		System.out.println("recent 3 nb buts équipe 2 : " + match.getNbButsEquipe2Recent3());
		System.out.println("recent4 : " + match.getRecent4());
		System.out.println("recent 4 nb buts équipe 1 : " + match.getNbButsEquipe1Recent4());
		System.out.println("recent 4 nb buts équipe 2 : " + match.getNbButsEquipe2Recent4());
		System.out.println("recent5 : " + match.getRecent5());
		System.out.println("recent 5 nb buts équipe 1 : " + match.getNbButsEquipe1Recent5());
		System.out.println("recent 5 nb buts équipe 2 : " + match.getNbButsEquipe2Recent5());
		System.out.println("surnom equipe 1 : " + match.getSurnomEquipe1());
		System.out.println("surnom equipe 2 : " + match.getSurnomEquipe2());
		System.out.println("total buts equipe 1 : " + match.getTotalButsEquipe1());
		System.out.println("total buts equipe 2 : " + match.getTotalButsEquipe2());
		System.out.println("moyenne buts equipe 1 : " + match.getMoyenneButsParMatchEquipe1());
		System.out.println("moyenne buts equipe 2 : " + match.getMoyenneButsParMatchEquipe2());
		System.out.println("pourcentage equipe 1 -0.5 : " + match.getPourcentageEquipeDomicileMoinsBut(0.5F));
		System.out.println("pourcentage equipe 1 -1.5 : " + match.getPourcentageEquipeDomicileMoinsBut(1.5F));
		System.out.println("pourcentage equipe 1 -2.5 : " + match.getPourcentageEquipeDomicileMoinsBut(2.5F));
		System.out.println("pourcentage equipe 1 -3.5 : " + match.getPourcentageEquipeDomicileMoinsBut(3.5F));
		System.out.println("pourcentage equipe 1 -4.5 : " + match.getPourcentageEquipeDomicileMoinsBut(4.5F));
		System.out.println("pourcentage equipe 2 -0.5 : " + match.getPourcentageEquipeExterieurMoinsBut(0.5F));
		System.out.println("pourcentage equipe 2 -1.5 : " + match.getPourcentageEquipeExterieurMoinsBut(1.5F));
		System.out.println("pourcentage equipe 2 -2.5 : " + match.getPourcentageEquipeExterieurMoinsBut(2.5F));
		System.out.println("pourcentage equipe 2 -3.5 : " + match.getPourcentageEquipeExterieurMoinsBut(3.5F));
		System.out.println("pourcentage equipe 2 -4.5 : " + match.getPourcentageEquipeExterieurMoinsBut(4.5F));
		System.out.println("pourcentage equipe 1 +0.5 : " + match.getPourcentageEquipeDomicilePlusBut(0.5F));
		System.out.println("pourcentage equipe 1 +1.5 : " + match.getPourcentageEquipeDomicilePlusBut(1.5F));
		System.out.println("pourcentage equipe 1 +2.5 : " + match.getPourcentageEquipeDomicilePlusBut(2.5F));
		System.out.println("pourcentage equipe 1 +3.5 : " + match.getPourcentageEquipeDomicilePlusBut(3.5F));
		System.out.println("pourcentage equipe 1 +4.5 : " + match.getPourcentageEquipeDomicilePlusBut(4.5F));
		System.out.println("pourcentage equipe 2 +0.5 : " + match.getPourcentageEquipeExterieurPlusBut(0.5F));
		System.out.println("pourcentage equipe 2 +1.5 : " + match.getPourcentageEquipeExterieurPlusBut(1.5F));
		System.out.println("pourcentage equipe 2 +2.5 : " + match.getPourcentageEquipeExterieurPlusBut(2.5F));
		System.out.println("pourcentage equipe 2 +3.5 : " + match.getPourcentageEquipeExterieurPlusBut(3.5F));
		System.out.println("pourcentage equipe 2 +4.5 : " + match.getPourcentageEquipeExterieurPlusBut(4.5F));
		System.out.println("pourcentage LDEM : " + match.getPourcentageLDEM());
		System.out.println("pourcentage match +0,5 buts : " + match.getPourcentageMatchPlusBut(0.5F));
		System.out.println("pourcentage match +1,5 buts : " + match.getPourcentageMatchPlusBut(1.5F));
		System.out.println("pourcentage match +2,5 buts : " + match.getPourcentageMatchPlusBut(2.5F));
		System.out.println("pourcentage match +3,5 buts : " + match.getPourcentageMatchPlusBut(3.5F));
		System.out.println("pourcentage match +4,5 buts : " + match.getPourcentageMatchPlusBut(4.5F));
		System.out.println("pourcentage match -0,5 buts : " + match.getPourcentageMatchMoinsBut(0.5F));
		System.out.println("pourcentage match -1,5 buts : " + match.getPourcentageMatchMoinsBut(1.5F));
		System.out.println("pourcentage match -2,5 buts : " + match.getPourcentageMatchMoinsBut(2.5F));
		System.out.println("pourcentage match -3,5 buts : " + match.getPourcentageMatchMoinsBut(3.5F));
		System.out.println("pourcentage match -4,5 buts : " + match.getPourcentageMatchMoinsBut(4.5F));
		System.out.println("pourcentage match equipe 1 encaisse exactement 0 but : "
				+ match.getPourcentageMatchsEquipe1EncaisseExactementXButs(0F));
		System.out.println("pourcentage match equipe 1 encaisse exactement 1 but : "
				+ match.getPourcentageMatchsEquipe1EncaisseExactementXButs(1F));
		System.out.println("pourcentage match equipe 1 encaisse exactement 2 but : "
				+ match.getPourcentageMatchsEquipe1EncaisseExactementXButs(2F));
		System.out.println("pourcentage match equipe 1 encaisse exactement 3 but : "
				+ match.getPourcentageMatchsEquipe1EncaisseExactementXButs(3F));
		System.out.println("pourcentage match equipe 1 encaisse exactement 4 but : "
				+ match.getPourcentageMatchsEquipe1EncaisseExactementXButs(4F));
		System.out.println("pourcentage match equipe 1 encaisse exactement 5 but : "
				+ match.getPourcentageMatchsEquipe1EncaisseExactementXButs(5F));
		System.out.println("pourcentage match equipe 2 encaisse exactement 0 but : "
				+ match.getPourcentageMatchsEquipe2EncaisseExactementXButs(0F));
		System.out.println("pourcentage match equipe 2 encaisse exactement 1 but : "
				+ match.getPourcentageMatchsEquipe2EncaisseExactementXButs(1F));
		System.out.println("pourcentage match equipe 2 encaisse exactement 2 but : "
				+ match.getPourcentageMatchsEquipe2EncaisseExactementXButs(2F));
		System.out.println("pourcentage match equipe 2 encaisse exactement 3 but : "
				+ match.getPourcentageMatchsEquipe2EncaisseExactementXButs(3F));
		System.out.println("pourcentage match equipe 2 encaisse exactement 4 but : "
				+ match.getPourcentageMatchsEquipe2EncaisseExactementXButs(4F));
		System.out.println("pourcentage match equipe 2 encaisse exactement 5 but : "
				+ match.getPourcentageMatchsEquipe2EncaisseExactementXButs(5F));
		System.out.println("pourcentage match equipe 1 marque exactement 0 but : "
				+ match.getPourcentageMatchsEquipe1MarqueExactementXButs(0F));
		System.out.println("pourcentage match equipe 1 marque exactement 1 but : "
				+ match.getPourcentageMatchsEquipe1MarqueExactementXButs(1F));
		System.out.println("pourcentage match equipe 1 marque exactement 2 but : "
				+ match.getPourcentageMatchsEquipe1MarqueExactementXButs(2F));
		System.out.println("pourcentage match equipe 1 marque exactement 3 but : "
				+ match.getPourcentageMatchsEquipe1MarqueExactementXButs(3F));
		System.out.println("pourcentage match equipe 1 marque exactement 4 but : "
				+ match.getPourcentageMatchsEquipe1MarqueExactementXButs(4F));
		System.out.println("pourcentage match equipe 1 marque exactement 5 but : "
				+ match.getPourcentageMatchsEquipe1MarqueExactementXButs(5F));
		System.out.println("pourcentage match equipe 2 marque exactement 0 but : "
				+ match.getPourcentageMatchsEquipe2MarqueExactementXButs(0F));
		System.out.println("pourcentage match equipe 2 marque exactement 1 but : "
				+ match.getPourcentageMatchsEquipe2MarqueExactementXButs(1F));
		System.out.println("pourcentage match equipe 2 marque exactement 2 but : "
				+ match.getPourcentageMatchsEquipe2MarqueExactementXButs(2F));
		System.out.println("pourcentage match equipe 2 marque exactement 3 but : "
				+ match.getPourcentageMatchsEquipe2MarqueExactementXButs(3F));
		System.out.println("pourcentage match equipe 2 marque exactement 4 but : "
				+ match.getPourcentageMatchsEquipe2MarqueExactementXButs(4F));
		System.out.println("pourcentage match equipe 2 marque exactement 5 but : "
				+ match.getPourcentageMatchsEquipe2MarqueExactementXButs(5F));
		System.out.println("pourcentage matchs nuls : " + match.getPourcentageNuls());
		System.out.println("pourcentage victoires equipe 1 : " + match.getPourcentageVictoiresEquipe1());
		System.out.println("pourcentage victoires equipe 2 : " + match.getPourcentageVictoiresEquipe2());
		System.out.println("pourcentage victoires equipe 1 par exactement 1 but : "
				+ match.getPourcentageVictoiresEquipe1ParXButs(1F));
		System.out.println("pourcentage victoires equipe 1 par exactement 2 but : "
				+ match.getPourcentageVictoiresEquipe1ParXButs(2F));
		System.out.println("pourcentage victoires equipe 1 par exactement 3 but : "
				+ match.getPourcentageVictoiresEquipe1ParXButs(3F));
		System.out.println("pourcentage victoires equipe 1 par exactement 4 but : "
				+ match.getPourcentageVictoiresEquipe1ParXButs(4F));
		System.out.println("pourcentage victoires equipe 1 par exactement 5 but : "
				+ match.getPourcentageVictoiresEquipe1ParXButs(5F));
		System.out.println("pourcentage victoires equipe 2 par exactement 1 but : "
				+ match.getPourcentageVictoiresEquipe2ParXButs(1F));
		System.out.println("pourcentage victoires equipe 2 par exactement 2 but : "
				+ match.getPourcentageVictoiresEquipe2ParXButs(2F));
		System.out.println("pourcentage victoires equipe 2 par exactement 3 but : "
				+ match.getPourcentageVictoiresEquipe2ParXButs(3F));
		System.out.println("pourcentage victoires equipe 2 par exactement 4 but : "
				+ match.getPourcentageVictoiresEquipe2ParXButs(4F));
		System.out.println("pourcentage victoires equipe 2 par exactement 5 but : "
				+ match.getPourcentageVictoiresEquipe2ParXButs(5F));

	}

}
