package servlets.oneRegionServlets.owner;

import Exceptions.DoubleObjectInCoordinateException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import logic.Owner;
import logic.SDMLogicInterface;
import superDuperMarketRegion.RegionInterface;
import superDuperMarketRegion.Sell;
import utils.ServletUtils;
import utils.SessionUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.List;

@WebServlet(name = "servlets.oneRegionServlets.owner.AddNewStoreServlet", urlPatterns = {"/addStore"})

public class AddNewStoreServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req,resp);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/plain");
        Gson gson = new Gson();

        String itemsToAddToStoreArr = request.getParameter("itemsToAdd");
        Type type = new TypeToken<List<Sell>>() {
        }.getType();

        List<Sell> inputItemsList = gson.fromJson(itemsToAddToStoreArr, type);
        String regionName = request.getParameter("region");
        String storeName = request.getParameter("storeName");
        int ppk = Integer.parseInt(request.getParameter("ppk"));
        int xCoordinate = Integer.parseInt(request.getParameter("x"));
        int yCoordinate = Integer.parseInt(request.getParameter("y"));


        try (PrintWriter out = response.getWriter()) {
            synchronized (getServletContext()) {
                SDMLogicInterface sdmLogic = ServletUtils.getSdmLogic(getServletContext());
                RegionInterface region = sdmLogic.getRegionByName(regionName);
                Owner owner = sdmLogic.getOwnerByName(SessionUtils.getUsername(request));
                region.addNewStoreToRegion(owner, storeName, ppk, xCoordinate, yCoordinate, inputItemsList);
                out.print(" Store added successfully!");     //On success.
                out.flush();
            }
        } catch (DoubleObjectInCoordinateException e) {
            e.printStackTrace();
        }
    }
}
