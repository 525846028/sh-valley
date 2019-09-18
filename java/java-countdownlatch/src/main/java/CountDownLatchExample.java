import java.util.concurrent.CountDownLatch;

public class CountDownLatchExample {
    static CountDownLatch cdl = new CountDownLatch(2);

    public static void main(String[] args) throws InterruptedException {
        new Thread(new Runnable(){
            @Override
            public void run(){
                try {
                    System.out.println("Test Thread 1: 1");
                    Thread.sleep(1000);
                    System.out.println("Test Thread 1: 2");
                    cdl.countDown();
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(new Runnable(){
            @Override
            public void run(){
                try {
                    System.out.println("Test Thread 2: 1");
                    Thread.sleep(1000);
                    System.out.println("Test Thread 2: 2");
                    cdl.countDown();
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
        }).start();        

        cdl.await();
        System.out.println( "Thread Main ended");
    }
}