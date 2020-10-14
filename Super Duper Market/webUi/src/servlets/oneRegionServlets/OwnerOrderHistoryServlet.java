package servlets.oneRegionServlets;

import DtoObjects.FeedbackDto;
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

@WebServlet(name = "servlets.oneRegionServlets.OwnerOrderHistoryServlet", urlPatterns = {"/ownerOrders"})

public class OwnerOrderHistoryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req,resp);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter())
        {
            String storeIdStr = (String) request.getParameter("param");
            int storeId=Integer.parseInt(storeIdStr);

            Gson gson = new Gson();
            List<StoreOrderDto> storeOrdersHistory=null;

            synchronized (getServletContext()) {
                SDMLogicInterface sdmLogic = ServletUtils.getSdmLogic(getServletContext());
                String regionName= SessionUtils.getRegionName(request);
                //TODO: save customer+location
                storeOrdersHistory = sdmLogic.getStoreOrderHistory(storeId, regionName);
            }
            String json = gson.toJson(storeOrdersHistory);
            out.println(json);
            out.flush();
        }
    }
}
