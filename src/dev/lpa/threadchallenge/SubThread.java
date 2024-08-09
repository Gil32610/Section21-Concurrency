package dev.lpa.threadchallenge;

public class SubThread extends Thread{
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            if(i%2 == 0){
                System.out.println("EvenNum = " + i);
            }
            try {
                Thread.currentThread().sleep(100);
            } catch (InterruptedException e) {
                return;
            }
        }
    }
}
