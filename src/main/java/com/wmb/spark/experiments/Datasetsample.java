
package com.wmb.spark.experiments;

import org.apache.log4j.Logger;
import org.apache.spark.ml.classification.LogisticRegression;
import org.apache.spark.ml.classification.LogisticRegressionModel;
import org.apache.spark.sql.*;

/**
 * a quick look at  LogisticRegressionModel and Dataset, Column, ... nothing much special happening here
 *
 *
 */
public class Datasetsample {
    private static Logger logger = Logger.getLogger(Datasetsample.class.getName());

    public static void main(String[] args) {
        SparkSession sparkSession = null;
        try {

            sparkSession = SparkSession
                    .builder()
                    .appName("workingwithDataset")
                    .getOrCreate();

            logger.debug("loading the training data");
            Dataset<Row> training = sparkSession.read().format("libsvm").load("data/sample_libsvm_data.txt");

            if(logger.isDebugEnabled()) {
                // playing around with the API. Does not seem to be an interactive mode for JAVA
                // next best thing. debug in the IDE
                logger.debug("##############################################");
                String[] columns = training.columns();
                if (columns != null && columns.length > 0) {
                    String columnString = "";
                    for (int count = 0; count < columns.length; count++) {
                        columnString += "|" + columns[count];
                    }
                    columnString += "|";
                    logger.debug("column names: " + columnString);
                }
                //training.createTempView("testview");
                training.printSchema();
                Column col1 =training.col(columns[0]);
                Column col2 =training.col(columns[1]);
                training.show();
                logger.debug("description: "+col1.desc());
                logger.debug("description: "+col2.desc());
                Column col3 =col1.alias("sometext");
                /*if(col3.equals(col1)){
                    logger.debug("they be equal");
                }
                if(col3==col1){
                    logger.debug("equals and the same object");
                }
                */
                logger.debug("# of 1's: "+training.filter(col1.$eq$eq$eq(1)).count());
                logger.debug("# of 0's: "+training.filter(col1.$eq$eq$eq(0)).count());
                RelationalGroupedDataset rgd =training.groupBy(col1);
                logger.debug("the count: "+rgd.count().count());
                logger.debug("record count: "+training.count());
                logger.debug("##############################################");
            }

            LogisticRegression lr = new LogisticRegression()
                    .setMaxIter(10)
                    .setRegParam(0.3)
                    .setElasticNetParam(0.8);


            LogisticRegressionModel lrModel = lr.fit(training);

            logger.info("classes: "+lrModel.numClasses());
            logger.info("features: "+ lrModel.numFeatures());

            logger.info("Coefficients: "
                    + lrModel.coefficients() + " Intercept: " + lrModel.intercept());

        }catch (Exception e){
            logger.error("something happened",e);
        }finally {
            try{
                sparkSession.stop();
            }catch (Exception e){}
        }
    }
}
