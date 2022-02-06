package com.implementation;


import com.interfaces.TimelineService;
import com.twitter.server.Account;
import com.twitter.server.Tweet;

import java.util.ArrayList;
import java.util.Comparator;

import static com.twitter.server.LoadingFiles.followingList;
import static com.twitter.server.LoadingFiles.individualTweets;

/**
 * timeline class
 * showing last tweets.ap of people whom are followed by you
 *
 * @author Mohammad Hoseinkhani
 * @version 0.0
 *
 */
public class TimelineServiceImpl implements TimelineService {
    /**
     * showing tweets.ap of a specific account
     * @param account user A that we want to see all tweets.ap which he tweeted
     * @return an array list of tweets.ap that user A tweeted
     */
    public ArrayList<Tweet> showTweetsOf(Account account) {
        ArrayList<Tweet> tweets;
        if (individualTweets.get(account).isEmpty()) {
            System.err.println("The account has not tweeted yet");
            return null;
        }
        else
            tweets = individualTweets.get(account);
            tweets.sort(Comparator.comparing(Tweet::getDate));
            return tweets;
    }

    /**
     * showing tweets.ap of all accounts that followed by user
     * @param account requester account that wants to see all tweets.ap of accounts that he followed
     * @return an array list of tweets.ap that all followed user tweeted
     */
    public ArrayList<Tweet> showAllTweets(Account account) {    // for another account
        return getTweets(account);
    }

    /**
     * show your tweets
     *
     * @param account your username
     * @return arraylist of tweets
     */
    public ArrayList<Tweet> showMyTweets(Account account) {    // for my account
        ArrayList<Tweet> myTweets = new ArrayList<>();
        for (Tweet tweet : individualTweets.get(account.getUsername())) {
            myTweets.add(tweet);
        }
        myTweets.sort(Comparator.comparing(Tweet::getDate));
        return myTweets;
    }


    /**
     * @param account your username
     * @return arraylist of tweets
     */
    public ArrayList<Tweet> showMyTimeLine(Account account) {
        return getTweets(account);
    }

    /**
     * show number of likes
     *
     * @param tweet specific tweet
     * @return number of likes
     */
    public int showLikes(Tweet tweet) {
        return tweet.getLikes();
    }

    /**
     * show number of retweets
     *
     * @param tweet specific tweet
     * @return number of tweets
     */
    public int showRetweets(Tweet tweet) {
        return tweet.getRetweets();
    }

    /**
     * get tweets of an account
     *
     * @param account
     * @return arraylist of tweets
     */
    private ArrayList<Tweet> getTweets(Account account) {
        ArrayList<Tweet> timeLine = new ArrayList<>();
        for (String followingUser : followingList.get(account.getUsername())) {
            for (Tweet tweet : individualTweets.get(followingUser)) {
                timeLine.add(tweet);
            }
        }
        timeLine.sort(Comparator.comparing(Tweet::getDate));
        return timeLine;
    }

}

