

// 测试synchronized控制线程同步问题
// 2019-9-3

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
    public void run(){
        Sync sync = new Sync();
        sync.test();
    }
}

public class Main{
    public static void main(String[] args){
        for(int i = 0; i<3; i++){
            Thread thread = new MyThread();
            thread.start();
        }
    }
}