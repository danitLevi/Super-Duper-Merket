package servlets.oneRegionServlets.customer.orderServlets.sale;

import DtoObjects.OfferDto;
import DtoObjects.OrderInputToSaveInSessionDto;
import com.google.gson.Gson;
import utils.Constants;
import utils.SessionUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "servlets.oneRegionServlets.customer.orderServlets.sale.SaveUsedSaleServlet", urlPatterns = {"/saveUsedSale"})

public class SaveUsedSaleServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req,resp);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Integer newUsedAmount;
        Gson gson=new Gson();
        OrderInputToSaveInSessionDto orderInput= SessionUtils.getOrderInput(request);
        Map<Integer, Map<OfferDto, Integer>> storeIdToUsedOfferDtoToUsedAmount =orderInput.getStoreIdToUsedOfferDtoToUsedAmount();
        String offerToSave = request.getParameter("offerToSave");
        int storeId= Integer.parseInt(request.getParameter(Constants.STORE_ID));

        OfferDto offer =gson.fromJson(offerToSave,OfferDto.class);

        if(!storeIdToUsedOfferDtoToUsedAmount.containsKey(storeId)) {
            storeIdToUsedOfferDtoToUsedAmount.put(storeId,new HashMap<>());
        }

        Map<OfferDto,Integer> currStoreUsedSales=storeIdToUsedOfferDtoToUsedAmount.get(storeId);
        if(currStoreUsedSales.containsKey(offer))
        {
            newUsedAmount=currStoreUsedSales.get(offer)+1;
        }
        else
        {
            newUsedAmount=1;
        }
        currStoreUsedSales.put (offer,newUsedAmount);


    }


}
