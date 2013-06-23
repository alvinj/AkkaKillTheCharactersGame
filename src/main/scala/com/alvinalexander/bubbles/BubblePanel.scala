package com.alvinalexander.bubbles

import akka.actor.Actor
import java.awt._
import javax.swing.JPanel
import javax.swing.SwingUtilities

class BubblePanel extends JPanel {

  private var bubble: Bubble = null

  def doRedraw(bubble: Bubble) {
    this.bubble = bubble
    // TODO: i don't know if this is the "right" way to do this, but the normal
    // 'repaint' wasn't cutting it.
    val redrawHeight = bubble.circleDiameter * 2
    paintImmediately(bubble.x, bubble.y, bubble.circleDiameter, redrawHeight)
  }

  // clearRect clears the rectangular area, filling it with the current bg color
  override def paintComponent(g: Graphics) {
    if (bubble != null) {
      val g2 = g.asInstanceOf[Graphics2D]
      // clear the previous circle
      g2.setBackground(Color.BLACK)
      g2.clearRect(bubble.lastX, bubble.lastY, bubble.circleDiameter, bubble.circleDiameter)
      // draw the circle
      g2.setColor(bubble.bgColor)
      g2.fillOval(bubble.x, bubble.y, bubble.circleDiameter, bubble.circleDiameter)
      // draw the character
      g2.setFont(new Font("Sans Serif", Font.BOLD, 18))
      g2.setColor(bubble.fgColor)
      g2.drawString(bubble.char.toString, bubble.x+14, bubble.y+26)
    }
  }

}

////////////////////////////////////////////////////////////////

class BubblePanelActor(bubblePanel: BubblePanel) extends Actor {

  def receive = {
    case bubble: Bubble => doRedraw(bubble) 
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



