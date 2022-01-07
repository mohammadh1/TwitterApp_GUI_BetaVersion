package com.implementation;


import com.interfaces.TweetingService;
import com.twitter.server.Reply;
import com.twitter.server.Retweet;
import com.twitter.server.Tweet;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static com.twitter.server.LoadingFiles.individualTweets;

/**
 * tweeting class
 * send tweets.ap
 * delete tweets.ap
 * retweet tweets.ap
 * like tweets.ap
 * reply tweets.ap (not completed yet)
 *
 * @author Mohammad Hoseinkhani
 * @version 0.0
 *
 */
public class TweetingServiceImpl implements TweetingService {

    public void tweeting(Tweet tweet) {
        if (!individualTweets.containsKey(tweet.getSender().getUsername())) {
            individualTweets.put(tweet.getSender().getUsername(), new ArrayList<>());
            individualTweets.get(tweet.getSender().getUsername()).add(tweet);
        }
        else {
            individualTweets.get(tweet.getSender().getUsername()).add(tweet);
        }
    }

    public void deleting(Tweet deletedTweet) {
        if (individualTweets.containsKey(deletedTweet.getSender().getUsername())) {
            if (individualTweets.get(deletedTweet.getSender().getUsername()).contains(deletedTweet)) {
                individualTweets.get(deletedTweet.getSender().getUsername()).remove(deletedTweet);
            }
            else {
                System.err.println("The Tweet has not been existed");
            }
        }
        else {
            System.err.println("The account has not tweeted anything yet");
        }
    }
    // assign tweet to another account
    public void retweeting(Tweet retweetedTweet, String usernameOfRetweeter) {
        Retweet retweet = new Retweet("Retweeted from " + retweetedTweet.getSender(),
                retweetedTweet.getSender(),
                retweetedTweet.getText(),
                LocalDateTime.now(),
                0,
                0);
        retweetedTweet.setRetweets(retweetedTweet.getRetweets()+1);
        retweetedTweet.getRetweeters().add(usernameOfRetweeter);
        retweetedTweet.setRetweeters(retweetedTweet.getRetweeters());
        tweeting(retweetedTweet);
    }

    // add up like number for the tweet
    public void liking(Tweet likedTweet, String usernameOfLiker) {
        likedTweet.setLikes(likedTweet.getLikes()+1);
        likedTweet.getLikers().add(usernameOfLiker);
        likedTweet.setLikeList(likedTweet.getLikers());
    }
    public void replying(Tweet tweet, Reply reply) {}
}
