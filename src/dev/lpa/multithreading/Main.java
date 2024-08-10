package dev.lpa.multithreading;

import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        StopWatch stopWatch = new StopWatch(TimeUnit.SECONDS);
        Thread green = new Thread(stopWatch::countDown, ThreadColor.ANSI_GREEN.name());
        Thread purple = new Thread(() -> stopWatch.countDown(12), ThreadColor.ANSI_PURPLE.name());
        Thread red = new Thread(stopWatch::countDown, ThreadColor.ANSI_RED.name());
        Thread white = new Thread(stopWatch::countDown, ThreadColor.ANSI_WHITE.name());
        Thread black = new Thread(() -> stopWatch.countDown(20), ThreadColor.ANSI_BLACK.name());
        Thread blue = new Thread(stopWatch::countDown, ThreadColor.ANSI_BLUE.name());
        white.start();
        black.start();
        blue.start();
        green.start();
        purple.start();
        red.start();
    }
}

class StopWatch {
    private TimeUnit timeUnit;

    public StopWatch(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }

    public void countDown() {
        countDown(30);
    }

    public void countDown(int unitCountDown) {
        String threadName = Thread.currentThread().getName();

        ThreadColor threadColor = ThreadColor.ANSI_RESET;
        try {
            threadColor = ThreadColor.valueOf(threadName);
        } catch (IllegalArgumentException ignore) {

        }
        String color = threadColor.color();
        for (int i = unitCountDown; i > 0; i--) {
            try {
                timeUnit.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.printf("%s%s Thread : i = %d%n", color, threadName, i);
        }


    }
}
