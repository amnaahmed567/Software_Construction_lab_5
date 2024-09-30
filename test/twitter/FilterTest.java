/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import static org.junit.Assert.*;
import static org.junit.Assert.*;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.Test;

public class FilterTest {

    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
    
    private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much?", d1);
    private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talk in 30 minutes #hype", d2);
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    // Tests for writtenBy()
    @Test
    public void testWrittenBySingleResult() {
        List<Tweet> result = Filter.writtenBy(Arrays.asList(tweet1, tweet2), "alyssa");
        assertEquals(1, result.size());
        assertTrue(result.contains(tweet1));
    }
    
    @Test
    public void testWrittenByNoResults() {
        List<Tweet> result = Filter.writtenBy(Arrays.asList(tweet1, tweet2), "nonexistentuser");
        assertTrue(result.isEmpty());
    }

    // Tests for inTimespan()
    @Test
    public void testInTimespanMultipleResults() {
        Instant testStart = Instant.parse("2016-02-17T09:00:00Z");
        Instant testEnd = Instant.parse("2016-02-17T12:00:00Z");
        List<Tweet> result = Filter.inTimespan(Arrays.asList(tweet1, tweet2), new Timespan(testStart, testEnd));
        assertEquals(2, result.size());
        assertTrue(result.containsAll(Arrays.asList(tweet1, tweet2)));
    }

    @Test
    public void testInTimespanNoResults() {
        Instant testStart = Instant.parse("2016-02-17T12:00:01Z");
        Instant testEnd = Instant.parse("2016-02-17T12:05:00Z");
        List<Tweet> result = Filter.inTimespan(Arrays.asList(tweet1, tweet2), new Timespan(testStart, testEnd));
        assertTrue(result.isEmpty());
    }

    // Tests for containing()
    @Test
    public void testContainingWordsPresent() {
        List<Tweet> result = Filter.containing(Arrays.asList(tweet1, tweet2), Arrays.asList("talk"));
        assertFalse(result.isEmpty());
        assertTrue(result.containsAll(Arrays.asList(tweet1, tweet2)));
    }

    @Test
    public void testContainingNoResults() {
        List<Tweet> result = Filter.containing(Arrays.asList(tweet1, tweet2), Arrays.asList("notaword"));
        assertTrue(result.isEmpty());
    }

    @Test
    public void testContainingMixedCase() {
        List<Tweet> result = Filter.containing(Arrays.asList(tweet1, tweet2), Arrays.asList("RIVEST"));
        assertFalse(result.isEmpty());
        assertTrue(result.containsAll(Arrays.asList(tweet1, tweet2)));
    }
}
