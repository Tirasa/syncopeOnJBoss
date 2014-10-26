Apache Syncope overlay to be run in JBoss.<br/>
Practical implementation of official advices at Syncope [wiki](https://cwiki.apache.org/confluence/display/SYNCOPE/Run+Syncope+in+real+environments).

## How to test ##

This projects assumes that you have
 1. [Apache Maven 3.0](http://maven.apache.org) installed
 1. a running JBoss AS 7.1.1 instance, listening to port 8080

#### clone ####

<pre>
$ git clone git://github.com/Tirasa/syncopeOnJBoss.git
</pre>

#### build ####

<pre>
$ cd syncopeOnJBoss
$ mvn clean package
</pre>

#### deploy ####

 1. <code>core/target/syncope.war</code>
 1. <code>console/target/syncope-console.war</code>

## Notes ##
 1. Currently on Apache Syncope 1.1.8
 1. Internal storage configured for in-memory H2 database instance, not using DataSource
 1. Not listenig on 8080? Just put the correct port in <code>console/src/main/resources/configuration.properties</code>, re-build and re-deploy

## Need more info? ##
Just drop an [e-mail](mailto:syncope@tirasa.net).
