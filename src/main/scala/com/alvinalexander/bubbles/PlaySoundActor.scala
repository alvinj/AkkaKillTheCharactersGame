package com.alvinalexander.bubbles

import akka.actor.Actor
import java.io._
import sun.audio._
import javax.sound.sampled.AudioSystem

class PlaySoundActor extends Actor {

  // this actor doesn't really receive, it just sends
  def receive = {
    case PlaySuccessSound => playSoundFile(SUCCESS_SOUND_FILENAME)
    case PlayFailureSound => playSoundFile(FAILURE_SOUND_FILENAME)
    case _ =>
  }

  def playSoundFile(filename: String) {
    // WORKS
//    val fis = new FileInputStream(filename)
//    val audioStream = new AudioStream(fis)
//    AudioPlayer.player.start(audioStream)

//    val inputStream = getClass.getResourceAsStream("Synth-Zingers-04.aif")
//    val audioInputStream = AudioSystem.getAudioInputStream(inputStream)
//    AudioPlayer.player.start(audioInputStream)
    
    val CLDR = this.getClass.getClassLoader
    val inputStream = CLDR.getResourceAsStream(filename)
    val audioStream = new AudioStream(inputStream)
    AudioPlayer.player.start(audioStream)
    
    // TODO sound won't play if you close the file right away; timing issue?
    //fis.close
  }

}

