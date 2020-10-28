package servlets.alerts;

import utils.Constants;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlertsManager {

    private List<SingleAlert> alerts;
    private Map<String,Boolean> userNameToHasNewAlert;
    public AlertsManager() {
        this.alerts = new ArrayList<>();
        this.userNameToHasNewAlert =new HashMap<>();
    }

    public synchronized void  addAlertMsg(SingleAlert singleAlert)
    {
        alerts.add(singleAlert);
        String  addresseeUserName=singleAlert.getAddresseeUserName();
        if(addresseeUserName.equals(Constants.ALL_USERS))
        {
            for (String currUserName:userNameToHasNewAlert.keySet())
            {
                userNameToHasNewAlert.put(currUserName,true);
            }
        }
        else
        {
            userNameToHasNewAlert.put(addresseeUserName,true);
        }
    }

    public synchronized AlertAndVersionOutput getUserAlerts(String userName,int userVersion)
    {
//        if()
        List<String> alertsToCurrUser=new ArrayList<>();
        List<SingleAlert> waitingsAlerts=alerts.subList(userVersion,alerts.size());
        for (SingleAlert currSingleAlert : waitingsAlerts)
        {
            if(currSingleAlert.getAddresseeUserName().equals(userName) || currSingleAlert.getAddresseeUserName().equals(Constants.ALL_USERS))
            {
                alertsToCurrUser.add("-"+currSingleAlert.getAlertMsg());
            }
        }
        userNameToHasNewAlert.put(userName,false);
        return new AlertAndVersionOutput(alerts.size(),alertsToCurrUser);
    }

    boolean isUserHasNewAlerts(String userName)
    {
        return (userNameToHasNewAlert.get(userName));
    }
    public int getCurrentVersionForUser()
    {
        return alerts.size();
    }

    public void addUserToAlertsManager(String userName)
    {
        userNameToHasNewAlert.put(userName,false);
    }
}
