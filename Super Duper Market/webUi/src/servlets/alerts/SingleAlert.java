package servlets.alerts;

public class SingleAlert {

    private final String addresseeUserName;
    private final String alertMsg;

    public SingleAlert(String addresseeUserName, String alertMsg) {
        this.addresseeUserName = addresseeUserName;
        this.alertMsg = alertMsg;
    }

    public String getAddresseeUserName() {
        return addresseeUserName;
    }

    public String getAlertMsg() {
        return alertMsg;
    }
}
