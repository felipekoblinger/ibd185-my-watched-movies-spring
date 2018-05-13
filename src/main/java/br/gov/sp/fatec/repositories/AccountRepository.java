package br.gov.sp.fatec.repositories;

import br.gov.sp.fatec.models.Account;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface AccountRepository extends CrudRepository<Account, Long> {
    @Query("SELECT a FROM Account a WHERE TRIM(LOWER(a.username)) = TRIM(LOWER(:username))")
    Account queryByUsername(@Param("username") String username);
}
