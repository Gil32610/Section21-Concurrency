package dev.lpa.concurrencychallenge;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ShoeWareHouse {

    public static List<Shoe> productList = List.of(new AdidasShoe(), new NikeShoe(), new FilaShoe());
    private Queue<Order> orderList;

    private Order currentOrder = null;

    public ShoeWareHouse(){
        this.orderList = new LinkedList<>();
    }
    public synchronized void  receiveOrder(Order order){
        while(orderList.size()>=5) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        orderList.add(order);
        System.out.println("Receiving current order:");
        System.out.println(order);
        notifyAll();
    }

    public synchronized void fulfillOrder(){
        while(orderList.isEmpty()){
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        currentOrder=orderList.poll();
        System.out.println("Processing current order:");
        System.out.println(currentOrder);
        notifyAll();
    }





}
