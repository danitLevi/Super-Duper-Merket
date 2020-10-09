package logic;

import superDuperMarket.Feedback;

import java.util.HashSet;
import java.util.Set;

public class Owner extends User {

    private Set<Feedback> feedbacks;

    public Owner(String name) {
        super(name);
        this.feedbacks = new HashSet<>();
    }

    public Set<Feedback> getFeedbacks() {
        return feedbacks;
    }


}
