package com.blakgeek.akka.java.pingpong;

import akka.actor.ActorSystem;
import akka.actor.Props;

/**
 * User: Carlos Lawton
 * Date: 3/20/14
 * Time: 1:45 PM
 */
public class PingPong {

    public static void main(String[] args) {

        ActorSystem stadium = ActorSystem.create();
        stadium.actorOf(Props.create(Game.class, 5));
    }
}
