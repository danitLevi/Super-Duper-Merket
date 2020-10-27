package servlets.alerts;

import java.util.List;

public class AlertAndVersionOutput {

    private final int version;
    private final List<String> alertMsgs;

    public AlertAndVersionOutput(int version, List<String> alertMsgs) {
        this.version = version;
        this.alertMsgs = alertMsgs;
    }

    public int getVersion() {
        return version;
    }

    public List<String> getAlertMsgs() {
        return alertMsgs;
    }
}
