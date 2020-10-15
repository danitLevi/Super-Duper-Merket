package servlets.oneRegionServlets.orderServlets;

import DtoObjects.FeedbackDto;
import com.google.gson.Gson;
import logic.SDMLogicInterface;
import superDuperMarket.StoreOrder;
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

@WebServlet(name = "servlets.oneRegionServlets.orderServlets.saveFeedbak", urlPatterns = {"/storesInOrder"})

public class getOrderStores extends HttpServlet {

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
            List<StoreOrder> storesInOrderList=null;

            synchronized (getServletContext()) {
                SDMLogicInterface sdmLogic = ServletUtils.getSdmLogic(getServletContext());
                String regionName= SessionUtils.getRegionName(request);
                //Integer orderId= SessionUtils.getOrderId(request); //TODO
                Integer orderId= 1;//TODO: delete alona
                storesInOrderList = sdmLogic.getOrderStores(orderId, regionName);
            }
            String json = gson.toJson(storesInOrderList);
            out.println(json);
            out.flush();
        }
    }
}
