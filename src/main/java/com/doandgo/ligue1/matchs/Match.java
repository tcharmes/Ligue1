package com.doandgo.ligue1.matchs;

import com.axemble.vdoc.sdk.document.extensions.BaseDocumentExtension;
import com.axemble.vdoc.sdk.interfaces.IAction;
import com.axemble.vdoc.sdk.interfaces.IProperty;
import com.axemble.vdoc.sdk.interfaces.IResourceController;
import com.axemble.vdoc.sdk.interfaces.IStorageResource;
import com.axemble.vdoc.sdk.interfaces.IWorkflowInstance;
import com.axemble.vdoc.sdk.modules.IWorkflowModule;
import com.doandgo.ligue1.utils.UtilitaireLigue1;
import com.doandgo.moovapps.exceptions.VdocHelperException;

/**
 * 
 * Classe d'extension positionnée sur le sous-formulaire de l'unique étape du
 * processus "Match" Vérifie que les données du formulaire sont cohérentes, met
 * à jour les données des classements et des confrontations dans les tables
 * correspondantes
 * 
 * @author Thomas CHARMES
 *
 */
public class Match extends BaseDocumentExtension {

	private static final long serialVersionUID = 1L;

	/**
	 * Vérifie la cohérence des données du formulaire (nombre de cases cochées,
	 * surnoms des équipes)
	 */
	@Override
	public boolean onBeforeSave() {

		IWorkflowInstance wi = getWorkflowInstance();
		String e1 = (String) wi.getValue(UtilitaireLigue1.FORM_FIELD_SURNOM_EQUIPE_DOMICILE);
		String e2 = (String) wi.getValue(UtilitaireLigue1.FORM_FIELD_SURNOM_EQUIPE_EXTERIEUR);
		String score = (String) wi.getValue(UtilitaireLigue1.FORM_FIELD_SCORE);
		if (!UtilitaireLigue1.isEmpty(e1) && !UtilitaireLigue1.isEmpty(e2) && !UtilitaireLigue1.isEmpty(score)) {
			return checkAllFieldsAreOK();
		}
		return super.onBeforeSave();
	}

