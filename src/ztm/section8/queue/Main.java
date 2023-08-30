import ztm.section8.queue.ArrayBlockingQueueI;

import java.util.concurrent.ArrayBlockingQueue;

//package ztm.section8.queue;
//
//import java.util.*;
//import java.util.concurrent.*;
//
//public class Main {

//
//    // Integer must be replaced with Payload
//    private static Map<Topic, ArrayBlockingQueueI<Integer>> kafka;
//
//    private static List<Topic> topicList = List.of(
//            new Topic("odd_even", 5),
//            new Topic("calculator", 10),
//            new Topic("factorial", 10),
//            new Topic("prime_factor", 10)
//    );
//
//    public static void main(String[] args) throws InterruptedException {
//
//        kafka = new HashMap<>(20);
//        topicList.forEach(topic -> {
//            kafka.put(topic, new ArrayBlockingQueueI<>(topic.getPartitions()));
//        });
//
//        final int numberOfCores = Runtime.getRuntime().availableProcessors();
//        final double blockingCoefficient = 0.9;
//        final int poolSize = (int) (numberOfCores / (1 - blockingCoefficient));
//
//        List<Integer> numbers = generateNumbers();
//        //List<Callable<Integer>> taskList = new ArrayList<>();
//       // produced tasks
//        for(Integer number: numbers) {
//            Callable<Integer> task = () -> oddOrEven(number);
//            kafka.get(topicList.get(0)).offer(number);
//        }
//
//        final ExecutorService executorPool = Executors.newFixedThreadPool(poolSize);
//        Collection<Integer> collection = kafka.get(topicList.get(0));
//        final List<Future<String>> aqiValues = executorPool.invokeAll(collection, 10000, TimeUnit.SECONDS);
//
//    }
//
//    // producer for prime_factor
//    // producer for calculator
//    //produce for factorial
//
//
//
////    public static void createTopic(String topicName, int partitions) {
////        Topic topic = new Topic(topicName, partitions);
////        kafka.put(topic, new ArrayBlockingQueueI<>(partitions));
////    }
//
//
//}
