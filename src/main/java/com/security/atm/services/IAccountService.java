package com.security.atm.services;

import com.security.atm.entities.Account;
import com.security.atm.viewmodels.AccountVm;
import com.security.atm.viewmodels.CredentialsVm;
import com.security.atm.viewmodels.TransactionVm;
import java.util.List;

public interface IAccountService{
    boolean save(AccountVm account) throws Exception;
    List<Account> findAll();
    AccountVm isValidCredentials(CredentialsVm credentialsVm) throws Exception;
    boolean isUserNameAvailable(String userName) throws Exception;
    AccountVm getAccountBalance(TransactionVm details) throws Exception;
    Account getAccountById(int id);
    boolean deposit(TransactionVm details) throws Exception;
    boolean withdraw(TransactionVm details) throws Exception;
}
