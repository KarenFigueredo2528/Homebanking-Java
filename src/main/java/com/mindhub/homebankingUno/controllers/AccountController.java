package com.mindhub.homebankingUno.controllers;

import com.mindhub.homebankingUno.dtos.AccountDTO;
import com.mindhub.homebankingUno.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
public class AccountController {
    @Autowired
    private AccountRepository repoAccount;

    @RequestMapping("/api/accounts")
    public List<AccountDTO> getAccounts() {
        return repoAccount.findAll().stream().map(AccountDTO::new).collect(toList());
    }

    @RequestMapping("/api/accounts/{id}")
    public AccountDTO getAccount (@PathVariable Long id) {
        return repoAccount.findById(id).map(AccountDTO::new).orElse(null);
    }
}
