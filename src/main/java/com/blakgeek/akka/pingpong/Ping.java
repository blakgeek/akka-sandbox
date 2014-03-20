package com.blakgeek.akka.pingpong;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import com.blakgeek.akka.pingpong.message.MissedMessage;
import com.blakgeek.akka.pingpong.message.ServeMessage;
import com.blakgeek.akka.pingpong.message.VolleyMessage;

/**
 * User: Carlos Lawton
 * Date: 3/20/14
 * Time: 1:45 PM
 */
public class Ping extends UntypedActor {

    private final ActorRef game;

    public Ping(ActorRef scoreKeeper) {
        this.game = scoreKeeper;
    }

    @Override
    public void onReceive(Object message) throws Exception {

        Class clazz = message.getClass();
        if(clazz.isAssignableFrom(VolleyMessage.class)) {
            VolleyMessage volleyMessage = (VolleyMessage) message;
            if(Math.random() * 10 < 8) {
                System.out.println("Ping!");
                getSender().tell(new VolleyMessage(volleyMessage.getCount() + 1), getSelf());
            } else {
                game.tell(new MissedMessage(), getSelf());
            }
        } else if(clazz.isAssignableFrom(ServeMessage.class)) {

            System.out.println("Game on!\nPING!!!");
            getSender().tell(new VolleyMessage(0), getSelf());
        }
    }
}
