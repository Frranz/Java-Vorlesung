package src.com.company;

public class Review {
    private Reviewer reviewer;
    private float score;
    private Movie movie;

    public Review(float score,Movie movie,Reviewer reviewer) {
        this.score = score;
        this.movie = movie;
        this.reviewer = reviewer;
    }

    public Reviewer getReviewer() {
        return reviewer;
    }

    public void setReviewer(Reviewer reviewer) {
        this.reviewer = reviewer;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}
