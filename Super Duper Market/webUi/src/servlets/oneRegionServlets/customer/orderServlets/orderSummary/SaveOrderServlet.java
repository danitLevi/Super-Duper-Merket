package servlets.oneRegionServlets.customer.orderServlets.orderSummary;

import DtoObjects.OrderInputToSaveInSessionDto;
import logic.Customer;
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

@WebServlet(name = "servlets.oneRegionServlets.customer.orderServlets.orderSummary.SaveOrderServlet", urlPatterns = {"/saveOrder"})

public class SaveOrderServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req,resp);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        SDMLogicInterface sdmLogic = null;
        RegionInterface region = null;

        synchronized (getServletContext()) {
            sdmLogic = ServletUtils.getSdmLogic(getServletContext());
            String regionName = SessionUtils.getRegionName(request);
            region = sdmLogic.getRegionByName(regionName);
        }

        OrderInputToSaveInSessionDto orderInput = SessionUtils.getOrderInput(request);

        //only customer can make order
        String userName = SessionUtils.getUsername(request);
        Customer currCustomer = sdmLogic.getCustomer(userName);

        try (PrintWriter out = response.getWriter()) {

            //todo synchronized it also ??
             int newOrderId=region.saveOrderAndReturnId(orderInput, currCustomer);

            out.print(newOrderId);
            out.flush();
            }
        }
    }


