package superDuperMarket;

import java.util.Date;

public class Feedback {

    private String name;
    private Date date;
    private int rate;
    private String feedbackText;

    public Feedback(String name, Date date, int rate) { //TODO: to keep? (no feedbackText)
        this.name = name;
        this.date = date;
        this.rate = rate;
        this.feedbackText = "None";
    }

    public Feedback(String name, Date date, int rate, String feedbackText) {
        this.name = name;
        this.date = date;
        this.rate = rate;
        this.feedbackText = feedbackText;
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
}
