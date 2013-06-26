package com.alvinalexander.bubbles

import akka.actor._
import java.awt.Color
import javax.swing.SwingUtilities

case object Tick
case object StopMoving

/**
 * A class to model each instance of a "bubble/circle".
 * Receives a `Tick`, which indicates the bubble should re-calc its position.
 * Receives StopMoving, which tells the actor to stop re-calculating its position.
 * (I'm using this instead of stopping the actor so the screen is drawn properly
 * at the end of the game.)
 */
class BubbleActor(
    bubblePanelActor: ActorRef,
    name: String,
    fgColor: Color,
    bgColor: Color,
    char: Char,
    x: Int,
    deltaY: Int) extends Actor {

  private val panelWidth = SCREEN_WIDTH
  private val panelHeight = SCREEN_HEIGHT
  private val diameter = CIRCLE_DIAMETER
  
  private var y = panelHeight - diameter
  private var lastX = x
  private var lastY = y

  // is the actor in a 'stopped' state?
  private var stopped = false
  
  private val bubble = Bubble(x, y, lastX, lastY, diameter, fgColor, bgColor, char)
  
  def receive = {
    case Tick => handleTick
    case StopMoving => stopped = true 
    case _ =>
  }
  
  def handleTick {
    if (! stopped) {
      recalculatePosition
    }
    updateBubblePanel
  }
  
  // removed 'x' calcs because only y-values change
  def recalculatePosition {
    lastY = y
    y = y + deltaY
    if (y <= 0) {
      y = 0
      val actorManager = context.actorFor(Seq("..", ACTOR_MANAGER_NAME))
      actorManager ! GameOver
    } else {
      bubble.y = y
      bubble.lastY = lastY
    } 
  }

  override def postStop { 
    updateBubblePanel
    println(s"OMG, they killed me (${this.name})") 
  }

  def updateBubblePanel {
    bubblePanelActor ! Redraw(bubble)
  }

}


