# JmxMonitor

**JmxMonitor** is an application designed to monitor MBean attributes published on a remote JVM using the JMX API.

## Features

JmxMonitor provides two main functionalities via its command-line interface (CLI):

1. **Connect to a Remote JMX Server**: Access and retrieve MBeans from a remote JMX server.
2. **Graphical User Interface (GUI)**: Display a chart illustrating Heap Memory Usage.

An example application called "JmxServer" is provided in order to test JmxMonitor.

## Development

This application is developed using Java 11, Apache Maven, and Visual Studio Code.

## Compilation and Execution

### Compiling JmxMonitor

To compile the main JmxMonitor application, use the following command:

```shell
mvn package
```

### Running JmxMonitor

You can run the JmxMonitor application with:

```shell
mvn exec:java -Dexec.mainClass=com.domen5.jmxmonitor.Main
```

### Compiling JmxServer

The JmxServer example application, which includes a single MBean named `Count`, can be compiled with:

```shell
mvn package
```

### Running JmxServer

To run JmxServer, use one of the following commands:

```shell
java -cp target/JmxServer-1.0-SNAPSHOT.jar -Dcom.sun.management.jmxremote.port=9999 -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false com.domen5.jmxserver.Main
```

Alternatively, you can run it from the `target/classes` directory with:

```shell
java -Dcom.sun.management.jmxremote.port=9999 -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false com.domen5.jmxserver.Main
```
