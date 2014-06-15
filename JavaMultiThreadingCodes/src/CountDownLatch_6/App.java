package CountDownLatch_6;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * CountDownLatch Java class to synchronize your threads’ activities.
 *
 * Source:
 * (http://stackoverflow.com/questions/17827022/what-is-countdown-latch-in-java-multithreading)
 * Any thread, usually main thread of application, which calls
 * CountDownLatch.await() will wait until count reaches zero or its interrupted
 * by another thread. All other thread are required to do count down by calling
 * CountDownLatch.countDown() once they are completed or ready.
 *
 * As soon as count reaches zero, Thread awaiting starts running. One of the
 * disadvantage of CountDownLatch is that its not reusable once count reaches to
 * zero you can not use CountDownLatch any more.
 *
 * Use CountDownLatch when one thread like main thread, require to wait for one
 * or more thread to complete, before it can start processing.
 *
 * Classical example of using CountDownLatch in Java is any server side core
 * Java application which uses services architecture, where multiple services
 * are provided by multiple threads and application can not start processing
 * until all services have started successfully.
 *
 * Codes with minor comments are from http://www.caveofprogramming.com/youtube/
 * also freely available at
 * https://www.udemy.com/java-multithreading/?couponCode=FREE
 *
 * @author Z.B. Celik <celik.berkay@gmail.com>
 */
class Processor implements Runnable {

    private CountDownLatch latch;

    public Processor(CountDownLatch latch) {
        this.latch = latch;
    }

    public void run() {
        System.out.println("Started.");

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        latch.countDown();
    }
}

public class App {

    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(3);
        ExecutorService executor = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 3; i++) {
            executor.submit(new Processor(latch));
        }
        try {
            // Application’s main thread waits, till other service threads which are 
            // as an example responsible for starting framework services have completed started all services.
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Completed.");
    }

}
