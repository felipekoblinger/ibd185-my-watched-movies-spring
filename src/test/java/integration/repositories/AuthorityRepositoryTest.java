package integration.repositories;

import br.gov.sp.fatec.models.Account;
import br.gov.sp.fatec.models.Authority;
import br.gov.sp.fatec.models.AuthorityName;
import br.gov.sp.fatec.repositories.AuthorityRepository;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/testContext.xml" })
@Transactional
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionDbUnitTestExecutionListener.class })
@DatabaseSetup("/datasets/accounts-movies.xml")
public class AuthorityRepositoryTest {
    @Autowired
    private AuthorityRepository authorityRepository;

    @Test
    public void testSave() {
        Authority authority = new Authority();
        authority.setName(AuthorityName.ROLE_COMMON);
        authorityRepository.save(authority);

        assertNotNull("Authority not saved", authority.getId());
    }
    @Test
    public void testDelete() {
        Authority authority = authorityRepository.findById(1L).orElse(null);
        assertNotNull("Authority not found", authority);
        authorityRepository.delete(authority);
        Authority authorityDeleted = authorityRepository.findById(1L).orElse(null);

        assertNull("Authority found after deleting", authorityDeleted);
    }
    @Test
    public void testUpdate() {
        Authority authority = authorityRepository.findById(1L).orElse(null);
        assertNotNull("Authority not found", authority);
        authority.setName(AuthorityName.ROLE_PAID);
        authorityRepository.save(authority);

        assertEquals("Authority name didn't update", AuthorityName.ROLE_PAID, authority.getName());
    }

    /* Query Methods */
    @Test
    public void testFindByName() {
        Authority authority = authorityRepository.findByName(AuthorityName.ROLE_COMMON);

        assertNotNull("Authority not found by name", authority);
    }
}
