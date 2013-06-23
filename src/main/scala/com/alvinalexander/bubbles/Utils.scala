package com.alvinalexander.bubbles

import scala.util.Random

object Utils {
  
  def getRandomChars(howMany: Int): Array[Char] = {
    val charsToChooseFrom = ('a' to 'z') ++ ('0' to '9')
    var chars = scala.collection.mutable.Set[Char]()
    val r = new Random
    while (chars.size < howMany) {
      val randomInt = r.nextInt(charsToChooseFrom.size)
      chars.add(charsToChooseFrom(randomInt))
    }
    chars.toArray
  }
  
  def getRandomSpeed(num: Int): Int = {
    val r = new Random
    val i = r.nextInt(4)
    val j = r.nextInt(5)
    -(i * j + 5)  // algorithm currently needs negative value
  }

}