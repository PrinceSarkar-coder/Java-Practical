class InvalidAmountException extends Exception {
    InvalidAmountException(String msg) { super(msg); }
}

class InsufficientBalanceException extends Exception {
    InsufficientBalanceException(String msg) { super(msg); }
}

class Account {
    static final double MIN_BALANCE = 500;      // final constant
    private static int totalAccounts = 0;       // static field

    private final int accNo;                    // final variable
    private String name;
    private double balance;

    Account(String name, double balance) throws InvalidAmountException {
        if (balance < MIN_BALANCE)
            throw new InvalidAmountException("Opening balance must be at least " + MIN_BALANCE);
        this.name = name;
        this.balance = balance;
        this.accNo = ++totalAccounts;
    }

    void deposit(double amt) throws InvalidAmountException {
        if (amt <= 0)
            throw new InvalidAmountException("Deposit must be positive");
        balance += amt;
        System.out.println("Deposited: " + amt);
    }

    void withdraw(double amt) throws InvalidAmountException, InsufficientBalanceException {
        if (amt <= 0)
            throw new InvalidAmountException("Withdrawal must be positive");
        if (balance - amt < MIN_BALANCE)
            throw new InsufficientBalanceException("Insufficient balance. Minimum " + MIN_BALANCE + " must remain.");
        balance -= amt;
        System.out.println("Withdrawn: " + amt);
    }

    final void display() {                      // final method
        System.out.println("Account No: " + accNo + ", Name: " + name + ", Balance: " + balance);
    }

    static int getTotalAccounts() {             // static method
        return totalAccounts;
    }
}

public class BankApp {
    public static void main(String[] args) {
        Account acc = null;

        try {
            acc = new Account("Prince", 2000);
            acc.deposit(500);
            acc.withdraw(1500);
            acc.withdraw(5000);                 // throws exception
        } catch (InvalidAmountException e) {
            System.out.println("Invalid amount: " + e.getMessage());
        } catch (InsufficientBalanceException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            System.out.println("Transaction finished");
        }

        try {
            Account bad = new Account("Riya", 100);   // below minimum
        } catch (InvalidAmountException e) {
            System.out.println("Invalid amount: " + e.getMessage());
        }

        acc.display();
        System.out.println("Total accounts: " + Account.getTotalAccounts());
    }
}
