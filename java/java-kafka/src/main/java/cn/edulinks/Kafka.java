package cn.edulinks;

import java.text.ParseException;

import org.apache.commons.cli.*;

public class Kafka {
    public static void main(String[] args){
        System.out.println("Kafka Example start.");

        //解析命令行参数, -type producer/consumer
        Option opt1 = new Option("t", "type", true, "Choose Kafka Type");
        opt1.setRequired(false);

        Options options = new Options();
        options.addOption(opt1);

        CommandLine cli = null;
        CommandLineParser cliParser = new DefaultParser();
        HelpFormatter helpFormatter = new HelpFormatter();

        try {
            cli = cliParser.parse(options, args);
        }catch(Exception e){
            helpFormatter.printHelp(">>>>>> kafka demo options", options);
            e.printStackTrace();
        }

        if(cli.hasOption("t")){
            String t = cli.getOptionValue("t", "producer");
            switch (t) {
                case "consumer":
                    try {
                        KafkaConsumerDemo kc = new KafkaConsumerDemo();
                        //consume continuously
                        kc.getPartitionsForTopic();
                        // kc.run();       

                        //consume every 10s
                        // kc.run10s();
                    }catch(Exception e){
                        e.printStackTrace();
                    }                            
                    break;
                case "producer":
                default:
                    KafkaProducerDemo kp = new KafkaProducerDemo();
                    // kp.run();
                    kp.async_send10s();
                    break;
            }
        }

    }
}