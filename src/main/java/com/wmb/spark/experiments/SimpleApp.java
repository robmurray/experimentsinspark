package com.wmb.spark.experiments;

import org.apache.spark.api.java.*;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.function.Function;

/**
 * This is basically the sample application found at http://spark.apache.org/docs/2.0.0/quick-start.html.
 * It seemed like a good starting point for creating a spark development environment and to begin
 * tinkering with the API.
 */
public class SimpleApp {
    public static void main(String[] args) {
        String logFile = "data/sampledatafile.txt"; // Should be some file on your system
        SparkConf conf = new SparkConf().setAppName("Simple Application");
        JavaSparkContext sc = new JavaSparkContext(conf);
        JavaRDD<String> logData = sc.textFile(logFile).cache();

        long numAs = logData.filter(new Function<String, Boolean>() {
            public Boolean call(String s) { return s.contains("a"); }
        }).count();

        long numBs = logData.filter(new Function<String, Boolean>() {
            public Boolean call(String s) { return s.contains("b"); }
        }).count();

        System.out.println("Lines with a: " + numAs + ", lines with b: " + numBs);
    }
}