	/**
	 * Coche les cases sur le classement, l'importance, et le résultat en fonction
	 * des informations renseignées dans le formulaire (surnoms des équipes, score)
	 */
	@Override
	public void onPropertyChanged(IProperty property) {

		IWorkflowModule wm = getWorkflowModule();
		IWorkflowInstance wi = getWorkflowInstance();

		if (property.getName().equals(UtilitaireLigue1.FORM_FIELD_SURNOM_EQUIPE_DOMICILE)
				|| property.getName().equals(UtilitaireLigue1.FORM_FIELD_SURNOM_EQUIPE_EXTERIEUR)) {

			String e1 = (String) wi.getValue(UtilitaireLigue1.FORM_FIELD_SURNOM_EQUIPE_DOMICILE);
			String e2 = (String) wi.getValue(UtilitaireLigue1.FORM_FIELD_SURNOM_EQUIPE_EXTERIEUR);

			if (!UtilitaireLigue1.isEmpty(e1) && !UtilitaireLigue1.isEmpty(e2)) {
				String equipeMieuxClassee = UtilitaireLigue1.getEquipeMieuxClassee(e1, e2);
				if (!UtilitaireLigue1.isEmpty(equipeMieuxClassee)) {
					if (equipeMieuxClassee.equals(e1)) {
						wi.setValue(UtilitaireLigue1.FORM_FIELD_CHECK_BOX_EQUIPE_DOMICILE_MIEUX_CLASSEE, true);
					} else {
						wi.setValue(UtilitaireLigue1.FORM_FIELD_CHECK_BOX_EQUIPE_DOMICILE_MIEUX_CLASSEE, false);
					}
				}

				boolean matchImportantE1 = UtilitaireLigue1.getMatchImportant(e1, e2);
				if (matchImportantE1) {
					wi.setValue(UtilitaireLigue1.FORM_FIELD_CHECK_BOX_IMPORTANT_EQUIPE_DOMICILE, true);
				} else {
					wi.setValue(UtilitaireLigue1.FORM_FIELD_CHECK_BOX_IMPORTANT_EQUIPE_DOMICILE, false);
				}

				boolean matchImportantE2 = UtilitaireLigue1.getMatchImportant(e2, e1);
				if (matchImportantE2) {
					wi.setValue(UtilitaireLigue1.FORM_FIELD_CHECK_BOX_IMPORTANT_EQUIPE_EXTERIEUR, true);
				} else {
					wi.setValue(UtilitaireLigue1.FORM_FIELD_CHECK_BOX_IMPORTANT_EQUIPE_EXTERIEUR, false);
				}
				wi.save(wm.getSysadminContext());
			}
		}

		if (property.getName().equals(UtilitaireLigue1.FORM_FIELD_SCORE)) {
			String score = (String) wi.getValue(UtilitaireLigue1.FORM_FIELD_SCORE);
			if (UtilitaireLigue1.isEmpty(score)) {
				getResourceController().alert("Merci de saisir un score.");
			} else {
				if (!UtilitaireLigue1.isScoreWellFormed(score)) {
					getResourceController()
							.alert("Merci de saisir un score valide au format x-x avec x compris entre 0 et 9.");
				}
			}
			if (UtilitaireLigue1.isScoreWellFormed(score)) {
				String[] split = score.split("-");
				if (Integer.parseInt(split[0]) > Integer.parseInt(split[1])) {
					wi.setValue(UtilitaireLigue1.FORM_FIELD_CHECK_BOX_VICTOIRE_EQUIPE_DOMICILE, true);
					wi.setValue(UtilitaireLigue1.FORM_FIELD_CHECK_BOX_NUL, false);
					wi.setValue(UtilitaireLigue1.FORM_FIELD_CHECK_BOX_VICTOIRE_EQUIPE_EXTERIEUR, false);
				}
				if (Integer.parseInt(split[0]) == Integer.parseInt(split[1])) {
					wi.setValue(UtilitaireLigue1.FORM_FIELD_CHECK_BOX_VICTOIRE_EQUIPE_DOMICILE, false);
					wi.setValue(UtilitaireLigue1.FORM_FIELD_CHECK_BOX_NUL, true);
					wi.setValue(UtilitaireLigue1.FORM_FIELD_CHECK_BOX_VICTOIRE_EQUIPE_EXTERIEUR, false);
				}
				if (Integer.parseInt(split[0]) < Integer.parseInt(split[1])) {
					wi.setValue(UtilitaireLigue1.FORM_FIELD_CHECK_BOX_VICTOIRE_EQUIPE_DOMICILE, false);
					wi.setValue(UtilitaireLigue1.FORM_FIELD_CHECK_BOX_NUL, false);
					wi.setValue(UtilitaireLigue1.FORM_FIELD_CHECK_BOX_VICTOIRE_EQUIPE_EXTERIEUR, true);
				}
			}
		}
		super.onPropertyChanged(property);
	}

	/**
	 * Comptabilise le match Ou réinitialise la saison Ou génère le rapport détaillé
	 * de la rencontre
	 */
	@Override
	public boolean onBeforeSubmit(IAction action) {

		if (action.getName().equals("Envoyer")) {

			IWorkflowInstance wi = getWorkflowInstance();
			IResourceController controller = getResourceController();
			IWorkflowModule wm = getWorkflowModule();
			return checkAllFieldsAreOKAndExecuteMatch(wi, controller, wm);
		}

		if (action.getName().equals(UtilitaireLigue1.FORM_FIELD_CHECK_BOX_REINITIALISER_SAISON)) {

			return resetAllSeason();
		}

		return super.onBeforeSubmit(action);
	}

