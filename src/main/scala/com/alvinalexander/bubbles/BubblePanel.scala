package com.alvinalexander.bubbles

import akka.actor.Actor
import java.awt._
import javax.swing.JPanel
import javax.swing.SwingUtilities
import java.awt.image.BufferedImage

/**
 * This is the JPanel that all of the bubbles are drawn on.
 */
class BubblePanel extends JPanel {

  private var bubble: Bubble = null

  def doRedraw(bubble: Bubble) {
    this.bubble = bubble
    // TODO: i don't know if this is the "right" way to do this, but the normal
    // 'repaint' wasn't cutting it.
    val redrawHeight = bubble.circleDiameter * 2
    paintImmediately(bubble.x, bubble.y, bubble.circleDiameter, redrawHeight)
  }

  // the bubbles now draw themselves 
  override def paintComponent(g: Graphics) {
    if (bubble != null) {
      val component = this.asInstanceOf[Component]
      bubble.drawBubbleFast(g, component.getGraphicsConfiguration)
    }
  }

}

////////////////////////////////////////////////////////////////

case class Redraw(bubble: Bubble)

class BubblePanelActor(bubblePanel: BubblePanel) extends Actor {

  def receive = {
    case Redraw(bubble) => doRedraw(bubble) 
    case _ =>
  }

  private def doRedraw(bubble: Bubble) {
    SwingUtilities.invokeLater(new Runnable {
      def run {
        bubblePanel.doRedraw(bubble)
      }
    })
  }

}



