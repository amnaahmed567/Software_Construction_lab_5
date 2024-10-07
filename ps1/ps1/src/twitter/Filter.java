package twitter;

import java.util.List;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Filter {

    /**
     * Find tweets written by the given username.
     * 
     * @param tweets   a list of tweets to search through
     * @param username the author's username (case-insensitive)
     * @return a list of tweets written by username
     */
    public static List<Tweet> writtenBy(List<Tweet> tweets, String username) {
        if (tweets == null || username == null || username.isEmpty()) {
            return new ArrayList<>();
        }

        return tweets.stream()
            .filter(tweet -> tweet.getAuthor().equalsIgnoreCase(username))
            .collect(Collectors.toList());
    }

    /**
     * Find tweets in the specified timespan.
     * 
     * @param tweets   a list of tweets to search through
     * @param timespan the timespan during which tweets should be written
     * @return a list of tweets that are within the given timespan
     */
    public static List<Tweet> inTimespan(List<Tweet> tweets, Timespan timespan) {
        if (tweets == null || timespan == null) {
            return new ArrayList<>();
        }

        return tweets.stream()
            .filter(tweet -> timespan.contains(tweet.getTimestamp()))
            .collect(Collectors.toList());
    }

    /**
     * Find tweets that contain at least one of the words.
     * 
     * @param tweets a list of tweets to search through
     * @param words  a list of words to search for (case-insensitive)
     * @return a list of tweets that contain at least one of the words
     */
    public static List<Tweet> containing(List<Tweet> tweets, List<String> words) {
        if (tweets == null || words == null || tweets.isEmpty() || words.isEmpty()) {
            return new ArrayList<>();
        }

        return tweets.stream()
            .filter(tweet -> {
                String tweetText = tweet.getText().toLowerCase();
                for (String word : words) {
                    String regex = "\\b" + Pattern.quote(word.toLowerCase()) + "\\b";
                    if (Pattern.compile(regex).matcher(tweetText).find()) {
                        return true;
                    }
                }
                return false;
            })
            .collect(Collectors.toList());
    }
}

