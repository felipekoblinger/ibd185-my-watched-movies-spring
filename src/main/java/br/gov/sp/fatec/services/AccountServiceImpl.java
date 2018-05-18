package br.gov.sp.fatec.services;

import br.gov.sp.fatec.models.Account;
import br.gov.sp.fatec.models.Authority;
import br.gov.sp.fatec.models.AuthorityName;
import br.gov.sp.fatec.repositories.AccountRepository;
import br.gov.sp.fatec.repositories.AuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("accountService")
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final AuthorityRepository authorityRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, AuthorityRepository authorityRepository) {
        this.accountRepository = accountRepository;
        this.authorityRepository = authorityRepository;
    }

    @Override
    @Transactional
    public void create(Account account) {
        /* BCrypt Password */
        account.setPassword(new BCryptPasswordEncoder().encode(account.getPassword()));

        Authority authority = authorityRepository.findByName(AuthorityName
                .ROLE_COMMON);

        List<Authority> authorities = new ArrayList<>();
        authorities.add(authority);

        account.setAuthorities(authorities);
        account.setUsername(account.getUsername().toLowerCase().trim());
        account.setName(account.getName().trim());
        accountRepository.save(account);
    }

    @Override
    public Account findById(Long id) {
        return accountRepository.findById(id).orElse(null);
    }
}
