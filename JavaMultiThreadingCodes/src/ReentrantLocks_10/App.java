package ReentrantLocks_10;

/**
 * the ReentrantLock class in Java as an alternative to synchronized code
 * blocks. ReentrantLocks let you do all the stuff that you can do with
 * synchronized, wait and notify, plus some more stuff besides that may come in
 * handy from time to time.
 *
 * Source:
 * http://docs.oracle.com/javase/1.5.0/docs/api/java/util/concurrent/locks/ReentrantLock.html
 *
 * ReentrantLock Extended capabilities include:
 *
 * The ability to have more than one condition variable per monitor. Monitors
 * that use the synchronized keyword can only have one. This means reentrant
 * locks support more than one wait()/notify() queue. The ability to make the
 * lock "fair". "[fair] locks favor granting access to the longest-waiting
 * thread. Otherwise this lock does not guarantee any particular access order."
 * Synchronized blocks are unfair. The ability to check if the lock is being
 * held. The ability to get the list of threads waiting on the lock.
 *
 * The disadvantages of reentrant locks are:
 *
 * Need to add import statement. Need to wrap lock acquisitions in a try/finally
 * block. This makes it more ugly than the synchronized keyword. The
 * synchronized keyword can be put in method definitions which avoids the need
 * for a block which reduces nesting. For more complete comparison of
 * reentrantLocks and synchronized see
 * http://guruzon.com/1/concurrency/explicit-lock-locking/difference-between-synchronized-and-reentrantlock-in-java
 *
 * Codes with minor comments are from http://www.caveofprogramming.com/youtube/
 * also freely available at
 * https://www.udemy.com/java-multithreading/?couponCode=FREE
 *
 * @author Z.B. Celik <celik.berkay@gmail.com>
 */
public class App {

    public static void main(String[] args) throws Exception {
        final Runner runner = new Runner();
        Thread t1 = new Thread(new Runnable() {
            public void run() {
                try {
                    runner.firstThread();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            public void run() {
                try {
                    runner.secondThread();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();
        runner.finished();
    }

}
