package dev.lpa.concurrencychallenge;

import java.util.Random;

public class OrderConsumer implements  Runnable{
private ShoeWareHouse wareHouse;

    public OrderConsumer(ShoeWareHouse wareHouse) {
        this.wareHouse = wareHouse;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            Random random = new Random();
            try {
                Thread.sleep(random.nextInt(300, 5000));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            wareHouse.fulfillOrder();
        }
    }
}
