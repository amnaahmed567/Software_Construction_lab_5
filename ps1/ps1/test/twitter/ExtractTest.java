package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import org.junit.Test;

public class ExtractTest {

    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
    private static final Instant d3 = Instant.parse("2016-02-17T12:00:00Z");
    
    private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much?", d1);
    private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talk in 30 minutes #hype", d2);
    private static final Tweet tweet3 = new Tweet(3, "charlie", "Hey @alyssa, are you coming?", d3);

    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }

    @Test
    public void testGetTimespanTwoTweets() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1, tweet2));
        
        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d2, timespan.getEnd());
    }

    @Test
    public void testGetTimespanMultipleTweets() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1, tweet2, tweet3));
        
        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d3, timespan.getEnd());
    }

    @Test
    public void testGetTimespanSingleTweet() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1));
        
        assertEquals("expected start and end", d1, timespan.getStart());
        assertEquals("expected start and end", d1, timespan.getEnd());
    }

    @Test(expected=IllegalArgumentException.class)
    public void testGetTimespanEmptyList() {
        Extract.getTimespan(Collections.emptyList());
    }

    @Test
    public void testGetMentionedUsersNoMention() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet1));
        
        assertTrue("expected empty set", mentionedUsers.isEmpty());
    }

    @Test
    public void testGetMentionedUsersSingleMention() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet3));
        
        assertEquals("expected one mention", 1, mentionedUsers.size());
        assertTrue("expected mention of alyssa", mentionedUsers.contains("alyssa"));
    }

    @Test
    public void testGetMentionedUsersMultipleMentions() {
        Tweet tweetWithMentions = new Tweet(4, "david", "@alyssa and @bbitdiddle are coming", d2);
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweetWithMentions));
        
        assertEquals("expected two mentions", 2, mentionedUsers.size());
        assertTrue("expected mention of alyssa", mentionedUsers.contains("alyssa"));
        assertTrue("expected mention of bbitdiddle", mentionedUsers.contains("bbitdiddle"));
    }

    @Test
    public void testGetMentionedUsersCaseInsensitive() {
        Tweet tweetWithMentions = new Tweet(4, "david", "@Alyssa and @BBITDIDDLE are coming", d2);
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweetWithMentions));
        
        assertEquals("expected two mentions", 2, mentionedUsers.size());
        assertTrue("expected mention of alyssa", mentionedUsers.contains("alyssa"));
        assertTrue("expected mention of bbitdiddle", mentionedUsers.contains("bbitdiddle"));
    }

    @Test
    public void testGetMentionedUsersInvalidMention() {
        Tweet tweetWithInvalidMention = new Tweet(5, "emily", "email me at emily@example.com", d2);
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweetWithInvalidMention));
        
        assertTrue("expected empty set", mentionedUsers.isEmpty());
    }
}
