package integration.services;

import br.gov.sp.fatec.services.TheMovieDatabaseService;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.io.InputStreamReader;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/testContext.xml" })
public class TheMovieDatabaseServiceTest {
    @Autowired
    private TheMovieDatabaseService theMovieDatabaseService;

    @Test
    public void testGetMovieById() {
        String id = "427641";
        JsonElement movie = theMovieDatabaseService.getMovieById(id);
        assertNotNull("Movie cannot be null", movie);
        assertTrue("Movie must be a JSON Object", movie.isJsonObject());
    }

    @Test
    public void testSearchMoviesByTerm() {
        String term = "avengers";
        JsonElement movies = theMovieDatabaseService.searchMoviesByTerm(term);
        assertNotNull("Movies cannot be null", movies);
        assertTrue("Movies must be a JSON Object", ((JsonObject) movies).has("results"));
    }
}
