package utils;

import logic.SDMLogic;
import logic.SDMLogicInterface;
import servlets.alerts.AlertsManager;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class ServletUtils {

	/*
	Note  the synchronization is done only on the question and\or creation of the relevant managers and once they exists -
	the actual fetch of them is remained un-synchronized for performance POV
	 */

	private static final Object userEngineLock = new Object();
	private static final Object alertsManagerLock = new Object();




	public static SDMLogicInterface getSdmLogic(ServletContext servletContext) {
		synchronized (userEngineLock) {
			if (servletContext.getAttribute(Constants.SDM_LOGIC_ATTRIBUTE_NAME) == null) {
				servletContext.setAttribute(Constants.SDM_LOGIC_ATTRIBUTE_NAME, new SDMLogic());
			}
		}

		return (SDMLogicInterface) servletContext.getAttribute(Constants.SDM_LOGIC_ATTRIBUTE_NAME);
	}

	public static AlertsManager getAlertsManager(ServletContext servletContext) {
		synchronized (alertsManagerLock) {
			if (servletContext.getAttribute(Constants.ALERTS_MANAGER_ATTRIBUTE) == null) {
				servletContext.setAttribute(Constants.ALERTS_MANAGER_ATTRIBUTE, new AlertsManager());
			}
		}
		return (AlertsManager) servletContext.getAttribute(Constants.ALERTS_MANAGER_ATTRIBUTE);
	}

	public static int getIntParameter(HttpServletRequest request, String name) {
		String value = request.getParameter(name);
		if (value != null) {
			try {
				return Integer.parseInt(value);
			} catch (NumberFormatException numberFormatException) {
			}
		}
		return Constants.INT_PARAMETER_ERROR;
	}

}
