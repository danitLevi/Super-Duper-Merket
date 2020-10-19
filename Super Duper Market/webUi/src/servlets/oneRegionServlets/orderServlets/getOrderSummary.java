package servlets.oneRegionServlets.orderServlets;

import DtoObjects.OrderInputToSaveInSessionDto;
import com.google.gson.Gson;
import logic.SDMLogicInterface;
import superDuperMarket.Region;
import superDuperMarket.StoreOrder;
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
import java.util.Set;

@WebServlet(name = "servlets.oneRegionServlets.orderServlets.getOrderSummary", urlPatterns = {"/orderSummaryData"})


public class getOrderSummary extends HttpServlet {

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

            synchronized (getServletContext()) {
                SDMLogicInterface sdmLogic = ServletUtils.getSdmLogic(getServletContext());
                OrderInputToSaveInSessionDto orderData = SessionUtils.getOrderInput(request);
                String regionName = SessionUtils.getRegionName(request);
                Region region = sdmLogic.getRegionByName(regionName);
//              OrderSummaryDto orderSummaryData = region.getOrderSummaryData(orderData);

            }

//            String json = gson.toJson();
//            out.println(json);
            out.flush();
        }
    }
}
