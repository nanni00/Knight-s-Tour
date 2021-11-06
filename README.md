# Knight-s-Tour

A simple program that solves the Knight's Tour problem on a generic board MxN. 

The project's core is written in C and is linked to the Java code through the Java Native Interface.
In order to do that it has been necessary get a specific header file and a library file (dll under Windows, so under Linux). 

To run the program, download the jar file in the out/artifacts/KnightTour_jar directory and run the line below:
> java -jar KnightTour.jar


For more details,
- about Knight's Tour problem: https://en.wikipedia.org/wiki/Knight%27s_tour
- about JNI: https://docs.oracle.com/en/java/javase/16/docs/specs/jni/index.html
