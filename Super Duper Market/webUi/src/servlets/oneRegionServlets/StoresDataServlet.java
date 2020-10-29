package servlets.oneRegionServlets;

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
import java.util.List;

@WebServlet(name = "servlets.oneRegionServlets.StoresDataServlet", urlPatterns = {"/storesInRegion"})

public class StoresDataServlet extends HttpServlet {

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
            List<StoreDto> storesDetailsList=null;
            SDMLogicInterface sdmLogic =null;
            String regionName=null;

            synchronized (getServletContext()) {
                sdmLogic = ServletUtils.getSdmLogic(getServletContext());
                regionName= SessionUtils.getRegionName(request);
            }
            synchronized (sdmLogic) {
                RegionInterface region=sdmLogic.getRegionByName(regionName);
                storesDetailsList = region.getStoresDetails();
            }

            String json = gson.toJson(storesDetailsList);
            out.println(json);
            out.flush();
        }

    }
}
