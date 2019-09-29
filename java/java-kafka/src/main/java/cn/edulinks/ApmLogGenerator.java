package cn.edulinks;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ApmLogGenerator {
    public static Map <Integer, String> app_map = new HashMap<>();
    public static Map <Integer, String> tc_map = new HashMap<>();

    public void ApmLogGenerator(){
        //Sample: app<1> trn<123> st<timestamp> tc<A001> cos<105>
        System.out.println("Apm Log Generator Run .");
    }
        
    public String getLog(){
        app_map.put(1, "APM");
        app_map.put(2, "FASTPAY");
        app_map.put(3, "N-SDD");
        app_map.put(4, "N-CCSVC");
        app_map.put(5, "N-RCC");

        
        tc_map.put(1, "TC0001");
        tc_map.put(2, "TC0002");
        tc_map.put(3, "TC0003");
        tc_map.put(4, "TC0004");
        tc_map.put(5, "TC0005");

        //获取随机数
        Random r = new Random();
        r.nextInt(10);

        return "app<" + app_map.get(r.nextInt(5)+1) + "> trn<" + r.nextInt(100000) + "> st<" 
            + System.currentTimeMillis() + "> tc<" + tc_map.get(r.nextInt(5)+1) + "> cos<" 
            + r.nextInt(2000) + ">";
    }
    
}