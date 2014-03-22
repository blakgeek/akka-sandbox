package com.blakgeek.akka.scala

import akka.actor.{Props, ActorSystem, Actor}
import akka.event.Logging
import akka.cluster.Cluster
import akka.cluster.ClusterEvent._

case object PopMessage

class Pop extends Actor {

  val logger = Logging(context.system, this)

  def receive = {
    case PopMessage => logger.debug("I got it!")
    case "fudge" => logger.info("yep")
    case _  => logger.info("nope")
  }
}

object Echo extends App {

  val system = ActorSystem("EchoChamber")
  val pop1 = system.actorOf(Props[Pop], name = "pop1")
  val pop2 = system.actorOf(Props[Pop], name = "pop2")
  pop1 ! "fudge"
  pop2 ! PopMessage
  pop1 ! "wtf?"
}