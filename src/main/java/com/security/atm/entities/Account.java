package com.security.atm.entities;

import javax.persistence.*;

import com.security.atm.utilities.EncryptionUtility;

import static org.apache.commons.lang3.Validate.*;

/**
 * Instantiates the accounts table used in the database.
 */
@Entity
@Table(name="accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="username")
    private String username;
    @Column(name="password")
    private String password;
    @Column(name="balance")
    private double balance;
    @Column(name="firstname")
    private String firstName;
    @Column(name="lastname")
    private String lastName;
    @Column(name="city")
    private String city;

    protected Account(){
    }

    public Account(String username,String password,String firstName,String lastName,String city,String balance){
        notNull(password);
        notNull(username);
        notNull(firstName);
        notNull(lastName);
        notNull(city);
        notNull(balance);
        isTrue(username.matches("[_\\-\\.0-9a-z]+"));
        isTrue(password.matches("[_\\-\\.0-9a-z]+"));
        isTrue(balance.matches("(([0])|([1-9][0-9]*))[\\.][0-9][0-9]"));
        inclusiveBetween(1,127,username.length());
        inclusiveBetween(1,127,password.length());
        isTrue(firstName.trim().length()!=0);
        isTrue(city.trim().length()!=0);
        EncryptionUtility encryptionUtility = new EncryptionUtility();
        String pwd = encryptionUtility.encrypt(password);
        this.username = username;
        this.password = pwd;
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.balance = Double.parseDouble(balance);
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public double getBalance() {
        return balance;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
