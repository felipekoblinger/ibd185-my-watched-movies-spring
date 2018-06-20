package integration.services;

import br.gov.sp.fatec.security.services.UserDetailsServiceImpl;
import br.gov.sp.fatec.services.MovieStatisticsService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

import static org.springframework.test.util.AssertionErrors.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/testContext.xml" })
@Transactional
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionDbUnitTestExecutionListener.class })
@DatabaseSetup("/datasets/accounts-movies.xml")
public class MovieStatisticsServiceTest {
    @Autowired
    private MovieStatisticsService movieStatisticsService;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    private void setNonPaidAccount() {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername("marionakani");
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    }

    @Before
    public void setUp() {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername("rodrikflint");
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    }

    @Test
    public void testNumberOfMovies() {
        Long total = movieStatisticsService.numberOfMovies(1L);
        assertTrue("Total of Movies must be greater than 0", total > 0);
    }

    @Test(expected = AccessDeniedException.class)
    public void testNumberOfMoviesWithoutRolePaid() {
        this.setNonPaidAccount();
        movieStatisticsService.numberOfMovies(1L);
    }

    @Test
    public void testNumberOfMoviesMonthly() {
        List<ObjectNode> monthlyMovies = movieStatisticsService.numberOfMoviesMonthly(1L);
        assertTrue("Monthly Movies can't be empty", monthlyMovies.size() > 0);
    }
    @Test(expected = AccessDeniedException.class)
    public void testNumberOfMoviesMonthlyWithoutRolePaid() {
        this.setNonPaidAccount();
        movieStatisticsService.numberOfMoviesMonthly(1L);
    }

    @Test
    public void testNumberOfMoviesType() {
        ObjectNode typeMovies = movieStatisticsService.numberOfMoviesType(1L);
        assertTrue("Type Movies can't be empty", typeMovies.size() > 0);
    }
    @Test(expected = AccessDeniedException.class)
    public void testNumberOfMoviesTypeWithoutRolePaid() {
        this.setNonPaidAccount();
        movieStatisticsService.numberOfMoviesType(1L);
    }
}
