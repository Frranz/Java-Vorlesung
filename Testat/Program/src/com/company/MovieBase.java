package src.com.company;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MovieBase {
    private int id;
    private HashMap<Integer,Movie> movies;
    private HashMap<Integer,Director> directors;
    private HashMap<Integer,Actor> actors;
    private List<Review> reviews;
    private HashMap<String,Reviewer> reviewers;

    public MovieBase() {
        movies = new HashMap<>();
        actors = new HashMap<>();
        directors = new HashMap<>();
        reviewers = new HashMap<>();
        reviews = new LinkedList<>();

        System.out.println("moviebase wurde initialisiert");
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Actor getActorById(int id){
        return actors.get(id);
    }





    public Map<Integer,Movie> getMoviesMap() {
        return movies;
    }

    public Map<Integer, Director> getDirectors() {
        return directors;
    }

    public Map<Integer,Actor> getActors() {
        return actors;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public HashMap<String, Reviewer> getReviewers() {
        return reviewers;
    }

    public void addMovie(Movie newMovie){
        movies.put(newMovie.getId(),newMovie);
    }

    public void addDirector(Director newDirector){
        directors.put(newDirector.getId(),newDirector);
    }

    public void addActor(Actor newActor){
        actors.put(newActor.getId(),newActor);
    }

    public void addReview(Review newReview){
        reviews.add(newReview);
    }

    public void addReviewer(Reviewer newReviewer){
        reviewers.put(newReviewer.getUserName(),newReviewer);
    }

    public Movie getMovieById(int i) {
        return movies.get(i);
    }

    public Director getDirectorById(int i) {
        return directors.get(i);
    }

    public Reviewer getReviewer(String userName){
        return reviewers.get(userName);
    }

    public List<Movie> getMovies() {
        List<Movie> someMovies = new LinkedList<>();
        for(int key: movies.keySet()){
            someMovies.add(movies.get(key));
        }
        return someMovies;
    }

    public static List<Movie> filterMoviesByGenre(List<Movie> movies,String genre){
        for(Movie m:movies){
            if(!m.getGenre().contains(genre)){
                movies.remove(m);
            }
        }
        return movies;
    }

    private HashSet<Movie>[] filterByListAttribute( String filterKey,String[] vals){
        Director director;
        HashSet<Movie>[] movieSet = new HashSet[vals.length];
        /*for(HashSet<Movie> hm:movieSet){
            hm = new HashSet<>();                   why does this not work???
        }*/
        for(int i =0; i<movieSet.length;i++){
            movieSet[i]=new HashSet<>();
        }
        boolean someCondition = false;
        Iterator<Movie> iterator = getMovies().iterator();
        Movie m;
        int filterCounter = 0;
        int counter = 0;
        while(iterator.hasNext()){
            m = iterator.next();

            switch(filterKey){
                case "genre":
                    filterCounter = 0;
                    for(String val:vals){
                        if(m.getGenre().contains(val)){
                            filterCounter++;
                        }
                    }
                    break;
                case "actor":
                    filterCounter = 0;
                    for(String val:vals){
                        if(m.getActorNames().contains(val)){
                            filterCounter++;
                        }
                    }
                    break;
                case "director":
                    filterCounter = 0;
                    for(String val:vals){
                        if(counter==509){
                            System.out.println(counter);
                        }
                        director = m.getDirector();
                        if(director != null && m.getDirector().getName().equals(val)){
                            filterCounter++;
                        }
                    }
                    break;
            }

            if (filterCounter>0) {
                movieSet[movieSet.length -filterCounter].add(m);
                //iterator.remove();
            }
            counter++;
        }

        return movieSet;
    }

    public static void printMoviesList(List<Movie> movies){
        for(Movie m: movies){
            System.out.println(m.toString());
        }
    }

    public List<Movie> getSuggestedMovies(String[][] args) {
        if (args.length == 0 ) {
            System.out.println("keine argumente bei getSuggestedMovie");
            return null;
        }
        String[] vals;
        List<HashSet<Movie>[]> moviesSplitted = new ArrayList<>();
        List<Movie> filteredMovies;
        int counter = 0;
        int limit = 0;

        boolean containsFilter;
        for (String[] s : args) {
            switch (s[0]) {
                case "genre":
                    vals = s[1].split(","); // .substring(1, s[1].length() - 1)
                    moviesSplitted.add(
                            filterByListAttribute(s[0],vals)
                    );
                    print("filtered by genre");
                    break;
                case "actor":
                    vals = s[1].split(","); // .substring(1, s[1].length() - 1)
                    moviesSplitted.add(
                            filterByListAttribute(s[0],vals)
                    );
                    print("filtered by actor");
                    break;
                case "director":
                    vals = s[1].split(","); // .substring(1, s[1].length() - 1)
                    moviesSplitted.add(
                            filterByListAttribute(s[0],vals)
                    );
                    print("filtered by director");
                    break;
                case "film":
                    vals = s[1].split(",");
                    moviesSplitted.add(
                            getSimilarMovies(vals)
                    );
                    print("getting similar films");
                    break;
                case "limit":
                    limit = Integer.parseInt(s[1]);
                    break;
                default:
                    print("Komisches Argument" + s[0]);
            }
            counter++;
        }

        filteredMovies = filterTopDownDuplicates(moviesSplitted).subList(0,limit);

        return filteredMovies;
    }

    private HashSet<Movie>[] getSimilarMovies(String[] vals) {
        HashSet<Movie>[] movieSetArr = new HashSet[3];
        for(int i =0; i<movieSetArr.length;i++){
            movieSetArr[i]=new HashSet<>();
        }
        HashMap<Movie,Integer> movieRatings = new HashMap<>();
        Movie movie;
        List<Review> reviews;
        List<Review> movieReviewerReviews;
        Movie reviewedMovie;
        int newScore;
        int addScore;
        int userAverageReviewScore;
        int standardDeviation;
        int[] counter = new int[3];
        counter[0] = 0;
        counter[1] = 0;
        counter[2] = 0;

        int[] counter2 = new int[3];
        counter2[0] = 0;
        counter2[1] = 0;
        counter2[2] = 0;
        for (String s: vals){
            movie = getMovieByName(s);      //movie to be filtered by
            if (movie != null) {
                reviews = movie.getReviews();
//
                for(Review movieReview: reviews){
                    movieReviewerReviews = movieReview.getReviewer().getReviews();
                    userAverageReviewScore = movieReviewerReviews.stream().mapToInt(value -> (int) value.getScore()*100).sum()/movieReviewerReviews.size(); //get average review score of certain reviewer for normalization
                    int finalUserAverageReviewScore = userAverageReviewScore;
                    standardDeviation = (int) Math.sqrt(movieReview.getReviewer().getReviews().stream().mapToInt(value ->(int) Math.pow(Math.abs( value.getScore()*100- finalUserAverageReviewScore),2)).sum()/movieReview.getReviewer().getReviews().size());
                    for(Review userReview:movieReviewerReviews){
                        reviewedMovie = userReview.getMovie();
                        if(userReview.getMovie()!=movie){
                            if(!movieRatings.containsKey(reviewedMovie)){
                                movieRatings.put(reviewedMovie,0);
                            }
                            if(userReview.getScore()*100<finalUserAverageReviewScore-(0.44*standardDeviation)){         //gets ~bottom 33% of Reviews
                                addScore = 1;
                                counter[0] +=1;
                            }else if(userReview.getScore()*100<finalUserAverageReviewScore+(0.43*standardDeviation)){   //gets ~middle 33% of Reviews
                                addScore = 2;
                                counter[1] +=1;
                            }else{                                                                                      //gets ~top 33% of Reviews
                                addScore = 3;
                                counter[2] +=1;
                            }

                            newScore = movieRatings.get(reviewedMovie) + addScore;
                            movieRatings.put(reviewedMovie,newScore);
                        }
                    }

                }
            }else{
                System.out.println("Movie: "+s +" could not be found in database");
                return null;
            }
        }

        int maxValue = movieRatings.entrySet().stream().max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1).get().getValue()+1; //+1 to maek any divison

        for(Map.Entry<Movie, Integer> entry: movieRatings.entrySet()){
            movieSetArr[2-(int) Math.floor(entry.getValue()*3/maxValue)].add(entry.getKey());
        }

        return movieSetArr;
    }

    private Movie getMovieByName(String s) {
        for(Movie m: movies.values()){
            if(m.getTitle().equalsIgnoreCase(s)){
                return m;
            }
        }
        return null;
    }

    private List<Movie> filterTopDownDuplicates(List<HashSet<Movie>[]> moviesSplitted) {
        List<Movie> movies = new LinkedList<>();
        HashSet<Movie>[] currentArrEl;
        HashSet<Movie> currentSet;
        HashSet<Movie>[] runnerArrEl;
        HashSet<Movie> runnerSet;

        Iterator it = moviesSplitted.listIterator();

        //deleting duplicates top down
        for (int i = 0; i < moviesSplitted.size()-1; i++) {
            currentArrEl = moviesSplitted.get(i);

            for(HashSet<Movie> hsm: currentArrEl){
                for(Movie m:hsm){
                    for (int j = i+1; j < moviesSplitted.size(); j++) {
                        runnerArrEl = moviesSplitted.get(j);
                        for (int k = 0; k < runnerArrEl.length; k++) {
                            runnerSet = runnerArrEl[i];
                            if(runnerSet.contains(m)){
                                moviesSplitted.get(j)[k].remove(m);
                            }
                        }
                    }
                }
            }


        }

        for(HashSet<Movie>[] hsa: moviesSplitted){
            for(HashSet<Movie>hs: hsa){
                movies.addAll(hs);
            }
        }

        return movies;
    }

    public static void print(String s){
        System.out.println(s);
    }

}
;