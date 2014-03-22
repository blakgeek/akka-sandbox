package com.blakgeek.akka.scala.pingpong

import akka.actor.{ActorLogging, Actor}
import scala.util.Random

/**
 * User: Carlos Lawton
 * Date: 3/21/14
 * Time: 7:00 PM
 */
class Player(val name: String) extends Actor with ActorLogging{

  def receive = {
    case Volley(count, score) =>
      // make the player miss roughly 20% of the time
      if(Random.nextInt(10) > 8) {
        sender ! IMissed(Score(score.you, score.me))
      } else {
        log.info(name + "!")
        sender ! Volley(count + 1, Score(score.you, score.me))
      }
    case IMissed(score) =>
      val newScore = Score(score.you + 1, score.me)
      if(newScore.me  < 5) {
        log.info("%s: %d serving %d".format(name, newScore.me, newScore.you))
        sender() ! Volley(0, newScore)
      } else {
        log.info("%s: I win %d to %d.".format(name, newScore.me, newScore.you))
      }
    case YourServe(opponent, score) =>
      log.info("%s: Game On! 0 serving 0".format(name))
      opponent ! Volley(0, score)
  }
}
