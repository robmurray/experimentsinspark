# experimentsonspark
exploratory development on Spark. Using java scala and python.

## Initial thoughts
It appears development on spark will end up being a combination of working in the shell (interactive mode) and in an IDE to write the application code. i.e. test out the idea in the shell then convert into app code.

## Setting up a spark development environment in intellij

### For spark + java
* install intellij
* install java 8+
* download spark the latest prebuilt version of spark (V2.0.0 at the writing of this doc)
** http://spark.apache.org/downloads.html **
* add the [spark install dir]/bin to your path

#### Run/Debug
For this round of development I opted for the remote debugging approach instead of attempting some 
more integrated development configuration. 

* enable remote debugging by setting the jvm option "SPARK_JAVA_OPTS". <br>
<code>export SPARK_JAVA_OPTS=-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=5005</code>
<br>(notice suspend=y. this will pause the execution util a remote process attaches to the jvm)

* From Intellij create a remote Run?Debug configuration using the port specified above, 5005
* to run any of the applications in the project at the command line type <br>
<code>$ spark-submit --class "fully qualfied app name"  --master local[4] target/jar file name.jar</code> <br>

example<br>

<code>$ spark-submit --class "com.wmb.spark.experiments.SimpleApp"  --master local[4] target/experimentsonspark-1.0-SNAPSHOT.jar</code>

* since suspend=y the JVM will pause until a remote process attaches. From intellij launch your newly create remote run/debug configuration

<br><b>Note - there is a multitude of opportunities that come to mind to improve this process however for this first development session the above is good enough.</b>
<br>
####TODO
* address 
    16/09/26 13:19:48 WARN SparkConf: 
    SPARK_JAVA_OPTS was detected (set to '-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=5005').
    This is deprecated in Spark 1.0+.
    Please instead use:
     - ./spark-submit with conf/spark-defaults.conf to set defaults for an application
     - ./spark-submit with --driver-java-options to set -X options for a driver
     - spark.executor.extraJavaOptions to set -X options for executors
     - SPARK_DAEMON_JAVA_OPTS to set java options for standalone daemons (master or worker)


### For spark + scala
TBD

### For spark + python
TBD

