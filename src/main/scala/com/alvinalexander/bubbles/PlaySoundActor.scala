package com.alvinalexander.bubbles

import akka.actor.Actor
import java.io._
import sun.audio._
import javax.sound.sampled.AudioSystem

case object PlaySuccessSound
case object PlayFailureSound
    
class PlaySoundActor extends Actor {

  private val SUCCESS_SOUND_FILENAME = "Synth-Zingers-04.aif"
  private val FAILURE_SOUND_FILENAME = "Comedy-Low-Honk.aif"

  def receive = {
    case PlaySuccessSound => playSoundFile(SUCCESS_SOUND_FILENAME)
    case PlayFailureSound => playSoundFile(FAILURE_SOUND_FILENAME)
    case _ =>
  }

  def playSoundFile(filename: String) {
    val classloader = getClass.getClassLoader
    val inputStream = classloader.getResourceAsStream(filename)
    val audioStream = new AudioStream(inputStream)
    AudioPlayer.player.start(audioStream)
    
    // TODO sound won't play if the file is closed right away
    //inputStream.close
  }

}

