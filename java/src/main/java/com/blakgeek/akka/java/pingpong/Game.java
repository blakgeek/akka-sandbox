package com.blakgeek.akka.java.pingpong;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import com.blakgeek.akka.java.pingpong.message.*;


/**
 * User: Carlos Lawton
 * Date: 3/20/14
 * Time: 1:55 PM
 */
public class Game extends UntypedActor {

    private final ActorRef ping;
    private final ActorRef pong;
    private final ActorRef scoreKeeper;

    public Game(Integer pointsNeededToWin) {

        ping = getContext().actorOf(Props.create(Ping.class, getSelf()), "ping");
        pong = getContext().actorOf(Props.create(Pong.class, getSelf()), "pong");
        scoreKeeper = getContext().actorOf(Props.create(ScoreKeeper.class, ping, pong, pointsNeededToWin), "score-keeper");
    }

    @Override
    public void preStart() throws Exception {

        ping.tell(new ServeMessage(), getSelf());
    }

    @Override
    public void onReceive(Object message) throws Exception {

        if(message instanceof MissedMessage) {

            String name = getSender().path().name();
            System.out.println(String.format("%s missed.", name));
            scoreKeeper.tell(name, getSender());
        }
    }
}
