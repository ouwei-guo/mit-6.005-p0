/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import java.time.Instant;
import java.util.*;
import java.util.regex.*;

/**
 * Extract consists of methods that extract information from a list of tweets.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class Extract {

    /**
     * Get the time period spanned by tweets.
     * 
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     * @return a minimum-length time interval that contains the timestamp of
     *         every tweet in the list.
     */
    public static Timespan getTimespan(List<Tweet> tweets) {
        if(tweets.size()<2) {
            throw new IllegalArgumentException("requires a list of two or more tweet");
        }
        Instant start = Instant.now();
        Instant end = tweets.get(0).getTimestamp();
        for(Tweet t: tweets) {
            if(t.getTimestamp().isBefore(start)) {
                start = t.getTimestamp();
                continue;
            }
            if(t.getTimestamp().isAfter(end)) {
                end = t.getTimestamp();
            }
        }
        return new Timespan(start, end);
    }

    /**
     * Get usernames mentioned in a list of tweets.
     * 
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     * @return the set of usernames who are mentioned in the text of the tweets.
     *         A username-mention is "@" followed by a Twitter username (as
     *         defined by Tweet.getAuthor()'s spec).
     *         The username-mention cannot be immediately preceded or followed by any
     *         character valid in a Twitter username.
     *         For this reason, an email address like bitdiddle@mit.edu does NOT 
     *         contain a mention of the username mit.
     *         Twitter usernames are case-insensitive, and the returned set may
     *         include a username at most once.
     */
    public static Set<String> getMentionedUsers(List<Tweet> tweets) {
        if(tweets.size()<1) {
            throw new IllegalArgumentException("requires a list of one or more tweets");
        }
        HashSet<String> mentioned = new HashSet<String>();
        String regex = "\\B@{1}[\\da-zA-Z-_]{1,}"; //regex that matches the requirements
        Pattern p = Pattern.compile(regex);
        Matcher m;
        String text;
        for(Tweet t: tweets) {
            text = t.getText();
            m = p.matcher(text);
            while(m.find()) {
                mentioned.add(text.substring(m.start()+1, m.end()));
            }
        }
        return mentioned;
    }

}
