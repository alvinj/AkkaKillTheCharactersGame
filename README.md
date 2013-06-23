An Akka Actors "Kill The Circles" Game
======================================

This is a simple video game written with the Scala programming language
and Akka Actors.

Object of the Game
------------------

The object of the game is to type all of the letters and numbers
before they reach the top of the screen. If you type them all first,
you win, otherwise you lose.

Demonstration
-------------

You can see the game in action here:

URLHERE


Running the Game
----------------

The game is distributed as a Java JAR file. You run it from your operating
system command line like this:

```
java -jar AkkaCharactersGame-1.0.jar
```

By default that command uses 10 circles. Set the game to use four circles like this:

```
java -jar AkkaCharactersGame-1.0.jar -n 4
```

You can also adjust the game speed. Set the game to use four circles and double the speed like this:

```
java -jar AkkaCharactersGame-1.0.jar -n 4 -s 2.0
```

I haven't seen any problems with the game on my seven-year-old Mac, but if you do,
you can try running it like this:

```
java -client -Dsun.java2d.opengl=true -jar AkkaCharactersGame-1.0.jar
```

In theory that command should make the game run a little faster, or redraw better.

