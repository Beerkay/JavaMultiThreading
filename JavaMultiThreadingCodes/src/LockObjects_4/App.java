package LockObjects_4;

/**
 * Codes with minor comments are from http://www.caveofprogramming.com/youtube/
 * also freely available at
 * https://www.udemy.com/java-multithreading/?couponCode=FREE
 *
 * @author Z.B. Celik <celik.berkay@gmail.com>
 */
public class App {

    public static void main(String[] args) {
        System.out.println("Synchronized Objects: ");
        Worker worker = new Worker();
        worker.main();
        System.out.println("Synchronized Methods: ");
        WorkerMethodsSynchronized worker2 = new WorkerMethodsSynchronized();
        worker2.main();
    }
}
