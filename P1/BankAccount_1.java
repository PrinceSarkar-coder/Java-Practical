class BankAccount {
    private double balance;
    private int accountNo;

    BankAccount()  //default constructor
    {
        accountNo = 0;
        balance = 0.0;
    }

    BankAccount(int accountNo, double balance)   // parameterized constructor
    {
        this.accountNo = accountNo;
        setBalance(balance);

    }

    //getter
    public int getAccountNo()
    {
        return accountNo;
    }
    public double getBalance()
    {
        return balance;
    }

    //setter
    public void setBalance(double balance)
    {
        if(balance >= 0)
        {
            this.balance= balance;
        }
        else
        {
            System.out.println("Balance can't be negative");
        }
    }
    public void deposit(double amount)
    {
        if(amount > 0)
            {
                balance += amount;
            }
                else
            {
                System.out.println("Deposit amount must be positive");
            }
    }
}

public class BankAcc {
    public static void main(String[] args)
    {
        BankAccount acc1 = new BankAccount();   //default
        BankAccount acc2 = new BankAccount(1234, 1000);   //parimeterized

        acc1.deposit(20000);
        acc2.setBalance(-50);   //rejected

        System.out.println("Account No: " + acc1.getAccountNo() + "Balance: " + acc1.getBalance());
        System.out.println("Account No: " + acc2.getAccountNo() + "Balance: " + acc2.getBalance());
    }
}
