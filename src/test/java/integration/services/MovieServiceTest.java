package integration.services;

import br.gov.sp.fatec.enums.MovieType;
import br.gov.sp.fatec.models.Movie;
import br.gov.sp.fatec.security.services.UserDetailsServiceImpl;
import br.gov.sp.fatec.services.MovieService;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import factories.MovieFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
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
    private UserDetailsServiceImpl userDetailsService;

    @PersistenceContext
    private EntityManager entityManager;

    @Before
    public void setUp() {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername("marionakani");
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    }

    @Test
    public void testCreate() {
        Movie movie = MovieFactory.validResource();
        movieService.create(movie);

        assertNotNull("Movie not saved", movie.getId());
        assertNotNull("Movie Created At not saved", movie.getCreatedAt());
        assertNotNull("Movie Updated At not saved", movie.getUpdatedAt());
    }

    @Test(expected = ConstraintViolationException.class)
    public void testCreateWithViolation() {
        Movie movie = MovieFactory.validResource();
        movie.setRating(6);
        movieService.create(movie);
    }

    @Test
    public void testFindById() {
        Movie movie = movieService.findById(1L);
        assertNotNull("Movie not found", movie);
    }

    @Test
    public void testFindAllByAccountId() {
        List<Movie> movies = movieService.findAllByAccountId(1L);
        assertTrue("Movies not found", movies.size() > 0);
    }

    @Test
    public void testUpdate() {
        Movie movie = movieService.findById(1L);
        assertNotNull("Movie not found", movie);

        movie.setDate(LocalDate.of(2018, 4, 20));
        // TODO: test rating and type
        movie.setRating(3);
        movie.setType(MovieType.ORIGINAL);

        LocalDateTime oldUpdatedAt = movie.getUpdatedAt();
        LocalDate oldDate = movie.getDate();
        movieService.update(movie);

        /* Ensuring Database Activity */
        entityManager.flush();
        entityManager.clear();

        Movie movieUpdated = movieService.findById(1L);

        assertEquals("Movie Date is not the same", oldDate, movieUpdated.getDate());
        assertNotSame("Movie Updated At cannot be the same", oldUpdatedAt, movieUpdated.getUpdatedAt());
    }

    @Test
    public void testDelete() {
        Movie movie = movieService.findById(1L);
        assertNotNull("Movie not found", movie);
        movieService.delete(movie);
        Movie findMovie = movieService.findById(1L);
        assertNull("Movie not deleted", findMovie);
    }

    @Test
    public void testFindByUuid() {
        Movie movie = movieService.findByUuidAndAccountId("a7b5fa22-4903-4059-948b-1790088f16ed",
                1L);
        assertNotNull("Movie not found", movie);
    }
}
