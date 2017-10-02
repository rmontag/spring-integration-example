Spring Integration
Spring Boot

Build an executable JAR
-----------------------


You can run the application from the command line with Maven. 
Or you can build a single executable JAR file that contains all the necessary dependencies, classes, and resources, and run that. 

This makes it easy to ship, version, and deploy the service as an application throughout the development lifecycle, across different environments, and so forth.

You can run the application using 

mvn spring-boot:run

Or you can build the JAR file with 

mvn clean package

Then you can run the JAR file:

java -jar target/gs-integration-0.1.0.jar


Run the application
-------------------

Set HTTP proxy settings via JMv arguments:

-Dhttps.proxySet=true -Dhttp.proxyHost=... -Dhttp.proxyPort=8080 -Dhttps.proxyHost=... -Dhttps.proxyPort=8080

Once the application starts up, it connects to the RSS feed and starts fetching blog posts. The application processes those posts through the integration flow you defined, ultimately appending the post information to a file at /tmp/si/SpringBlog.

After the application has been running for awhile, you should be able to view the file at /tmp/si/SpringBlog to see the data from a handful of posts. On a UNIX-based operating system, you can also choose to tail the file to see the results as they are written:

tail -f /tmp/si/SpringBlog	

	