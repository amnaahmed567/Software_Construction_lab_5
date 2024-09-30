package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

import org.junit.Test;

public class ExtractTest {

    /*
     * Testing strategy for getTimespan():
     * 
     * Partition the inputs as follows:
     * - number of tweets: 0, 1, 2, >2
     * - tweet times: same, different
     * 
     * Testing strategy for getMentionedUsers():
     * 
     * Partition the inputs as follows:
     * - number of tweets: 0, 1, >1
     * - mentions: no mention, one mention, multiple mentions
     * - case sensitivity: different cases for the same username
     * - usernames: valid, invalid (preceded or followed by invalid characters)
     */
    
    private static final Instant d1 = Instant.parse("2022-07-01T08:00:00Z");
    private static final Instant d2 = Instant.parse("2022-07-01T09:30:00Z");
    private static final Instant d3 = Instant.parse("2022-07-01T10:15:00Z");

    private static final Tweet tweet1 = new Tweet(1, "sam", "Loving the summer vibes!", d1);
    private static final Tweet tweet2 = new Tweet(2, "alex", "Meeting in 15 minutes #excited", d2);
    private static final Tweet tweet3 = new Tweet(3, "sam", "@mike check this out!", d3);
    private static final Tweet tweet4 = new Tweet(4, "jordan", "@mike @SARA having a great time", d3);

    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    // Test case: Two tweets with different timestamps
    @Test
    public void testGetTimespanTwoTweets() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1, tweet2));
        
        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d2, timespan.getEnd());
    }

    // Test case: One tweet, start and end should be the same
    @Test
    public void testGetTimespanOneTweet() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1));
        
        assertEquals("expected start and end to be the same", d1, timespan.getStart());
        assertEquals("expected start and end to be the same", d1, timespan.getEnd());
    }

    // Test case: No tweets, should throw IllegalArgumentException
    @Test(expected = IllegalArgumentException.class)
    public void testGetTimespanNoTweets() {
        Extract.getTimespan(Arrays.asList()); // empty list
    }

    // Test case: Multiple tweets with different timestamps
    @Test
    public void testGetTimespanMultipleTweets() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1, tweet2, tweet3));
        
        assertEquals("expected start to be the earliest timestamp", d1, timespan.getStart());
        assertEquals("expected end to be the latest timestamp", d3, timespan.getEnd());
    }

    // Test case: No mention in the tweet, should return an empty set
    @Test
    public void testGetMentionedUsersNoMention() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet1));
        
        assertTrue("expected empty set", mentionedUsers.isEmpty());
    }

    // Test case: One tweet with a single @mention
    @Test
    public void testGetMentionedUsersOneMention() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet3));
        
        assertTrue("expected set to contain 'mike'", mentionedUsers.contains("mike"));
        assertEquals("expected size of set to be 1", 1, mentionedUsers.size());
    }

    // Test case: Multiple tweets with multiple mentions
    @Test
    public void testGetMentionedUsersMultipleMentions() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet3, tweet4));
        
        Set<String> expectedUsers = new HashSet<>(Arrays.asList("mike", "sara"));
        
        assertEquals("expected mentioned users", expectedUsers, mentionedUsers);
    }

    // Test case: Case insensitivity for usernames
    @Test
    public void testGetMentionedUsersCaseInsensitive() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet4));
        
        assertTrue("expected set to contain 'mike' and 'sara' in lowercase", 
                   mentionedUsers.containsAll(Arrays.asList("mike", "sara")));
    }

    // Test case: Invalid mentions (usernames preceded by invalid characters)
    @Test
    public void testGetMentionedUsersInvalidMentions() {
        Tweet tweetWithInvalidMentions = new Tweet(5, "david", "Invalid mention: @name!@example.", d1);
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweetWithInvalidMentions));
        
        assertTrue("expected empty set due to invalid mentions", mentionedUsers.isEmpty());
    }
}
