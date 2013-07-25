leap-clj
========

A Clojure binding for the Leap Motion API

Like most things, this is a work in progress. So far it's just a thin wrapper around the Java bindings.

Most of the work has been in translating Frame data to a palatable form for Clojure, and a group of functions for working with the Clojure form of Vectors. Config has not been mapped at all, though it's still easy to add it to a Connection object using standard Java interop. See the Leap Motion [Javadoc](https://developer.leapmotion.com/documentation/Languages/Java/API/annotated.html "LeapMotion Javadoc").

## Demo

The demo is launched with `lein run` and just prints the direction of the palm of the *first* hand (if one is visible) in the view. It runs for 20 seconds.

## Installing

Of course, this code requires a Leap Motion device. The code also requires library files that have to be downloaded separately. All the required library files are found at https://www.leapmotion.com/developers

The libraries consist of 2 parts: a jar file and dynamic libraries. The jar file is called LeapJava.jar and can go into the classpath in the usual way (I have it added to :resource-paths in the project.clj).

The dynamically linked libraries (libLeap.dylib, libLeapJava.dylib, or libLeap.dll and libLeapJava.dll, or libLeap.so and libLeapJava.so) need to be added to the Java library path, which is different to the classpath.

On my Mac I put them into /Library/Java/Extensions, though there are other choices:
 - ~/Library/Java/Extensions
 - /Library/Java/Extensions
 - /Network/Library/Java/Extensions
 - /System/Library/Java/Extensions
 - /usr/lib/java
 - .

On Linux, the directory options are different. For instance, on a Java 1.6.0-27 system the library path is:

   /usr/lib/jvm/java-6-openjdk-amd64/jre/lib/amd64/server:/usr/lib/jvm/java-6-openjdk-amd64/jre/lib/amd64:/usr/lib/jvm/java-6-openjdk-amd64/jre/../lib/amd64:/usr/java/packages/lib/amd64:/usr/lib/x86\_64-linux-gnu/jni:/lib/x86\_64-linux-gnu:/usr/lib/x86\_64-linux-gnu:/usr/lib/jni:/lib:/usr/lib

You can find the appropriate list of directories for your system with `(System/getProperty "java.library.path")`

