import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BankAccount {
    private double balance;
    private final List<String> transactions;

    public BankAccount(double initialBalance) {
        if (initialBalance < 0) {
            throw new IllegalArgumentException("Initial balance cannot be negative.");
        }
        this.balance = initialBalance;
        this.transactions = new ArrayList<>();
        transactions.add("Account opened with balance: $" + initialBalance);
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive.");
        }
        balance += amount;
        transactions.add("Deposited: $" + amount);
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive.");
        }
        if (amount > balance) {
            throw new IllegalArgumentException("Insufficient balance.");
        }
        balance -= amount;
        transactions.add("Withdrawn: $" + amount);
    }

    public List<String> getTransactionHistory() {
        return transactions;
    }

    public static void main(String[] args) {
        BankAccount account = new BankAccount(100); // Start with $100
        System.out.println("🏦 Bank Account Created with Initial Balance: $" + account.getBalance());

        account.deposit(50);
        System.out.println("✅ Deposited $50. New Balance: $" + account.getBalance());

        account.withdraw(30);
        System.out.println("✅ Withdrawn $30. New Balance: $" + account.getBalance());

        System.out.println("\n📋 Transaction History:");
        for (String transaction : account.getTransactionHistory()) {
            System.out.println(transaction);
        }
    }
}

// ✅ JUnit 5 Test Cases
class BankAccountTest {
    private BankAccount account;

    @BeforeEach
    void setUp() {
        account = new BankAccount(100); // Start with $100
    }

    @Test
    void testDeposit() {
        account.deposit(50);
        assertEquals(150, account.getBalance(), 0.001);
    }

    @Test
    void testWithdraw() {
        account.withdraw(40);
        assertEquals(60, account.getBalance(), 0.001);
    }

    @Test
    void testInsufficientFunds() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> account.withdraw(200));
        assertEquals("Insufficient balance.", exception.getMessage());
    }

    @Test
    void testNegativeDeposit() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> account.deposit(-10));
        assertEquals("Deposit amount must be positive.", exception.getMessage());
    }

    @Test
    void testTransactionHistory() {
        account.deposit(50);
        account.withdraw(30);
        List<String> history = account.getTransactionHistory();
        assertTrue(history.contains("Deposited: $50"));
        assertTrue(history.contains("Withdrawn: $30"));
    }
}
