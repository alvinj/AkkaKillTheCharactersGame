package com.alvinalexander.bubbles

import akka.actor._
import java.awt.Color
import javax.swing.SwingUtilities

/**
 * A class to represent each instance of a "bubble".
 */
class BubbleActor(
    bubblePanelActor: ActorRef,
    name: String,
    fgColor: Color,
    bgColor: Color,
    char: Char,
    x: Int,
    delta: Int) extends Actor {

  val panelWidth = SCREEN_WIDTH
  val panelHeight = SCREEN_HEIGHT
  val diameter = 40
  
  var y = panelHeight - diameter
  var lastX = 0
  var lastY = 0

  // pixels to move on each call
  private val deltaY = delta
  
  def receive = {
    case Tick => recalculatePosition
                 updateBubblePanel
    case _ =>
  }
  
  override def postStop { 
    println(s"OMG, the killed me (${this.name})") 
  }
  
  // TODO if (y <= 0) fire GameOver event
  def recalculatePosition {
    lastX = x
    lastY = y
    y = y + deltaY
    if (y <= 0) {
      val actorManager = context.actorFor(Seq("..", ACTOR_MANAGER_NAME))
      actorManager ! GameOver 
    } 
  }

  def updateBubblePanel {
    bubblePanelActor ! Bubble(x, y, lastX, lastY, diameter, fgColor, bgColor, char)
  }

}

