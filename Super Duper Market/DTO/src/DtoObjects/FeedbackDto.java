package DtoObjects;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FeedbackDto {

    private String name;
    private String strDate;
    private int rate;
    private String feedbackText;


    public FeedbackDto(String name, Date date, int rate, String feedbackText) {
        this.name = name;

        String pattern = "dd/MM/yyyy";
        DateFormat df = new SimpleDateFormat(pattern);
        this.strDate = df.format(date);

        this.rate = rate;
        this.feedbackText = feedbackText;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return strDate;
    }

    public int getRate() {
        return rate;
    }

    public String getFeedbackText() {
        return feedbackText;
    }
}
