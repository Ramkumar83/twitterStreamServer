package com.ebuddy.twitter;

import com.ebuddy.twitter.nio.server.NioHttpServer;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created with IntelliJ IDEA.
 * User: Ramkumar S
 * Date: 21/3/13
 * Time: 3:07 AM
 */
public class TweetDataStore {

    private static TweetDataStore tweetDataStore;

    public Map<String, ConcurrentLinkedQueue<String>> tweets = new HashMap<String, ConcurrentLinkedQueue<String>>();

    private String [] filters;

    public static TweetDataStore createInstance(String filters){
        System.out.println("Initializing DataStore....");
        tweetDataStore = new TweetDataStore();
        tweetDataStore.setFilters(filters);
        tweetDataStore.initialize();
        return tweetDataStore;
    }

    private void initialize(){
        for(String filter : filters){
            tweets.put(filter, new ConcurrentLinkedQueue<String>());
        }
    }

    public static TweetDataStore getInstance(){
        return tweetDataStore;
    }



    public void addTweet(String tweet, String user){
        for(String filter : filters){
            if(tweet.contains(filter)){
                tweets.get(filter).add(tweet + " by " + user);
            }
        }
    }

    public String [] getLatestTweets(String filter){
        ConcurrentLinkedQueue<String> filteredTweets = tweets.get(filter);
        if(filteredTweets != null){
            String[] latestTweets = filteredTweets.toArray(new String[filteredTweets.size()]);
            filteredTweets.clear();
            return latestTweets;
        }else{
            return null;
        }
    }

    public void setFilters(String tweetFilters) {
        filters = tweetFilters.split(",");
    }


}
