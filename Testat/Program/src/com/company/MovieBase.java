package src.com.company;

import java.util.*;

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
        HashSet<Movie>[] movieSet = new HashSet[vals.length];
        for(HashSet<Movie> hm:movieSet){
            hm = new HashSet<>();
        }
        boolean someCondition = false;
        Iterator<Movie> iterator = getMovies().iterator();
        Movie m;
        int filterCounter = 0;
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
                        if(m.getDirector().getName().equals(val)){
                            filterCounter++;
                        }
                    }
                    break;
            }

            if (filterCounter>0) {
                movieSet[filterCounter-1].add(m);
                //iterator.remove();
            }
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
        List<Movie> filteredMovies = new LinkedList<>();
        int counter = 0;

        boolean containsFilter;
        for (String[] s : args) {
            switch (s[0]) {
                case "genre":
                    vals = s[1].substring(1, s[1].length() - 1).split(",");
                    moviesSplitted.add(
                            filterByListAttribute(s[0],vals)
                    );
                    print("filtered by genre");
                    break;
                case "actor":
                    vals = s[1].substring(1, s[1].length() - 1).split(",");
                    moviesSplitted.add(
                            filterByListAttribute(s[0],vals)
                    );
                    print("filtered by actor");
                    break;
                case "director":
                    vals = s[1].substring(1, s[1].length() - 1).split(",");
                    moviesSplitted.add(
                            filterByListAttribute(s[0],vals)
                    );
                    print("filtered by director");
                    break;
                case "film":
                    //später machen
                    break;
                case "limit":
                    //movies = movies.subList(0, Integer.parseInt(s[1]));
                    break;
                default:
                    print("Komisches Argument" + s[0]);
            }
            counter++;
        }

        return filteredMovies;
    }
    public static void print(String s){
        System.out.println(s);
    }

}
;