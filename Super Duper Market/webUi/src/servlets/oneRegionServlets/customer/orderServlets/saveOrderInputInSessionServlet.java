package servlets.oneRegionServlets.customer.orderServlets;

import DtoObjects.ItemToOrderInputDto;
import DtoObjects.OrderInputToSaveInSessionDto;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@WebServlet(name = "servlets.oneRegionServlets.customer.orderServlets.saveOrderInputInSessionServlet", urlPatterns = {"/saveOrderInputInSession"})

public class saveOrderInputInSessionServlet extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req,resp);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RegionInterface region;

        Map<Integer, Map<Integer, Double>> orderMinimalPriceBag=new HashMap<>();
        Gson gson = new Gson();

        //get data
        Date date= null;
        String stringDate=request.getParameter(Constants.DATE);

        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(stringDate);


        int xCoordinate= Integer.parseInt(request.getParameter(Constants.X_COORDINATE));
        int yCoordinate= Integer.parseInt(request.getParameter(Constants.Y_COORDINATE));
        int storeId= Integer.parseInt(request.getParameter(Constants.STORE_ID));
        String itemsToOrderArr=request.getParameter(Constants.ITEMS);

        Type type = new TypeToken<List<ItemToOrderInputDto>>() {}.getType();
        List<ItemToOrderInputDto> inputItemsList = gson.fromJson(itemsToOrderArr, type);

        Map<Integer,Double> itemsToOrderMap=convertListToMap(inputItemsList);

        //save in session
        synchronized (getServletContext()) {
            SDMLogicInterface sdmLogic = ServletUtils.getSdmLogic(getServletContext());
            String regionName= SessionUtils.getRegionName(request);
            region=sdmLogic.getRegionByName(regionName);
        }

        boolean isDynamic= Boolean.parseBoolean(request.getParameter(Constants.IS_DYNAMIC));
        if(isDynamic)
        {
            orderMinimalPriceBag=region.getMinimalItemsBag(itemsToOrderMap);
        }
        else
        {
            orderMinimalPriceBag.put(storeId,itemsToOrderMap);
        }

        OrderInputToSaveInSessionDto orderInput=new OrderInputToSaveInSessionDto(orderMinimalPriceBag,date,isDynamic,storeId,xCoordinate,yCoordinate);

        SessionUtils.setOrderInput(request,orderInput);

        } catch (ParseException e) {
            response.sendError(406 );
        }
    }

    private Map<Integer,Double> convertListToMap(List<ItemToOrderInputDto> itemsList)
    {
        Map<Integer,Double> itemsToOrderMap=new HashMap<>();
        for (ItemToOrderInputDto currItem:itemsList)
        {
            itemsToOrderMap.put(currItem.getItemId(),currItem.getQuantity());
        }

        return itemsToOrderMap;
    }
}


