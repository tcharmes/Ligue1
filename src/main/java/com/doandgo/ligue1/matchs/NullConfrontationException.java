package com.doandgo.ligue1.matchs;

/**
 * Exception renvoyée si la confrontation renseignée dans le document process 
 * n'est pas retrouvée dans la table "Confrontations"
 * 
 * @author Thomas CHARMES
 *
 */
public class NullConfrontationException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public NullConfrontationException(String message) {
		super(message);
	}

}
