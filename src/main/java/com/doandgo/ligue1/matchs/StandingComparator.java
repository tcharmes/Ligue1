package com.doandgo.ligue1.matchs;

import java.util.Comparator;
import java.util.Map;

/**
 * Classe qui surcharge la méthode compare(String a, String b) de l'interface Comparator
 * pour déterminer quelle équipe est mieux classée que l'autre en tenant compte de son
 * nombre de points et éventuellement de sa différence de buts
 * 
 * @author Thomas CHARMES
 *
 */
public class StandingComparator implements Comparator<String> {
	
	private Map<String, Long[]> base;
	
	public StandingComparator(Map<String, Long[]> base) {
		this.base = base;
	}
	
	/**
	 * place premièrement l'équipe avec le plus de points et en cas d'égalité,
	 * l'équipe avec la différence de but la plus haute
	 * 
	 * ex : {"MHSC", [10,4]} 
	 * est mieux classé que {"OM", [10,2]} 
	 * et que {"RCL", [9,8]}
	 * mais moins bien que {"PSG", [10,7]}
	 * 
	 * @param team1 le surnom de la première équipe
	 * @param team2 le surnom de la deuxième équipe
	 */
	@Override
	public int compare(String team1, String team2) {
		
		Long[] statsTeam1 = base.get(team1);
		Long[] statsTeam2 = base.get(team2);
		if (statsTeam1 != null && statsTeam2 != null) {
			if(statsTeam1[0] > statsTeam2[0]) {
				return -1;
			}
			if(statsTeam1[0] < statsTeam2[0]) {
				return 1;
			}
			if (statsTeam1[0] == statsTeam2[0]) {
				if (statsTeam1[1] > statsTeam2[1]) {
					return -1;
				}
				else {
					return 1;
				}
			}
		}
		return 0;
	}

}
