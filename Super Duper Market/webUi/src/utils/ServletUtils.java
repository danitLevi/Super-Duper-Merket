package utils;

import logic.SDMLogic;
import logic.SDMLogicInterface;

import javax.servlet.ServletContext;

public class ServletUtils {

	/*
	Note  the synchronization is done only on the question and\or creation of the relevant managers and once they exists -
	the actual fetch of them is remained un-synchronized for performance POV
	 */

	private static final Object userEngineLock = new Object();
//	private static final Object mapEngineLock = new Object();



	public static SDMLogicInterface getSdmLogic(ServletContext servletContext) {
		synchronized (userEngineLock) {
			if (servletContext.getAttribute(Constants.SDM_LOGIC_ATTRIBUTE_NAME) == null) {
				servletContext.setAttribute(Constants.SDM_LOGIC_ATTRIBUTE_NAME, new SDMLogic());
			}
		}

		return (SDMLogicInterface) servletContext.getAttribute(Constants.SDM_LOGIC_ATTRIBUTE_NAME);
	}



}
