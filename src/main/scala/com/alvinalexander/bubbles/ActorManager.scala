package com.alvinalexander.bubbles

import akka.actor.Actor
import akka.actor.ActorRef

case class KillActor(actorRef: ActorRef)
case class SetInitialNumActors(num: Int)

/**
 * Responsibilities:
 * 
 * 1) kill the actors
 * 2) shut down the system
 * 3) show the "You Win" message when all the actors are stopped
 */
class ActorManager(bubbles: Array[ActorRef]) extends Actor {
  
  private var activeActorCount = bubbles.size

  def receive = {
    case KillActor(actorRef) => doKillActorAction(actorRef)
    case GameOver => doGameOverAction
    case _ =>
  }
  
  def doKillActorAction(actorRef: ActorRef) {
    context.stop(actorRef)
    activeActorCount -= 1
    if (activeActorCount == 0) {
      val mainFrameActor = getMainFrameActor
      mainFrameActor ! ShowYouWinWindow
      shutdownApplication
    }
  }
  
  def doGameOverAction {
    stopAllBubbles
    val mainFrameActor = getMainFrameActor
    mainFrameActor ! ShowGameOverWindow
    // TODO don't shut the app down until the 'Game Over' overlay is shown
    //shutdownApplication
  }
  
  // use StopMoving so the panel redraws properly at the end (vs. context.stop) 
  def stopAllBubbles {
    for (b <- bubbles) {
      b ! StopMoving
    }
  }
  
  def getMainFrameActor = context.actorFor(Seq("..", MAIN_FRAME_ACTOR_NAME))

  def shutdownApplication {
    context.system.shutdown
    Thread.sleep(3000)
    System.exit(0)
  }

}

