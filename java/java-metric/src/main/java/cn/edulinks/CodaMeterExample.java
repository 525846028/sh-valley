package cn.edulinks;

import java.util.concurrent.TimeUnit;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;

public class CodaMeterExample {

    public static void run() throws InterruptedException{
        final MetricRegistry registry = new MetricRegistry();

        System.out.println("This is Coda Meter Example!");

        ConsoleReporter report = ConsoleReporter.forRegistry(registry)
            .convertRatesTo(TimeUnit.SECONDS)
            .convertDurationsTo(TimeUnit.MILLISECONDS)
            .build();

        report.start(3, TimeUnit.SECONDS);
        Meter meterTps = registry.meter(MetricRegistry.name(CodaMeterExample.class, "request", "tps"));

        while(true){
            meterTps.mark();
            Thread.sleep(500);
        }
    }
}