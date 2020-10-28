package servlets.oneRegionServlets.customer.orderServlets.orderSummary;

import DtoObjects.OrderInputToSaveInSessionDto;
import DtoObjects.OrderSummaryDto;
import com.google.gson.Gson;
import logic.SDMLogicInterface;
import superDuperMarketRegion.RegionInterface;
import utils.ServletUtils;
import utils.SessionUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "servlets.oneRegionServlets.customer.orderServlets.orderSummary.GetOrderSummaryDataServlet", urlPatterns = {"/getOrderSummaryData"})


public class GetOrderSummaryDataServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req,resp);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        OrderSummaryDto orderSummaryData;
        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter())
        {
            Gson gson = new Gson();
            RegionInterface region;
            synchronized (getServletContext()) {
                SDMLogicInterface sdmLogic = ServletUtils.getSdmLogic(getServletContext());
                OrderInputToSaveInSessionDto orderInputData = SessionUtils.getOrderInput(request);
                String regionName = SessionUtils.getRegionName(request);
                region = sdmLogic.getRegionByName(regionName);

                //todo: see this function !!  alona did
               orderSummaryData = region.getOrderSummaryData(orderInputData);

            }


            String dataJson = gson.toJson(orderSummaryData);
            out.println(dataJson);
            out.flush();
        }
    }
}
