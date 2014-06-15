package WaitAndNotify_8;

import java.util.Scanner;

/**
 * Some background knowledge
 * Source:http://www.programcreek.com/2009/02/notify-and-wait-example/
 * synchronized keyword is used for exclusive accessing. To make a method
 * synchronized, simply add the synchronized keyword to its declaration. Then no
 * two invocations of synchronized methods on the same object can interleave
 * with each other. Synchronized statements must specify the object that
 * provides the intrinsic lock. When synchronized(this) is used, you have to
 * avoid to synchronizing invocations of other objects' methods. wait() tells
 * the calling thread to give up the lock and go to sleep (not polling) until
 * some other thread enters the same lock and calls notify(). notify() wakes up
 * the first thread that called wait() on the same object.
 *
 * Codes with minor comments are from http://www.caveofprogramming.com/youtube/
 * also freely available at
 * https://www.udemy.com/java-multithreading/?couponCode=FREE
 *
 * @author Z.B. Celik <celik.berkay@gmail.com>
 */
public class Processor {

    /**
     * public synchronized void getSomething(){ this.hello = "hello World"; }
     * public void getSomething(){ synchronized(this){ this.hello = "hello
     * World"; } , two code blocks by specification, functionally identical.
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
        }
    }
}
