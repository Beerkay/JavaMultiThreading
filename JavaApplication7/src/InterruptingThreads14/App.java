package InterruptingThreads14;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * how to interrupt running threads in Java using the built-in thread
 * interruption mechanism.
 *
 * Source:http://www.javamex.com/tutorials/threads/thread_interruption.shtml
 * Incidentally, it is important not to confuse thread interruption with either
 * software interrupts (where the CPU automatically interrupts the current
 * instruction flow in order to call a registered piece of code periodically— as
 * in fact happens to drive the thread scheduler) and hardware interrupts (where
 * the CPU automatically performs a similar task in response to some hardware
 * signal).
 *
 * Codes with minor comments are from http://www.caveofprogramming.com/youtube/
 * also freely available at
 * https://www.udemy.com/java-multithreading/?couponCode=FREE
 *
 * @author Z.B. Celik <celik.berkay@gmail.com>
 */
public class App {

    public static void main(String[] args) throws InterruptedException {

        System.out.println("Starting.");

        ExecutorService executer = Executors.newCachedThreadPool();

        Future<?> fu = executer.submit(new Callable<Void>() {

            @Override
            public Void call() throws Exception {
                Random ran = new Random();
                for (int i = 0; i < 1E8; i++) {
                    if (Thread.currentThread().isInterrupted()) {
                        System.out.println("Interrupted!");
                        break;
                    }
                    Math.sin(ran.nextDouble());
                }
                return null;
            }
        });

        executer.shutdown();
        Thread.sleep(500);
        executer.shutdownNow();

        executer.awaitTermination(1, TimeUnit.DAYS);
        System.out.println("Finished.");
    }

}