	/**
	 * Vérifie la cohérence des données du formulaire (nombre de cases cochées,
	 * surnoms des équipes)
	 * 
	 * @return
	 */
	private boolean checkAllFieldsAreOK() {
		IWorkflowInstance wi = getWorkflowInstance();
		if (wi != null) {
			try {
				String Equipe1 = (String) wi.getValue(UtilitaireLigue1.FORM_FIELD_SURNOM_EQUIPE_DOMICILE);
				String Equipe2 = (String) wi.getValue(UtilitaireLigue1.FORM_FIELD_SURNOM_EQUIPE_EXTERIEUR);

				if (!UtilitaireLigue1.isEmpty(Equipe1) && !UtilitaireLigue1.isEmpty(Equipe2)) {

					IStorageResource resourceEquipe1 = UtilitaireLigue1.getResourceEquipe(Equipe1);
					IStorageResource resourceEquipe2 = UtilitaireLigue1.getResourceEquipe(Equipe2);

					if (resourceEquipe1 == null || resourceEquipe2 == null) {

						IResourceController controller = getResourceController();
						controller.alert("Attention ! Au moins une des équipes n'évolue pas en Ligue 1 ! ");
						return false;
					}

					Object surnameE1 = resourceEquipe1.getValue(UtilitaireLigue1.TABLE_FIELD_SURNAME);
					Object surnameE2 = resourceEquipe2.getValue(UtilitaireLigue1.TABLE_FIELD_SURNAME);

					if (surnameE1 != null && surnameE2 != null) {

						String s1 = (String) surnameE1;
						String s2 = (String) surnameE2;

						if (s1.equals(s2)) {
							IResourceController controller = getResourceController();
							controller.alert("Attention ! Une équipe ne peut pas s'auto-affronter ! ");
							return false;
						}

					}

					boolean victoireEquipe1 = false;
					boolean victoireEquipe2 = false;
					boolean nul = false;
					Object victoireE1 = wi.getValue(UtilitaireLigue1.FORM_FIELD_CHECK_BOX_VICTOIRE_EQUIPE_DOMICILE);
					Object victoireE2 = wi.getValue(UtilitaireLigue1.FORM_FIELD_CHECK_BOX_VICTOIRE_EQUIPE_EXTERIEUR);
					Object matchNul = wi.getValue(UtilitaireLigue1.FORM_FIELD_CHECK_BOX_NUL);

					if (victoireE1 != null && victoireE2 != null && matchNul != null) {
						victoireEquipe1 = (boolean) victoireE1;
						victoireEquipe2 = (boolean) victoireE2;
						nul = (boolean) matchNul;

						// Vérifications formulaire
						if (victoireEquipe1 && (victoireEquipe2 || nul) || victoireEquipe2 && (victoireEquipe1 || nul)
								|| (!victoireEquipe1 && !victoireEquipe2 && !nul)) {
							IResourceController controller = getResourceController();
							controller.alert("Attention ! Il ne peut y avoir qu'un seul résultat ! ");
							return false;
						}
					} else {
						IResourceController controller = getResourceController();
						controller.alert("Attention ! Au moins l'une des cases à cocher V/N/D est nulle ! ");
						return false;
					}
				} else {
					IResourceController controller = getResourceController();
					controller.alert("Attention ! L'une des deux équipe n'est pas renseignée ! ");
					return false;
				}
			} catch (VdocHelperException e) {
				e.printStackTrace();
				e.getMessage();
			}
		}
		return true;
	}

