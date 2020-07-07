package com.security.atm.services;

import com.security.atm.entities.Account;
import com.security.atm.viewmodels.AccountVm;
import com.security.atm.viewmodels.CredentialsVm;
import com.security.atm.viewmodels.TransactionVm;
import com.security.atm.repositories.AccountRepository;
import com.security.atm.utilities.EncryptionUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.List;

import static org.apache.commons.lang3.Validate.isTrue;
import static org.apache.commons.lang3.Validate.notNull;

@Service
public class AccountService implements IAccountService{

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private EncryptionUtility encryptionUtility;

    /**
     * Checks if the given user name is available
     */
    @Override
    public boolean isUserNameAvailable(String userName) throws Exception {
        List<Account> accounts = accountRepository.getAccountByUsername(userName);
        if(accounts.size()>0)
            throw new IllegalArgumentException("Username is already taken !!");
        return true;
    }

    /**
     * Saves the account details entered through registration form.
     */
    @Override
    public boolean save(AccountVm accountDetails) throws Exception {
        Account account = new Account(accountDetails.getUsername(),encryptionUtility.decrypt(accountDetails.getPassword()),accountDetails.getFirstName(),
                accountDetails.getLastName(),accountDetails.getCity(),accountDetails.getBalance());
        if(account.getBalance()<=0){
            throw new IllegalArgumentException("Initial balance must be greater than USD 0.00 !!");
        }
        if(account.getBalance()> 4294967295.99){
            throw new IllegalArgumentException("Amount deposited should be less than USD 4294967296.00 !!");
        }
        if(isUserNameAvailable(account.getUsername()) && account.getBalance()>0) {
            accountRepository.save(account);
            return true;
        }

        throw new Exception("Username is already taken !!");
    }

    /**
     * Gets account by id
     */
    @Override
    public Account getAccountById(int id){
        return accountRepository.findById(id).get();
    }

    /**
     * Checks if the entered credentials are valid.
     */
    @Override
    public AccountVm isValidCredentials(CredentialsVm credentialsVm) throws Exception {
        notNull(credentialsVm.getUsername());
        notNull(credentialsVm.getPassword());
        String password = encryptionUtility.encrypt(encryptionUtility.decrypt(credentialsVm.getPassword()));
        Account account = accountRepository.getAccountByUsernameAndPassword(credentialsVm.getUsername(),password);

        if(account!=null) {
            AccountVm result = new AccountVm();
            result.setId(account.getId());
            result.setFirstName(account.getFirstName());
            result.setLastName(account.getLastName());
            return result;
        }
        return null;
    }

    /**
     * Checks if the password and account Id are valid
     */
    private boolean isValidPassword(int accountId,String password) throws Exception{
        password = encryptionUtility.encrypt(encryptionUtility.decrypt(password));
        Account account = accountRepository.getAccountByIdAndPassword(accountId,password);
        return account != null;
    }

    /**
     * Returns an account object from  which the balance can be inferred.
     */
    @Override
    public AccountVm getAccountBalance(TransactionVm details) throws Exception{
        notNull(details.getPassword());
        notNull(details.getAccountId());

        if(!isValidPassword(details.getAccountId(),details.getPassword())){
            throw new IllegalArgumentException("Sorry! Invalid credentials !!");
        }
        Account account = accountRepository.findById(details.getAccountId()).get();
        AccountVm accountVm = new AccountVm();
        final DecimalFormat df = new DecimalFormat("#.00");
        accountVm.setBalance(df.format(account.getBalance()));
        return accountVm;
    }

    /**
     * Updates balance after letting the user deposit money.
     */
    @Override
    public boolean deposit(TransactionVm details) throws Exception {

        notNull(details.getPassword());
        notNull(details.getAccountId());
        notNull(details.getAmount());
        isTrue(String.valueOf(details.getAmount()).matches("(([0])|([1-9][0-9]*))[\\.][0-9][0-9]"));

        if(Double.parseDouble(details.getAmount())<= 0){
            throw new IllegalArgumentException("Amount should be greater than USD 0.00 !!");
        }
        if(Double.parseDouble(details.getAmount())> 4294967295.99){
            throw new IllegalArgumentException("Amount should be less than USD 4294967296.00 !!");
        }
        if(!isValidPassword(details.getAccountId(),details.getPassword())){
            throw new IllegalArgumentException("Sorry! Invalid credentials !!");
        }

        double balance = Double.parseDouble(getAccountBalance(details).getBalance());
        balance = balance + Double.parseDouble(details.getAmount());
        accountRepository.updateBalance(balance,details.getAccountId());
        return true;
    }

    /**
     * Updates balance after letting the user withdraw money, if sufficient funds are available.
     */
    @Override
    public boolean withdraw(TransactionVm details) throws Exception {
        notNull(details.getPassword());
        notNull(details.getAccountId());
        notNull(details.getAmount());
        isTrue(String.valueOf(details.getAmount()).matches("(([0])|([1-9][0-9]*))[\\.][0-9][0-9]"));
        if(Double.parseDouble(details.getAmount())<= 0){
            throw new IllegalArgumentException("Amount should be greater than USD 0.00 !!");
        }

        if(Double.parseDouble(details.getAmount())> 4294967295.99){
            throw new IllegalArgumentException("Amount should be less than USD 4294967296.00 !!");
        }

        if(!isValidPassword(details.getAccountId(),details.getPassword())){
            throw new IllegalArgumentException("Sorry! Invalid credentials !!");
        }
        double balance = Double.parseDouble(getAccountBalance(details).getBalance());
        if(balance<Double.parseDouble(details.getAmount())){
            throw new IllegalArgumentException("Sorry! You don't have enough balance to perform the transaction !!");
        }
        balance = balance -  Double.parseDouble(details.getAmount());
        accountRepository.updateBalance(balance,details.getAccountId());
        return true;

    }

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }
}
