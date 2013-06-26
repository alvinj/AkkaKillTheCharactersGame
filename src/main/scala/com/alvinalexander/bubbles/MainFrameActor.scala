package com.alvinalexander.bubbles

import akka.actor.Actor
import javax.swing.{JFrame, JPanel, JLabel, SwingUtilities}
import java.awt.{BorderLayout, Dimension, Color}
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.Font
import java.awt.event.KeyListener
import java.awt.event.KeyEvent
import akka.actor.ActorRef

case object DisplayMainFrame
case object ShowGameOverWindow
case object ShowYouWinWindow

/**
 * An Actor to handle all the interactions with the JFrame.
 * User keystrokes trigger a noise and may kill a bubble (character actor).
 */
class MainFrameActor(bubblePanel: BubblePanel) extends Actor {

  private var playSoundActor: ActorRef = null
  private var actorManager: ActorRef = null
  
  def receive = {
    case DisplayMainFrame => showMainFrame
    case ShowGameOverWindow => showGameOverWindow
    case ShowYouWinWindow => showYouWinWindow
    case _ =>
  }
  
  val myKeyListener = new KeyListener {
    override def keyPressed(e: KeyEvent) 
    {
      if (e.getKeyCode == KeyEvent.VK_ESCAPE) {
        // TODO quit the app
      } else {
        // look up the peer actor and kill it (assuming the keystroke is right. invalid keystrokes cause no problems)
        attemptToKillCharacterActor(e.getKeyChar)
      }
    }

    override def keyReleased(e: KeyEvent) {} 
    override def keyTyped(e: KeyEvent) {}
  }

  val mainFrame = new JFrame {
    setMinimumSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT))
    setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT))
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
  
  def lookupCharacterActor(c: Char) = context.actorFor(Seq("..", c.toString))

  def getPlaySoundActor = {
    if (playSoundActor == null) playSoundActor = context.actorFor(Seq("..", PLAY_SOUND_ACTOR_NAME))
    playSoundActor
  } 
  
  def getActorManager = {
    if (actorManager == null) actorManager = context.actorFor(Seq("..", ACTOR_MANAGER_NAME))
    actorManager
  } 
  
  def attemptToKillCharacterActor(c: Char) {
    val characterActor = lookupCharacterActor(c)
    if (characterActor.isTerminated) {
      // character-actor does not exist
      getPlaySoundActor ! PlayFailureSound
    } else {
      // character-actor exists
      getPlaySoundActor ! PlaySuccessSound
      getActorManager ! KillActor(characterActor)
    }
  }

  def showGameOverWindow {
    SwingUtilities.invokeLater(new Runnable {
      def run {
        mainFrame.removeKeyListener(myKeyListener)
        mainFrame.setGlassPane(new OverlayPanel(240, 320, "GAME OVER", new Font("Sans Serif", Font.BOLD, 60), Color.RED))
        mainFrame.getGlassPane.setVisible(true)
      }
    })
  }
  
  def showYouWinWindow {
    SwingUtilities.invokeLater(new Runnable {
      def run {
        mainFrame.removeKeyListener(myKeyListener)
        mainFrame.setGlassPane(new OverlayPanel(240, 320, "YOU WIN!", new Font("Sans Serif", Font.BOLD, 60), Color.GREEN))
        mainFrame.getGlassPane.setVisible(true)
      }
    })
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

class OverlayPanel(
    x: Int, 
    y: Int, 
    message: String,
    font: Font,
    fontColor: Color) extends JPanel {

  setOpaque(false)
  override def paintComponent(g: Graphics) {
    val g2 = g.asInstanceOf[Graphics2D]
    g2.setFont(font)
    g2.setColor(fontColor)
    g2.drawString(message, x, y)
  }
  
}



