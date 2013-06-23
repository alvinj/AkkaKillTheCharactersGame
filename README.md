An Akka Actors "Kill The Characters" Game
=========================================

This is a simple video game written with the Scala programming language
and Akka Actors.

Object of the Game
------------------

The object of the game is to type all of the letters and numbers
before they reach the top of the screen. If you type them all first,
you win, otherwise you lose.

Status
------

This is very much an "Alpha" release. Everything seemed to be working
well on my old iMac, and then I tried using it on my new MBA, and, 
well ... I think I got the bugs worked out.

Demonstration
-------------

You can see the game in action here:

* http://alvinalexander.com/scala/akka-actors-video-game

Running the Game
----------------

Package the game with the `sbt assembly` command. This uses the
sbt-assembly plugin to create a single JAR file.  Once it creates a JAR
file in the `target` directory, run the JAR file like this (the name will
be different):

```
java -jar target/AkkaGame-1.0.jar
```

By default that command uses 10 circles. Set the game to use four circles like this:

```
java -jar target/AkkaGame-1.0.jar -n 4
```

You can also adjust the game speed. Set the game to use four circles and double the speed like this:

```
java -jar target/AkkaGame-1.0.jar -n 4 -s 2.0
```

I haven't seen any problems with the game on my six-year-old Mac, but if you do,
you can try running it like this:

```
java -client -Dsun.java2d.opengl=true -jar target/AkkaGame-1.0.jar
```

In theory that command should make the game run a little faster, or redraw better.

Created By
----------

The original game was created by Alvin Alexander, http://alvinalexander.com


