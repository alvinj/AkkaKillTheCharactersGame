package com.alvinalexander

import java.awt.Color
import java.awt.Font

package object bubbles {
  
  val SCREEN_HEIGHT = 600
  val SCREEN_WIDTH  = 800

  val NUM_CIRCLES           = 10
  val CIRCLE_DIAMETER       = 40
  val SPACE_BETWEEN_CIRCLES = 72
  val INITIAL_SPACE         = 40
  val CIRCLE_FONT           = new Font("Sans Serif", Font.BOLD, 22)
  val CIRCLE_FONT_PADDING_X = 13
  val CIRCLE_FONT_PADDING_Y = 28

  val APPLICATION_NAME        = "Akka Actors 'Kill The Characters' Game"
  val ACTOR_SYSTEM_NAME       = "TinyBubbles"
  val BUBBLE_PANEL_ACTOR_NAME = "bubblePanelActor"
  val MAIN_FRAME_ACTOR_NAME   = "mainFrameActor"
  val KEY_LISTENER_ACTOR_NAME = "keyListenerActor"
  val PLAY_SOUND_ACTOR_NAME   = "playSoundActor"
  val ACTOR_MANAGER_NAME      = "actorManager"

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
  case object GameOver

}




