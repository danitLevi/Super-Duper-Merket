package utils;


import DtoObjects.OrderInputToSaveInSessionDto;

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

    //set user details in session
    public  static void saveUserDetailsInSession(HttpServletRequest request , String userNameFromParameter,String userTypeFromParameter )
    {
        request.getSession(true).setAttribute(Constants.USERNAME, userNameFromParameter);
        request.getSession(true).setAttribute(Constants.USER_TYPE, userTypeFromParameter);
        request.getSession(false).setAttribute(Constants.USER_VERSION, 0);
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

    public static void setOrderInput(HttpServletRequest request,OrderInputToSaveInSessionDto orderInput)
    {
        HttpSession session = request.getSession();
        session.setAttribute(Constants.ORDER_INPUT,orderInput);
    }

    public static OrderInputToSaveInSessionDto getOrderInput (HttpServletRequest request) {
        HttpSession session = request.getSession();
         return (OrderInputToSaveInSessionDto) session.getAttribute(Constants.ORDER_INPUT);

    }

    public static int  getVersion(HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        return (int) session.getAttribute(Constants.USER_VERSION);
    }
    public static void setVersion(HttpServletRequest request,int newVersion)
    {
        HttpSession session = request.getSession();
        session.setAttribute(Constants.USER_VERSION,newVersion);
    }


}