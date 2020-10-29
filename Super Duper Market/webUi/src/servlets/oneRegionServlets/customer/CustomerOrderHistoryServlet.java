package servlets.oneRegionServlets.customer;

import DtoObjects.OrderDto;
import DtoObjects.StoreOrderDto;
import com.google.gson.Gson;
import logic.SDMLogicInterface;
import utils.ServletUtils;
import utils.SessionUtils;

import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "servlets.oneRegionServlets.customer.CustomerOrderHistoryServlet", urlPatterns = {"/customerOrders"})

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
            String json="";
            SDMLogicInterface sdmLogic=null;
            List<OrderDto> customerOrdersHistory=null;
            String customerName=SessionUtils.getUsername(request);
            String regionName=SessionUtils.getRegionName(request);
            synchronized (getServletContext()) {
                sdmLogic = ServletUtils.getSdmLogic(getServletContext());
            }
            synchronized (sdmLogic) {
                customerOrdersHistory = sdmLogic.getCustomerOrderHistoryInRegion(customerName,regionName);
            }
            json = gson.toJson(customerOrdersHistory);

            out.println(json);
            out.flush();
        }
    }
}
