package com.blakgeek.akka.java.pingpong;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import com.blakgeek.akka.java.pingpong.message.*;

/**
 * User: Carlos Lawton
 * Date: 3/20/14
 * Time: 2:53 PM
 */
public class ScoreKeeper extends UntypedActor {

    private final Integer pointsToWin;
    private final ActorRef ping;
    private final ActorRef pong;
    private Integer pingPoints = 0;
    private Integer pongPoints = 0;

    public ScoreKeeper(ActorRef ping, ActorRef pong, Integer pointsToWin) {
        this.pointsToWin = pointsToWin;
        this.ping = ping;
        this.pong = pong;
    }

    @Override
    public void onReceive(Object name) throws Exception {

        if ("pong".equals(name)) {
            pingPoints++;
            if (pingPoints >= pointsToWin) {
                System.out.println(String.format("Ping won by %d", pingPoints - pongPoints));
                getContext().system().shutdown();
            } else {
                ping.tell(new ServeMessage(), pong);
            }
        } else if ("ping".equals(name)) {
            pongPoints++;
            if (pongPoints >= pointsToWin) {
                System.out.println(String.format("Pong won by %d", pongPoints - pingPoints));
                getContext().system().shutdown();
            } else {
                pong.tell(new ServeMessage(), ping);
            }
        }
    }
}
