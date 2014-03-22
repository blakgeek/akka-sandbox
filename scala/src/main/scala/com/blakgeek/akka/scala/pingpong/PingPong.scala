package com.blakgeek.akka.scala.pingpong

import akka.actor.{Props, ActorSystem}

/**
 * User: Carlos Lawton
 * Date: 3/21/14
 * Time: 2:24 PM
 */
object PingPong extends App {

  val system = ActorSystem.create()
  // create the to players
  val ping = system.actorOf(Props(classOf[Player], "ping"), name = "ping")
  val pong = system.actorOf(Props(classOf[Player], "pong"), name = "pong")
  // tell on of them its there serve and who they are server to
  ping ! YourServe(pong, Score(0,0))
}
