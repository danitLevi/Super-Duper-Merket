package servlets.oneRegionServlets;

import com.google.gson.Gson;
import logic.SDMLogicInterface;
import superDuperMarketRegion.RegionInterface;
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

@WebServlet(name = "servlets.oneRegionServlets.getStoreNameByStoreIdServlet", urlPatterns = {"/getStoreName"})

public class getStoreNameByStoreIdServlet  extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req,resp);
    }
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RegionInterface region;
        response.setContentType("text/plain");

        try (PrintWriter out = response.getWriter())
        {
            int storeId= Integer.parseInt(request.getParameter(Constants.STORE_ID));
            Gson gson = new Gson();
            SDMLogicInterface sdmLogic =null;
            String regionName=null;
            synchronized (getServletContext()) {
                sdmLogic = ServletUtils.getSdmLogic(getServletContext());
                regionName= SessionUtils.getRegionName(request);
            }
            synchronized (sdmLogic) {
                region=sdmLogic.getRegionByName(regionName);
            }

           String storeName=region.getStoreName(storeId);
            out.println(storeName);
            out.flush();
        }
    }
}
