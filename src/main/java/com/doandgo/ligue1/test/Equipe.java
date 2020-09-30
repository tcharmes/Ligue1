package com.doandgo.ligue1.test;

public class Equipe {
	
	String surnom;
	Long nbPoints;
	Long differenceButs;
	
	public Equipe(String surnom, Long nbPoints, Long differenceButs) {
		
		this.surnom = surnom;
		this.nbPoints = nbPoints;
		this.differenceButs = differenceButs;
	}

	public String getSurnom() {
		return surnom;
	}

	public Long getNbPoints() {
		return nbPoints;
	}

	public Long getDifferenceButs() {
		return differenceButs;
	}

	public void setSurnom(String surnom) {
		this.surnom = surnom;
	}

	public void setNbPoints(Long nbPoints) {
		this.nbPoints = nbPoints;
	}

	public void setDifferenceButs(Long differenceButs) {
		this.differenceButs = differenceButs;
	}

	
}
