package com.blakgeek.akka.java.pingpong;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import com.blakgeek.akka.java.pingpong.message.*;

/**
 * User: Carlos Lawton
 * Date: 3/20/14
 * Time: 1:45 PM
 */
public class Pong extends UntypedActor {

    private final ActorRef game;

    public Pong(ActorRef scoreKeeper) {
        this.game = scoreKeeper;
    }

    @Override
    public void onReceive(Object message) throws Exception {

        if(message instanceof VolleyMessage) {
            VolleyMessage volleyMessage = (VolleyMessage) message;
            if(Math.random() * 10 < 8) {
                System.out.println("Pong!");
                getSender().tell(new VolleyMessage(volleyMessage.getCount() + 1), getSelf());
            } else {
                game.tell(new MissedMessage(), getSelf());
            }
        } else if(message instanceof ServeMessage) {

            System.out.println("Game on!\nPONG!!!");
            context().actorFor("../ping").tell(new VolleyMessage(0), getSelf());
        }
    }
}
