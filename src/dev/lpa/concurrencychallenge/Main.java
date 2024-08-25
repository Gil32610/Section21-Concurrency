package dev.lpa.concurrencychallenge;

public class Main {
    public static void main(String[] args) {
        ShoeWareHouse wareHouse = new ShoeWareHouse();
        Thread producer = new Thread(new OrderProducer(wareHouse));

        Thread consumer1 = new Thread(new OrderConsumer(wareHouse));
        Thread consumer2 = new Thread(new OrderConsumer(wareHouse));

        producer.start();
        consumer1.start();
        consumer2.start();
    }
}
