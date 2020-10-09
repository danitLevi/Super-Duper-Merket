package servlets;

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

@WebServlet(name = "servlets.ChargeServlet", urlPatterns = {"/balance"})

public class getBalanceAmount extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req,resp);
    }
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        response.setContentType("text");
        try (PrintWriter out = response.getWriter()) {
            SDMLogicInterface sdmLogicInterface = ServletUtils.getSdmLogic(getServletContext());
            String userName = SessionUtils.getUsername(request);

            double balance=sdmLogicInterface.getCustomerBalance(userName);
            out.print(balance);
            out.flush();
        }

    }
}
