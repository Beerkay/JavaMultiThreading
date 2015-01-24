package Semaphores_12;

import java.util.concurrent.Semaphore;

/**
 * Semaphores
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
public class Connection {

    private static Connection instance = new Connection();
/*
    limit connections to 10
    true means whichever thread gets first in the waiting pool (queue)
    waiting to acquire a resource, is first to obtain the permit.

    Note that I called it a pool!
    The reason is when you say "Queue", you're saying that things are
    scheduled to be FIFO (First In First Out) .. which is not always the case
    here!
    When you initialize the semaphore with Fairness, by setting its second
    argument to true, it will treat the waiting threads like FIFO.
    But,
    it doesn't have to be that way if you don't set on the fairness. the JVM
    may schedule the waiting threads in some other manner that it sees best
    (See the Java specifications for that).
*/
    private Semaphore sem = new Semaphore(10, true);
    private int connections = 0;

    private Connection() {
    }

    public static Connection getInstance() {
        return instance;
    }

    public void connect() {
        try {

            // get permit decrease the sem value, if 0 wait for release
            sem.acquire();

            //if doConnect throws and exception is still releases the permit
            //so we use a separate method here to increase the connections count
            doConnect();

        } catch (InterruptedException ignored) {
        } finally {
            //release permit, increase the sem value and activate waiting thread
            sem.release();
        }
    }

    public void doConnect() {
        synchronized (this) { //atomic
            connections++;
            System.out.println("Current connections (max 10 allowed): " + connections);
        }
        try {
            //do your job
            System.out.println("Working on connections " + Thread.currentThread().getName());
            Thread.sleep(2000);
        } catch (InterruptedException ignored) {}
        //when exit doConnect method decrement number of connections
        synchronized (this) {//atomic
            connections--;
            System.out.println("I'm done " + Thread.currentThread().getName() + " Connection is released , connection count: " + connections);
        }
    }
}
