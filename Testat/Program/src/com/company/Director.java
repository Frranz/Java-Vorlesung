package src.com.company;

import java.util.LinkedList;
import java.util.List;

public class Director extends Human{
    private List<Movie> directedMovies;

    public Director(String line) {
        directedMovies = new LinkedList<>();
        String[] lineSplit = line.substring(1,line.length()-1).split("\",\"");
        if(lineSplit.length != 2){
            System.out.println("komischer Datensatz: "+line);
        }else{
            setId(Integer.valueOf(lineSplit[0]));
            setName(lineSplit[1].trim());
        }
    }

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
