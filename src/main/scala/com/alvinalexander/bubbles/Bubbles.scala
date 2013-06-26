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
import org.apache.commons.cli.CommandLine

/**
 * The game starts with this object.
 */
object AkkaBubblesGame extends App {

  // set default values, and let users override them
  private var speedMultiplier = 0.6
  private var numCircles = NUM_CIRCLES
  overrideDefaultSettingsWithCommandLineArgs

  // needed by several actors
  private val bubblePanel = new BubblePanel

  // create the actor system and actors
  private val actorSystem = ActorSystem(ACTOR_SYSTEM_NAME)
  private val bubblePanelActor = actorSystem.actorOf(Props(new BubblePanelActor(bubblePanel)), name = BUBBLE_PANEL_ACTOR_NAME)
  private val mainFrameActor = actorSystem.actorOf(Props(new MainFrameActor(bubblePanel)), name = MAIN_FRAME_ACTOR_NAME)
  private val playSoundActor = actorSystem.actorOf(Props(new PlaySoundActor()), name = PLAY_SOUND_ACTOR_NAME)
  
  // create the desired number of circles
  private val circles = new Array[ActorRef](numCircles)
  populateTheCircles
  private val actorManager = actorSystem.actorOf(Props(new ActorManager(circles)), name = ACTOR_MANAGER_NAME)
  displayMainFrame
  startTheCircles

  // if nothing else happens, the game ends in XX seconds
  Thread.sleep(20*1000)
  actorSystem.shutdown
  System.exit(0)

  def displayMainFrame {
    mainFrameActor ! DisplayMainFrame
  }
  
  // create the circles. each will have a unique/random character, speed, and color.
  // they'll also be spread out along the x-axis.
  def populateTheCircles {
    val chars = Utils.getRandomChars(numCircles)
    for (i <- 0 to numCircles-1) {
      val name = chars(i).toString
      val xPos = i * SPACE_BETWEEN_CIRCLES + INITIAL_SPACE
      val speed = (Utils.getRandomSpeed(i) * speedMultiplier).toInt
      val (fgColor, bgColor) = COLORS(i)
      circles(i) = actorSystem.actorOf(Props(new BubbleActor(bubblePanelActor, name, fgColor, bgColor, chars(i), xPos, speed)), name = name)
    }
  }
  
  def overrideDefaultSettingsWithCommandLineArgs {
    val options = new Options
    options.addOption("n", true, "number of circles")
    options.addOption("s", true, "speed multiplier")
    val commandLineArgs = (new BasicParser).parse(options, args)
    handleNumBubblesOverride(commandLineArgs)
    handleSpeedOverride(commandLineArgs)
  }

  def handleNumBubblesOverride(commandLineArgs: CommandLine) {
    val numBubblesString = commandLineArgs.getOptionValue("n")
    if (numBubblesString != null) {
      val nDesired = numBubblesString.toInt
      if (nDesired > 0 && nDesired < 11) numCircles = nDesired
    }
  }

  def handleSpeedOverride(commandLineArgs: CommandLine) {
    val speedString = commandLineArgs.getOptionValue("s")
    if (speedString != null) {
      speedMultiplier = speedString.toFloat
    }
  }

  def startTheCircles {
    for (i <- 0 to numCircles-1) {
      addActorToScheduler(circles(i))
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














