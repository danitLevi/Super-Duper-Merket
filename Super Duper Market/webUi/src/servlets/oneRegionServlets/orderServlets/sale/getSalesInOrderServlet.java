package servlets.oneRegionServlets.orderServlets.sale;

import DtoObjects.OrderInputToSaveInSessionDto;
import DtoObjects.SaleDto;
import DtoObjects.StoreDto;
import DtoObjects.StoreInCalcDyanmicOrderDto;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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

@WebServlet(name = "servlets.oneRegionServlets.orderServlets.sale.getSalesInOrderServlet", urlPatterns = {"/salesInOrder"})

public class getSalesInOrderServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req,resp);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
//        List<StoreDto> storesDetailsList=null;
        RegionInterface region=null;
        try (PrintWriter out = response.getWriter())
        {
//            Gson gson = new Gson();
            Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();


            synchronized (getServletContext()) {
                SDMLogicInterface sdmLogic = ServletUtils.getSdmLogic(getServletContext());
                String regionName= SessionUtils.getRegionName(request);
                region=sdmLogic.getRegionByName(regionName);

            }
            OrderInputToSaveInSessionDto orderInput=SessionUtils.getOrderInput(request);

            Map<Integer, Map<SaleDto,Integer>> storeIDToStoreSaleToAmount= region.getSalesInOrder(orderInput.getOrderMinimalPriceBag());

            if(storeIDToStoreSaleToAmount.size()==0)
            {
                out.println("No Sales");

            }
            else
            {
                String SalesJson = gson.toJson(storeIDToStoreSaleToAmount);
                out.println(SalesJson);
            }
            out.flush();
        }

    }
}
