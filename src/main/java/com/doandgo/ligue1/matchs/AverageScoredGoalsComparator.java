package com.doandgo.ligue1.matchs;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * Classe utilisée pour classer les équipes selon leur moyenne de buts marqués.
 * 
 * @author Thomas CHARMES
 *
 */
public class AverageScoredGoalsComparator implements Comparator<String> {

	private String name;
	private String moyenne;

	Map<String, String> base;

	public AverageScoredGoalsComparator(Map<String, String> base) {
		this.base = base;
	}

	public String getMoyenne() {
		return moyenne;
	}

	public String getName() {
		return name;
	}

	public void setMoyenne(String moyenne) {
		this.moyenne = moyenne;
	}

	public void setName(String name) {
		this.name = name;
	}

	public AverageScoredGoalsComparator(String name, String moyenne) {
		this.moyenne = moyenne;
		this.name = name;
	}

	@Override
	public int compare(String a, String b) {
		String valueOfA = base.get(a);
		String valueOfB = base.get(b);
		String[] splitA = valueOfA.split("buts");
		String[] splitB = valueOfB.split("buts");
		if (Float.parseFloat(splitA[0]) >= Float.parseFloat(splitB[0])) {
			return 1;
		} else {
			return -1;
		}
	}
	
	public static void main(String[] args) {
		
		Map<String, String> stats = new HashMap<String, String>();
		stats.put("MHSC", "1.5 buts");
		stats.put("OGCN", "0.75 buts");
		stats.put("PSG", "10.1 buts");
		AverageScoredGoalsComparator p = new AverageScoredGoalsComparator(stats);
		System.out.println(p.compare("MHSC", "PSG"));
		System.out.println(p.compare("OGCN", "PSG"));
		System.out.println(p.compare("MHSC", "OGCN"));
	}

}
