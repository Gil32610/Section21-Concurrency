package dev.lpa.threadchallenge;

public class Main {
    public static void main(String[] args) {
        Thread evenNumbers = new SubThread();

        Thread oddNumbers = new Thread(new ImpThread());

        evenNumbers.start();
        oddNumbers.start();

        long now = System.currentTimeMillis();
        while (evenNumbers.isAlive()||oddNumbers.isAlive()) {
            try {
                Thread.sleep(100);
                if (System.currentTimeMillis() - now > 500) {
                    evenNumbers.interrupt();
                    oddNumbers.interrupt();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }
}
