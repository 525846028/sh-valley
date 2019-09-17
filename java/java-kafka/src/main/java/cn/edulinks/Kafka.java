package cn.edulinks;

public class Kafka {
    public static void main(String[] args){
        System.out.println("Kafka Example start.");

        KafkaProducerDemo kp = new KafkaProducerDemo();
        kp.run();
    }
}