package dev.lpa.threadchallenge;

public class Main {
    public static void main(String[] args) {
        Thread evenNumbers = new SubThread();
        Runnable oddLambda = () ->{
            for (int i = 0; i < 100; i+=2) {
                System.out.println("Odd Num = "+ i);
                try {
                    Thread.currentThread().sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("Odd thread stopped");
                    return;
                }
            }
        };
        Thread oddNumbers = new Thread(new ImpThread());
        //Can easily be replaced by a lambda expression
        //Thread oddNumbers = new Thread(oddLambda);


        evenNumbers.start();
        oddNumbers.start();

        try{
            Thread.sleep(4000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        evenNumbers.interrupt();
        oddNumbers.interrupt();

    }
}
