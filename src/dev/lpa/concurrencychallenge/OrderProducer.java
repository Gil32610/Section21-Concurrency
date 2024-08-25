package dev.lpa.concurrencychallenge;

import java.util.Random;

public class OrderProducer implements Runnable{
    private ShoeWareHouse wareHouse;


    public OrderProducer(ShoeWareHouse wareHouse){
        this.wareHouse = wareHouse;
    }
    @Override
    public void run() {
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            Shoe nextShoe = ShoeWareHouse.productList.get(random.nextInt(0,2));
            Order nextOrder = new Order(i,nextShoe,i+2);
            wareHouse.receiveOrder(nextOrder);
            try {
                Thread.sleep(random.nextInt(300, 1000));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