	/**
	 * Remet à zéro la totalité de la table "Equipes" pour redémarrer une nouvelle
	 * saison
	 * 
	 * @return
	 */
	private boolean resetAllSeason() {
		IWorkflowInstance wi = getWorkflowInstance();

		if (wi != null) {

			Object valeurCheckBox = wi.getValue(UtilitaireLigue1.FORM_FIELD_CHECK_BOX_RESET_ALL_SEASON);

			if (valeurCheckBox != null) {
				boolean reset = (boolean) valeurCheckBox;
				if (reset) {
					try {
						UtilitaireLigue1.resetAllSeason();
					} catch (VdocHelperException e) {
						e.printStackTrace();
					}
				} else {
					getResourceController()
							.alert("Si vous voulez réinitialiser la saison, merci de cocher la case correspondante");
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Vérifie que les données du formulaire sont cohérentes, met à jour les données
	 * des classements et des confrontations dans les tables correspondantes en
	 * fonction des cases cochées
	 * 
	 * @param wi
	 * @param controller
	 * @param wm
	 * @return
	 */
	public static boolean checkAllFieldsAreOKAndExecuteMatch(IWorkflowInstance wi, IResourceController controller,
			IWorkflowModule wm) {

		if (wi != null) {
			try {
				String Equipe1 = (String) wi.getValue(UtilitaireLigue1.FORM_FIELD_SURNOM_EQUIPE_DOMICILE);
				String Equipe2 = (String) wi.getValue(UtilitaireLigue1.FORM_FIELD_SURNOM_EQUIPE_EXTERIEUR);

				if (!UtilitaireLigue1.isEmpty(Equipe1) && !UtilitaireLigue1.isEmpty(Equipe2)) {

					IStorageResource resourceEquipe1 = UtilitaireLigue1.getResourceEquipe(Equipe1);
					IStorageResource resourceEquipe2 = UtilitaireLigue1.getResourceEquipe(Equipe2);

					if (resourceEquipe1 == null || resourceEquipe2 == null) {
						controller.alert("Attention ! Au moins une des équipes n'évolue pas en Ligue 1 ! ");
						return false;
					}

					Object surnameE1 = resourceEquipe1.getValue(UtilitaireLigue1.TABLE_FIELD_SURNAME);
					Object surnameE2 = resourceEquipe2.getValue(UtilitaireLigue1.TABLE_FIELD_SURNAME);

					if (surnameE1 != null && surnameE2 != null) {

						String s1 = (String) surnameE1;
						String s2 = (String) surnameE2;

						if (s1.equals(s2)) {
							controller.alert("Attention ! Une équipe ne peut pas s'auto-affronter ! ");
							return false;
						}

					}

					boolean victoireEquipe1 = false;
					boolean victoireEquipe2 = false;
					boolean nul = false;
					Object victoireE1 = wi.getValue(UtilitaireLigue1.FORM_FIELD_CHECK_BOX_VICTOIRE_EQUIPE_DOMICILE);
					Object victoireE2 = wi.getValue(UtilitaireLigue1.FORM_FIELD_CHECK_BOX_VICTOIRE_EQUIPE_EXTERIEUR);
					Object matchNul = wi.getValue(UtilitaireLigue1.FORM_FIELD_CHECK_BOX_NUL);

					if (victoireE1 != null && victoireE2 != null && matchNul != null) {
						victoireEquipe1 = (boolean) victoireE1;
						victoireEquipe2 = (boolean) victoireE2;
						nul = (boolean) matchNul;

						// Vérifications formulaire
						if (victoireEquipe1 && (victoireEquipe2 || nul) || victoireEquipe2 && (victoireEquipe1 || nul)
								|| (!victoireEquipe1 && !victoireEquipe2 && !nul)) {
							controller.alert("Attention ! Il ne peut y avoir qu'un seul résultat ! ");
							return false;
						}
					}

					boolean equipe1mieuxClassee = false;
					Object e1mieuxClassee = wi
							.getValue(UtilitaireLigue1.FORM_FIELD_CHECK_BOX_EQUIPE_DOMICILE_MIEUX_CLASSEE);
					boolean matchImportantEquipe1 = false;
					Object importantE1 = wi.getValue(UtilitaireLigue1.FORM_FIELD_CHECK_BOX_IMPORTANT_EQUIPE_DOMICILE);
					boolean matchImportantEquipe2 = false;
					Object importantE2 = wi.getValue(UtilitaireLigue1.FORM_FIELD_CHECK_BOX_IMPORTANT_EQUIPE_EXTERIEUR);
					String score = (String) wi.getValue(UtilitaireLigue1.FORM_FIELD_SCORE);

					if (e1mieuxClassee != null && importantE1 != null && importantE2 != null && score != null
							&& !score.isEmpty()) {

						if (!UtilitaireLigue1.isScoreWellFormed(score)) {
							controller.alert("Le format du score doit être x-x avec x un entier compris entre 0 et 9!");
						}
						equipe1mieuxClassee = (boolean) e1mieuxClassee;
						matchImportantEquipe1 = (boolean) importantE1;
						matchImportantEquipe2 = (boolean) importantE2;

						if (UtilitaireLigue1.verifGenerale()) {

							try {
								wm.beginTransaction();
								UtilitaireLigue1.setResultat(Equipe1, Equipe2, equipe1mieuxClassee,
										matchImportantEquipe1, matchImportantEquipe2, victoireEquipe1, nul,
										victoireEquipe2, score);
								wm.commitTransaction();
							} catch (Exception e) {
								wm.rollbackTransaction();
								return false;
							}

						} else {
							return false;
						}

						if (!UtilitaireLigue1.verif(Equipe1) || !UtilitaireLigue1.verif(Equipe2)) {
							return false;
						}
					}

					String scoreInverse = null;

					try {

						wm.beginTransaction();

						IStorageResource confrontation = UtilitaireLigue1
								.getResourceConfrontation(Equipe1 + "-" + Equipe2);

						if (confrontation == null) {
							confrontation = UtilitaireLigue1.getResourceConfrontation(Equipe2 + "-" + Equipe1);
							if (confrontation != null) {

								String[] split = score.split("-");
								scoreInverse = split[1] + "-" + split[0];

								String recent1 = (String) confrontation
										.getValue(UtilitaireLigue1.TABLE_CONFRONTATIONS_FIELD_RECENT_1);
								String recent2 = (String) confrontation
										.getValue(UtilitaireLigue1.TABLE_CONFRONTATIONS_FIELD_RECENT_2);
								String recent3 = (String) confrontation
										.getValue(UtilitaireLigue1.TABLE_CONFRONTATIONS_FIELD_RECENT_3);
								String recent4 = (String) confrontation
										.getValue(UtilitaireLigue1.TABLE_CONFRONTATIONS_FIELD_RECENT_4);

								confrontation.setValue(UtilitaireLigue1.TABLE_CONFRONTATIONS_FIELD_RECENT_5, recent4);
								confrontation.setValue(UtilitaireLigue1.TABLE_CONFRONTATIONS_FIELD_RECENT_4, recent3);
								confrontation.setValue(UtilitaireLigue1.TABLE_CONFRONTATIONS_FIELD_RECENT_3, recent2);
								confrontation.setValue(UtilitaireLigue1.TABLE_CONFRONTATIONS_FIELD_RECENT_2, recent1);
								confrontation.setValue(UtilitaireLigue1.TABLE_CONFRONTATIONS_FIELD_RECENT_1,
										scoreInverse);

							} else {
								throw new NullConfrontationException("Confrontation non trouvée dans la table");
							}
						} else {
							String recent1 = (String) confrontation
									.getValue(UtilitaireLigue1.TABLE_CONFRONTATIONS_FIELD_RECENT_1);
							String recent2 = (String) confrontation
									.getValue(UtilitaireLigue1.TABLE_CONFRONTATIONS_FIELD_RECENT_2);
							String recent3 = (String) confrontation
									.getValue(UtilitaireLigue1.TABLE_CONFRONTATIONS_FIELD_RECENT_3);
							String recent4 = (String) confrontation
									.getValue(UtilitaireLigue1.TABLE_CONFRONTATIONS_FIELD_RECENT_4);

							confrontation.setValue(UtilitaireLigue1.TABLE_CONFRONTATIONS_FIELD_RECENT_5, recent4);
							confrontation.setValue(UtilitaireLigue1.TABLE_CONFRONTATIONS_FIELD_RECENT_4, recent3);
							confrontation.setValue(UtilitaireLigue1.TABLE_CONFRONTATIONS_FIELD_RECENT_3, recent2);
							confrontation.setValue(UtilitaireLigue1.TABLE_CONFRONTATIONS_FIELD_RECENT_2, recent1);
							confrontation.setValue(UtilitaireLigue1.TABLE_CONFRONTATIONS_FIELD_RECENT_1, score);
						}

						confrontation.save(wm.getSysadminContext());

						wm.commitTransaction();
					} catch (NullConfrontationException e) {
						wm.rollbackTransaction();
						return false;
					}
				} else {
					controller.alert("Attention ! L'une des deux équipe n'est pas renseignée ! ");
					return false;
				}
			} catch (VdocHelperException e) {
				e.getMessage();
				e.printStackTrace();
			}
		}
		return true;
	}

	public static void main(String[] args) {
		String score = "8-2";
		String[] split = score.split("-");
		System.out.println("score equipe 1 : " + split[0]);
		System.out.println("score equipe 2 : " + split[1]);
	}
}
