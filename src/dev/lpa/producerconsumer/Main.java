package dev.lpa.producerconsumer;

import java.util.Random;

//Service
class MessageRepository {
    //Consumer and Producer shared resources of interest.
    private String message;
    private boolean hasMessage = false;

    public synchronized String read() {
        while (!hasMessage) {

        }
        hasMessage = false;
        return message;
    }

    public synchronized void write(String message) {
        while (hasMessage) {

        }
        hasMessage = true;
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


    }
}