////val2
//public class Filter {
//
//    /**
//     * Find tweets written by the specified user.
//     *
//     * @param tweets   the list of tweets to filter through
//     * @param username the username to match (case-sensitive)
//     * @return a list of tweets written by the given username
//     */
//    public static List<Tweet> writtenBy(List<Tweet> tweets, String username) {
//        if (tweets == null || username == null || username.isEmpty()) {
//            return new ArrayList<>();
//        }
//
//        // Stream API for filtering and using equalsIgnoreCase for case-insensitive match
//        return tweets.stream()
//                     .filter(tweet -> tweet.getAuthor().equalsIgnoreCase(username))
//                     .collect(Collectors.toList());
//    }
//
//    /**
//     * Find tweets within the specified timespan.
//     *
//     * @param tweets   the list of tweets to filter through
//     * @param timespan the timespan during which tweets should be found
//     * @return a list of tweets that were tweeted within the timespan
//     */
//    public static List<Tweet> inTimespan(List<Tweet> tweets, Timespan timespan) {
//        if (tweets == null || timespan == null) {
//            return new ArrayList<>();
//        }
//
//        // Stream API for filtering tweets within the timespan
//        return tweets.stream()
//                     .filter(tweet -> timespan.contains(tweet.getTimestamp()))
//                     .collect(Collectors.toList());
//    }
//
//    /**
//     * Find tweets that contain at least one of the specified words.
//     *
//     * @param tweets the list of tweets to filter through
//     * @param words  the list of words to search for (case-insensitive)
//     * @return a list of tweets that contain at least one of the words
//     */
//    public static List<Tweet> containing(List<Tweet> tweets, List<String> words) {
//        if (tweets == null || words == null || tweets.isEmpty() || words.isEmpty()) {
//            return new ArrayList<>();
//        }
//
//        // Use streams and pattern matching for case-insensitive and word boundary checks
//        return tweets.stream()
//                     .filter(tweet -> {
//                         String tweetText = tweet.getText().toLowerCase();
//                         return words.stream().anyMatch(word -> {
//                             String regex = "\\b" + Pattern.quote(word.toLowerCase()) + "\\b";
//                             return Pattern.compile(regex).matcher(tweetText).find();
//                         });
//                     })
//                     .collect(Collectors.toList());
//    }
//}
////val3
//public class Filter {
//
//    /**
//     * Find tweets written by a specific user.
//     *
//     * @param tweets   the list of tweets to search through
//     * @param username the username to match (case-insensitive)
//     * @return a list of tweets written by the given username
//     */
//    public static List<Tweet> writtenBy(List<Tweet> tweets, String username) {
//        if (tweets == null || username == null || username.isEmpty()) {
//            return new ArrayList<>();
//        }
//
//        // Use stream and equalsIgnoreCase for case-insensitive matching
//        return tweets.stream()
//                     .filter(tweet -> tweet.getAuthor().equalsIgnoreCase(username))
//                     .collect(Collectors.toList());
//    }
//
//    /**
//     * Find tweets within a specific timespan.
//     *
//     * @param tweets   the list of tweets to search through
//     * @param timespan the timespan to match
//     * @return a list of tweets that were tweeted within the given timespan
//     */
//    public static List<Tweet> inTimespan(List<Tweet> tweets, Timespan timespan) {
//        if (tweets == null || timespan == null) {
//            return new ArrayList<>();
//        }
//
//        // Filter the tweets within the timespan using stream
//        return tweets.stream()
//                     .filter(tweet -> timespan.contains(tweet.getTimestamp()))
//                     .collect(Collectors.toList());
//    }
//
//    /**
//     * Find tweets that contain at least one of the given words.
//     *
//     * @param tweets the list of tweets to search through
//     * @param words  the list of words to match (case-insensitive)
//     * @return a list of tweets that contain at least one of the given words
//     */
//    public static List<Tweet> containing(List<Tweet> tweets, List<String> words) {
//        if (tweets == null || words == null || tweets.isEmpty() || words.isEmpty()) {
//            return new ArrayList<>();
//        }
//
//        // Use streams and regex to check for word boundaries
//        return tweets.stream()
//                     .filter(tweet -> {
//                         String tweetText = tweet.getText().toLowerCase();
//                         return words.stream()
//                                     .anyMatch(word -> {
//                                         String regex = "\\b" + Pattern.quote(word.toLowerCase()) + "\\b";
//                                         return Pattern.compile(regex).matcher(tweetText).find();
//                                     });
//                     })
//                     .collect(Collectors.toList());
//    }
//}
//
////bug task4
//
//
//package twitter;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
//public class TwitterFilter {
//
//    public static void main(String[] args) {
//        // Example usage of the classes and methods
//        List<Tweet> tweets = new ArrayList<>();
//        tweets.add(new Tweet("user1", LocalDateTime.of(2024, 10, 1, 10, 0), "Hello World!"));
//        tweets.add(new Tweet("user2", LocalDateTime.of(2024, 10, 2, 11, 0), "Goodbye world!"));
//        tweets.add(new Tweet("user1", LocalDateTime.of(2024, 10, 3, 12, 0), "Another tweet!"));
//
//        // Test writtenBy method
//        List<Tweet> user1Tweets = Filter.writtenBy(tweets, "user1");
//        System.out.println("Tweets by user1: " + user1Tweets.size()); // Should print 2
//
//        // Test inTimespan method
//        Timespan timespan = new Timespan(LocalDateTime.of(2024, 10, 1, 0, 0), LocalDateTime.of(2024, 10, 2, 23, 59));
//        List<Tweet> tweetsInTimespan = Filter.inTimespan(tweets, timespan);
//        System.out.println("Tweets in timespan: " + tweetsInTimespan.size()); // Should print 2
//
//        // Test containing method
//        List<String> searchWords = List.of("hello", "goodbye");
//        List<Tweet> containingTweets = Filter.containing(tweets, searchWords);
//        System.out.println("Tweets containing specified words: " + containingTweets.size()); // Should print 2
//    }
//
//    /**
//     * Find tweets by a specific author.
//     * 
//     * @param tweets   list of tweets.
//     * @param username the author's username.
//     * @return list of tweets written by the specified user.
//     */
//    public static List<Tweet> writtenBy(List<Tweet> tweets, String username) {
//        List<Tweet> result = new ArrayList<>();
//        for (Tweet tweet : tweets) {
//            if (tweet.getAuthor().equalsIgnoreCase(username)) {
//                result.add(tweet);
//            }
//        }
//        return result;
//    }
//
//    /**
//     * Find tweets that were sent during a specified time period.
//     * 
//     * @param tweets   list of tweets.
//     * @param timespan the time range.
//     * @return list of tweets that fall within the timespan.
//     */
//    public static List<Tweet> inTimespan(List<Tweet> tweets, Timespan timespan) {
//        List<Tweet> result = new ArrayList<>();
//        for (Tweet tweet : tweets) {
//            if (!tweet.getTimestamp().isBefore(timespan.getStart()) && !tweet.getTimestamp().isAfter(timespan.getEnd())) {
//                result.add(tweet);
//            }
//        }
//        return result;
//    }
//
//    /**
//     * Find tweets that contain any of the specified words.
//     * 
//     * @param tweets list of tweets.
//     * @param words  list of words to search for.
//     * @return list of tweets containing any of the words.
//     */
//    public static List<Tweet> containing(List<Tweet> tweets, List<String> words) {
//        List<Tweet> result = new ArrayList<>();
//        for (Tweet tweet : tweets) {
//            String tweetText = tweet.getText().toLowerCase(); // Ensure the tweet text is in lowercase
//            for (String word : words) {
//                if (tweetText.contains(word.toLowerCase())) { // Ensure the word is in lowercase for comparison
//                    result.add(tweet);
//                    break; // Break to avoid adding the same tweet multiple times
//                }
//            }
//        }
//        return result;
//    }
//}
//
//class Tweet {
//    private String author;
//    private LocalDateTime timestamp;
//    private String text;
//
//    public Tweet(String author, LocalDateTime timestamp, String text) {
//        this.author = author;
//        this.timestamp = timestamp;
//        this.text = text;
//    }
//
//    public String getAuthor() {
//        return author;
//    }
//
//    public LocalDateTime getTimestamp() {
//        return timestamp;
//    }
//
//    public String getText() {
//        return text;
//    }
//}
//
//class Timespan {
//    private LocalDateTime start;
//    private LocalDateTime end;
//
//    public Timespan(LocalDateTime start, LocalDateTime end) {
//        this.start = start;
//        this.end = end;
//    }
//
//    public LocalDateTime getStart() {
//        return start;
//    }
//
//    public LocalDateTime getEnd() {
//        return end;
//    }
//}


