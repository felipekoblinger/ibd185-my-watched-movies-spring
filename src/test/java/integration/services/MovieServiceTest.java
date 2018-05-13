package integration.services;

import br.gov.sp.fatec.models.Movie;
import br.gov.sp.fatec.services.AccountService;
import br.gov.sp.fatec.services.MovieService;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/testContext.xml" })
@Transactional
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionDbUnitTestExecutionListener.class })
@DatabaseSetup("/datasets/accounts-movies.xml")
public class MovieServiceTest {
    @Autowired
    private MovieService movieService;
    @Autowired
    private AccountService accountService;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void testCreate() {
        Movie movie = new Movie();
        movie.setAccount(accountService.findById(1L));
        movie.setDate(LocalDate.of(2018, 4, 4));
        movie.setImdbId("tt2231461");
        movie.setTheMovieDatabaseId("427641");
        movie.setTitle("Rampage");
        movieService.create(movie);

        assertNotNull("Movie not saved.", movie.getId());
        assertNotNull("Movie Created At not saved.", movie.getCreatedAt());
        assertNotNull("Movie Updated At not saved.", movie.getUpdatedAt());
    }

    @Test
    public void testFindById() {
        Movie movie = movieService.findById(1L);
        assertNotNull("Movie not found.", movie);
    }

    @Test
    public void testFindAllByAccountId() {
        List<Movie> movies = movieService.findAllByAccountId(1L);
        assertTrue("Movies not found.", movies.size() > 0);
    }

    @Test
    public void testUpdate() {
        Movie movie = movieService.findById(1L);
        assertNotNull("Movie not found.", movie);
        movie.setTitle("Rampage New");
        movie.setImdbId("tt2231463");
        movie.setTheMovieDatabaseId("427642");
        movie.setDate(LocalDate.of(2018, 4, 20));

        LocalDateTime oldUpdatedAt = movie.getUpdatedAt();
        LocalDate oldDate = movie.getDate();
        movieService.update(movie);

        /* Ensuring Database Activity */
        entityManager.flush();
        entityManager.clear();

        Movie movieUpdated = movieService.findById(1L);

        assertEquals("Movie IMDB ID is not the same", "tt2231463", movieUpdated.getImdbId());
        assertEquals("Movie TheMovieDatabase ID is not the same", "427642", movieUpdated.getTheMovieDatabaseId());
        assertEquals("Movie Title is not the same", "Rampage New", movieUpdated.getTitle());
        assertEquals("Movie Date is not the same", oldDate, movieUpdated.getDate());
        assertNotSame("Movie Updated At cannot be the same", oldUpdatedAt, movieUpdated.getUpdatedAt());
    }

    @Test
    public void testDelete() {
        Movie movie = movieService.findById(1L);
        assertNotNull("Movie not found.", movie);
        movieService.delete(movie);
        Movie findMovie = movieService.findById(1L);
        assertNull("Movie not deleted.", findMovie);
    }
}
