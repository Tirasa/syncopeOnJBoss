Apache Syncope overlay to be run in JBoss.<br/>
Practical implementation of official advices at Syncope [wiki](https://cwiki.apache.org/confluence/display/SYNCOPE/Run+Syncope+in+real+environments).

**This branch is for Apache Syncope 1.2 / Wildfly 8.1**: looking for [Apache Syncope 1.1 / JBoss AS 7.1](https://github.com/Tirasa/syncopeOnJBoss/tree/1_1_X)?

## How to test ##

This projects assumes that you have
 1. [Apache Maven 3.0](http://maven.apache.org) installed
 1. a running Wildfly 8.1.9 instance, listening to port 8080

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
 1. Currently on Apache Syncope 1.2.0
 1. Internal storage configured for in-memory H2 database instance, not using DataSource
 1. To avoid a whole lot of harmless yet annoyng error messages and stacktraces during core start-up, an overriden implementation was provided: see [issue 1](https://github.com/Tirasa/syncopeOnJBoss/issues/1) for more details
 1. Not listenig on 8080? Just put the correct port in <code>console/src/main/resources/console.properties</code>, re-build and re-deploy

## Need more info? ##
Just drop an [e-mail](mailto:syncope@tirasa.net).
