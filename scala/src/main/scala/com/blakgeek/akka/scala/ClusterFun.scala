package com.blakgeek.akka.scala

import akka.actor.{ActorLogging, Props, ActorSystem, Actor}
import akka.cluster.Cluster
import akka.cluster.ClusterEvent._
import akka.cluster.ClusterEvent.MemberUp
import akka.cluster.ClusterEvent.UnreachableMember
import com.typesafe.config.{Config, ConfigFactory}

/**
 * User: Carlos Lawton
 * Date: 3/20/14
 * Time: 10:57 PM
 */
object ClusterFun {


  def main(args: Array[String]): Unit = {

    val port = if(args.length > 0) args(0) else 0
    val customConfig = ConfigFactory.parseString("akka.remote.netty.tcp.port=" + port)
    val appConfig = ConfigFactory.load();
    val config: Config = customConfig.withFallback(appConfig.getConfig("clustered")).withFallback(appConfig)
    val system = ActorSystem("da-cluster", config)
    system.actorOf(Props[ClusterVoyeur], "da-watcher")
  }
}

class ClusterVoyeur extends Actor with ActorLogging {

  val cluster = Cluster.get(context.system)

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
