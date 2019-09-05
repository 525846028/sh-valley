package com.edulinks;

import org.apache.log4j.Logger;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;

public class Baseline {
    private static Logger logger = Logger.getLogger(Baseline.class);
    public static void main(String[] args){
        System.out.println(System.getProperty("java.class.path"));
        logger.debug("Baseline Project start.");

        System.out.println("Welcome use baseline project");
    }
}