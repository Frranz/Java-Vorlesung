package src.com.company;

import java.util.List;
import java.util.Map;

public class MovieBase {
    private int id;
    private Map<Integer,Movie> movies;
    private Map<Integer,Director> directors;
    private Map<Integer,Actor> actors;
    private List<Review> reviews;
    private List<Reviewer> reviewers;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Actor getActorById(int id){
        return actors.get(id);
    }





    public Map<Integer,Movie> getMovies() {
        return movies;
    }

    public Map<Integer, Director> getDirectors() {
        return directors;
    }

    public Map<Integer,Actor> getActors() {
        return actors;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public List<Reviewer> getReviewers() {
        return reviewers;
    }

    public void addMovie(Movie newMovie){
        movies.put(newMovie.getId(),newMovie);
    }

    public void addDirector(Director newDirector){
        directors.put(newDirector.getId(),newDirector);
    }

    public void addActor(Actor newActor){
        actors.put(newActor.getId(),newActor);
    }

    public void addReview(Review newReview){
        reviews.add(newReview);
    }

    public void addReviewer(Reviewer newReviewer){
        reviewers.add(newReviewer);
    }

    public Movie getMovieById(int i) {
        return movies.get(i);
    }

    public Director getDirectorById(int i) {
        return directors.get(i);
    }
}
