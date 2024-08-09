package dev.lpa.runningthreads;

import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        //Simulating a situation where a installation can only occur when a download thread completes its task
        System.out.println("The main thread is running!");
        try {
            System.out.println("Paused for 1 second!");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Thread thread = new Thread(() -> {
            String tname = Thread.currentThread().getName();
            System.out.println(tname + " should take 10 dots to run.");
            for (int i = 0; i < 10; i++) {
                System.out.print(". ");
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    System.out.println("\nWhoops!! " + tname + " interrupted");
                    Thread.currentThread().interrupt();
                    // Java recommends that any method that catches an InterruptedException and is not
                    //prepared to deal with it immediately, should reassert the exception.
                    //Thread must reinterrupt itself or call interrupt on itself
                    return;
                }

            }
            System.out.println("\n" + tname + " completed.");
        });

        Thread installThread = new Thread(() -> {
            try {
                for (int i = 0; i < 3; i++) {
                    Thread.sleep(300);
                    System.out.println("Installation Step " + (i + 1) + " is completed");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "InstallThread");

        Thread installMonitor = new Thread(() -> {
            long now = System.currentTimeMillis();
            while (thread.isAlive()) {
                try {
                    Thread.sleep(1000);
                    if (System.currentTimeMillis() - now > 8000) {
                        thread.interrupt();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        System.out.println(thread.getName() + " starting");
        thread.start();
        installMonitor.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (!thread.isInterrupted()) {
            installThread.start();
        } else {
            System.out.println("Previous thread was interrupted, " + installThread.getName()
                    + " can't run.");
        }

//        System.out.println("Main thread continuing here.");
//
//        try{
//            Thread.sleep(4000);
//        }catch (InterruptedException e){
//            e.printStackTrace();
//        }
//        thread.interrupt();
        // Manually interrupting a thread
        // Cancel operations and close its resources
    }
}
