package dev.lpa.multithreading.bank;

public class BankAccount {

    private double balance;

    public BankAccount(double balance) {
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }
    // Solving caching and thread interference using the Synchronized keyword

    // Critical section is the code that's referencing a shared resource like a variable
    //Only one thread at a time should be able to execute a critical section
    //Thread-safe: All critical sections are synchronized!
    public  void deposit(double amount){
        try {
            System.out.println("Deposit - Talking to teller");
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException();
        }
        synchronized(this){
            double origBalance = balance;
            balance += amount;
            System.out.printf("STARTING BALANCE: %.0f, DEPOSIT (%.0f)"+
                    " NEW BALANCE: %.0f%n", origBalance,amount, balance);
        }

    }
    //Different invocations of synchronized methods on the same object are guaranteed not to interleave
// When a thread is executing a synchronized method, any other synchronized method being called by other threads
    // will be blocked and suspend their execution until the first thread is done.
    public synchronized void withdraw(double amount){
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException();
        }
        double origBalance = balance;
        if(balance>=amount){
            balance -= amount;
            System.out.printf("STARTING BALANCE: %.0f, WITHDRAWAL (%.0f)"+
                    " NEW BALANCE: %.0f%n", origBalance, amount,balance);
        }else{
            System.out.printf("STARTING BALANCE: %.0f, WITHDRAWAL (%.0f)"+
                    " INSUFFICIENT FUNDS!", origBalance, amount);
        }

    }
}
