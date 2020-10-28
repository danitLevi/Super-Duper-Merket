package servlets.alerts;

import utils.Constants;
import utils.ServletUtils;
import utils.SessionUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;

@WebServlet(name = "servlets.alerts.AddUserToAlertsManagerServlet", urlPatterns = {"/addUserToAlertsManager"})

public class AddUserToAlertsManagerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userName = SessionUtils.getUsername(request);
        AlertsManager alertsManager=null;
        synchronized (getServletContext()) {
            alertsManager = ServletUtils.getAlertsManager(getServletContext()); //todo: need synchronized on alerts manager(not only servlet context) ?
            alertsManager.addUserToAlertsManager(userName);
        }
    }
}
