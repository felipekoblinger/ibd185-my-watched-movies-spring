package br.gov.sp.fatec.controllers;

import br.gov.sp.fatec.models.Account;
import br.gov.sp.fatec.services.AccountService;
import br.gov.sp.fatec.views.View;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/accounts/")
public class AccountController {
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @JsonView(View.Common.class)
    public ResponseEntity<Account> create(@Valid @RequestBody Account account) {
        return new ResponseEntity<>(accountService.create(account), HttpStatus.CREATED);
    }

}
