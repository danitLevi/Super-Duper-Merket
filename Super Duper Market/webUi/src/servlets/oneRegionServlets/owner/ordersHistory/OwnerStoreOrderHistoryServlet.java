package servlets.oneRegionServlets.owner.ordersHistory;

import DtoObjects.FeedbackDto;
import DtoObjects.StoreOrderDto;
import com.google.gson.Gson;
import logic.SDMLogicInterface;
import utils.Constants;
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

@WebServlet(name = "servlets.oneRegionServlets.owner.ordersHistory.OwnerStoreOrderHistoryServlet", urlPatterns = {"/ownerStoreOrders"})

public class OwnerStoreOrderHistoryServlet extends HttpServlet {

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
            int storeId = Integer.parseInt(request.getParameter(Constants.STORE_ID));

            List<StoreOrderDto> storeOrdersHistory=null;

            synchronized (getServletContext()) {
                SDMLogicInterface sdmLogic = ServletUtils.getSdmLogic(getServletContext());
                String regionName= SessionUtils.getRegionName(request);
                storeOrdersHistory = sdmLogic.getStoreOrderHistory(storeId, regionName);
            }
            String json = gson.toJson(storeOrdersHistory);
            out.println(json);
            out.flush();
        }
    }
}
