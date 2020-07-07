package com.security.atm.repositories;

import com.security.atm.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * SQL functions related to the accounts table.
 */
@Repository
public interface AccountRepository extends JpaRepository<Account,Integer> {

    Account getAccountByUsernameAndPassword(String userName, String password);
    List<Account> getAccountByUsername(String userName);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "UPDATE accounts SET balance = :amount WHERE id = :accountId",
            nativeQuery = true)
    void updateBalance(@Param("amount")double amount, @Param("accountId")int accountId);

    Account getAccountByIdAndPassword(int id,String password);
}
