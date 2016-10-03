package com.wmb.spark.experiments;

/**
  * a simple app to test the scala develop environment configuration
  */

import scala.math.random

object SimpleScalaApp {

  def main(args: Array[String]) {
    var results = 0.0
    for (i <- 1 to 1000000) {
      val x = random * 3
      results += x
    }
    println("results: "+results)
  }
}