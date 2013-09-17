PCMM

1. Make a stub class that for PCMM that can be filled in along the way.

COPS

2 Use the Client and Server to 
	a) Open TCP connection Client to Server listening for incoming COPS connections on the TCP port number 3918
	b) Send PEP COPS Server OPN to Client PDP Client-Open message without Multimedia Version Info object.
	c) Send PDP Client-Accept message without the Version Info.

3. Locate or write a COPS message printing method for both side.

Socket Client Server

4. Fix the org.program.connection classes to handle client/server 


Quickstart

1. Make the class jar
cd packetcable/src/main/java/
make

2. Make the menu-based test program
cd packetcable
make

The menuing system is a placeholder for testing and is not the ultimate taget for integration.

Other notes ...

Maven

Maven (http://maven.apache.org/) is a build tool. Maven defines project types (archetypes) needed for the target delivery type of Bundles. When you choose one (or more) archetype for your project, it mandates a directory structure that is quite deep. 

For example:

project-name
+- src/
   +- main/
      +- java/
         +- com/example/project-name/
            +- Java source files
   +- test/
      +- tends to mirror above
+- target/
   +- classes/
      +- com/example/project-name/
         +- Java class files
   +- test-classes/
      +- tends to mirror above
...etc...

This project structure is constraining, but the Maven way.  Maven will also give you lots of standard commands to run, such as compile, test, install, etc.

Maven is also useful for dependencies on third-party libraries. You declare these in your Maven project file and they are retrieved from repositories on the internet (and cached locally unless you set it up otherwise).

Coming from a C background, Makefile got things up and running despite not being the Java way. 

