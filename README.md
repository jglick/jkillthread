Command-line tool to kill a single thread in a Java VM, using the Oracle JDK 6+ Attach API.

Download [`jkillthread-1.0.jar`](https://github.com/jglick/jkillthread/releases/download/1.0/jkillthread-1.0.jar) and run using the `java` command from a JDK to get usage instructions.
Essentially you pass a process ID (or unique name substring) and then a thread name (or substring):

    java -jar jkillthread-1.0.jar 12345 "rogue HTTP handler"

(`jps -lm` is useful for finding a process ID. `jstack 12345` can be used to see currently running threads.)

Beware that killing a thread in Java (`Thread.stop`) can have various effects, depending on what it was doing:

* It might die quietly and that is that.
* It might die, but print or log a stack trace somewhere first.
* It might die but a similar thread be automatically relaunched by some sentinel.
* It might not die because it is blocked in some native call which does not honor `stop`.
* It might go into an odd state and not release locks that it should have. (Theoretically. I have never actually seen this happen.)

Caveat interfector!
