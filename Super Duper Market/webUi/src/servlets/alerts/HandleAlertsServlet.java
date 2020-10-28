package servlets.alerts;

import com.google.gson.Gson;
import utils.ServletUtils;
import utils.SessionUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "servlets.alerts.HandleAlertsServlet", urlPatterns = {"/handleAlerts"})

public class HandleAlertsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        AlertsManager alertsManager=null;

        response.setContentType("application/json");

        try (PrintWriter out = response.getWriter()) {
            Gson gson = new Gson();
            synchronized (getServletContext()) {
                alertsManager = ServletUtils.getAlertsManager(getServletContext()); //todo: no need of synchronized ?
            }
            String userName = SessionUtils.getUsername(request);

            if(alertsManager.isUserHasNewAlerts(userName))
            {
                int userVersion = SessionUtils.getVersion(request);
                AlertAndVersionOutput alertAndVersionOutput = alertsManager.getUserAlerts(userName, userVersion);
                SessionUtils.setVersion(request, alertAndVersionOutput.getVersion());
                String json = gson.toJson(alertAndVersionOutput.getAlertMsgs());
                out.println(json);
            }
            else
            {
                out.print("No alerts");
            }


            out.flush();

        }
    }

//todo : handle version (do alerts infinity )
}
