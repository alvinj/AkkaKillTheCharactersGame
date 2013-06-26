For Scala and Akka Developers
=============================

Versions
--------

* v0.1 was the initial release (written very quickly)
* v0.2 is a second release that cleaned up and reorganized the code slightly
  (the re-org still has a ways to go)

Building the App
----------------

While developing the app, you can run it from the root project directory
with SBT like this:

```
sbt run
```

To create a single JAR file from the code, run this SBT command:

```
sbt assembly
```

That command uses the sbt-assembly plugin to create one JAR file that
you can run with the Java interpreter. There's nothing you need to do 
to make this happen; the sbt-assembly plugin is already included in 
the project definition files.

The Code
--------

As mentioned, the code is written in the Scala programming language, and
uses Akka Actors. I wrote this game as a way of demonstrating and exploring
Akka Actors. It shows a few useful things:

* How to write actors
* How to create actors
* How to lookup actors
* How to communicate between actors
* How to kill actors
* How to organize an actor-based system (the code is a little sloppy here, but getting better)

The code still needs some refactoring. I didn't start to create a video
game, I was just fooling around, and next thing you know, suddenly there was
a game here. The code isn't horrible, but it can still be cleaned up, and some of
the responsibilities can be moved around a little bit.

See the TODO.md file for items that are next on my To-Do List (when I get some
more free time).

JVM Notes
---------

To improve the performance, it _may_ help to run the game with these 
JVM command-line options:

```
-client -Dsun.java2d.opengl=true
```

Then again, it may not. I just have some Mac computers around here, and I don't
see a difference with and without them, and I haven't looked into this at all
other than knowing that I used to use those settings, and they are recommended
in some Swing books I have.




