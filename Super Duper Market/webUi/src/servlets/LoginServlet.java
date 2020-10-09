package servlets;

import constants.Constants;
import logic.SDMLogicInterface;
import utils.ServletUtils;
import utils.SessionUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "servlets.LoginServlet", urlPatterns = {"/login"})

public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String usernameFromSession = SessionUtils.getUsername(request);

            SDMLogicInterface sdmLogic = ServletUtils.getSdmLogic(getServletContext());

            //First time logging in
            if (usernameFromSession == null) {
                String userNameFromParameter = request.getParameter(Constants.USERNAME);
                String userTypeFromParameter = request.getParameter(Constants.USER_TYPE);

                if (userNameFromParameter == null || userNameFromParameter.isEmpty()
                        || userTypeFromParameter == null || userTypeFromParameter.isEmpty()) {
                    out.print(Constants.LOGIN_HTML);
//                    response.sendRedirect(Constants.LOGIN_HTML); //?
                    out.flush();
                } else {
                    userNameFromParameter = userNameFromParameter.trim();
                    synchronized (this) {

                        if (sdmLogic.isUserExist(userNameFromParameter)) {

                            out.print("The username " + userNameFromParameter + " is already taken.");
                            out.flush();
                        } else {
                            if (userTypeFromParameter.equals(Constants.customer)) {
                                sdmLogic.addCustomer(userNameFromParameter);
                            } else if (userTypeFromParameter.equals(Constants.storeOwner)) {
                                sdmLogic.addOwner(userNameFromParameter);
                            } else {
                                out.print("User must be a customer or a storeOwner.");
                                out.flush();
                                return;
                            }
                            request.getSession(true).setAttribute(Constants.USERNAME, userNameFromParameter);
                            request.getSession(true).setAttribute(Constants.USER_TYPE, userTypeFromParameter);
                            out.print(Constants.REGIONS_PAGE);
//                            response.sendRedirect(Constants.STORES_PAGE); //?
                            out.flush();
                        }
                    }
                }
            } else {
//                response.sendRedirect(Constants.STORES_PAGE);
                out.print(Constants.REGIONS_PAGE);
                out.flush();
            }
        }
    }
}







