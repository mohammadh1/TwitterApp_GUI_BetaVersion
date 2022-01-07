package com.interfaces;

import com.twitter.server.Account;

public interface ObserverService {
    boolean follow(Account doer, Account target);
    boolean unfollow(Account doer, Account target);
}
