package dev.lpa;

import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        var currentThread = Thread.currentThread();

        System.out.println(currentThread);
        printThreadState(currentThread);

        currentThread.setName("MainGuy");
        currentThread.setPriority(Thread.MAX_PRIORITY);
        printThreadState(currentThread);

        CustomThread customThread = new CustomThread();
        customThread.start();
        //Native modifier indicates tha a method's source code isn't written in Java, such as C or C++.
        // Java uses it for platform-specific operations at system-level, interface with hardware and computationally-intensive tasks
        Runnable myRunnable = () -> {
            for (int i = 0; i < 8; i++) {
                System.out.print(" 2");
                try{
                    TimeUnit.MILLISECONDS.sleep(1000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        };

        Thread myThread = new Thread(myRunnable);
        myThread.start();
        for (int i = 0; i < 3; i++) {
            System.out.print(" 0");
            try {
                TimeUnit.SECONDS.sleep(1);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }


    }

    public static void printThreadState(Thread thread){
        System.out.println("-".repeat(30));
        System.out.printf("Thread ID: %s %n",thread.getId());
        System.out.printf("Thread Name: %s %n",thread.getName());
        System.out.printf("Thread Priority: %s %n",thread.getPriority());
        System.out.printf("Thread State: %s %n",thread.getState());
        System.out.printf("Thread Group: %s %n",thread.getThreadGroup());
        System.out.printf("Thread Is Alive: %s %n",thread.isAlive());
        System.out.println("-".repeat(30));
    }
}
