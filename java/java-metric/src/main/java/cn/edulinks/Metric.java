package cn.edulinks;
// //Metric examples by shiqiang

import cn.edulinks.GaugeExample.*;
import cn.edulinks.CounterExample.*;

public class Metric {

    public static void main(String[] args){
        
        System.out.println("Welcome use metrics example!");
        GaugeExample ge = new GaugeExample();
        ge.run();

        CounterExample ce = new CounterExample();
        ce.run();


    }
}