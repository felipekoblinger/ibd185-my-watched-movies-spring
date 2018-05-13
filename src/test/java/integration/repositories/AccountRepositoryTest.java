package integration.repositories;

import br.gov.sp.fatec.models.Account;
import br.gov.sp.fatec.repositories.AccountRepository;
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
public class AccountRepositoryTest {
    @Autowired
    private AccountRepository accountRepository;

    @Test
    public void testSave() {
        Account account = new Account();
        account.setUsername(" Accountprimary ");
        account.setName(" Account Primary ");
        account.setEmail("accountprimary@test.com");
        account.setPassword("12345678");
        accountRepository.save(account);

        assertNotNull("Account not saved", account.getId());
    }
    @Test
    public void testDelete() {
        Account account = accountRepository.findById(1L).orElse(null);
        assertNotNull("Account not found", account);
        accountRepository.delete(account);
        Account accountDeleted = accountRepository.findById(1L).orElse(null);

        assertNull("Account found after deleting", accountDeleted);
    }
    @Test
    public void testUpdate() {
        Account account = accountRepository.findById(1L).orElse(null);
        assertNotNull("Account not found", account);
        account.setUsername("marionakani2");
        accountRepository.save(account);

        assertEquals("Account username didn't update", "marionakani2", account.getUsername());
    }

    /* @Query Methods */
    @Test
    public void testQueryByUsername() {
        Account account = accountRepository.queryByUsername("marionaKanI ");
        assertNotNull("Account not found", account);
    }
}
