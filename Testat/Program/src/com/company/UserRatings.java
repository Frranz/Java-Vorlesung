package src.com.company;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class UserRatings {
    private List<Review> reviews;
    private String filePath;

    public List<Review> getReviews() {
        return reviews;
    }

    public void addReview(Review r){
        reviews.add(r);
        try {

            //add new userRating to userRatings.txt
            Writer output = new BufferedWriter(new FileWriter(this.filePath,true));
            String write = "\""+r.getMovie().getId()+"\",\""+r.getScore()+"\"\n";
            output.append(write);
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public UserRatings(String path, MovieBase mb){
        this.filePath = path;
        reviews = new LinkedList<>();
        Movie movie;
        String[] lineSplit;

        try {

            //import userRatings from userRatings.txt
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line = br.readLine();

            while(line!=null){
                lineSplit = line.substring(1,line.length()-1).split("\",\"");
                movie = mb.getMovieById(Integer.parseInt(lineSplit[0]));
                Review review = new Review(Float.parseFloat(lineSplit[1]),movie);
                reviews.add(review);
                line = br.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("user Ratings von Datei eingelesen");
    }


    public void newReviewInteractive(MovieBase mb) {

        //chose movie
        boolean runLoop = true;
        Scanner reader = new Scanner(System.in);
        float score = -1;
        Movie selectedMovie = mb.selectMovieFromConsole();

        //set rating
        if(selectedMovie!=null){
            while(runLoop){                                                                 
                System.out.println("Wie viele Sterne wuerden Sie dem Film geben? [1-5]");
                score = reader.nextFloat();
                if(score>=0 && score <=5){
                    runLoop = false;
                }else{
                    System.out.println("Eingabe muss kleiner 5 und groeßer 1 sein.\n");
                }
            }
            Review review =  new Review(score,selectedMovie);
            addReview(review);
            System.out.println("Review wurde hinzugefuegt");
        }else{
            System.out.println("ungueltiger Index ausgewaehlt oder keinen Film mit aehnlichem Titel gefunden");
        }

    }
}
