package src.com.company;

import java.util.Date;
import java.util.List;

public class Movie {
    private int id;
    private String title;
    private String plot;
    private String genre;
    private List<Actor> actors;
    private Director director;
    private Date release;
    private List<Review> reviews;
    private int reviewAmount;
    private float averageReviewScore;

    public Movie(String line) {
        String[] lineSplit = line.split("[^\",].*?(?=\")");
        if(lineSplit.length!=7){
            System.out.println("komischer Datensatz: "+line);
        }else{
            id = Integer.valueOf(lineSplit[0]);
            title = lineSplit[1];
            plot = lineSplit[2];
            genre = lineSplit[3];
            release = new Date(lineSplit[4]);
            reviewAmount = Integer.valueOf(lineSplit[5]);
            averageReviewScore = Float.valueOf(lineSplit[6]);
        }
    }

    public void addActors(Actor newActor){
        actors.add(newActor);
    }

    public void addReview(Review newReview){
        reviews.add(newReview);
    }

    public List<Actor> getActors() {
        return actors;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Director getDirector() {
        return director;
    }

    public void setDirector(Director director) {
        this.director = director;
    }

    public Date getRelease() {
        return release;
    }

    public void setRelease(Date release) {
        this.release = release;
    }

    public int getReviewAmount() {
        return reviewAmount;
    }

    public void setReviewAmount(int reviewAmount) {
        this.reviewAmount = reviewAmount;
    }

    public float getAverageReviewScore() {
        return averageReviewScore;
    }

    public void setAverageReviewScore(float averageReviewScore) {
        this.averageReviewScore = averageReviewScore;
    }
}
