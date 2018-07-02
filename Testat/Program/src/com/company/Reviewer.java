package src.com.company;

import java.util.LinkedList;
import java.util.List;

public class Reviewer {
    private List<Review> reviews;
    private String userName;
    public Reviewer() {
        reviews = new LinkedList<>();
    }

    public Reviewer(String s) {
        reviews = new LinkedList<>();
        this.userName = s;
    }

    public void addReview(Review review){
        reviews.add(review);
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public String getUserName() {
        return this.userName;
    }
}
