package com.company;

import java.util.List;

public class Actor extends Human {
    private List<Movie> actedMovies;

    public List<Movie> getActedMovies() {
        return actedMovies;
    }

    public void addActedMovie(Movie movie){
        actedMovies.add(movie);
    }

    public boolean playsInMovie(Movie moviePar){
        for(Movie movEl: actedMovies){
            if(movEl == moviePar){
                return true;
            }
        }
        return false;
    }
}
