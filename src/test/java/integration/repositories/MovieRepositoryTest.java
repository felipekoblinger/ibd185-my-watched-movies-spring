package integration.repositories;

import br.gov.sp.fatec.models.Movie;
import br.gov.sp.fatec.repositories.AccountRepository;
import br.gov.sp.fatec.repositories.MovieRepository;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/testContext.xml" })
@Transactional
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionDbUnitTestExecutionListener.class })
@DatabaseSetup("/datasets/accounts-movies.xml")
public class MovieRepositoryTest {
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private AccountRepository accountRepository;

    @Test
    public void testSave() {
        Movie movie = new Movie();
        movie.setAccount(accountRepository.findById(1L).orElse(null));
        movie.setDate(LocalDate.of(2018, 4, 4));
        movie.setTheMovieDatabaseId("427641");
        movie.setTitle("Rampage");
        movie.setCreatedAt(LocalDateTime.now());
        movie.setUpdatedAt(LocalDateTime.now());
        movieRepository.save(movie);
        assertNotNull("Movie not saved", movie.getId());
    }
    @Test
    public void testDelete() {
        Movie movie = movieRepository.findById(1L).orElse(null);
        assertNotNull("Movie not found", movie);
        movieRepository.delete(movie);
        Movie movieDeleted = movieRepository.findById(1L).orElse(null);
        assertNull("Movie found after delete", movieDeleted);
    }
    @Test
    public void testUpdate() {
        Movie movie = movieRepository.findById(1L).orElse(null);
        assertNotNull("Movie not found", movie);
        movie.setTitle("Rampage 2");
        movieRepository.save(movie);

        assertEquals("Authority name didn't update", "Rampage 2", movie.getTitle());
    }

    /* Query Methods */
    @Test
    public void testFindAllByAccountId() {
        List<Movie> movies = movieRepository.findAllByAccountId(1L);
        assertTrue("Movies not found", movies.size() > 0);
    }

    @Test
    public void testFindByUuid() {
        Movie movie = movieRepository.findByUuidAndAccountId
                ("a7b5fa22-4903-4059-948b-1790088f16ed", 1L);
        assertNotNull("Movie not found", movie);
    }
}
