package src.com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

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
        someMovies.addAll(movies.values());
//        for(int key: movies.keySet()){
//            someMovies.add(movies.get(key));
//        }
        return someMovies;
    }

    private void filterByListAttribute(HashMap<Movie, Integer> filteredMovies, String filterKey, String[] vals){
        Director director;
        Iterator<Movie> iterator = getMovies().iterator();
        Movie m;

        //filter counter gets up by 1 for every time some filter is hit
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
                filteredMovies.putIfAbsent(m, 0);
                filteredMovies.put(m,filteredMovies.get(m)+filterCounter);
            }
            counter++;
        }
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
        HashMap<Movie,Integer> filteredMovies = new HashMap<>();
        List<Movie> retMovies;
        int limit = 0;

        boolean containsFilter;
        for (String[] s : args) {
            switch (s[0]) {
                case "genre":
                    vals = s[1].split(","); // .substring(1, s[1].length() - 1)
                    filterByListAttribute(filteredMovies,s[0],vals);
                    print("filtered by genre");
                    break;
                case "actor":
                    vals = s[1].split(","); // .substring(1, s[1].length() - 1)
                    filterByListAttribute(filteredMovies,s[0],vals);
                    print("filtered by actor");
                    break;
                case "director":
                    vals = s[1].split(","); // .substring(1, s[1].length() - 1)
                    filterByListAttribute(filteredMovies,s[0],vals);
                    print("filtered by director");
                    break;
                case "film":
                    vals = s[1].split(",");
                    getSimilarMovies(filteredMovies,vals);
                    print("getting similar films");
                    break;
                case "limit":
                    limit = Integer.parseInt(s[1]);
                    break;
                default:
                    print("Komisches Argument" + s[0]);
            }
        }
        retMovies = movieMapToSortedList(filteredMovies);

        if(limit>0 && retMovies.size()>limit){
            retMovies = retMovies.subList(0,limit);
        }

        return retMovies;
    }

    private List<Movie> movieMapToSortedList(HashMap<Movie,Integer> filteredMovies) {
        List<Movie> movies;

        //converts hashmap to sorted movielist. sorted by value
        movies = filteredMovies.entrySet().stream()
                .sorted(Comparator.comparing(Map.Entry::getValue,Comparator.reverseOrder()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        return movies;
    }

    public void getSimilarMovies(HashMap<Movie, Integer> filteredMovies, String[] vals) {
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

                    /*  calculate average Review score and standard Deviation
                    *   to find bottom, middle and top 1/3 of their reviews     */
                    userAverageReviewScore = movieReviewerReviews.stream().mapToInt(value -> (int) value.getScore()*100).sum()/movieReviewerReviews.size(); //get average review score of certain reviewer for normalization
                    int finalUserAverageReviewScore = userAverageReviewScore;
                    standardDeviation = (int) Math.sqrt(movieReview.getReviewer().getReviews().stream().mapToInt(value ->(int) Math.pow(Math.abs( value.getScore()*100- finalUserAverageReviewScore),2)).sum()/movieReview.getReviewer().getReviews().size());

                    for(Review userReview:movieReviewerReviews){
                        reviewedMovie = userReview.getMovie();

                        if(userReview.getMovie()!=movie){

                            filteredMovies.putIfAbsent(reviewedMovie,0);

                            if(userReview.getScore()*100<finalUserAverageReviewScore-(0.44*standardDeviation)){         //gets ~bottom 33% of Reviews
                                addScore = -1;
                                counter[0] +=1;
                            }else if(userReview.getScore()*100<finalUserAverageReviewScore+(0.43*standardDeviation)){   //gets ~middle 33% of Reviews
                                addScore = 1;
                                counter[1] +=1;
                            }else{                                                                                      //gets ~top 33% of Reviews
                                addScore = 2;
                                counter[2] +=1;
                            }

                            newScore = filteredMovies.get(reviewedMovie) + addScore;
                            filteredMovies.put(reviewedMovie,newScore);
                        }
                    }

                }
            }else{
                System.out.println("Movie: "+s +" could not be found in database. This error should not occure. Something went really really wrong here");
            }
        }
    }

    private Movie getMovieByName(String s) {
        for(Movie m: movies.values()){
            if(m.getTitle().equalsIgnoreCase(s)){
                return m;
            }
        }
        return null;
    }

    public static void print(String s){
        System.out.println(s);
    }

    public List<Movie> getMoviesWithSimilarTitle(String input) {
        List<Movie> movies = new ArrayList<>();
        for(Movie m: getMovies()){
            if(m.getTitle().contains(input)){
                movies.add(m);
            }
        }
        return movies;
    }

    public Movie selectMovieFromConsole(){

        //Show films with similar title
        Scanner reader = new Scanner(System.in);
        System.out.println("Bitte geben Sie einen zu suchenden Titel ein");
        String title = reader.nextLine();
        List<Movie> movies = getMoviesWithSimilarTitle(title);

        //choose one of them by index
        System.out.println("Bitte geben Sie den Index für den gewünschten Film ein.");
        if(movies.size()>0){
            int counter = 0;
            for(Movie m: movies){
                System.out.println("("+counter+")"+m.toString());
                counter ++;
            }
            int inpInt = Integer.valueOf(reader.nextLine());
            return movies.get(inpInt);
        }else{
            return null;
        }

    }

    public MovieBase(String path){

        //import database from file
        movies = new HashMap<>();
        actors = new HashMap<>();
        directors = new HashMap<>();
        reviewers = new HashMap<>();
        reviews = new LinkedList<>();
        
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

                //get type of following datasets
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

                    //mode is current type of datasets for individual handling
                    switch(mode){
                        case "actorIdToName":
                            actor = new Actor(line);
                            addActor(actor);
                            break;
                        case "movie":
                            movie = getMovieById(Integer.parseInt(line.substring(1,line.length()-1).split("\",\"")[0]));
                            if(movie==null){  //wenn Film mit id bereits existiert
                                movie = new Movie(line);
                                addMovie(movie);
                            }else{
                                genre = line.substring(1,line.length()-1).split("\",\"")[3];
                                movie.addGenre(genre);
                            }
                            break;
                        case "directorIdToName":
                            director = new Director(line);
                            addDirector(director);
                            break;
                        case "actorIdToMovieId":
                            lineSplit = line.substring(1,line.length()-1).split("\",\"");
                            actor = getActorById(Integer.parseInt(lineSplit[0]));
                            movie = getMovieById(Integer.parseInt(lineSplit[1]));
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
                            director = getDirectorById(Integer.parseInt(lineSplit[0]));
                            movie = getMovieById(Integer.parseInt(lineSplit[1]));
                            if(director!=null && movie!=null){
                                director.addDirectedMovie(movie);
                                movie.setDirector(director);
                            }else{
                                System.out.println("director or movie null in line: "+line);
                            }

                            break;
                        case "review":
                            lineSplit = line.substring(1,line.length()-1).split("\",\"");
                            reviewer = getReviewer(lineSplit[0]);
                            if(reviewer == null){
                                reviewer = new Reviewer(lineSplit[0]);
                            }
                            addReviewer(reviewer);
                            movie = getMovieById(Integer.parseInt(lineSplit[2]));
                            if(movie!=null){
                                review = new Review(Float.parseFloat(lineSplit[1]),movie,reviewer);
                                reviewer.addReview(review);
                                movie.addReview(review);
                                addReview(review);
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
    }
}
;