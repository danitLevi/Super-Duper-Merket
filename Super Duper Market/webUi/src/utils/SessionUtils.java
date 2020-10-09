package utils;


import constants.Constants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionUtils {

    public static String getUsername (HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Object sessionAttribute = session != null ? session.getAttribute(Constants.USERNAME) : null;
        return sessionAttribute != null ? sessionAttribute.toString() : null;
    }
    public static String getUserType (HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Object sessionAttribute = session != null ? session.getAttribute(Constants.USER_TYPE) : null;
        return sessionAttribute != null ? sessionAttribute.toString() : null;
    }

    public static void setRegionName(HttpServletRequest request,String regionName)
    {
        HttpSession session = request.getSession();
        session.setAttribute(Constants.REGION_NAME,regionName);
    }

    public static String  getRegionName(HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        return (String) session.getAttribute(Constants.REGION_NAME);
    }
    public static void clearSession (HttpServletRequest request) {
        request.getSession().invalidate();
    }
}