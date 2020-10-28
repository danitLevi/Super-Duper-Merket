package superDuperMarketRegion;

import java.util.Date;

public class Feedback {

    private String name;
    private Date date;
    private int rate;
    private String feedbackText;
    private String storeName;

//    public Feedback(String name, Date date, int rate) { //TODO: to keep? (no feedbackText)
//        this.name = name;
//        this.date = date;
//        this.rate = rate;
//        this.feedbackText = "None";
//    }

    public Feedback(String name, Date date, int rate, String feedbackText, String storeName) {
        this.name = name;
        this.date = date;
        this.rate = rate;
        this.storeName = storeName;
        if(feedbackText==null) {
            this.feedbackText = "None";
        }
        else {
            this.feedbackText = feedbackText;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getFeedbackText() {
        return feedbackText;
    }

    public void setFeedbackText(String feedbackText) {
        this.feedbackText = feedbackText;
    }

    public void setStoreName(String storeName) { this.storeName = storeName; }

    public String getStoreName() { return storeName; }
}
