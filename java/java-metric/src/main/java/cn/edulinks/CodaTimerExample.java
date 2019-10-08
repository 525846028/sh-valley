// package main.java;
package cn.edulinks;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.codahale.metrics.*;

public class CodaTimerExample {

    // 产生一个随机数
    public Integer getRandNumber(){
        Random r = new Random();
        return r.nextInt(1000);
    }

    public Long getResponseTime(){
        return System.currentTimeMillis();
    }

    public void run(){
        System.out.println("Timer Example running...");
        final MetricRegistry metrics = new MetricRegistry();
        final Timer responseTimes = metrics.timer(MetricRegistry.name(CodaHistogramsExample.class, "process-time"));

        ConsoleReporter reporter = ConsoleReporter.forRegistry(metrics).convertRatesTo(TimeUnit.SECONDS).convertDurationsTo(TimeUnit.MICROSECONDS).build();
        reporter.start(1, TimeUnit.SECONDS);
    
        try(final Timer.Context context = responseTimes.time()){
            for(int i = 0; i < 100000; i++){
                try {
                    Thread.sleep(this.getRandNumber());    
                    context.stop();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }    
        }
    }
}