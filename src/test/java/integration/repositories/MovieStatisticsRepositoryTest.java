package integration.repositories;

import br.gov.sp.fatec.dtos.MovieGroupMonthlyDTO;
import br.gov.sp.fatec.dtos.MovieGroupTypeDTO;
import br.gov.sp.fatec.repositories.MovieStatisticsRepository;
import br.gov.sp.fatec.services.MovieStatisticsService;
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

import java.util.List;

import static org.springframework.test.util.AssertionErrors.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/testContext.xml" })
@Transactional
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionDbUnitTestExecutionListener.class })
@DatabaseSetup("/datasets/accounts-movies.xml")
public class MovieStatisticsRepositoryTest {
    @Autowired
    private MovieStatisticsRepository movieStatisticsRepository;

    @Test
    public void testNumberOfMovies() {
        Long numberOfMovies = movieStatisticsRepository.numberOfMovies(1L);
        assertTrue("Movies not found.", numberOfMovies > 0);
    }

    @Test
    public void testFindAllGroupedMonthly() {
        List<MovieGroupMonthlyDTO> result = movieStatisticsRepository
                .findAllGroupedMonthly(1L);
        System.out.println(result.get(0).getYearMonth());
        assertTrue("Movies not found.", result.size() > 0);
    }

    @Test
    public void testFindAllGroupedType() {
        List<MovieGroupTypeDTO> result = movieStatisticsRepository.findAllGroupedType(1L);
        assertTrue("Movies not found.", result.size() > 0);
    }
}
