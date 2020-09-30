package com.doandgo.ligue1.matchs;

import java.util.Comparator;
import java.util.Map;

/**
 * 
 * Classe utilisée pour classée les équipes dans les 3 classements selon leur
 * nombre de points
 * 
 * @author Thomas CHARMES
 *
 */
public class ClassementComparator implements Comparator<String> {

	private Long classement;
	private Long differenceButs;
	private String name;

	Map<String, Long> base;

	public ClassementComparator(Map<String, Long> base) {
		this.base = base;
	}

	public Long getClassement() {
		return classement;
	}

	public Long getDifferenceButs() {
		return differenceButs;
	}

	public String getName() {
		return name;
	}

	public void setClassement(Long classement) {
		this.classement = classement;
	}

	public void setDifferenceButs(Long differenceButs) {
		this.differenceButs = differenceButs;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ClassementComparator(Long classement, String name) {
		this.classement = classement;
		this.name = name;
//		this.differenceButs = differenceButs;
	}

	@Override
	public int compare(String a, String b) {
		if (base.get(a) >= base.get(b)) {
			return -1;
		} else {
			return 1;
		}
	}

}
