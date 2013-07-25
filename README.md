leap-clj
========

A Clojure binding for the Leap Motion API

Like most things, this is a work in progress. So far it's just a thin wrapper around the Java bindings.

The demo just prints the direction of the palm of the *first* hand in the view - if there is one. It runs for 20 seconds.

Of course, this code requires a Leap Motion device. Also, the dynamically linked libraries (libLeap.dylib, libLeapJava.dylib, or libLeap.dll and libLeapJava.dll, or libLeap.so and libLeapJava.so) need to be added to the Java library path. On my Mac I put them into /Library/Java/Extensions, though there are other choices:
 - ~/Library/Java/Extensions
 - /Library/Java/Extensions
 - /Network/Library/Java/Extensions
 - /System/Library/Java/Extensions
 - /usr/lib/java
 - .

 All the required library files are found at https://www.leapmotion.com/developers
