import src.com.company.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Stream;

public class Main {
    private static final String filepath = "C:\\Users\\BUBELF\\Documents\\uni\\Java-Vorlesung\\Testat\\movieproject.db";

    public static void main(String args[]){

        System.out.println("Moviedatenbank wird geladen...");
        MovieBase bs = createMovieBase(filepath);
        System.out.println("Moviedatenbank wurde fertig geladen...");

        if(args.length>0){
            String[][] arr = new String[args.length][2];
            

        }

        int count = 0;
        for(String s: bs.getReviewers().keySet()){
            count += bs.getReviewer(s).getReviews().size();
        }
        System.out.printf("reviews: %d",count);
    }

    private static MovieBase createMovieBase(String path){
        MovieBase mb = new MovieBase();
        Actor actor;
        Movie movie;
        Director director;
        Review review;
        Reviewer reviewer;
        int counter = 0;

        String[] lineSplit;
        try {
            String mode="";
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line = br.readLine();


            while(line!=null){
//                System.out.println(counter++);
                if(line.length()>13&& line.substring(0,12).equals("New_Entity: ")){
                    switch(line){
                        case "New_Entity: \"actor_id\",\"actor_name\"":
                            mode = "actorIdToName";
                            break;
                        case "New_Entity: \"movie_id\",\"movie_title\",\"movie_plot\",\"genre_name\",\"movie_released\",\"movie_imdbVotes\",\"movie_imdbRating\"":
                            mode = "movie";
                            break;
                        case "New_Entity: \"director_id\",\"director_name\"":
                            mode = "directorIdToName";
                            break;
                        case "New_Entity: \"actor_id\",\"movie_id\"":
                            mode = "actorIdToMovieId";
                            break;
                        case "New_Entity: \"director_id\",\"movie_id\"":
                            mode = "directorIdToMovieId";
                            break;
                        case "New_Entity: \"user_name\",\"rating\",\"movie_id\"":
                            mode = "review";
                            break;
                        default:
                            System.out.println("Unknown Entity");
                            throw new FileValidityException("unhandled Entity:" + line);
                    }
                }else{
                    switch(mode){
                        case "actorIdToName":
                            actor = new Actor(line);
                            mb.addActor(actor);
                            break;
                        case "movie":
                            movie = new Movie(line);
                            mb.addMovie(movie);
                            break;
                        case "directorIdToName":
                            director = new Director(line);
                            mb.addDirector(director);
                            break;
                        case "actorIdToMovieId":
                            lineSplit = line.substring(1,line.length()-1).split("\",\"");
                            actor = mb.getActorById(Integer.parseInt(lineSplit[0]));
                            movie = mb.getMovieById(Integer.parseInt(lineSplit[1]));
                            if(movie!=null && actor!=null){
                                actor.addActedMovie(movie);
                                movie.addActors(actor);
                            }else{
                                System.out.println("movie or actor null in line: "+line);
                            }
                            break;
                        case "directorIdToMovieId":
                            lineSplit = line.substring(1,line.length()-1).split("\",\"");
                            director = mb.getDirectorById(Integer.parseInt(lineSplit[0]));
                            movie = mb.getMovieById(Integer.parseInt(lineSplit[1]));
                            if(director!=null && movie!=null){
                                director.addDirectedMovie(movie);
                                movie.setDirector(director);
                            }else{
                                System.out.println("director or movie null in line: "+line);
                            }

                            break;
                        case "review":
                            lineSplit = line.substring(1,line.length()-1).split("\",\"");
                            reviewer = mb.getReviewer(lineSplit[0]);
                            if(reviewer == null){
                                reviewer = new Reviewer(lineSplit[0]);
                            }
                            mb.addReviewer(reviewer);
                            movie = mb.getMovieById(Integer.parseInt(lineSplit[2]));
                            if(movie!=null){
                                review = new Review(Float.parseFloat(lineSplit[1]),movie,reviewer);
                                reviewer.addReview(review);
                                movie.addReview(review);
                                mb.addReview(review);
                            }else{
                                System.out.println("movie null in line: "+line);
                            }
                            break;
                    }
                }


                line = br.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FileValidityException e) {
            e.printStackTrace();
        }

        return mb;
    }


    public static void print(String s){
        System.out.println(s);
    }
}
