package com.security.atm;

import com.security.atm.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Server side application to mimic ATM functionalities.
 */
@SpringBootApplication
public class SecurityAtmApplication {

    @Autowired
    private AccountRepository accountRepository;

    public static void main(String[] args) {
        SpringApplication.run(SecurityAtmApplication.class, args);
    }
}