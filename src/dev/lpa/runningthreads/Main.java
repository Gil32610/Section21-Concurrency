package dev.lpa.runningthreads;

import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        System.out.println("The main thread is running!");
        try {
            System.out.println("Paused for 1 second!");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Thread thread = new Thread(()->{
            String tname = Thread.currentThread().getName();
            System.out.println(tname+ " should take 10 dots to run.");
            for (int i = 0; i < 10; i++) {
                System.out.print(". ");
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                    System.out.println("A. State = " + Thread.currentThread().getState());
                } catch (InterruptedException e) {
                    System.out.println("\nWhoops!! " + tname + " interrupted");
                    System.out.println("A1. State = " + Thread.currentThread().getState());
                    return;
                }

            }
            System.out.println("\n"+ tname + " completed.");
        });
        System.out.println(thread.getName()+ " starting");
        thread.start();

        long now = System.currentTimeMillis();
        while(thread.isAlive()){
            System.out.println("\nWaiting for thread to complete");
            try {
                Thread.sleep(1000);
                System.out.println("B. State = "+ thread.getState());
                if(System.currentTimeMillis() - now > 2000){
                    thread.interrupt();
                }
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }

        System.out.println("C. State = " + thread.getState());
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
