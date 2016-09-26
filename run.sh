#!/usr/bin/env bash
export SPARK_JAVA_OPTS=-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=5005
spark-submit --class "com.wmb.spark.experiments.SimpleApp"  --master local[4] target/experimentsonspark-1.0-SNAPSHOT.jar
