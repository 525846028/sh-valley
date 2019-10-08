// package main.java;
package cn.edulinks;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

import com.codahale.metrics.*;

public class CodaCounterExample {
    private List<String> stringList = new LinkedList<String>();

    public void inputElement(String input){
        stringList.add(input);
    }

    public void run(){
        System.out.println("\nCounter Example running...");
        final MetricRegistry metrics = new MetricRegistry();
        final Counter elementCounters = metrics.counter("element-counter");

        ConsoleReporter reporter = ConsoleReporter.forRegistry(metrics).convertRatesTo(TimeUnit.SECONDS).convertDurationsTo(TimeUnit.MICROSECONDS).build();
        reporter.start(1, TimeUnit.SECONDS);
    
        for(int i = 0; i < 10; i++){
            try {
                this.inputElement(String.valueOf(i));
                elementCounters.inc();
                Thread.sleep(1000);    
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        for(int i = 0; i < 10; i++){
            try {
                // this.inputElement(String.valueOf(i));
                elementCounters.dec();
                Thread.sleep(1000);    
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}