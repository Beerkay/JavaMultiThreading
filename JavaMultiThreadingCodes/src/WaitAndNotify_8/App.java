package WaitAndNotify_8;

/**
 * {@link Object#wait()} and {@link Object#notify()} in Java; low-level
 * multi-threading methods of the {@link java.lang.Object} class
 * that allow you to have one or more threads sleeping, only to be woken up by
 * other threads at the right moment. Extremely useful for avoiding those
 * processor-consuming "polling loops".
 *
 * Codes with minor comments are from <em>http://www.caveofprogramming.com/youtube/</em><br>
 * also freely available at
 * <em>https://www.udemy.com/java-multithreading/?couponCode=FREE</em>
 *
 * @author Z.B. Celik <celik.berkay@gmail.com>
 */
public class App {

    public static void main(String[] args) throws InterruptedException {
        final Processor processor = new Processor();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    processor.produce();
                } catch (InterruptedException ignored) {}
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    processor.consume();
                } catch (InterruptedException ignored) {}
            }
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }
}
