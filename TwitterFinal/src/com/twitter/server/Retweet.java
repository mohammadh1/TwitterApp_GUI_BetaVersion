package com.twitter.server;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *
 *
 * @author Mohammad Hoseinkhani
 * @version 0.0
 *
 */
public class Retweet extends Tweet implements Serializable {
    private String header;
    private String idRetweet;
    public Retweet(String header, Account sender, String text, LocalDateTime date, int likes, int retweets) {
        super(sender, text, date, likes, retweets);
        this.header = header;
        idRetweet = (countTweet++) + "";
    }

    /**
     * getter and setter for retweeters and header
     * header example : "Jack retweeted"
     */
    public String getHeader() {
        return header;
    }
    public void setHeader(String header) {
        this.header = header;
    }
}
