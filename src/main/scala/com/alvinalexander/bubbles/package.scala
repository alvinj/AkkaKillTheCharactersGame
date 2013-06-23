package com.alvinalexander

import java.awt.Color

package object bubbles {
  
  val SCREEN_HEIGHT = 600
  val SCREEN_WIDTH  = 800
  val NUM_BUBBLES   = 10
  val SPACE_BETWEEN_BUBBLES = 72
  val INITIAL_SPACE = 40

  val APPLICATION_NAME        = "Akka Actors Video Game #1"
  val ACTOR_SYSTEM_NAME       = "TinyBubbles"
  val BUBBLE_PANEL_ACTOR_NAME = "bubblePanelActor"
  val MAIN_FRAME_ACTOR_NAME   = "mainFrameActor"
  val KEY_LISTENER_ACTOR_NAME = "keyListenerActor"
  val PLAY_SOUND_ACTOR_NAME   = "playSoundActor"
  val ACTOR_MANAGER_NAME      = "actorManager"

  // sound actor
  val SUCCESS_SOUND_FILENAME = "Synth-Zingers-04.aif"
  val FAILURE_SOUND_FILENAME = "Comedy-Low-Honk.aif"
    
  val COLORS = Array(
      (Color.WHITE, Color.BLUE), 
      (Color.BLACK, Color.CYAN), 
      (Color.BLACK, Color.GREEN), 
      (Color.BLACK, Color.LIGHT_GRAY), 
      (Color.WHITE, Color.MAGENTA),
      (Color.WHITE, Color.ORANGE), 
      (Color.WHITE, Color.PINK), 
      (Color.WHITE, Color.RED), 
      (Color.BLACK, Color.WHITE), 
      (Color.BLACK, Color.YELLOW))

  // actor messages
  case object DisplayMainFrame
  case object Tick
  case object GameOver
  case object ShowGameOverWindow
  case object ShowYouWinWindow
  
  case object PlaySuccessSound
  case object PlayFailureSound

}

