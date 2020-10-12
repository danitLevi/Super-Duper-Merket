package DtoObjects;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FeedbackDto {

    private String name;
    private Date date;
    private int rate;
    private String feedbackText;


    public FeedbackDto(String name, Date date, int rate, String feedbackText) {
        this.name = name;
        this.date = date;
        this.rate = rate;
        this.feedbackText = feedbackText;
    }

    public String getName() {
        return name;
    }

    public Date getDate() {
        return date;
    }

    public int getRate() {
        return rate;
    }

    public String getFeedbackText() {
        return feedbackText;
    }
}
