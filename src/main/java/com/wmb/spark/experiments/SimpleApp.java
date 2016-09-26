package com.wmb.spark.experiments;

import org.apache.spark.api.java.*;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.function.Function;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * This is basically the sample application found at http://spark.apache.org/docs/2.0.0/quick-start.html with
 * some logging and other minor changes
 * It seemed like a good starting point for creating a spark development environment and to begin
 * tinkering with the API.
 */
public class SimpleApp {
    static Logger logger = Logger.getLogger(SimpleApp.class.getName());

    public static void main(String[] args) {

        String logFile = "data/sampledatafile.txt"; // Should be some file on your system
        logger.debug("###input filename: " + logFile);
        JavaSparkContext sc = null;
        SparkConf conf = null;
        try {
            conf = new SparkConf().setAppName("Simple Application");
            sc = new JavaSparkContext(conf);
            JavaRDD<String> logData = sc.textFile(logFile).cache();

            long numAs = logData.filter(new Function<String, Boolean>() {
                public Boolean call(String s) {
                    return s.contains("a");
                }
            }).count();

            long numBs = logData.filter(new Function<String, Boolean>() {
                public Boolean call(String s) {
                    return s.contains("b");
                }
            }).count();

            logger.info("Lines with a: " + numAs + ", lines with b: " + numBs);
        } catch (Exception e) {
            logger.error("SimpleApp is broken. ", e);
        } finally {
            try {
                sc.close();
            } catch (Exception e) {
            }
        }
    }
}