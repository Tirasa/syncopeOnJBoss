# Run Apache Syncope on JBoss / Wildfy

Practical implementation of official advices at Syncope [documentation](https://syncope.apache.org/docs/reference-guide.html#wildfly-9-and-10).

**This branch is for Apache Syncope 2.0 / Wildfly 10**.<br/>
Looking for [Apache Syncope 1.2 / Wildfly 8.1](https://github.com/Tirasa/syncopeOnJBoss/tree/1_2_X) or [Apache Syncope 1.1 / JBoss AS 7.1](https://github.com/Tirasa/syncopeOnJBoss/tree/1_1_X)?

## How to test ##

This projects assumes that you have
 1. [Apache Maven 3.0](http://maven.apache.org) installed
 1. followed the [deployment directories convention](https://syncope.apache.org/docs/reference-guide.html#deployment-directories)
 1. a running Wildfly 10 instance, listening to port 8080

#### clone ####

<pre>
$ git clone git://github.com/Tirasa/syncopeOnJBoss.git
</pre>

#### build ####

<pre>
$ cd syncopeOnJBoss
$ mvn -Dconf.directory=/opt/syncope/conf \
  -Dbundles.directory=/opt/syncope/bundles \
  -Dlog.directory=/opt/syncope/log clean package
$ cp core/target/classes/*properties /opt/syncope/conf/
$ cp console/target/classes/*properties /opt/syncope/conf/
$ cp enduser/target/classes/*properties /opt/syncope/conf/
</pre>
(add `-P all` to the command above if wanting to include Activiti, Swagger and Apache Camel features)

#### deploy ####

 1. <code>core/target/syncope.war</code>
 1. <code>console/target/syncope-console.war</code>
 1.  <code>console/target/syncope-enduser.war</code>

## Notes ##
 1. Currently on Apache Syncope 2.0.1
 1. Internal storage configured for in-memory H2 database instance, not using DataSource
 1. Not listenig on 8080? Just put the correct port in <code>console/src/main/resources/console.properties</code> and <code>enduser/src/main/resources/enduser.properties</code>, re-build and re-deploy

## Need more info? ##
Just drop an [e-mail](mailto:syncope@tirasa.net).
