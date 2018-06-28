package com.company;

import java.util.List;

public class Director {
    List<Movie> directedMovies;

    public List<Movie> getDirectedMovies() {
        return directedMovies;
    }

    public void addDirectedMovie(Movie newMovie){
        directedMovies.add(newMovie);
    }

    public boolean directsInMovie(Movie moviePar){
        for(Movie movEl: directedMovies){
            if(movEl == moviePar){
                return true;
            }
        }
        return false;
    }
}
