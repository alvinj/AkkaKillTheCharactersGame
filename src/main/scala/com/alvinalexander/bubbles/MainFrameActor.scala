package com.alvinalexander.bubbles

import akka.actor.Actor
import javax.swing.{JFrame, JPanel, JLabel, SwingUtilities}
import java.awt.{BorderLayout, Dimension, Color}
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.Font
import java.awt.event.KeyListener
import java.awt.event.KeyEvent

/**
 * An Actor to handle all the interactions with the JFrame. 
 */
class MainFrameActor(bubblePanel: BubblePanel) extends Actor {
  
  def receive = {
    case DisplayMainFrame => showMainFrame
    case ShowGameOverWindow => showGameOverWindow
    case ShowYouWinWindow => showYouWinWindow
    case _ =>
  }
  
  val mainFrame = new JFrame {
    setMinimumSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT))
    setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT))
    val myKeyListener = new KeyListener {
      override def keyPressed(e: KeyEvent) 
      {
        if (e.getKeyCode == KeyEvent.VK_ESCAPE) {
          // TODO quit the app
        } else {
          // look up the peer actor and kill it (assuming the keystroke is right. invalid keystrokes cause no problems)
          val char = e.getKeyChar
          val actor = context.actorFor(Seq("..", char.toString))
          val playSoundActor = context.actorFor(Seq("..", PLAY_SOUND_ACTOR_NAME))
          if (actor.isTerminated) {
            playSoundActor ! PlayFailureSound
          } else {
            // actor exists
            playSoundActor ! PlaySuccessSound
            val actorManager = context.actorFor(Seq("..", ACTOR_MANAGER_NAME))
            actorManager ! KillActor(actor)
          }
        }
      }
    
      override def keyReleased(e: KeyEvent) {} 
      override def keyTyped(e: KeyEvent) {}
    
    }
    addKeyListener(myKeyListener)

  }

  configureMainFrame

  def configureMainFrame {
    mainFrame.setTitle(APPLICATION_NAME)
    mainFrame.setBackground(Color.BLACK)
    mainFrame.getContentPane.add(bubblePanel)
    mainFrame.setLocationRelativeTo(null)
    mainFrame.setUndecorated(true)
    mainFrame.getRootPane.putClientProperty("Window.alpha", 0.9f)
  }
  
  def showGameOverWindow {
    mainFrame.setGlassPane(new GameOverPanel)
    mainFrame.getGlassPane.setVisible(true)
  }
  
  def showYouWinWindow {
    mainFrame.setGlassPane(new YouWinPanel)
    mainFrame.getGlassPane.setVisible(true)
  }

  def showMainFrame {
    SwingUtilities.invokeLater(new Runnable {
      def run {
        mainFrame.setVisible(true)
      }
    })
  }
}


////////////////////////////////////

// TODO refactor these classes
class GameOverPanel extends JPanel {
  override def paintComponent(g: Graphics) {
    val g2 = g.asInstanceOf[Graphics2D]
    g2.setFont(new Font("Sans Serif", Font.BOLD, 60))
    g2.setColor(Color.RED)
    g2.drawString("GAME OVER", 200, 320)
  }
  
}

////////////////////////////////////

class YouWinPanel extends JPanel {
  override def paintComponent(g: Graphics) {
    val g2 = g.asInstanceOf[Graphics2D]
    g2.setFont(new Font("Sans Serif", Font.BOLD, 60))
    g2.setColor(Color.GREEN)
    g2.drawString("YOU WIN!", 230, 320)
  }
  
}



