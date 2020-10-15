package servlets.oneRegionServlets.orderServlets;

import DtoObjects.OrderInputToSaveInSessionDto;
import DtoObjects.StoreDto;
import DtoObjects.StoreInCalcDyanmicOrderDto;
import com.google.gson.Gson;
import logic.SDMLogicInterface;
import superDuperMarket.RegionInterface;
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
import java.util.Map;


@WebServlet(name = "servlets.oneRegionServlets.orderServlets.getStoresInDynamicOrderServlet", urlPatterns = {"/storesInDynamicOrder"})

public class
getStoresInDynamicOrderServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req,resp);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        List<StoreDto> storesDetailsList=null;
        RegionInterface region=null;
        try (PrintWriter out = response.getWriter())
        {
            Gson gson = new Gson();

            synchronized (getServletContext()) {
                SDMLogicInterface sdmLogic = ServletUtils.getSdmLogic(getServletContext());
                String regionName= SessionUtils.getRegionName(request);
                region=sdmLogic.getRegionByName(regionName);

            }
            OrderInputToSaveInSessionDto orderInput=SessionUtils.getOrderInput(request);
            List<StoreInCalcDyanmicOrderDto> storesInDynamicOrder= region.getStoresInDynamicOrderDetails(orderInput.getOrderMinimalPriceBag(),
                                                                                                        orderInput.getxCoordinate(),
                                                                                                        orderInput.getyCoordinate());

            String json = gson.toJson(storesInDynamicOrder);
            out.println(json);
            out.flush();
        }
    }
}
