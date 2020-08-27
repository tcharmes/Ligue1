package com.doandgo.ligue1.agents;

import com.axemble.vdoc.sdk.Modules;
import com.axemble.vdoc.sdk.agent.base.BaseAgent;
import com.axemble.vdoc.sdk.exceptions.WorkflowModuleException;
import com.axemble.vdoc.sdk.interfaces.IAgent;
import com.axemble.vdoc.sdk.interfaces.IReport;
import com.axemble.vdoc.sdk.modules.IWorkflowModule;
import com.axemble.vdoc.sdk.utils.Logger;

/**
 * 
 * @author Thomas CHARMES
 */
public class AgentJourneeTerminee extends BaseAgent {

	private static final Logger logger = Logger.getLogger(AgentJourneeTerminee.class);
	public static IReport iReport = null;

	@Override
	protected void execute() {
		try {
			execute(getReport());
		} catch (Exception e) {
			reportError("Erreur à l'éxécution de l'agent " + this.getClass().getName() + " : " + e.getMessage());
			e.printStackTrace();
		}
	}

	private static void execute(IReport report) throws Exception {
		iReport = report;

		update10MatchesDatas();
	}

	private static void update10MatchesDatas() {

		IWorkflowModule wm = Modules.getWorkflowModule();
		try {
			IAgent agentComptabilisationMatchs = wm.getAgent(wm.getSysadminContext(), "ComptabilisationMatchs");
			wm.execute(agentComptabilisationMatchs);
		} catch (WorkflowModuleException e) {
			reportError("Erreur à la récupération/exécution de l'agent ChangeEtape : " + e.getMessage());
			e.printStackTrace();
		}
		
		reportInfo(
				"L'agent ComptabilisationMatchs a été correctement exécuté. Les données de la table Equipe sont à jour.");
		Modules.releaseModule(wm);

	}

	public static void reportInfo(String log) {
		if (iReport != null)
			iReport.addInfo(log);
		logger.debug(log);
	}

	public static void reportError(String log) {
		if (iReport != null)
			iReport.addError(log);
		logger.error(log);
	}
}
