package com.twitter.server;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

/**
 * timeline class
 * showing last tweets.ap of people whom are followed by you
 *
 * @author Mohammad Hoseinkhani
 * @version 0.0
 *
 */
public class Tweet implements Serializable {
    private Account sender;
    private String text;
    private LocalDateTime date;
    private int likes;
    private ArrayList<String> likers;
    private int retweets;
    private ArrayList<String> retweeters;
    public static int countTweet = 0;
    private String idTweet;

    public Tweet(Account sender, String text, LocalDateTime date, int likes, int retweets) {
        this.sender = sender;
        this.text = text;
        this.date = date;
        this.likes = likes;
        this.likers = new ArrayList<>();
        this.retweets = retweets;
        this.retweeters = new ArrayList<>();
        this.idTweet = (countTweet++) + "";
    }
    public Tweet(Account sender, String text, LocalDateTime date) {
        this.sender = sender;
        this.text = text;
        this.date = date;
        this.likes = 0;
        this.likers = new ArrayList<>();
        this.retweets = 0;
        this.retweeters = new ArrayList<>();
        this.idTweet = countTweet++ + "";
    }
    public Tweet(Account sender, String text) {
        this.sender = sender;
        this.text = text;
        this.date = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);   // limit to minutes not further
        this.likes = 0;
        this.likers = new ArrayList<>();
        this.retweets = 0;
        this.retweeters = new ArrayList<>();
        this.idTweet = countTweet++ + "";
    }


    /**
     * getter methods of Tweet class for all methods
     */
    public Account getSender() {
        return sender;
    }
    public String getText() {
        return text;
    }
    public LocalDateTime getDate() {
        return date;
    }
    public int getLikes() {
        return likes;
    }
    public int getRetweets() {
        return retweets;
    }
    public String getIdTweet() {
        return idTweet;
    }
    public ArrayList<String> getLikers() {
        return likers;
    }
    public ArrayList<String> getRetweeters() {
        return retweeters;
    }


    /**
     * setter methods of Tweet class for text , likes and retweets number
     */
    protected void setIdTweet(String idTweet) {
        this.idTweet = idTweet;
    }
    public void setText(String text) {
        this.text = text;
    }
    public void setLikes(int likes) {
        this.likes = likes;
    }
    public void setRetweets(int retweets) {
        this.retweets = retweets;
    }
    public void setLikeList(ArrayList<String> likeList) {
        this.likers = likeList;
    }
    public void setRetweeters(ArrayList<String> retweeter) {
        this.retweeters = retweeter;
    }


    public boolean checkText() {
        if (this.text.length() > 256) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Tweet : " +
                "text='" + text + '\'' +
                ", date=" + date +
                ", likes=" + likes +
                ", retweets=" + retweets +
                '}';
    }
}
