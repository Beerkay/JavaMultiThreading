package ProducerConsumer_7;

/**
 * Producer-Consumer pattern in Java using the {@link java.util.concurrent
 * .ArrayBlockingQueue} Java class.
 * <br><br>
 * Producer-Consumer is the situation where one or more threads are producing
 * data items and adding them to a shared data store of some kind while one or
 * more other threads process those items, removing them from the data store.
 * <br><br>
 * Codes with minor comments are from
 * <a href="http://www.caveofprogramming.com/youtube/">
 * <em>http://www.caveofprogramming.com/youtube/</em>
 * </a>
 * <br>
 * also freely available at
 * <a href="https://www.udemy.com/java-multithreading/?couponCode=FREE">
 *     <em>https://www.udemy.com/java-multithreading/?couponCode=FREE</em>
 * </a>
 *
 * @author Z.B. Celik <celik.berkay@gmail.com>
 */
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

@SuppressWarnings("InfiniteLoopStatement")
public class App {

    /**
     * Thread safe implementation of {@link java.util.Queue} data structure so
     * you do not need to worry about synchronization.
     * More specifically {@link java.util.concurrent.BlockingQueue}
     * implementations are thread-safe. All queuing methods are atomic in nature
     * and use internal locks or other forms of concurrency control. If
     * BlockingQueue is not used queue is shared data structure either
     * {@code synchronized} or {@code wait() notify()} (see Course 8) should be
     * used.
     * Java 1.5 introduced a new concurrency library {@link java.util.concurrent}
     * which was designed to provide a higher level abstraction over
     * the wait/notify mechanism.
     */
    private static BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);

    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(new Runnable() {
            public void run() {
                try {
                    producer();
                } catch (InterruptedException ignored) {}
            }
        });

        Thread t2 = new Thread(new Runnable() {
            public void run() {
                try {
                    consumer();
                } catch (InterruptedException ignored) {}
            }
        });
        t1.start();
        t2.start();
//        t1.join();
//        t2.join();

        // Pause for 30 seconds and force quitting the app (because we're
        // looping infinitely)
        Thread.sleep(30000);
        System.exit(0);
    }

    private static void producer() throws InterruptedException {
        Random random = new Random();
        while (true) {//loop indefinitely
            queue.put(random.nextInt(100));//if queue is full (10) waits
        }
    }

    private static void consumer() throws InterruptedException {
        Random random = new Random();
        while (true) {
            Thread.sleep(100);
            if (random.nextInt(10) == 0) {
                Integer value = queue.take();//if queue is empty waits
                System.out.println("Taken value: " + value + "; Queue size is: " + queue.size());
            }
        }
    }
}
