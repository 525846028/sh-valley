package cn.edulinks;

//Synchronized 示例
public class SyncExample implements Runnable {
    static int i = 0;

    public synchronized void increase(){
        i++;
    }

    @Override
    public void run(){
        for(int j=0; j<10000; j++){
            increase();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        SyncExample se = new SyncExample();

        Thread t1 = new Thread(se);
        Thread t2 = new Thread(se);
        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println(i);
    }
}
