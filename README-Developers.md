For Scala and Akka Developers
=============================


Building the App
----------------

Build the JAR file with SBT like this:

```
sbt assembly
```

This builds one single JAR file that you can distribute to others.


The Code
--------

As mentioned, the code is written in the Scala programming language, and
uses Akka Actors. I wrote this game as a way of demonstrating and exploring
Akka Actors. It shows a few useful things:

* How to write actors
* How to create actors
* How to lookup actors
* How to kill actors
* How to organize an actor-based system (the code is a little sloppy here, but getting better)

The code needs a few hours of refactoring. I didn't start to create a video
game, I was just fooling around, and next thing you know, suddenly there was
a game here. It isn't horrible, but there are a few things like a) handling
keystrokes and b) moving responsibilities around that should be improved.


JVM Notes
---------

It may help to run the game with these JVM command-line options:

```
-client -Dsun.java2d.opengl=true
```

Then again, it may not. I just have some Mac computers around here, and I don't
see a difference with and without them, and I haven't looked into this at all
other than knowing that I used to use those settings.

