package it.unibo.bank.impl;

import it.unibo.bank.api.AccountHolder;
import it.unibo.bank.api.BankAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Test class for the {@link StrictBankAccount} class.
 */
class TestStrictBankAccount {

    // Create a new AccountHolder and a StrictBankAccount for it each time tests are executed.
    private AccountHolder mRossi;
    private BankAccount bankAccount;

    /**
     * Prepare the tests.
     */
    @BeforeEach
    public void setUp() {
        this.mRossi = new AccountHolder("Mario", "Rossi", 1);
        this.bankAccount = new StrictBankAccount(mRossi, 0.0);
    }

    /**
     * Test the initial state of the StrictBankAccount.
     */
    @Test
    public void testInitialization() {
        assertEquals("Mario", mRossi.getName());
        assertEquals("Rossi", mRossi.getSurname());
        assertEquals(1, mRossi.getUserID());

        assertEquals(mRossi, bankAccount.getAccountHolder());
        assertEquals(0.0, bankAccount.getBalance());
        assertEquals(0, bankAccount.getTransactionsCount());
    }

    /**
     * Perform a deposit of 100€, compute the management fees, and check that the balance is correctly reduced.
     */
    @Test
    public void testManagementFees() {
        final  double testManagementFee = 5;
        final  double testTransactionFee = 0.1;
        final double testReducedbalance; 
        final int currentTransactions;
        this.bankAccount.deposit(mRossi.getUserID(), 100);
        currentTransactions = this.bankAccount.getTransactionsCount();
        this.bankAccount.chargeManagementFees(mRossi.getUserID());
        testReducedbalance = 100 - (testManagementFee + currentTransactions*testTransactionFee);
        assertEquals(testReducedbalance, bankAccount.getBalance());
        assertEquals(0, bankAccount.getTransactionsCount());
    }

    /**
     * Test that withdrawing a negative amount causes a failure.
     */
    @Test
    public void testNegativeWithdraw() {
        try {
           this.bankAccount.deposit(mRossi.getUserID(), -100);
           this.bankAccount.withdraw(mRossi.getUserID(), 100);
           fail("Cannot withdraw from a negative amount");
        } catch (IllegalArgumentException e) {
            System.out.println("OK! Catched correct exception while trying to do a withdraw from a negative amount");
        }
    }

    /**
     * Test that withdrawing more money than it is in the account is not allowed.
     */
    @Test
    public void testWithdrawingTooMuch() {
        try {
            this.bankAccount.deposit(mRossi.getUserID(), 100);
            this.bankAccount.withdraw(mRossi.getUserID(), 200);
            fail("Cannot do a withdraw greater than the amount");
        } catch (IllegalArgumentException e) {
            System.out.println("OK! Catched correct exception while trying to do a withdraw greater than the amount");
        }
        
    }
}
