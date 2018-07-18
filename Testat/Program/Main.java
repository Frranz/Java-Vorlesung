import src.com.company.*;

import java.io.*;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    private static final String filepath = "C:\\Users\\BUBELF\\Documents\\uni\\Java-Vorlesung\\Testat\\movieproject.db";
    private static final String userRatingsPath = "C:\\Users\\BUBELF\\Documents\\uni\\Java-Vorlesung\\Testat\\userRatings.txt";
    private static final String testPath = "C:\\Users\\BUBELF\\Documents\\uni\\Java-Vorlesung\\Testat\\test.txt";

    public static void main(String args[]){
        String[][] argsArr = new String[args.length][];
        System.out.println("Moviedatenbank wird geladen...");
        MovieBase bs = new MovieBase(filepath);
  //      MovieBase bs = createMovieBase(filepath);
        System.out.println("Moviedatenbank wurde fertig geladen...");



        if(args.length>0) {
            if (args[0].equals("--test=true")) {
                System.out.println("Testmodus wurde gestartet");
                test(bs);
            } else {

                //"normaler" statischer Modus
                int counter = 0;
                for (String s : args) {
                    s = s.substring(2);
                    argsArr[counter] = s.split("=");
                    counter++;
                }

                List<Movie> movies = bs.getSuggestedMovies(argsArr);
                MovieBase.printMoviesList(movies);
            }
        }else{
            interactiveMode(bs);
        }
    }

    private static void interactiveMode(MovieBase bs) {
        Scanner scanner = new Scanner(System.in);
        boolean runLoop = true;
        UserRatings userRatings = new UserRatings(userRatingsPath,bs);
        List<Movie> movies;
        ArrayList<Movie> movieList;
        int maxOutput;

        while(runLoop){
            System.out.println("Willkommen in der Filmdatenbank");
            System.out.println("================================\n");
            System.out.println("Bitte wählen sie eine Option aus:");
            System.out.println("[0] Filme bewerten");
            System.out.println("[1] Empfehlungen ansehen");
            System.out.println("[2] Film suchen");
            System.out.println("[3] Bewertungen anzeigen");
            System.out.println("[4] Interaktiven Modus beenden");

            String c = scanner.nextLine();

            switch(c){
                case "0":
                    //rate movie
                    userRatings.newReviewInteractive(bs);
                    break;
                case "1":
                    //see recommendations
                    System.out.println("Wie viele Filme sollen maximal ausgegeben werden?");
                    maxOutput = scanner.nextInt();
                    HashMap<Movie, Integer> movieMap = new HashMap<>();

                    // converts list to String array
                    String[] moviesArr = userRatings.getReviews().stream().map(review -> review.getMovie().getTitle()).toArray(String[]::new);
                    bs.getSimilarMovies(movieMap, moviesArr);
                    movies = movieMap.entrySet().stream()
                            .sorted(Comparator.comparing(Map.Entry::getValue, Comparator.reverseOrder()))
                            .map(Map.Entry::getKey)
                            .collect(Collectors.toList());
                    if(movies.size()>maxOutput){
                        movies = movies.subList(0,maxOutput);
                    }
                    movieList =  new ArrayList<>(movies);

                    outputListStepByStep(movieList,10);
                    break;
                case "2":
                    //search by name
                    System.out.println("Bitte geben sie den zu suchenden Namen ein:");
                    String input = scanner.nextLine();
                    movieList = (ArrayList<Movie>) bs.getMoviesWithSimilarTitle(input);
                    outputListStepByStep(movieList,10);
                    break;
                case "3":
                    //see user reviews
                    for(Review r:userRatings.getReviews()){
                        System.out.println(r);
                    }
                    System.out.println("Ausgabe beendet. Enter drücken zum fortfahren");
                    scanner.nextLine();
                    break;
                case "4":
                    //exit interactive menu
                    runLoop = false;
                    break;
                default:
                    System.out.println("ungültige Eingabe. Versuchen Sie es erneut.");
            }
        }
    }

    private static void outputListStepByStep(ArrayList<Movie> list, int stepSize ){
        Scanner sc = new Scanner(System.in);
        int sizeLeft = list.size();
        int counter = 0;
        while(sizeLeft>0){
            counter = stepSize;
            while(sizeLeft>0 && counter>0){
                //list.size() - sizeLeft = int for next output => changes from beginning to end of list
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

    private static void test(MovieBase bs){
        try {
            Writer output = new BufferedWriter(new FileWriter(testPath));
            List<Movie> movies;
            String[][] argsArr = {{"genre","Thriller"},{"movie","Indiana Jones"},{"limit","10"}};

            output.append("Test from: ");
            output.append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()));
            ((BufferedWriter) output).newLine();
            ((BufferedWriter) output).newLine();


            System.out.println("test with following args: "+get2dArrayAsString(argsArr));
            output.append("testing with following args: ");
            output.append(get2dArrayAsString(argsArr));
            ((BufferedWriter) output).newLine();
            movies = bs.getSuggestedMovies(argsArr);

            //prints movies in list to file
            for(Movie m: movies){
                output.append(m.toString());
                ((BufferedWriter) output).newLine();
            }

            argsArr = new String[][]{{"film", "Indiana Jones and the temple of doom"}, {"genre", "adventure"},{"limit","15"}};
            System.out.println("test with following args: "+get2dArrayAsString(argsArr));
            ((BufferedWriter) output).newLine();
            output.append("testing with following args: ");
            output.append(get2dArrayAsString(argsArr));
            ((BufferedWriter) output).newLine();
            movies = bs.getSuggestedMovies(argsArr);

            //prints movies in list to file
            for(Movie m: movies){
                output.append(m.toString());
                ((BufferedWriter) output).newLine();
            }

            argsArr = new String[][]{{"actor", "Jason Statham,Keanu Reeves"}, {"genre","action"},{"limit","50"}};
            System.out.println("testing with following args: "+get2dArrayAsString(argsArr));
            ((BufferedWriter) output).newLine();
            output.append("testing with following args: ");
            output.append(get2dArrayAsString(argsArr));
            ((BufferedWriter) output).newLine();
            movies = bs.getSuggestedMovies(argsArr);

            //prints movies in list to file
            for(Movie m: movies){
                output.append(m.toString());
                ((BufferedWriter) output).newLine();
            }
            System.out.println("Tests ended successfully");
            ((BufferedWriter) output).newLine();
            output.append("Test ended succesfully");
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private static String get2dArrayAsString(String[][] sArr2d){
        StringBuilder sBuilder = new StringBuilder();

        for(String[] sArr: sArr2d){
            sBuilder.append(sArr[0]);
            sBuilder.append(":");
            sBuilder.append(sArr[1]);
            sBuilder.append(" | ");
        }

        return sBuilder.toString();
    }
}
