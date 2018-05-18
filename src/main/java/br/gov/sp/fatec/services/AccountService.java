package br.gov.sp.fatec.services;

import br.gov.sp.fatec.models.Account;
import org.springframework.security.access.prepost.PreAuthorize;

public interface AccountService {
    void create(Account account);

    @PreAuthorize("hasAnyRole('ROLE_COMMON', 'ROLE_PAID')")
    Account findById(Long id);
}
