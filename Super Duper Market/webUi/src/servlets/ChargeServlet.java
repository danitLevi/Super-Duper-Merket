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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet(name = "servlets.ChargeServlet", urlPatterns = {"/charge"})

public class ChargeServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req,resp);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SDMLogicInterface sdmLogicInterface= ServletUtils.getSdmLogic(getServletContext());
        String userName= SessionUtils.getUsername(request);
        double amountToCharge= Double.parseDouble(request.getParameter(Constants.AMOUNT_TO_CHARGE));
//        Date date=new Date( request.getParameter(Constants.DATE));
        Date date= null;
        String stringDate=request.getParameter(Constants.DATE);
        try {
            date = new SimpleDateFormat("yyyy-mm-dd").parse(stringDate);
        } catch (ParseException e) {
            response.sendError(406 );
        }
        sdmLogicInterface.chargeCustomerBalance(userName,amountToCharge,date);
    }
}
