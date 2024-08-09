package dev.lpa.threadchallenge;

public class ImpThread implements Runnable{
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            if(i%2!=0){
                System.out.println("Odd Num = "+ i);
            }
            try {
                Thread.currentThread().sleep(100);
            } catch (InterruptedException e) {
                return;
            }
        }
    }
}
