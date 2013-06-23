package com.alvinalexander

import java.awt.Color
import java.awt.Font

package object bubbles {
  
  val SCREEN_HEIGHT = 625
  val SCREEN_WIDTH  = 1000

  val NUM_CIRCLES           = 10
  val CIRCLE_DIAMETER       = 60
  val SPACE_BETWEEN_CIRCLES = 95
  val INITIAL_SPACE         = 30
  val CIRCLE_FONT           = new Font("Sans Serif", Font.BOLD, 26)
  val CIRCLE_FONT_PADDING_X = 22
  val CIRCLE_FONT_PADDING_Y = 38

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

