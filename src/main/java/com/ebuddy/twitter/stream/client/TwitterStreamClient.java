package com.ebuddy.twitter.stream.client;

import com.ebuddy.twitter.TweetDataStore;
import twitter4j.*;
import twitter4j.auth.AccessToken;

/**
 * Created with IntelliJ IDEA.
 * User: Ramkumar S
 * Date: 21/3/13
 * Time: 1:37 AM
 */
public class TwitterStreamClient {
    private String consumerKey, consumerSecret, token, tokenSecret, filters;

    public void startStream(){
        System.out.println("Starting twitter streaming.....");

        TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
        twitterStream.setOAuthConsumer(consumerKey, consumerSecret);
        AccessToken accessToken = new AccessToken(token, tokenSecret);
        twitterStream.setOAuthAccessToken(accessToken);
        FilterQuery query = new FilterQuery();
        query.track(filters.split(","));
        twitterStream.addListener(new StatusListener() {

            @Override
            public void onException(Exception arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onTrackLimitationNotice(int arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onStatus(Status status) {
                TweetDataStore.getInstance().addTweet(status.getText(), status.getUser().getName());
            }

            @Override
            public void onStallWarning(StallWarning arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onScrubGeo(long arg0, long arg1) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onDeletionNotice(StatusDeletionNotice arg0) {
                // TODO Auto-generated method stub

            }
        });
        twitterStream.filter(query);
    }

    public void stopStream(){
        System.out.println("Stop twitter streaming.....");

    }

    public void setConsumerKey(String consumerKey) {
        this.consumerKey = consumerKey;
    }

    public void setConsumerSecret(String consumerSecret) {
        this.consumerSecret = consumerSecret;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setTokenSecret(String tokenSecret) {
        this.tokenSecret = tokenSecret;
    }

    public void setFilters(String filters) {
        this.filters = filters;
    }

}
