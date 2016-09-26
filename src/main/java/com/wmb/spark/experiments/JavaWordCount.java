package com.wmb.spark.experiments;

import org.apache.log4j.Logger;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.sql.SparkSession;
import scala.Tuple2;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

/**
 * adapted from a spark example bundled with the spark source org.apache.spark.examples.JavaWordCount
 * Added a logger and shuffled the coded around a little
 *
 */
public final class JavaWordCount {
    private static Logger logger = Logger.getLogger(SimpleApp.class.getName());
    private static final Pattern SPACE = Pattern.compile(" ");

    public static void main(String[] args) throws Exception {

        String logFile = "data/sampledatafile.txt"; // Should be some file on your system
        SparkSession spark = null;

        try {
            spark = SparkSession
                    .builder()
                    .appName("JavaWordCount")
                    .getOrCreate();

            JavaRDD<String> lines = spark.read().textFile(logFile).javaRDD();

            JavaRDD<String> words = lines.flatMap(new FlatMapFunction<String, String>() {
                public Iterator<String> call(String s) {
                    return Arrays.asList(SPACE.split(s)).iterator();
                }
            });

            JavaPairRDD<String, Integer> ones = words.mapToPair(
                    new PairFunction<String, String, Integer>() {
                        public Tuple2<String, Integer> call(String s) {
                            return new Tuple2<>(s, 1);
                        }
                    });

            JavaPairRDD<String, Integer> counts = ones.reduceByKey(
                    new Function2<Integer, Integer, Integer>() {
                        public Integer call(Integer i1, Integer i2) {
                            return i1 + i2;
                        }
                    });

            List<Tuple2<String, Integer>> output = counts.collect();
            for (Tuple2<?, ?> tuple : output) {
                logger.info(tuple._1() + ": " + tuple._2());
            }
        } catch (Exception e) {
            logger.error("badness there was an error", e);
        } finally {
            // not entirely sure this necessary to do in a finally
            spark.stop();
        }
        spark.stop();
    }
}