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
        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {

            Gson gson = new Gson();
            String currUserName = SessionUtils.getUsername(request);
            AlertsManager chatManager = ServletUtils.getAlertsManager(getServletContext()); //todo: no need of synchronized ?

            String userName = SessionUtils.getUsername(request);
            int userVersion = SessionUtils.getVersion(request);

            if(userVersion!=chatManager.getCurrentVersion())
            {
                AlertAndVersionOutput alertAndVersionOutput = chatManager.getUserAlerts(userName, userVersion);
                SessionUtils.setVersion(request, alertAndVersionOutput.getVersion());
                String json = gson.toJson(alertAndVersionOutput.getAlertMsgs());
                out.println(json);
            }
            else
            {
                out.println("No alerts");
            }


            out.flush();

        }
    }


}
