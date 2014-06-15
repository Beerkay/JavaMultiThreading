package CallableAndFuture_13;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * Source:http://java-x.blogspot.com.tr/2006/11/java-5-concurrency-callable-and-future.html
 * Till Java 1.4, threads could be implemented by either implementing Runnable
 * or extending Thread. This was quite simple, but had a serious limitation -
 * They have a run method that cannot return values. In order to side-step this,
 * most programmers use side-effects (writing to a file etc.) to mimic returning
 * values to the invoker of the thread. Java 5 introduces the Callable
 * interface, that allows users to return values from a thread
 *
 * Runnable vs Callable<T>
 * Runnable Introduced in Java 1.0 Callable<T> Introduced in Java 1.5 as part of
 * java.util.concurrent library
 *
 * Runnable cannot be parameterized Callable is a parameterized type whose type
 * parameter indicates the return type of its run method Classes implementing
 *
 * Runnable needs to implement run() method, classes implementing Callable needs
 * to implement call() method
 *
 * Runnable.run() returns no value, Callable.call() returns a value of Type T
 *
 * Runnable can not throw checked exceptions, Callable can throw checked
 * exceptions
 *
 * @author Z.B. Celik <celik.berkay@gmail.com>
 */
class CallableImpl implements Callable<Integer> {

    private int myName;

    CallableImpl(int i) {
        myName = i;
    }

    @Override
    public Integer call() {
        for (int i = 0; i < 10; i++) {
            System.out.println("Thread : " + getMyName() + " value is : " + i);
        }
        return getMyName();
    }

    public int getMyName() {
        return myName;
    }

    public void setMyName(int myName) {
        this.myName = myName;
    }
}

public class CallableTester {

    public static void main(String[] args) {

        Callable<Integer> callable = new CallableImpl(2);
        ExecutorService executor = new ScheduledThreadPoolExecutor(1);
        Future<Integer> future = executor.submit(callable);

        try {
            System.out.println("Future value: " + future.get());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
            executor.isTerminated();
        }
    }

}
