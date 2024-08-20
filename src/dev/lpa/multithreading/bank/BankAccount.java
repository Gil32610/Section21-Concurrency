package dev.lpa.multithreading.bank;

public class BankAccount {

    private double balance;
    private String name;

    private final Object lockName = new Object();
    private final Object lockBalance = new Object();
    //Manage locking for different fields. When a thread instance invokes a sync. statement, this lock will be given.

    public BankAccount(String name, double balance) {
        this.name = name;
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        synchronized (lockName) {
            this.name = name;
            System.out.println("The name " + this.name + " was properly set!");
        }
    }

    public double getBalance() {
        return balance;
    }
    // Solving caching and thread interference using the Synchronized keyword

    // Critical section is the code that's referencing a shared resource like a variable
    //Only one thread at a time should be able to execute a critical section
    //Thread-safe: All critical sections are synchronized!
    public void deposit(double amount) {
        try {
            System.out.println("Deposit - Talking to teller");
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException();
        }
        // Synchronized statement requires an instance or instance field
        // Objects instances have intrinsic locks.
        // The thread acquires the lock of an instance when executing Sync. methods on the instances
        //  or passing instances on  Sync. statements
        // Lock is released when the thread exists from the above-mentioned
        // Only one thread can acquire a lock at a time, preventing all other threads to access an instance's state.


        //Locking on local variables is useless, since threads do not share stack memory
        // The lock would be on the thread stack instance only

        //Locks removes the dependencies of the critical fields types!!!
        synchronized (lockBalance) {
            double origBalance = balance;
            balance += amount;
            System.out.printf("STARTING BALANCE: %.0f, DEPOSIT (%.0f)" +
                    " NEW BALANCE: %.0f%n", origBalance, amount, balance);
            addPromoDollars(amount);
        }
        // Limiting sync. to critical sections of the code. More granular control!

    }

    private void addPromoDollars(double amount) {
        // Reentrant Synchronization prevents indefinite blockage
        // When a thread has acquired a lock and tries to nest another lock request it won't be blocked
        // The current thread already has the lock!
        if (amount >= 5000) {
            synchronized (lockBalance) {
                System.out.println("You earned a promotional deposit!!!");
                balance += 25.0;
            }
        }
    }

    //Different invocations of synchronized methods on the same object are guaranteed not to interleave
// When a thread is executing a synchronized method, any other synchronized method being called by other threads
    // will be blocked and suspend their execution until the first thread is done.
    public synchronized void withdraw(double amount) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException();
        }
        double origBalance = balance;
        if (balance >= amount) {
            balance -= amount;
            System.out.printf("STARTING BALANCE: %.0f, WITHDRAWAL (%.0f)" +
                    " NEW BALANCE: %.0f%n", origBalance, amount, balance);
        } else {
            System.out.printf("STARTING BALANCE: %.0f, WITHDRAWAL (%.0f)" +
                    " INSUFFICIENT FUNDS!", origBalance, amount);
        }

    }

}
