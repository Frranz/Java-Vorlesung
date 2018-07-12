package src.com.company;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class UserRatings {
    List<Review> reviews;
    String filePath;

    public List<Review> getReviews() {
        return reviews;
    }

    public void addReview(Review r){
        reviews.add(r);
        try {
            Writer output = new BufferedWriter(new FileWriter(this.filePath));
            String write = "\""+r.getMovie().getId()+"\",\""+r.getScore()+"\"";
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
        boolean runLoop = true;
        Scanner reader = new Scanner(System.in);
        String inp;
        float score = -1;
        Movie selectedMovie = mb.selectMovieFromConsole();

        if(selectedMovie!=null){
            while(runLoop){                                                                 //score einlesen
                System.out.println("Wie viele Sterne würden Sie dem Film geben? [1-5]");
                score = reader.nextFloat();
                if(score>=0 && score <=5){
                    runLoop = false;
                }else{
                    System.out.println("Eingabe muss kleiner 5 und größer 1 sein.\n");
                }
            }
            Review review =  new Review(score,selectedMovie);
            addReview(review);
            System.out.println("Review wurde hinzugefügt");
        }else{
            System.out.println("ungültiger Index ausgewählt oder keinen Film mit ähnlichem Titel gefunden");
        }

    }
}
