package servlets.alerts;

import DtoObjects.OrderDto;
import DtoObjects.StoreOrderDto;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@WebServlet(name = "servlets.alerts.SaveAlertToShowLaterServlet", urlPatterns = {"/saveAlertToShowLater"})

public class SaveAlertToShowLaterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            Gson gson = new Gson();
//            List<Integer> storesIdLst;
            RegionInterface region=null;
            synchronized (getServletContext()) {
                SDMLogicInterface sdmLogic = ServletUtils.getSdmLogic(getServletContext());
                String regionName= SessionUtils.getRegionName(request);
                region=sdmLogic.getRegionByName(regionName);

            }
            String alertType=request.getParameter(Constants.ALERT_TYPE);

            switch (alertType) {

                case "order":
                    saveOrderAlert(request,region);
                break;
                case "feedback":
                    saveFeedbackAlert(request,region);
                    break;
                case "newStore":
                    saveNewStoreAlert(request,region);
                    break;
                case "upload":
                    saveUploadAlert(request);
                    break;

            }
    }

    public void saveFeedbackAlert(HttpServletRequest request,RegionInterface region){
        String msg;
        int storeId= Integer.parseInt(request.getParameter("store"));
        String ownerName=region.getStoreOwnerName(storeId);
        String customerName=SessionUtils.getUsername(request);
        Date feedbackDate=SessionUtils.getOrderInput(request).getDate();
        String rate=request.getParameter("rate");
        String review=request.getParameter("review");

        msg="You recieved new feedback from "+customerName+" in "+convertDateToString(feedbackDate)+".\n"
            +"He gave you "+rate+" stars\n";
        if(review!="")
        {
            msg+="Review:"+review;
        }
        addAlertToManager(msg,ownerName);
    }

    public void saveNewStoreAlert(HttpServletRequest request,RegionInterface region){

        String regionName=region.getRegionName();
        String newStoreOwnerName=SessionUtils.getUsername(request);

        String storeName=request.getParameter("storeName");
        int xCoordinate= Integer.parseInt(request.getParameter("xCoordinate"));
        int yCoordinate= Integer.parseInt(request.getParameter("yCoordinate"));
//        String numOfItemsStoreSell=request.getParameter("numOfItemsToSell");

        int numOfItemsStoreSell=Integer.parseInt(request.getParameter("numOfItemsToSell"));

        int numOfItemsInRegion=region.getNumOfItemsInSystem();

        String msg="The store "+storeName+" opened in your region "+regionName+ " by "+newStoreOwnerName+"\n"
                +"Location: ("+xCoordinate+","+yCoordinate+")\n"
                +"Num of products the store sells from total items in system: "+numOfItemsStoreSell+"/"+numOfItemsInRegion+"\n";

        String regionOwner=region.getRegionOwner().getName();

        addAlertToManager(msg,regionOwner);
    }

    public void saveOrderAlert(HttpServletRequest request,RegionInterface region){
        String msg="";

        int newOrderId= Integer.parseInt(request.getParameter("newOrderId"));
        OrderDto orderDetails=region.getWantedOrderDetails(newOrderId);

        Map<Integer,StoreOrderDto> storeIdToStoreOrderDetails=orderDetails.getStoreIdToStoreOrderDetails();
        for (Integer currStoreId:storeIdToStoreOrderDetails.keySet())
        {
            String currStoreName= region.getStoreName(currStoreId);
            StoreOrderDto currStoreOrderDetails=storeIdToStoreOrderDetails.get(currStoreId);
            msg="new order from "+ orderDetails.getCustomerName()+" in the store "+currStoreName+"\n"
                    +"Order id:"+orderDetails.getOrderId()+"\n"
                    +"Number of items in order: "+String.format("%.2f",currStoreOrderDetails.getItemsTotalAmount() )+" units \n"
                    +"Items cost: "+String.format("%.2f",currStoreOrderDetails.getItemsTotalPrice()) +" Shekels \n"
                    +"Delivery cost: "+String.format("%.2f",currStoreOrderDetails.getDeliveryTotalPrice()) +" Shekels \n";

            String currStoreOwner=region.getStoreOwnerName(currStoreId);
            addAlertToManager(msg,currStoreOwner);
        }

    }

    public void saveUploadAlert(HttpServletRequest request){
        String newRegionName=request.getParameter("newRegionNameData");

        String msg="The region "+newRegionName+" was uploaded to system\n";
        addAlertToManager(msg,Constants.ALL_USERS);
    }



    public void addAlertToManager(String msg,String addresseeUserName)
    {
        // todo: synchtnized ??
        AlertsManager alertsManager=null;

        SingleAlert singleAlert=new SingleAlert(addresseeUserName,msg);

        synchronized (getServletContext()) {
            alertsManager = ServletUtils.getAlertsManager(getServletContext());
            alertsManager.addAlertMsg(singleAlert);
        }

    }

    private String convertDateToString(Date date)
    {
        String pattern = "dd/MM/yyyy";
        DateFormat df = new SimpleDateFormat(pattern);
        return df.format(date);
    }


}
