package src.com.company;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MovieBase {
    private int id;
    private HashMap<Integer,Movie> movies;
    private HashMap<Integer,Director> directors;
    private HashMap<Integer,Actor> actors;
    private List<Review> reviews;
    private HashMap<String,Reviewer> reviewers;

    public MovieBase() {
        movies = new HashMap<>();
        actors = new HashMap<>();
        directors = new HashMap<>();
        reviewers = new HashMap<>();
        reviews = new LinkedList<>();

        System.out.println("moviebase wurde initialisiert");
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Actor getActorById(int id){
        return actors.get(id);
    }





    public Map<Integer,Movie> getMoviesMap() {
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

    public HashMap<String, Reviewer> getReviewers() {
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
        reviewers.put(newReviewer.getUserName(),newReviewer);
    }

    public Movie getMovieById(int i) {
        return movies.get(i);
    }

    public Director getDirectorById(int i) {
        return directors.get(i);
    }

    public Reviewer getReviewer(String userName){
        return reviewers.get(userName);
    }

    public List<Movie> getMovies() {
        List<Movie> someMovies = new LinkedList<>();
        for(int key: movies.keySet()){
            someMovies.add(movies.get(key));
        }
        return someMovies;
    }

    public static List<Movie> filterMoviesByGenre(List<Movie> movies,String genre){
        for(Movie m:movies){
            if(!m.getGenre().contains(genre)){
                movies.remove(m);
            }
        }
        return movies;
    }


}
