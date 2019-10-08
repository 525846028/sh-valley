// package main.java;
package cn.edulinks;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.codahale.metrics.*;

public class CodaHistogramsExample {

    // 产生一个随机数
    public Integer getRandNumber(){
        Random r = new Random();
        return r.nextInt(1000);
    }

    public void run(){
        System.out.println("Histograms Example running...");
        final MetricRegistry metrics = new MetricRegistry();
        final Histogram randomNumbers = metrics.histogram(MetricRegistry.name(CodaHistogramsExample.class, "randomNumbers"));

        ConsoleReporter reporter = ConsoleReporter.forRegistry(metrics).convertRatesTo(TimeUnit.SECONDS).convertDurationsTo(TimeUnit.MICROSECONDS).build();
        reporter.start(1, TimeUnit.SECONDS);
    
        for(int i = 0; i < 100000; i++){
            try {
                randomNumbers.update(this.getRandNumber());
                Thread.sleep(10);    
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}