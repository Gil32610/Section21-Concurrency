package dev.lpa.multithreading;

import java.util.concurrent.TimeUnit;

public class CacheData {
    //memory consistency!!!
    private volatile boolean flag = false;
    //Modifier for class variables that indicates that the value may be changed by multiple threads
    //Ensures that variables are always read and written from main memory, not any thread caches!
    public void toggleFlag(){
        flag = !flag;
    }
    public boolean isReady(){
        return flag;
    }

    public static void main(String[] args) {
        CacheData example = new CacheData();

        Thread writerThread = new Thread(()-> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            example.toggleFlag();
            System.out.println("A. Flag set to " + example.isReady());
        });

        Thread readerThread = new Thread(()-> {
            while (!example.isReady()){
                //keeping it busy
            }
            System.out.println("B. Flag is " + example.isReady());
        });

        writerThread.start();
        //memory inconsistency! Thread cached memory may contain  copy of heap variable which have not yet been flushed!!
        readerThread.start();
    }

}
