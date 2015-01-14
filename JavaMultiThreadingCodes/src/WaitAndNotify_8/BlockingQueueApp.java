package WaitAndNotify_8;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Source:
 * <em>http://stackoverflow.com/questions/2536692/a-simple-scenario-using-wait-and-notify-in-java</em>
 * <br><br>
 * Firstly, you need to ensure that any calls to {@code wait() or notify()} are
 * within a synchronized region of code (with the {@code wait() or notify()}
 * calls being synchronized on the "same" object).
 * The reason for this (other than the standard thread safety concerns) is
 * due to something known as a missed signal.
 * <br><br>
 * An example of this, is that a thread may call {@link WaitAndNotify_8.BlockingQueue#put}
 * when the queue happens to be full, it then checks the condition, sees that
 * the queue is full, however
 * before it can block another thread is scheduled. This second thread then
 * {@link WaitAndNotify_8.BlockingQueue#take()}'s an element from the queue,
 * and notifies the waiting threads that the
 * queue is no longer full. Because the first thread has already checked the
 * condition. However, it will simply call {@code wait()} after being
 * re-scheduled, even though it could make progress.
 * <br>
 * By synchronizing on a shared object, you can ensure that this problem does
 * not occur,as the second thread's {@link WaitAndNotify_8.BlockingQueue#take()}
 * call will not be able to make progress until the first thread has actually
 * blocked.
 * <br>
 * You must hold the lock (synchronized) before invoking wait/notify. Threads
 * also have to acquire lock before waking.
 * <br><br>
 * More: In order to wait on an object, we must be synchronized on that object.
 * <br>
 * But our thread will automatically release the lock temporarily while waiting.
 * Calling {@code wait()} means that our thread will be suspended until it is
 * "notified". Our thread will be "notified", and thus woken up, when another
 * thread calls {@code notify()} on the object that we're waiting on (in this
 * case, the connection list).
 * When our thread wakes up, it automatically regains the
 * lock. We can now check again that the list is not empty, and if it isn't,
 * safely take out the first connection. This checking and removing will be
 * atomic because we have the lock on the list.
 *
 *
 * @author Z.B. Celik <celik.berkay@gmail.com>
 */
//For the other version of the implementation please check
// LowLevelProducerConsumer_9.App
class BlockingQueue<T> {

    private Queue<T> queue = new LinkedList<>();
    private int capacity;
    private Lock lock = new ReentrantLock();
    //condition variables
    private Condition notFull = lock.newCondition();
    private Condition notEmpty = lock.newCondition();

    public BlockingQueue(int capacity) {
        this.capacity = capacity;
    }

    public void put(T element) throws InterruptedException {
        lock.lock();
        try {
            while (queue.size() == capacity) {
                System.out.println("queue is full cannot put");
                notFull.await(); //releases lock
            }

            queue.add(element);
            System.out.println("Added to the queue " + element);
            notEmpty.signal(); //calls waiting thread on the same object
        } finally {
            lock.unlock();
        }
    }

    public T take() throws InterruptedException {
        lock.lock();
        try {
            while (queue.isEmpty()) {
                System.out.println("queue is empty, cannot take");
                notEmpty.await(); //releases lock
            }

            T item = queue.remove();
            System.out.println("Removed to the queue " + item);
            notFull.signal(); //calls waiting thread on same object
            return item;
        } finally {
            lock.unlock();
        }
    }
}

@SuppressWarnings("InfiniteLoopStatement")
public class BlockingQueueApp {

    public static void main(String[] args) throws InterruptedException {
        final BlockingQueue<Integer> blockingQueue = new BlockingQueue<>(10);
        final Random random = new Random();
        Thread t1 = new Thread(new Runnable() {
            public void run() {
                try {
                    while (true) {
                        blockingQueue.put(random.nextInt(10));
                    }
                } catch (InterruptedException ignored) {}
            }
        });

        Thread t2 = new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(1000);//wait for putting to the queue first
                } catch (InterruptedException ex) {
                    System.out.println("Exception " + ex.getMessage());
                }
                try {
                    while (true) {
                        blockingQueue.take();
                    }
                } catch (InterruptedException ignored) {}
            }
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }
}
