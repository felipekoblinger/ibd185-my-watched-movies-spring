package br.gov.sp.fatec.services;

import br.gov.sp.fatec.models.Account;

public interface AccountService {
    Account create(Account account);
    Account findById(Long id);
}
