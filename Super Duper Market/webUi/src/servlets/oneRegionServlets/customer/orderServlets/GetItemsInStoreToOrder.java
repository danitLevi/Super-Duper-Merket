package servlets.oneRegionServlets.customer.orderServlets;

import DtoObjects.ItemInStoreDto;
import com.google.gson.Gson;
import utils.Constants;
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
import java.util.List;

@WebServlet(name = "servlets.oneRegionServlets.customer.orderServlets.GetItemsInStoreToOrder", urlPatterns = {"/itemsFromStoreToOrder"})

public class GetItemsInStoreToOrder extends HttpServlet {

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
            List<ItemInStoreDto> ItemsInStoreToOrder=null;
            synchronized (getServletContext()) {
                SDMLogicInterface sdmLogic = ServletUtils.getSdmLogic(getServletContext());
                String regionName= SessionUtils.getRegionName(request);
                RegionInterface region=sdmLogic.getRegionByName(regionName);

                int storeId = Integer.parseInt(request.getParameter(Constants.STORE_ID));

                ItemsInStoreToOrder = region.getStoreItemsDetails(storeId);
            }
            String json = gson.toJson(ItemsInStoreToOrder);
            out.println(json);
            out.flush();
        }
    }
}
