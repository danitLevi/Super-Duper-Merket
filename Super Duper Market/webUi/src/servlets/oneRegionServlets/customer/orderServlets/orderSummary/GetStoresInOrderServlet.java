package servlets.oneRegionServlets.customer.orderServlets.orderSummary;

import DtoObjects.OrderInputToSaveInSessionDto;
import DtoObjects.StoreDto;
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
import java.util.*;

@WebServlet(name = "servlets.oneRegionServlets.customer.orderServlets.GetStoresInOrderServlet", urlPatterns = {"/getStoresInOrder"})

public class GetStoresInOrderServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req,resp);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        Set<StoreDto> storesInOrder;
        RegionInterface region;
        try (PrintWriter out = response.getWriter())
        {
            Gson gson = new Gson();
            SDMLogicInterface sdmLogic =null;
            String regionName=null;
            Set<Integer> storesInOrderIds=null;
            synchronized (getServletContext()) {
                sdmLogic = ServletUtils.getSdmLogic(getServletContext());
                regionName= SessionUtils.getRegionName(request);
                OrderInputToSaveInSessionDto orderInput=SessionUtils.getOrderInput(request);
                Map<Integer, Map<Integer, Double>> orderMinimalPriceBag=orderInput.getOrderMinimalPriceBag();
                storesInOrderIds= orderMinimalPriceBag.keySet();
            }
            synchronized (sdmLogic) {
                region=sdmLogic.getRegionByName(regionName);
                storesInOrder=region.getWantedStoresDetails(storesInOrderIds);
            }
            String storesInOrderJson = gson.toJson(storesInOrder);
            out.println(storesInOrderJson);
            out.flush();
        }
    }
}
