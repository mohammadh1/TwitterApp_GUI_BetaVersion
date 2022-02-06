package com.interfaces;

import com.twitter.server.Tweet;

import java.io.File;
import java.util.ArrayList;

public interface ConsoleViewService {
    //void terminalStart(File file);
    Boolean login(File file);
    Boolean signup(File file);
    String timeline(File file);
    ArrayList<Tweet> showTweetOfPerson(File file);
    Boolean like(File file);
    Boolean follow(File file);
    Boolean unfollow(File file);
}
