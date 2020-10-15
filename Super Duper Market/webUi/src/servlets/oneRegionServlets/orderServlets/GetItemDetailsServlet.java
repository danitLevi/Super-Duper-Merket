package servlets.oneRegionServlets.orderServlets;

import DtoObjects.ItemDto;
import DtoObjects.ItemInStoreDto;
import com.google.gson.Gson;
import logic.SDMLogicInterface;
import superDuperMarket.RegionInterface;
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

@WebServlet(name = "servlets.oneRegionServlets.orderServlets.GetItemDetailsServlet", urlPatterns = {"/itemData"})

public class GetItemDetailsServlet extends HttpServlet {

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
            ItemDto itemDetails;
            synchronized (getServletContext()) {
                SDMLogicInterface sdmLogic = ServletUtils.getSdmLogic(getServletContext());
                String regionName= SessionUtils.getRegionName(request);
                RegionInterface region=sdmLogic.getRegionByName(regionName);

                int itemId = Integer.parseInt(request.getParameter(Constants.ITEM_ID));

                itemDetails = region.getItemDetails(itemId);
            }
            String json = gson.toJson(itemDetails);
            out.println(json);
            out.flush();
        }
    }
}
