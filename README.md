# JmxMonitor
 A simple program which monitors values of a Mbean attribute published on a remote JVM using the JMX API

## Components
JmxMonitor has a CLI Main application that offers you 2 options:
[1] Connect to a remote JMX Server application and get a MBean from it.
[2] Open a GUI to show a chart of the Heap Memory Usage.

## Developement
This app was developed in Java 11 with Apache Maven and VS CODE.

## Complinig and running
Main app JmxMonitor can be compiled with:
```shell
mvn package
```

and run with:
```shell
mvn exec:java -D exec.mainClass=com.domen5.jmxmonitor.Main
```

JmxServer is an example app with one MBean named Count.
It can be compiled with:
```shell
mvn package
```

It **must** be run with:
```shell
java -cp target/JmxServer-1.0-SNAPSHOT.jar  -Dcom.sun.management.jmxremote.port=9999 -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false com.domen5.jmxserver.Main
```
or 
```shell
target/classes> java -Dcom.sun.management.jmxremote.port=9999 -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false com.domen5.jmxserver.Main
```