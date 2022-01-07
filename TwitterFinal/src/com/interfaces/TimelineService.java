package com.interfaces;

import com.twitter.server.Account;
import com.twitter.server.Tweet;

import java.util.ArrayList;

public interface TimelineService {
    ArrayList<Tweet> showTweetsOf(Account account);
    ArrayList<Tweet> showAllTweets(Account account);
    ArrayList<Tweet> showMyTweets(Account account);
    ArrayList<Tweet> showMyTimeLine(Account account);
    int showLikes(Tweet tweet);
    int showRetweets(Tweet tweet);
}
