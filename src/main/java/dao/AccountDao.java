package dao;

import exception.DuplicateAccountIdException;
import exception.InsufficientBalanceException;
import exception.NoSuchAccountException;
import model.Account;

public interface AccountDao {

    /**
     *  Creates an account with the given account id with an initial balance of 0
     * @param accountId AccountId for the new account
     * @throws DuplicateAccountIdException Thrown when account id already exists in memory
     */
    Account createAccount(long accountId) throws DuplicateAccountIdException;

    /**
     * Creates an account with the given account id and the given initial balance
     * @param accountId  AccountId for the new account
     * @param initialBalance Initial balance for the new account
     * @throws DuplicateAccountIdException Thrown when account id already exists in memory
     */
    Account createAccount(long accountId, double initialBalance) throws DuplicateAccountIdException;

    /**
     * Withdraws money from given acount
     * @param accountId Account which will be withdrawn from
     * @param amount Amount of money to be withrdawn
     * @throws NoSuchAccountException Thrown when no such account exists
     * @throws InsufficientBalanceException Thrown when there isn't enough money to withdraw
     */
    void withdraw(long accountId,double amount) throws NoSuchAccountException, InsufficientBalanceException;

    /**
     * Deposits money to the given account
     * @param accountId Account which will be deposited to
     * @param amount Amount of money to be deposited
     * @throws NoSuchAccountException Thrown when no such account exists
     */
    void deposit(long accountId, double amount) throws NoSuchAccountException;
}
