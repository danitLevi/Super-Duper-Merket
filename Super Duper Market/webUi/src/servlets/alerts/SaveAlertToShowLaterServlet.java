package servlets.alerts;

import DtoObjects.ItemInSystemDto;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import logic.SDMLogicInterface;
import superDuperMarket.RegionInterface;
import superDuperMarket.Sell;
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
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
                break;
                case "feedback":saveFeedbackAlert(request,region);
                    break;
//                case "newStore":
//                case "upload":

            }

//            String storesListJson=request.getParameter(Constants.STORES);
//
//
//            Type type = new TypeToken<List<String>>() {}.getType();
//            if(!storesListJson.equals("null"))
//            {
//                storesIdLst=gson.fromJson(storesListJson,type);
//            }





    }

    public void saveFeedbackAlert(HttpServletRequest request,RegionInterface region){
        String msg;
        int storeId= Integer.parseInt(request.getParameter("store"));
        String ownerName=region.getStoreOwnerName(storeId);
        String customerName=SessionUtils.getUsername(request);
//        Date feedbackDate=SessionUtils.getOrderInput(request).getDate();
        String rate=request.getParameter("rate");
        String review=request.getParameter("review");

        msg="You recieved new feedback from "+customerName+" in "
                //todo: change
//                +convertDateToString(feedbackDate)+".\n"
                +"\n"
                    +"He gave you "+rate+" stars\n";
        if(review!="")
        {
            msg+="Review:"+review;
        }
        addAlertToManager(msg,ownerName);
    }

    public void saveNewStoreAlert(){

    }

    public void saveOrderAlert(){

    }

    public void saveUploadAlert(){

    }

    public String convertDateToString(Date date)
    {
        String pattern = "dd/MM/yyyy";
        DateFormat df = new SimpleDateFormat(pattern);
        return df.format(date);
    }

    public void addAlertToManager(String msg,String userName)
    {
        // todo: synchtnized ??
        AlertsManager alertsManager=ServletUtils.getAlertsManager(getServletContext());
        SingleAlert singleAlert=new SingleAlert(userName,msg);
        alertsManager.addAlertMsg(singleAlert);
    }


}
