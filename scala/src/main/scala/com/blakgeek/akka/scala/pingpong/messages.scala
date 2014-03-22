package com.blakgeek.akka.scala.pingpong

import akka.actor.ActorRef

case class IMissed(score: Score)
case class YourServe(opponent: ActorRef, score: Score)
case class Volley(count: Int, score: Score)
case class IWin(winner: String, score: Score)
case class Score(me: Int, you: Int)

