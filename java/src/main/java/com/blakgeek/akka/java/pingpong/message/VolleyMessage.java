package com.blakgeek.akka.java.pingpong.message;

/**
 * User: Carlos Lawton
 * Date: 3/20/14
 * Time: 1:47 PM
 */
public class VolleyMessage {

    private final int count;

    public VolleyMessage(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }
}
