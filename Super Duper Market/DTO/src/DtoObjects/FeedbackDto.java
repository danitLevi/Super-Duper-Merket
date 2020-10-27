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
    private String storeName;


    public FeedbackDto(String name, Date date, int rate, String feedbackText, String storeName) {
        this.name = name;

        String pattern = "dd/MM/yyyy";
        DateFormat df = new SimpleDateFormat(pattern);
        this.strDate = df.format(date);

        this.rate = rate;
        this.feedbackText = feedbackText;
        this.storeName = storeName;
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

    public String getStrDate() {
        return strDate;
    }

    public String getStoreName() {
        return storeName;
    }
}
