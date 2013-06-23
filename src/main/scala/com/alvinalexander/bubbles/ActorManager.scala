package com.alvinalexander.bubbles

import akka.actor.Actor
import akka.actor.ActorRef

case class KillActor(actorRef: ActorRef)
case class SetInitialNumActors(num: Int)

/**
 * Responsibilities:
 * 1) kill the actors
 * 2) shut down the system
 * 3) show the "You Win" message when all the actors are stopped
 */
class ActorManager extends Actor {
  
  var activeActorCount = 0

  def receive = {
    case KillActor(actorRef) => doKillActorAction(actorRef)
    case GameOver => doGameOverAction
    case SetInitialNumActors(n) => activeActorCount = n
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
    val mainFrameActor = getMainFrameActor
    mainFrameActor ! ShowGameOverWindow
    shutdownApplication
  }
  
  def getMainFrameActor = context.actorFor(Seq("..", MAIN_FRAME_ACTOR_NAME))

  def shutdownApplication {
    context.system.shutdown
    Thread.sleep(3000)
    System.exit(0)
  }

}

