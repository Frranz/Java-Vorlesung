package src.com.company;

import java.util.List;

public class Actor extends Human {
    private List<Movie> actedMovies;

    public Actor(String line) {
        String[] lineSplit = line.split("[^\",].*?(?=\")");
        setId(Integer.valueOf(lineSplit[0].trim()));
        setName(lineSplit[1]);
    }

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
