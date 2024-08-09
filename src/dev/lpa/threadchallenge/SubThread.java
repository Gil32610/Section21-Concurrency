package dev.lpa.threadchallenge;

public class SubThread extends Thread{
    @Override
    public void run() {
        for (int i = 1; i < 100; i+=2) {
                System.out.println("EvenNum = " + i);
            try {
                Thread.currentThread().sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Even thread stopped");
                return;
            }
        }
    }
}
