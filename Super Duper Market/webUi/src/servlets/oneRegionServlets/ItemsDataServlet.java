package servlets.oneRegionServlets;

import DtoObjects.ItemInSystemDto;
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
import java.util.List;

@WebServlet(name = "servlets.oneRegionServlets.ItemsDataServlet", urlPatterns = {"/items"})

public class ItemsDataServlet extends HttpServlet {

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
            List<ItemInSystemDto> itemsDetailsList=null;
            synchronized (getServletContext()) {
                SDMLogicInterface sdmLogic = ServletUtils.getSdmLogic(getServletContext());
                String regionName= SessionUtils.getRegionName(request);
                RegionInterface region=sdmLogic.getRegionByName(regionName);
                itemsDetailsList = region.getItemsDetails();
            }
            String json = gson.toJson(itemsDetailsList);
            out.println(json);
            out.flush();
        }

    }
}
