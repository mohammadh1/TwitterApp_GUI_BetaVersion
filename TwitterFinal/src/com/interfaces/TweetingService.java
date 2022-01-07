package com.interfaces;

import com.twitter.server.Reply;
import com.twitter.server.Tweet;

public interface TweetingService {
    void tweeting(Tweet tweet);
    void deleting(Tweet deletedTweet);
    void retweeting(Tweet retweetedTweet, String usernameOfRetweeter);
    void liking(Tweet likedTweet, String usernameOfLiker);
    void replying(Tweet tweet, Reply reply);
}
