package cn.edulinks;
// //Metric examples by shiqiang

import cn.edulinks.GaugeExample.*;

import com.codahale.metrics.*;
// import com.codahale.metrics.MetricRegistry;

import cn.edulinks.CounterExample.*;

public class Metric {

    /**
     * Uasge: java Metric method
     */
    public static void main(String[] args){
        String user_choice = "";

        System.out.println("Welcome use metrics example!");
        // GaugeExample ge = new GaugeExample();
        // ge.run();

        if(args.length < 1){
            System.out.println("Usage: Metric [method: counter|meter|gauge|histgram");
            System.exit(1);
        }else{
            user_choice = args[0];
        }

        switch (user_choice) {
            case "counter":
                CounterExample ce = new CounterExample();
                ce.run();                    
                break;
            case "meter":
                try {
                    CodaMeterExample me = new CodaMeterExample();
                    me.run();    
                }catch(Exception e){
                    e.printStackTrace();
                }
                break;
            case "gauge":
                try{
                    CodaGaugeExample cg = new CodaGaugeExample();
                    cg.run(new MetricRegistry(), "CodaGuagueExample");
                }catch(Exception e){
                    e.printStackTrace();
                }
                break;
            default:
                System.out.println("No option are avaliable for your input.");
                break;
        }

    }
}