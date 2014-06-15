package Semaphores_12;

import java.util.concurrent.Semaphore;

/**
 * Semaphores
 *
 * Codes with minor comments are from http://www.caveofprogramming.com/youtube/
 * also freely available at
 * https://www.udemy.com/java-multithreading/?couponCode=FREE
 *
 * @author Z.B. Celik <celik.berkay@gmail.com>
 */
public class Connection {

    private static Connection instance = new Connection();
    //limit connections to 10
    //true means whichever the thread call the acquire first gets it first, 
    //in queue placed firts to obtain the permit.
    private Semaphore sem = new Semaphore(10, true);
    private int connections = 0;

    private Connection() {
    }

    public static Connection getInstance() {
        return instance;
    }

    public void connect() {
        try {
            sem.acquire(); // get permit decrease the sem value, if 0 wait for release
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        try {
            //if doConnect throws and exception is still releases the permit 
            //so we use a separate method here to increase the connections count.
            doConnect();
        } finally {
            sem.release();// release permit, increase the sem value and activate waiting thread
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
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //when exit doConnect method decrement number of connections
        synchronized (this) {//atamoic
            connections--;
            System.out.println("I'm done " + Thread.currentThread().getName() + " Connection is released , connection count: " + connections);
        }
    }
}
