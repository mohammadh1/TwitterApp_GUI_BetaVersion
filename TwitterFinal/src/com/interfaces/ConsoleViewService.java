package com.interfaces;

import com.twitter.server.Tweet;

import java.io.File;
import java.util.ArrayList;

public interface ConsoleViewService {
    //void terminalStart(File file);
    Boolean login(File file);
    Boolean signup();
    ArrayList<Tweet> timeline();
    ArrayList<Tweet> showTweetOfPerson();
    Boolean like();
    Boolean follow();
    Boolean unfollow();
}
