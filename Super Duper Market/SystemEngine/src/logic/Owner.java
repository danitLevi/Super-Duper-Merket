package logic;

import DtoObjects.FeedbackDto;
import DtoObjects.ItemInStoreDto;
import superDuperMarket.Feedback;
import superDuperMarket.Item;

import java.util.*;

public class Owner extends User {

    private Set<Feedback> feedbacks;

    public Owner(String name) {
        super(name);
        this.feedbacks = new HashSet<>();
    }

    public Set<Feedback> getFeedbacks() {
        return feedbacks;
    }

    public List<FeedbackDto> getFeedbacksDetails()
    {
        List<FeedbackDto> feedbacksDetails=new ArrayList<>();
        for (Feedback currFeedback : feedbacks) {
            feedbacksDetails.add(new FeedbackDto(currFeedback.getName(),
                                                    currFeedback.getDate(),
                                                    currFeedback.getRate(),
                                                    currFeedback.getFeedbackText()));
        }
        return feedbacksDetails;
    }

    public void addFeedback(String customer, Date date, Integer rate, String review, String storeName){
        Feedback newFeedback = new Feedback(customer,date,rate,review,storeName);
        this.feedbacks.add(newFeedback);
    }
}
