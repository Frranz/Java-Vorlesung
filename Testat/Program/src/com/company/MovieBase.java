package com.company;

import java.util.List;

public class MovieBase {
    private int id;
    private List<Movie> movies;
    private List<Director> directors;
    private List<Actor> actors;
    private List<Review> reviews;
    private List<Reviewer> reviewers;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public List<Director> getDirectors() {
        return directors;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public List<Reviewer> getReviewers() {
        return reviewers;
    }

    public void addMovie(Movie newMovie){
        movies.add(newMovie);
    }

    public void addDirector(Director newDirector){
        directors.add(newDirector);
    }

    public void addActor(Actor newActor){
        actors.add(newActor);
    }

    public void addReview(Review newReview){
        reviews.add(newReview);
    }

    public void addReviewer(Reviewer newReviewer){
        reviewers.add(newReviewer);
    }
}
