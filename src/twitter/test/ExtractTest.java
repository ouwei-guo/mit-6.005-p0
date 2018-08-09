/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter.test;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.Set;

import org.junit.Test;

import twitter.Extract;
import twitter.Timespan;
import twitter.Tweet;

public class ExtractTest {

    /*
     * Testing strategy
     * Partition the inputs as follows:
     * list.size(): > 1
     */
    
    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
    private static final Instant d3 = Instant.parse("2016-02-17T11:00:01Z");
    private static final Instant d4 = Instant.parse("2016-02-17T11:10:01Z");
    
    
    private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much?", d1);
    private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talk in 30 minutes #hype", d2);
    private static final Tweet tweet3 = new Tweet(3, "kruger", "talk @alyssa good, bitdiddle@mit.edu talk smart #hype", d3);
    private static final Tweet tweet4 = new Tweet(4, "donning", "talk @alyssa good, @bbitdiddle @kruger bitdiddle@mit.edu talk smart #hype", d4);
    
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    // tests two tweets
    @Test
    public void testGetTimespanTwoTweets() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1, tweet2));
        
        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d2, timespan.getEnd());
    }
    
    @Test
    public void testGetTimespan() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1, tweet2, tweet3));
        
        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d3, timespan.getEnd());
    }
    
    @Test
    public void testGetMentionedUsersNoMention() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet1));
        
        assertTrue("expected empty set", mentionedUsers.isEmpty());
    }
    
    // tests one mention
    @Test
    public void testGetMentionedUsersOneMention() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet1, tweet3));
        
        assertTrue("set of one", mentionedUsers.contains("alyssa"));
    }
    
    // tests multiple mention
    @Test
    public void testGetMentionedUsersMoreMention() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet1, tweet4));
        
        assertTrue("mention alyssa", mentionedUsers.contains("alyssa"));
        assertTrue("mention bbitdiddle", mentionedUsers.contains("bbitdiddle"));
        assertTrue("mention kruger", mentionedUsers.contains("kruger"));
    }

    /*
     * Warning: all the tests you write here must be runnable against any
     * Extract class that follows the spec. It will be run against several staff
     * implementations of Extract, which will be done by overwriting
     * (temporarily) your version of Extract with the staff's version.
     * DO NOT strengthen the spec of Extract or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in Extract, because that means you're testing a
     * stronger spec than Extract says. If you need such helper methods, define
     * them in a different class. If you only need them in this test class, then
     * keep them in this test class.
     */

}
