package dev.lpa.producerconsumer;

import java.util.Random;

//Service
class MessageRepository {
    //Consumer and Producer shared resources of interest.
    private String message;
    private boolean hasMessage = false;

    public synchronized String read() {

        //Any code that is in a sync method that's sitting in a loop (waiting for something to change),
        //should be calling the wait method!
        // Suspends sync claims on the object, while thread waits for some condition to be met (doesn't block other threads)
        while (!hasMessage) {
            try {
                //Causes the current thread to place itself on a wait set condition, releasing the synchronization claims
                // Allows any other threads to acquire the lock (monitor) while on waiting state/set.
                wait();
                // dormant
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        //When a thread gets waked up, it doesn't mean that the condition it was waiting on was met
        // That's why we call the wait method inside a loop. The thread will check the condition it is interested in repeatedly
        hasMessage = false;
        notifyAll();
        //Wakes up all threads that are waiting on the local monitor (on the shared resource sync.)
        //Don't use it when multiple threads are performing the same task (performance issue!!)
        return message;
    }

    public synchronized void write(String message) {
        while (hasMessage) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        hasMessage = true;
        notifyAll();
        this.message = message;
    }
}

//Producer
class MessageWriter implements Runnable {
    private MessageRepository outgoingMessage;
    private final String text = """
                    Água mole, pedra dura
                    Tanto bate até que fura.""";

    public MessageWriter(MessageRepository outgoingMessage) {
        this.outgoingMessage = outgoingMessage;
    }

    @Override
    public void run() {
        Random random = new Random();
        String[] lines = text.split("\n");
        for (int i = 0; i < lines.length; i++) {
            outgoingMessage.write(lines[i]);
            try {
                //writes a message then waits a bit before writing the next one
                Thread.sleep(random.nextInt(500, 2000));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        outgoingMessage.write("Finished");
    }
}

//Consumer

class MessageReader implements Runnable {
    private MessageRepository incomingMessage;


    public MessageReader(MessageRepository incomingMessage) {
        this.incomingMessage = incomingMessage;
    }

    @Override
    public void run() {
        Random random = new Random();
        String latestMessage = "";
        do {

            try {
                Thread.sleep(random.nextInt(500, 2000));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            latestMessage = incomingMessage.read();
            System.out.println(latestMessage);

        } while (!latestMessage.equals("Finished"));
    }
}

public class Main {

    public static void main(String[] args) {
        MessageRepository messageRepository = new MessageRepository();

        Thread reader = new Thread(new MessageReader(messageRepository));
        Thread writer = new Thread(new MessageWriter(messageRepository));
        //This code is prone to Deadlocks: While the consumer wants to read the message, the producer wants to
        //write a message (shared resource). Both threads can possibly be stuck in an eternal while loop!

        //Thread reader could acquire the lock on the shared resource via the synchronized method. When this happens
        //Thread writer would not be able to change the flag to false, causing a deadlock.

        reader.start();
        writer.start();

        // Using wait, notify and notifyAll methods to manage monitor locks (sync.)


    }
}
