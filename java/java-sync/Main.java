

// 测试synchronized控制线程同步问题
// 2019-9-3
// Synchronized 锁定的是对象，而不是代码段
// javac Main.java
// java Main

class Sync{
    public synchronized void test(){
        System.out.println("测试线程 开始 ...");
        try {
            Thread.sleep(1000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        System.out.println("测试线程 结束 ...");
    }
}

class MyThread extends Thread{
    private Sync sync;

    public MyThread(Sync sync){
        this.sync = sync;
    }

    public void run(){
        // Sync sync = new Sync();
        sync.test();
    }
}

public class Main{
    public static void main(String[] args){
        System.out.println(System.getProperty("java.class.path"));

        Sync sync = new Sync();

        for(int i = 0; i<3; i++){
            Thread thread = new MyThread(sync);
            thread.start();
        }
    }
}