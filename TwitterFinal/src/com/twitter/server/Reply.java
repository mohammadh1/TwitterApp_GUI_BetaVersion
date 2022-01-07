package com.twitter.server;

import java.time.LocalDateTime;
/**
 * Reply class (but it's not working)
 *
 * @author Mohammad Hoseinkhani
 * @version 0.0
 */
public class Reply extends Tweet {
    public Reply(Account sender, String text, LocalDateTime date, int likes, int retweets) {
        super(sender, text, date, likes, retweets);
    }
}
