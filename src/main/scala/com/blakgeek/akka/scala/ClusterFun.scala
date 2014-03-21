package com.blakgeek.akka.scala

import akka.actor.{Props, ActorSystem, Actor}
import akka.cluster.Cluster
import akka.event.Logging
import akka.cluster.ClusterEvent._
import akka.cluster.ClusterEvent.MemberUp
import akka.cluster.ClusterEvent.UnreachableMember
import com.typesafe.config.ConfigFactory

/**
 * User: Carlos Lawton
 * Date: 3/20/14
 * Time: 10:57 PM
 */
object ClusterFun {


  def main(args: Array[String]): Unit = {

    val config = ConfigFactory.parseString("akka.remote.netty.tcp.port=" + args(0))
      .withFallback(ConfigFactory.load())
    val system = ActorSystem("da-cluster", config)
    system.actorOf(Props[ClusterVoyeur], "da-watcher")
  }
}

class ClusterVoyeur extends Actor {

  val cluster = Cluster.get(context.system)
  val log = Logging(context.system, this)

  override def preStart(): Unit = {

    cluster.subscribe(self, initialStateMode = InitialStateAsEvents,
      classOf[MemberEvent], classOf[UnreachableMember])
  }

  override def postStop(): Unit = cluster.unsubscribe(self)

  def receive = {
    case MemberUp(member) =>
      log.info("Member is Up: {}", member.address)
    case UnreachableMember(member) =>
      log.info("Member detected as unreachable: {}", member)
    case MemberRemoved(member, previousStatus) =>
      log.info("Member is Removed: {} after {}",
        member.address, previousStatus)
    case _: MemberEvent =>

  }
}
