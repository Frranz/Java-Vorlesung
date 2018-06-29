package src.com.company;

import java.util.List;

public class Reviewer {
    private List<Review> reviews;

    public void addReview(Review review){
        reviews.add(review);
    }

    public List<Review> getReviews() {
        return reviews;
    }
}
