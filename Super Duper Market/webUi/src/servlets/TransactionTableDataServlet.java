package servlets;

import DtoObjects.TransactionDto;
import com.google.gson.Gson;
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
import java.util.List;

@WebServlet(name = "servlets.TransactionTableDataServlet", urlPatterns = {"/transactionsData"})

public class TransactionTableDataServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req,resp);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter())
        {
            Gson gson = new Gson();
            String userName= SessionUtils.getUsername(request);
            List<TransactionDto> transactionsDetails=null;

            synchronized (getServletContext()) {
                SDMLogicInterface sdmLogic = ServletUtils.getSdmLogic(getServletContext());
                transactionsDetails = sdmLogic.getTransactionsDetails(userName);

            }

            String json = gson.toJson(transactionsDetails);
            out.println(json);
            out.flush();

        }
    }
}
