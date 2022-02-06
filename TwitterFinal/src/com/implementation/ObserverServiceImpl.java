package com.implementation;

import com.interfaces.ObserverService;
import com.twitter.server.Account;
import com.twitter.server.LoadingFiles;

import java.util.ArrayList;

import static com.twitter.server.LoadingFiles.*;
import static com.twitter.server.LoadingFiles.loadingFollowingList;

/**
 * follow and unfollow people
 *
 * @author Mohammad Hoseinkhani
 * @version 0.0
 *
 */
public class ObserverServiceImpl implements ObserverService {
    /**
     * @param doer account who wants to follow someone
     * @param target account whom supposed to be followed by doer account
     * @return boolean that shows whether operation is successful or not
     */
    public boolean follow(Account doer, Account target) {
        new LoadingFiles();
        loadingAccounts();
        loadingTweets();
        loadingFollowingList();
        if (doer.getFollowing() == 0) {
            followingList.put(doer.getUsername(), new ArrayList<>());
            followingList.get(doer.getUsername()).add(target.getUsername());
            doer.setFollowing(doer.getFollowing() + 1);
            return true;
        }
        else if (doer.getFollowing() != 0) {
            followingList.get(doer.getUsername()).add(target.getUsername());
            doer.setFollowing(doer.getFollowing() + 1);
            return true;
        }
        else
            return false;
    }
    /**
     * @param doer account who wants to unfollow someone
     * @param target account whom supposed to be unfollowed by doer account
     * @return boolean that shows whether operation is successful or not
     */
    public boolean unfollow(Account doer, Account target) {
        if (doer.getFollowing() != 0) {
            followingList.get(doer.getUsername()).remove(target.getUsername());
            doer.setFollowing(doer.getFollowing() - 1);
            return true;
        }
        else {
            System.err.println("You have not followed this account yet");
            return false;
        }
    }
}

