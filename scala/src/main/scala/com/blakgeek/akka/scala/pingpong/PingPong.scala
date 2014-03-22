package com.blakgeek.akka.scala.pingpong

import akka.actor.{Actor, ActorLogging, Props, ActorSystem}

/**
 * User: Carlos Lawton
 * Date: 3/21/14
 * Time: 2:24 PM
 */
object PingPong extends App {

  val system = ActorSystem.create()
  system.actorOf(Props[PingPong], "game")
}

class PingPong extends Actor with ActorLogging {

  // create the two players
  val player1 = context.actorOf(Player.props("ping"), "player1")
  val player2 = context.actorOf(Player.props("pong"), "player2")
  // tell on of them its there serve and who they are server to
  player1 ! YourServe(player2, Score(0, 0))

  def receive = {
    case IWin(name, score) =>
      log.info("%s won %d to %d".format(name, score.me, score.you))
      context.system.shutdown()

  }
}
