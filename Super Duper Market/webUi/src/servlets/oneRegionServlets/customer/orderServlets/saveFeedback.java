package servlets.oneRegionServlets.customer.orderServlets;

import logic.SDMLogicInterface;
import utils.ServletUtils;
import utils.SessionUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@WebServlet(name = "servlets.oneRegionServlets.customer.orderServlets.saveFeedback", urlPatterns = {"/saveFeedback"})

public class saveFeedback extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req,resp);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        SDMLogicInterface sdmLogic= ServletUtils.getSdmLogic(getServletContext());
        String regionName = SessionUtils.getRegionName(request);
        String customer = SessionUtils.getUsername(request);
        int storeId= Integer.parseInt(request.getParameter("store"));
        int rate= Integer.parseInt(request.getParameter("rate"));
        String review = request.getParameter("review");

        Date date =SessionUtils.getOrderInput(request).getDate();

        sdmLogic.addFeedbackToOwner(regionName, customer, date, rate, review, storeId);
    }
}
