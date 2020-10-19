package servlets.alerts;

import utils.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlertsManager {

    private List<SingleAlert> alerts;

    public AlertsManager() {
        this.alerts = new ArrayList<>();
    }

    public synchronized void  addAlertMsg(SingleAlert singleAlert)
    {
        alerts.add(singleAlert);
    }

    public synchronized AlertAndVersionOutput getUserAlerts(String userName,int userVersion)
    {
        List<String> alertsToCurrUser=new ArrayList<>();
        List<SingleAlert> waitingsAlerts=alerts.subList(userVersion,alerts.size());
        for (SingleAlert currSingleAlert : waitingsAlerts)
        {
            if(currSingleAlert.getAddresseeUserName().equals(userName) || currSingleAlert.getAddresseeUserName().equals(Constants.ALL_USERS))
            {
                alertsToCurrUser.add(currSingleAlert.getAlertMsg());
            }
        }
        return new AlertAndVersionOutput(alerts.size(),alertsToCurrUser);
    }

    public int getCurrentVersion()
    {
        return alerts.size();
    }
}
