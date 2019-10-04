package cn.edulinks;
// //Metric examples by shiqiang

import cn.edulinks.GaugeExample.*;
import cn.edulinks.CounterExample.*;

public class Metric {

    /**
     * Uasge: java Metric method
     */
    public static void main(String[] args){
        
        System.out.println("Welcome use metrics example!");
        // GaugeExample ge = new GaugeExample();
        // ge.run();

        if(args.length < 1){
            System.out.println("Usage: Metric [method: counter|meter|gauge|histgram");
            // System.exit();
        }

        CounterExample ce = new CounterExample();
        ce.run();

        try {
            CodaMeterExample me = new CodaMeterExample();
            me.run();    
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}