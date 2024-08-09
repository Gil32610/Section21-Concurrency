package dev.lpa.threadchallenge;

public class ImpThread implements Runnable{
    @Override
    public void run() {
        for (int i = 0; i < 100; i+=2) {
                System.out.println("Odd Num = "+ i);
            try {
                Thread.currentThread().sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Odd thread stopped");
                return;
            }
        }
    }
}
