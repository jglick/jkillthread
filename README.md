Command-line tool to kill a single thread in a Java VM, using the JDK 6+ Attach API.

Builds using Maven. Run the resulting JAR (using the `java` command from a JDK) to get usage instructions. Essentially you pass a process ID (or unique name substring) and then a thread name (or substring):

    mvn package
    java -jar target/jkillthread-1.0-SNAPSHOT.jar 12345 "rogue HTTP handler"
