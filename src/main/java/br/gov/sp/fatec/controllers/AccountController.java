package br.gov.sp.fatec.controllers;

import br.gov.sp.fatec.dtos.AccountChangePasswordDTO;
import br.gov.sp.fatec.dtos.AccountCreationDTO;
import br.gov.sp.fatec.models.Account;
import br.gov.sp.fatec.security.models.SecurityAccount;
import br.gov.sp.fatec.services.AccountService;
import br.gov.sp.fatec.views.View;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
    public ResponseEntity<Account> create(@Valid @RequestBody AccountCreationDTO accountCreationDTO) {
        ModelMapper modelMapper = new ModelMapper();
        Account account = modelMapper.map(accountCreationDTO, Account.class);
        accountService.create(account);
        return new ResponseEntity<>(account, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/me/", method = RequestMethod.GET)
    @JsonView(View.Common.class)
    public ResponseEntity<Account> me() {
        SecurityAccount account = (SecurityAccount) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        return new ResponseEntity<>(accountService.findById(account.getId()), HttpStatus.OK);
    }

    @RequestMapping(value = "/exists-username-or-email", method = RequestMethod.GET)
    @JsonView(View.Common.class)
    public ResponseEntity<ObjectNode> existsUsernameEmail(@RequestParam String username,
                                                         @RequestParam
            String
            email) {
        boolean usernameExists = accountService.findByUsername(username) != null;
        boolean emailExists = accountService.findByEmail(email) != null;
        /* Create JSON to return */
        ObjectNode objectNode = new ObjectMapper().createObjectNode();
        objectNode.put("username", usernameExists);
        objectNode.put("email", emailExists);

        return new ResponseEntity<>(objectNode, HttpStatus.OK);
    }

    @RequestMapping(value = "/change-password", method = RequestMethod.PUT)
    @JsonView(View.Common.class)
    public ResponseEntity changePassword(@Valid @RequestBody AccountChangePasswordDTO accountChangePasswordDTO) {
        SecurityAccount securityAccount = (SecurityAccount) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        Account account = accountService.findById(securityAccount.getId());

        boolean isChanged = accountService.changePassword(account, accountChangePasswordDTO.getCurrentPassword(),
                accountChangePasswordDTO.getNewPassword());
        if (isChanged) {
            return new ResponseEntity(HttpStatus.OK);
        }

        return new ResponseEntity(HttpStatus.FORBIDDEN);
    }

}
