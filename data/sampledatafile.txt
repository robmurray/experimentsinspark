# experimentsonspark
exploratory development on Spark. Using java scala and python.

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
* Note - this process can be improved but is good enough for this first development session

### For spark + scala
TBD

### For spark + python
TBD

