package br.gov.sp.fatec.repositories;

import br.gov.sp.fatec.models.Authority;
import br.gov.sp.fatec.models.AuthorityName;
import org.springframework.data.repository.CrudRepository;

public interface AuthorityRepository extends CrudRepository<Authority, Long> {
    Authority findByName(AuthorityName name);
}
