package servlets.oneRegionServlets.customer.orderServlets;

import utils.Constants;
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

@WebServlet(name = "servlets.oneRegionServlets.customer.orderServlets.getDeliveryCost", urlPatterns = {"/getDeliveryCost"})

public class getDeliveryCost extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req,resp);

    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/plain");
        try (PrintWriter out = response.getWriter())
        {

            RegionInterface region=null;
            synchronized (getServletContext()) {
                SDMLogicInterface sdmLogic = ServletUtils.getSdmLogic(getServletContext());
                String regionName= SessionUtils.getRegionName(request);
                region=sdmLogic.getRegionByName(regionName);

            }

            int storeId = Integer.parseInt(request.getParameter(Constants.STORE_ID));
            int xCoordinate = Integer.parseInt(request.getParameter("xCoordinate"));
            int yCoordinate = Integer.parseInt(request.getParameter("yCoordinate"));

            double deliveryCost=region.getDeliveryPriceFromCoordinates(storeId,xCoordinate,yCoordinate);
            out.println(deliveryCost);
            out.flush();
        }
    }
}
