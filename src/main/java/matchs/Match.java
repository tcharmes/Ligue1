package matchs;

import com.axemble.vdoc.sdk.document.extensions.BaseDocumentExtension;
import com.axemble.vdoc.sdk.interfaces.IAction;
import com.axemble.vdoc.sdk.interfaces.IResourceController;
import com.axemble.vdoc.sdk.interfaces.IStorageResource;
import com.axemble.vdoc.sdk.interfaces.IWorkflowInstance;
import com.doandgo.commons.utils.Strings;
import com.doandgo.moovapps.exceptions.VdocHelperException;

import utilitaire.UtilitaireLigue1;

public class Match extends BaseDocumentExtension {

	private static final long serialVersionUID = 1L;

	@Override
	public boolean onBeforeSubmit(IAction action) {

		if (action.getName().equals("Envoyer")) {

			IWorkflowInstance wi = getWorkflowInstance();
			if (wi != null) {
				try {
					String Equipe1 = (String) wi.getValue(UtilitaireLigue1.FORM_FIELD_SURNOM_EQUIPE_DOMICILE);
					String Equipe2 = (String) wi.getValue(UtilitaireLigue1.FORM_FIELD_SURNOM_EQUIPE_EXTERIEUR);

					if (!Strings.isEmpty(Equipe1) && !Strings.isEmpty(Equipe2)) {

						IStorageResource resourceEquipe1 = UtilitaireLigue1.getResourceEquipe(Equipe1);
						IStorageResource resourceEquipe2 = UtilitaireLigue1.getResourceEquipe(Equipe2);

						if (resourceEquipe1 == null || resourceEquipe2 == null) {

							IResourceController controller = getResourceController();
							controller.alert("Attention ! Au moins une des équipes n'évolue pas en Ligue 1 ! ");
							return false;
						}
						
						Object surnameE1 = resourceEquipe1.getValue(UtilitaireLigue1.TABLE_FIELD_SURNAME);
						Object surnameE2 = resourceEquipe2.getValue(UtilitaireLigue1.TABLE_FIELD_SURNAME);
						
						if(surnameE1 != null && surnameE2 != null) {
							
							String s1 = (String) surnameE1;
							String s2 = (String) surnameE2;
							
							if( s1.equals(s2)) {
								IResourceController controller = getResourceController();
								controller.alert("Attention ! Une équipe ne peut pas s'auto-affronter ! ");
								return false;
							}
							
						}

						boolean victoireEquipe1 = false;
						boolean victoireEquipe2 = false;
						boolean nul = false;
						Object victoireE1 = wi
								.getValue(UtilitaireLigue1.FORM_FIELD_CHECK_BOX_VICTOIRE_EQUIPE_DOMICILE);
						Object victoireE2 = wi
								.getValue(UtilitaireLigue1.FORM_FIELD_CHECK_BOX_VICTOIRE_EQUIPE_EXTERIEUR);
						Object matchNul = wi.getValue(UtilitaireLigue1.FORM_FIELD_CHECK_BOX_NUL);

						if(victoireE1 != null && victoireE2 != null && matchNul != null) {
							victoireEquipe1 = (boolean) victoireE1;
							victoireEquipe2 = (boolean) victoireE2;
							nul = (boolean) matchNul;
							
							// Vérifications formulaire
							if (victoireEquipe1 && (victoireEquipe2 || nul) 
									|| victoireEquipe2 && (victoireEquipe1 || nul)
									|| (!victoireEquipe1 && !victoireEquipe2 && ! nul)) {
								IResourceController controller = getResourceController();
								controller.alert("Attention ! Il ne peut y avoir qu'un seul résultat ! ");
								return false;
							}
						}
						else {
							IResourceController controller = getResourceController();
							controller.alert("Attention ! Au moins l'une des cases à cocher V/N/D est nulle ! ");
							return false;
						}

						boolean equipe1mieuxClassee = false;
						Object e1mieuxClassee = wi
								.getValue(UtilitaireLigue1.FORM_FIELD_CHECK_BOX_EQUIPE_DOMICILE_MIEUX_CLASSEE);
						boolean matchImportantEquipe1 = false;
						Object importantE1 = wi
								.getValue(UtilitaireLigue1.FORM_FIELD_CHECK_BOX_IMPORTANT_EQUIPE_DOMICILE);
						boolean matchImportantEquipe2 = false;
						Object importantE2 = wi
								.getValue(UtilitaireLigue1.FORM_FIELD_CHECK_BOX_IMPORTANT_EQUIPE_EXTERIEUR);

						if (e1mieuxClassee != null && importantE1 != null && importantE2 != null) {
							
							equipe1mieuxClassee = (boolean) e1mieuxClassee;
							matchImportantEquipe1 = (boolean) importantE1;
							matchImportantEquipe2 = (boolean) importantE2;
							
							if (UtilitaireLigue1.verifGenerale()) {
	
								UtilitaireLigue1.setResultat(Equipe1, Equipe2, equipe1mieuxClassee, matchImportantEquipe1, matchImportantEquipe2,
										victoireEquipe1, nul, victoireEquipe2);
							} else {
								IResourceController controller = getResourceController();
								controller.alert("Attention ! Il y a une incohérence dans les chiffres du tableau ! ");
								return false;
							}

							if (!UtilitaireLigue1.verif(Equipe1)) {
								IResourceController controller = getResourceController();
								controller.alert(
										"Attention ! La dernière modification a entraîné une incohérence dans les chiffres du tableau de l'équipe 1 ! (celle qui évolue à domicile)");
								return false;
							}
							if(!UtilitaireLigue1.verif(Equipe2)) {
								IResourceController controller = getResourceController();
								controller.alert(
										"Attention ! La dernière modification a entraîné une incohérence dans les chiffres du tableau de l'équipe 2 ! (celle qui évolue à l'extérieur)");
								return false;
							}
						}
					}
					else {
						IResourceController controller = getResourceController();
						controller.alert(
								"Attention ! L'une des deux équipe n'est pas renseignée ! ");
						return false;
					}
				} catch (VdocHelperException e) {
					e.printStackTrace();
				}
			}
		}

		if (action.getName().equals(UtilitaireLigue1.FORM_FIELD_CHECK_BOX_REINITIALISER_SAISON)) {

			IWorkflowInstance wi = getWorkflowInstance();

			if (wi != null) {

				Object valeurCheckBox = wi.getValue(UtilitaireLigue1.FORM_FIELD_CHECK_BOX_RESET_ALL_SEASON);

				if (valeurCheckBox != null) {
					boolean reset = (boolean) valeurCheckBox;
					if(reset) {
						try {
							UtilitaireLigue1.resetAllSeason();
						} catch (VdocHelperException e) {
							e.printStackTrace();
						}
					}
					else {
					getResourceController()
							.alert("Si vous voulez réinitialiser la saison, merci de cocher la case correspondante");
					return false;
					}
				}
			}
		}

		return super.onBeforeSubmit(action);
	}

}
