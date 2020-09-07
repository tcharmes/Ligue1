package com.doandgo.ligue1.matchs;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * Classe utilisée pour classée les équipes dans les 3 classements selon leur nombre de points
 * 
 * @author Thomas CHARMES
 *
 */
public class PourcentageComparator implements Comparator<String> {

	private String name;
	private String pourcentage;

	Map<String, String> base;

	public PourcentageComparator(Map<String, String> base) {
		this.base = base;
	}

	public String getPourcentage() {
		return pourcentage;
	}

	public String getName() {
		return name;
	}

	public void setPourcentage(String pourcentage) {
		this.pourcentage = pourcentage;
	}

	public void setName(String name) {
		this.name = name;
	}

	public PourcentageComparator(String name, String pourcentage) {
		this.pourcentage = pourcentage;
		this.name = name;
	}

	@Override
	public int compare(String a, String b) {
		String valueOfA = base.get(a);
		String valueOfB = base.get(b);
		String[] splitA = valueOfA.split("%");
		String[] splitB = valueOfB.split("%");
		if (Float.parseFloat(splitA[0]) >= Float.parseFloat(splitB[0])) {
			return -1;
		} else {
			return 1;
		}
	}
	
	public static void main(String[] args) {
		
		Map<String, String> stats = new HashMap<String, String>();
		stats.put("MHSC", "60.0%");
		stats.put("OGCN", "50.00%");
		stats.put("nul", "70%");
		PourcentageComparator p = new PourcentageComparator(stats);
		System.out.println(p.compare("MHSC", "nul"));
		System.out.println(p.compare("OGCN", "nul"));
	}

}

