Command-line tool to kill a single thread in a Java VM, using the JDK 6+ Attach API.

Builds using Maven. Run the resulting JAR (using the `java` command from a JDK) to get usage instructions. Essentially you pass a process ID (or unique name substring) and then a thread name (or substring):

    mvn package
    java -jar target/jkillthread-1.0-SNAPSHOT.jar 12345 "rogue HTTP handler"

Beware that killing a thread in Java (`Thread.stop`) can have various effects, depending on what it was doing:

* It might die quietly and that is that.
* It might die, but print or log a stack trace somewhere first.
* It might die but a similar thread be automatically relaunched by some sentinel.
* It might not die because it is blocked in some native call which does not honor `stop`.
* It might go into an odd state and not release locks that it should have. (Theoretically. I have never actually seen this happen.)

Caveat interfector!
