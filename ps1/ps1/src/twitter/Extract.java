//task4
//package twitter;
//
//import java.time.Instant;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//public class Extract {
//
//    /**
//     * Get the time period spanned by tweets.
//     *
//     * @param tweets list of tweets with distinct ids, not modified by this method.
//     * @return a minimum-length time interval that contains the timestamp of every tweet in the list.
//     */
//
//    public static Timespan getTimespan(List<Tweet> tweets) {
//        if (tweets.isEmpty()) {
//            return null;
//        }
//        
//        // Bug: Always taking the first and second tweets (if present), instead of calculating the actual earliest and latest timestamps.
//        Instant start = tweets.get(0).getTimestamp();
//        Instant end = tweets.size() > 1 ? tweets.get(1).getTimestamp() : start;
//        
//        return new Timespan(start, end);
//    }
//
//    /**
//     * Get usernames mentioned in a list of tweets.
//     *
//     * @param tweets list of tweets with distinct ids, not modified by this method.
//     * @return the set of usernames who are mentioned in the text of the tweets.
//     * A username-mention is "@" followed by a Twitter username (as defined by Tweet.getAuthor()'s spec).
//     * The username-mention cannot be immediately preceded or followed by any character valid in a Twitter username.
//
//    public static Set<String> getMentionedUsers(List<Tweet> tweets) {
//        Set<String> mentionedUsers = new HashSet<>();
//        
//        for (Tweet tweet : tweets) {
//            String text = tweet.getText();
//            int atIndex = text.indexOf('@');
//            if (atIndex != -1) {
//                // Bug: Only extracting the first mentioned user and ignoring the rest.
//                int endIndex = text.indexOf(' ', atIndex);
//                if (endIndex == -1) endIndex = text.length();
//                String mentionedUser = text.substring(atIndex + 1, endIndex).toLowerCase();
//                mentionedUsers.add(mentionedUser);
//            }
//        }
//        
//        return mentionedUsers;
//    }
//}
//

package twitter;

import java.util.List;
import java.time.Instant;
import java.util.Set;
import java.util.HashSet;

public class Extract {
    
    public static Timespan getTimespan(List<Tweet> tweets) {
        if (tweets.isEmpty()) {
            throw new IllegalArgumentException("Tweet list cannot be empty.");
        }
        
        Instant start = tweets.stream().map(Tweet::getTimestamp).min(Instant::compareTo).get();
        Instant end = tweets.stream().map(Tweet::getTimestamp).max(Instant::compareTo).get();
        
        return new Timespan(start, end);
    }

    public static Set<String> getMentionedUsers(List<Tweet> tweets) {
        Set<String> mentionedUsers = new HashSet<>();
        
        for (Tweet tweet : tweets) {
            String[] words = tweet.getText().split("\\s+");
            for (String word : words) {
                if (word.matches("@[A-Za-z0-9_]+")) {
                    String mention = word.substring(1).toLowerCase();
                    mentionedUsers.add(mention);
                }
            }
        }

        return mentionedUsers;
    }
}

//package twitter;
//
//import java.util.List;
//import java.time.Instant;
//import java.util.Set;
//import java.util.TreeSet;
//
//public class ExtractVariant2 {
//    
//    public static Timespan getTimespan(List<Tweet> tweets) {
//        if (tweets.isEmpty()) {
//            throw new IllegalArgumentException("Tweet list cannot be empty.");
//        }
//        
//        Instant start = tweets.get(0).getTimestamp();
//        Instant end = start;
//
//        for (Tweet tweet : tweets) {
//            Instant timestamp = tweet.getTimestamp();
//            if (timestamp.isBefore(start)) {
//                start = timestamp;
//            }
//            if (timestamp.isAfter(end)) {
//                end = timestamp;
//            }
//        }
//
//        return new Timespan(start, end);
//    }
//
//    public static Set<String> getMentionedUsers(List<Tweet> tweets) {
//        Set<String> mentionedUsers = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
//        
//        for (Tweet tweet : tweets) {
//            String[] words = tweet.getText().split("\\s+");
//            for (String word : words) {
//                if (word.startsWith("@") && word.length() > 1) {
//                    String mention = word.substring(1).replaceAll("[^A-Za-z0-9_]", "").toLowerCase();
//                    mentionedUsers.add(mention);
//                }
//            }
//        }
//
//        return mentionedUsers;
//    }
//}
//package twitter;
//
//import java.util.List;
//import java.time.Instant;
//import java.util.Set;
//import java.util.HashSet;
//
//public class ExtractVariant3 {
//
//    public static Timespan getTimespan(List<Tweet> tweets) {
//        if (tweets.isEmpty()) {
//            throw new IllegalArgumentException("Tweet list cannot be empty.");
//        }
//        
//        Instant minTime = tweets.get(0).getTimestamp();
//        Instant maxTime = tweets.get(0).getTimestamp();
//        
//        for (Tweet tweet : tweets) {
//            Instant timestamp = tweet.getTimestamp();
//            if (timestamp.isBefore(minTime)) {
//                minTime = timestamp;
//            }
//            if (timestamp.isAfter(maxTime)) {
//                maxTime = timestamp;
//            }
//        }
//        
//        return new Timespan(minTime, maxTime);
//    }
//
//    public static Set<String> getMentionedUsers(List<Tweet> tweets) {
//        Set<String> mentionedUsers = new HashSet<>();
//        
//        for (Tweet tweet : tweets) {
//            String text = tweet.getText();
//            int atIndex = text.indexOf('@');
//            while (atIndex != -1) {
//                int endIndex = atIndex + 1;
//                while (endIndex < text.length() && Character.isLetterOrDigit(text.charAt(endIndex))) {
//                    endIndex++;
//                }
//                String mention = text.substring(atIndex + 1, endIndex).toLowerCase();
//                mentionedUsers.add(mention);
//                atIndex = text.indexOf('@', endIndex);
//            }
//        }
//        
//        return mentionedUsers;
//    }
//}
