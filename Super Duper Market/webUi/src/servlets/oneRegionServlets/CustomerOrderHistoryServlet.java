package servlets.oneRegionServlets;

import DtoObjects.OrderDto;
import DtoObjects.StoreOrderDto;
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

@WebServlet(name = "servlets.oneRegionServlets.CustomerOrderHistoryServlet", urlPatterns = {"/customerOrders"})

public class CustomerOrderHistoryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req,resp);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter())
        {
            Gson gson = new Gson();
            List<OrderDto> customerOrdersHistory=null;

            synchronized (getServletContext()) {
                SDMLogicInterface sdmLogic = ServletUtils.getSdmLogic(getServletContext());
                //TODO: write method (save order to customer)
               customerOrdersHistory = sdmLogic.getCustomerOrderHistory(SessionUtils.getUsername(request));
            }
            String json = gson.toJson(customerOrdersHistory);
            out.println(json);
            out.flush();
        }
    }
}
