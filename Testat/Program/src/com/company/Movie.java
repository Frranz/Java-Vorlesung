package src.com.company;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class Movie{
    private int id;
    private String title;
    private String plot;
    private List<String> genre;
    private List<Actor> actors;
    private List<String> actorNames;

    private Director director;
    private String release;
    private List<Review> reviews;
    private int reviewAmount;
    private float averageReviewScore;

    public Movie(String line) {
        actors = new LinkedList<>();
        actorNames = new LinkedList<>();
        reviews = new LinkedList<>();
        genre = new LinkedList<>();

        //splits database entry to relevant contents
        String[] lineSplit = line.substring(1,line.length()-1).split("\",\"");
        id = Integer.valueOf(lineSplit[0]);
        title = lineSplit[1];
        plot = lineSplit[2];
        genre.add(lineSplit[3]);

        //some entries dont exist all the time => checking is for those
        if(lineSplit.length > 4 && lineSplit[4]!=null){
            release = lineSplit[4];
        }
        if(lineSplit.length > 5 && !lineSplit[5].equals("")){
            reviewAmount = Integer.valueOf(lineSplit[5]);
        }
        if(lineSplit.length > 6 && lineSplit[6]!=null){
            averageReviewScore = Float.valueOf(lineSplit[6]);
        }
    }

    public String toString(){
        String title = (this.title==null)?"":this.title;
        String genre = (this.genre==null)?"":this.genre.toString();
        String director = (this.director==null)?"":this.director.getName();

        return "Film "+id+" rating: "+averageReviewScore+" "+title+" Genre: "+genre+" director: "+director;
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

    public List<String> getActorNames() {
        return actorNames;
    }

    public void addActorName(String name){
        actorNames.add(name);
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

    public List<String> getGenre() {
        return genre;
    }

    public void addGenre(String genre) {
        this.genre.add(genre);
    }

    public Director getDirector() {
        return director;
    }

    public void setDirector(Director director) {
        this.director = director;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
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

class MovieComparator implements Comparator<Movie>{

    @Override
    public int compare(Movie o1, Movie o2) {
        return (int) ((int) o1.getAverageReviewScore()*10 - o2.getAverageReviewScore()*10);
    }
}