// package main.java;
package cn.edulinks;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.codahale.metrics.*;

public class CodaGaugeExample {
    static final MetricRegistry metrics = new MetricRegistry();
    private List<String> stringList = new LinkedList<String>();
    Gauge<Integer> gauge = Metrics.newGauge(GaugeExample.class, "list-size-gauge", new Gauge<Integer>(){
        @Override
        public Integer value(){
            return stringList.size();
        }
    });

    public void inputElement(String input){
        stringList.add(input);
    }

    public static void run(){
        System.out.println("GaugeExample running...");
        ConsoleReporter reporter = ConsoleReporter.forRegistry(metrics).convertRatesTo(TimeUnit.SECONDS).convertDurationsTo(TimeUnit.MICROSECONDS).build();
        reporter.start(1, TimeUnit.SECONDS);

        GaugeExample ge = new GaugeExample();
        for(int i = 0; i < 10; i++){
            try {
                ge.inputElement(String.valueOf(i));
                // System.out.println(String.valueOf(i));
                // System.out.println(ge.stringList.size());
                Thread.sleep(1000);    
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}