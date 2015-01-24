package WaitAndNotify_8;

import java.util.Scanner;

/**
 * Some background knowledge<br>
 * Source: <em>http://www.programcreek.com/2009/02/notify-and-wait-example/</em>
 * <br><br>
 * {@code synchronized} keyword is used for exclusive accessing. To make a
 * method {@code synchronized}, simply add the {@code synchronized} keyword to its
 * declaration.<be>
 * Then no two invocations of synchronized methods on the same object can
 * interleave with each other.
 * <br>
 * Synchronized statements must specify the object that
 * provides the intrinsic lock. When {@code synchronized(this)} is used, you
 * have to avoid to synchronizing invocations of other objects' methods.
 * <br>
 * {@link Object#wait()} tells
 * the calling thread to give up the lock and go to sleep (not polling) until
 * some other thread enters the same lock and calls {@link Object#notify()}.
 * <br>
 * {@link Object#notify()} wakes up the first thread that called wait() on
 * the same object.
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
public class Processor {

    /*
     * public synchronized void getSomething(){ this.hello = "hello World"; }
     * public void getSomething(){ synchronized(this){ this.hello = "hello
     * World"; } }
     * two code blocks by specification, functionally identical.
     */


    public void produce() throws InterruptedException {
        synchronized (this) {
            System.out.println("Producer thread running ....");
            wait();//this.wait() is fine.
            System.out.println("Resumed.");
        }
    }

    public void consume() throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        Thread.sleep(2000);
        synchronized (this) {
            System.out.println("Waiting for return key.");
            scanner.nextLine();
            System.out.println("Return key pressed.");
            notify();
            Thread.sleep(5000);
            System.out.println("Consumption done.");
        }
    }
}
