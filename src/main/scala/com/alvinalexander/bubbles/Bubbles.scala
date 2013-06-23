package com.alvinalexander.bubbles

import akka.actor._
import java.awt._
import javax.swing._
import scala.concurrent.duration._
import java.awt.event.KeyListener
import org.apache.commons.cli.Option
import org.apache.commons.cli.BasicParser
import org.apache.commons.cli.CommandLineParser
import org.apache.commons.cli.Options

/**
 * The game starts with this object.
 */
object AkkaBubblesGame extends App {
  
  var speedMultiplier = 1.0

  // needed by several actors
  val bubblePanel = new BubblePanel

  // create the actor system and actors
  val actorSystem = ActorSystem(ACTOR_SYSTEM_NAME)
  val actorManager = actorSystem.actorOf(Props(new ActorManager()), name = ACTOR_MANAGER_NAME)
  val bubblePanelActor = actorSystem.actorOf(Props(new BubblePanelActor(bubblePanel)), name = BUBBLE_PANEL_ACTOR_NAME)
  val mainFrameActor = actorSystem.actorOf(Props(new MainFrameActor(bubblePanel)), name = MAIN_FRAME_ACTOR_NAME)
  val playSoundActor = actorSystem.actorOf(Props(new PlaySoundActor()), name = PLAY_SOUND_ACTOR_NAME)
  
  // set the desired number of bubbles, allowing cmd-line override
  var numBubbles = NUM_CIRCLES
  overrideSettingsWithCommandLineArgs
  actorManager ! SetInitialNumActors(numBubbles)

  // create the bubbles 
  val bubbles = new Array[ActorRef](numBubbles)
  createTheBubbles
  displayMainFrame
  startTheBubbles

  // if nothing else happens, the game ends in 20 seconds
  Thread.sleep(20*1000)
  actorSystem.shutdown
  System.exit(0)

  def displayMainFrame {
    mainFrameActor ! DisplayMainFrame
  }
  
  def createTheBubbles {
    val chars = Utils.getRandomChars(numBubbles)
    for (i <- 0 to numBubbles-1) {
      val name = chars(i).toString
      val xPos = i * SPACE_BETWEEN_CIRCLES + INITIAL_SPACE
      val speed = (Utils.getRandomSpeed(i) * speedMultiplier).toInt
      val (fgColor, bgColor) = COLORS(i)
      bubbles(i) = actorSystem.actorOf(Props(new BubbleActor(bubblePanelActor, name, fgColor, bgColor, chars(i), xPos, speed)), name = name)
    }
  }
  
  def overrideSettingsWithCommandLineArgs {
    val options = new Options
    options.addOption("n", true, "number of circles")
    options.addOption("s", true, "speed multiplier")
    val parser = new BasicParser
    val cmds = parser.parse(options, args)
    // num bubbles
    val numBubblesString = cmds.getOptionValue("n")
    if (numBubblesString != null) {
      val nDesired = numBubblesString.toInt
      if (nDesired > 0 && nDesired < 11) numBubbles = nDesired
    }
    // speed multiplier
    val speedString = cmds.getOptionValue("s")
    if (speedString != null) {
      speedMultiplier = speedString.toFloat
    }
  }

  def startTheBubbles {
    for (i <- 0 to numBubbles-1) {
      addActorToScheduler(bubbles(i))
    }
  }
  
  def addActorToScheduler(actor: ActorRef) {
    // use actorsystem's dispatcher as ExecutionContext
    import actorSystem.dispatcher
    // send the tick message every XX ms (25ms = 40 frames/sec)
    val cancellable =
      actorSystem.scheduler.schedule(0 milliseconds,
        25 milliseconds,
        actor,
        Tick)
  }
}














