package src.com.company;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class MovieBaseTest {

    @Test
    public void addActor() {
        MovieBase mb = new MovieBase();
        Actor a = new Actor("\"9817\",\" Jules-Eugène Legris\"");
        mb.addActor(a);

        Assert.assertEquals(a,mb.getActorById(9817));
    }

    @Test
    public void addReview() {
        MovieBase mb = new MovieBase();
        Review r = new Review(3.4f,null);
        mb.addReview(r);

        Assert.assertEquals(mb.getReviews().get(0),r);

    }

    @Test
    public void addMovie() {
        MovieBase mb = new MovieBase();
        Movie m = new Movie("\"8696\",\"Boxtrolls, The\",\"A young orphaned boy raised by underground cave-dwelling trash collectors tries to save his friends from an evil exterminator.\",\"Adventure\",\"2014-09-26\",\"38141\",\"6.8\n");
        m.setId(3);
        mb.addMovie(m);

        Assert.assertEquals(m,mb.getMovieById(m.getId()));
    }
}