import src.com.company.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

public class Main {
    private static final String filepath = "C:\\Users\\BUBELF\\Documents\\uni\\Java-Vorlesung\\Testat\\movieproject.db";
    private static final String userRatingsPath = "C:\\Users\\BUBELF\\Documents\\uni\\Java-Vorlesung\\Testat\\userRatings.txt";

    public static void main(String args[]){
        String[][] argsArr = new String[args.length][];
        System.out.println("Moviedatenbank wird geladen...");
        MovieBase bs = createMovieBase(filepath);
        System.out.println("Moviedatenbank wurde fertig geladen...");

        if(args.length>0){
            int counter = 0;
            for(String s:args){
                s = s.substring(2);
                argsArr[counter] = s.split("=");
                counter++;
            }

            List<Movie> movies = bs.getSuggestedMovies(argsArr);
            MovieBase.printMoviesList(movies);
        }else{
            interactiveMode(bs);
        }

        int count = 0;
        for(String s: bs.getReviewers().keySet()){
            count += bs.getReviewer(s).getReviews().size();
        }
        System.out.printf("reviews: %d",count);
    }

    private static void interactiveMode(MovieBase bs) {
        Scanner scanner = new Scanner(System.in);
        boolean runLoop = true;
        UserRatings userRatings = new UserRatings(userRatingsPath,bs);
        ArrayList<Movie> movieList;

        while(runLoop){
            System.out.println("Willkommen in der Filmdatenbank");
            System.out.println("================================\n");
            System.out.println("Bitte wählen sie eine Option aus:");
            System.out.println("[0] Filme bewerten");
            System.out.println("[1] Empfehlungen ansehen");
            System.out.println("[2] Film suchen");
            try {
                char c = (char) System.in.read();

                switch(c){
                    case '0':
                        userRatings.newInteractive(bs);
                        break;
                    case '1':
                        HashMap<Movie,Integer> movieMap = new HashMap<>();
                        String[] movies = (String[]) userRatings.getReviews().stream().map(review -> review.getMovie().getTitle()).toArray(); // converts list to String array
                        bs.getSimilarMovies(movieMap,movies);
                        break;
                    case '2':
                        System.out.println("Bitte geben sie den zu suchenden Namen ein:");
                        String input = scanner.next();
                        movieList = (ArrayList<Movie>) bs.getMoviesWithSimilarTitle(input);
                        outputListStepByStep(movieList,10);
                        break;
                    default:
                        System.out.println("ungültige Eingabe. Versuchen Sie es erneut.");
                }
            } catch (IOException e) {
                System.out.println("Zeichen konnte nicht eingelesen werden");
                e.printStackTrace();
            }
        }
    }

    private static MovieBase createMovieBase(String path){
        MovieBase mb = new MovieBase();
        String genre;
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
                            movie = mb.getMovieById(Integer.parseInt(line.substring(1,line.length()-1).split("\",\"")[0]));
                            if(movie==null){  //wenn Film mit id bereits existiert
                                movie = new Movie(line);
                                mb.addMovie(movie);
                            }else{
                                genre = line.substring(1,line.length()-1).split("\",\"")[3];
                                movie.addGenre(genre);
                            }
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
                                movie.addActorName(actor.getName());
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
        } catch (FileValidityException | IOException e) {
            e.printStackTrace();
        }

        return mb;
    }

    public static void print(String s){
        System.out.println(s);
    }

    public static void outputListStepByStep(ArrayList<Movie> list, int stepSize ){
        Scanner sc = new Scanner(System.in);
        int sizeLeft = list.size();
        int counter = 0;
        while(sizeLeft>0){
            counter = stepSize;
            while(sizeLeft>0 && counter>0){
                System.out.println(list.get(list.size()-sizeLeft));
                counter--;
                sizeLeft--;
            }
            if(sizeLeft>0){
                System.out.println("Drücken Sie Enter für weiter Ergebnisse");
                sc.nextLine();
            }
        }
        System.out.println("Drücken sie Enter zum beenden");
        sc.nextLine();
    }
}
