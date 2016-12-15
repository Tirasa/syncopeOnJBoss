# Run Apache Syncope on JBoss / Wildfy with MariaDB (container-managed DataSource)

Practical implementation of official advices at Syncope [documentation](https://syncope.apache.org/docs/reference-guide.html#wildfly-9-and-10).

Internal storage [configured for MariaDB](https://syncope.apache.org/docs/reference-guide.html#mariadb), _with container-managed DataSource_.

**This branch is for Apache Syncope 2.0 / Wildfly 10**.<br/>
Looking for [Apache Syncope 1.2 / Wildfly 8.1](https://github.com/Tirasa/syncopeOnJBoss/tree/1_2_X) or [Apache Syncope 1.1 / JBoss AS 7.1](https://github.com/Tirasa/syncopeOnJBoss/tree/1_1_X)?

## How to test ##

This projects assumes that you have
 1. [Apache Maven 3.0](http://maven.apache.org) installed
 1. followed the [deployment directories convention](https://syncope.apache.org/docs/reference-guide.html#deployment-directories)
 1. a running Wildfly 10 instance, listening to port 8080
 1. a MariaDB database instance, with `my.cnf` featuring, under `[mysqld]`,
 
  ```
  binlog_format=MIXED
  ```
  The JDBC coordinates are defined in [core/src/main/resources/domains/Master.properties](https://github.com/Tirasa/syncopeOnJBoss/blob/MariaDB_NoContainerDataSource/core/src/main/resources/domains/Master.properties)

#### clone ####

<pre>
$ git clone git://github.com/Tirasa/syncopeOnJBoss.git
$ git checkout MariaDB_ContainerDataSource
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

### configure Wildfly

Assuming that Wildfy 10 is installed under `WILDFLY_HOME`:

1. `mkdir $WILDFLY_HOME/modules/system/layers/base/org/mariadb/jdbc/main/`
1. create `module.xml` file in this new directory, with content:

  ```xml
  <?xml version="1.0" encoding="UTF-8"?>

<module xmlns="urn:jboss:module:1.3" name="org.mariadb.jdbc">

    <resources>
        <resource-root path="mariadb-java-client-1.5.5.jar"/>
    </resources>
    <dependencies>
        <module name="javax.api"/>
        <module name="javax.transaction.api"/>
        <module name="javax.servlet.api" optional="true"/>
    </dependencies>
</module>
  ```
1. download [mariadb-java-client-1.5.5.jar](http://search.maven.org/remotecontent?filepath=org/mariadb/jdbc/mariadb-java-client/1.5.5/mariadb-java-client-1.5.5.jar) in this new directory
1. add the following definition to `$WILDFLY_HOME/standalone/configuration/standalone.xml`, under `<datasources>`

 ```xml
<datasource jndi-name="java:jboss/datasources/syncopeDS" pool-name="syncopeDS"
            enabled="true" use-java-context="true">    
    <connection-url>jdbc:mariadb://localhost:3306/syncope?characterEncoding=UTF-8</connection-url>
    <driver>mariadb</driver>
    <security>
        <user-name>syncope</user-name>
        <password>syncope</password>
    </security>
</datasource>
 ```

1. add the following definition to `$WILDFLY_HOME/standalone/configuration/standalone.xml`, under `<drivers>`

 ```xml
<driver name="mariadb" module="org.mariadb.jdbc">    
    <xa-datasource-class>org.mariadb.jdbc.MySQLDataSource</xa-datasource-class>
</driver>
 ```

#### deploy ####

 1. <code>core/target/syncope.war</code>
 1. <code>console/target/syncope-console.war</code>
 1. <code>console/target/syncope-enduser.war</code>

## Notes ##
 1. Currently on Apache Syncope 2.0.1
 1. Not listenig on 8080? Just put the correct port in <code>console/src/main/resources/console.properties</code> and <code>enduser/src/main/resources/enduser.properties</code>, re-build and re-deploy

## Need more info? ##
Just drop an [e-mail](mailto:syncope@tirasa.net).
