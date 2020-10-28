package servlets.oneRegionServlets.customer.orderServlets.fillOrder;

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

@WebServlet(name = "servlets.oneRegionServlets.customer.fillOrder.ValidateOrderLocationServlet", urlPatterns = {"/validateOrderLocation"})

public class ValidateOrderLocationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req,resp);

    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/plain");
        Gson gson=new Gson();
        try (PrintWriter out = response.getWriter())
        {

            RegionInterface region=null;
            synchronized (getServletContext()) {
                SDMLogicInterface sdmLogic = ServletUtils.getSdmLogic(getServletContext());
                String regionName= SessionUtils.getRegionName(request);
                region=sdmLogic.getRegionByName(regionName);

            }

            int xCoordinate = Integer.parseInt(request.getParameter("xCoordinate"));
            int yCoordinate = Integer.parseInt(request.getParameter("yCoordinate"));

            String isValid= String.valueOf(region.isValidOrderLocation(xCoordinate,yCoordinate));

            out.print(isValid);
            out.flush();
        }
    }
}
