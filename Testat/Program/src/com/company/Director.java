package src.com.company;

import java.util.List;

public class Director extends Human{
    List<Movie> directedMovies;

    public Director(String line) {
        String[] lineSplit = line.split("[^\",].*?(?=\")");
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
