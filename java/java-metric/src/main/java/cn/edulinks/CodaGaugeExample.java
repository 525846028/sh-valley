// package main.java;
package cn.edulinks;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

import com.codahale.metrics.*;

public class CodaGaugeExample {
    // private final Queue queue;
    // static final MetricRegistry metrics = new MetricRegistry();
    private List<String> stringList = new LinkedList<String>();
    // Gauge<Integer> gauge = Metrics.newGauge(GaugeExample.class, "list-size-gauge", new Gauge<Integer>(){
    //     @Override
    //     public Integer value(){
    //         return stringList.size();
    //     }
    // });
    public CodaGaugeExample(MetricRegistry metrics, String name){
        this.stringList = new LinkedList<String>();
        metrics.register(MetricRegistry.name(CodaGaugeExample.class, name, "size"),
            new Gauge<Integer>(){
                @Override
                public Integer getValue(){
                    System.out.println("Call Gauge Internal");
                    return stringList.size();
                }
            });

            ConsoleReporter reporter = ConsoleReporter.forRegistry(metrics).convertRatesTo(TimeUnit.SECONDS).convertDurationsTo(TimeUnit.MICROSECONDS).build();
            reporter.start(1, TimeUnit.SECONDS);
        }

    public void inputElement(String input){
        stringList.add(input);
    }

    public void run(MetricRegistry metrics, String name){
        System.out.println("\nGaugeExample running...");
    }
}