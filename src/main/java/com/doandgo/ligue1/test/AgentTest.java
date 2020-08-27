package com.doandgo.ligue1.test;

import com.axemble.vdoc.sdk.agent.base.BaseAgent;
import com.axemble.vdoc.sdk.interfaces.IReport;
import com.axemble.vdoc.sdk.utils.Logger;

/**
 * 
 * @author Thomas CHARMES
 */
public class AgentTest extends BaseAgent {
	
	private static final Logger logger = Logger.getLogger(AgentTest.class);
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
		//TODO
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
