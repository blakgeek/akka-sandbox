package com.blakgeek.akka;

import akka.actor.ActorSystem;

/**
 * User: Carlos Lawton
 * Date: 3/19/14
 * Time: 8:57 PM
 */
public class Arena {

    public static void main(String[] args) {
        ActorSystem system;
        if (args.length > 0) {
            system = ActorSystem.create(args[0].replaceAll("\\s+", "-"));
        }

//        ActorRef a = system.actorOf(Props.create(HelloWorld.class), "helloWorld");
//        system.actorOf(Props.create(Terminator.class, a), "terminator");
    }
}
