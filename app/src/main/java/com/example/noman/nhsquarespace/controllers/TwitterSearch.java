package com.example.noman.nhsquarespace.controllers;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;

import com.example.noman.nhsquarespace.R;
import com.example.noman.nhsquarespace.models.Tweet;
import com.example.noman.nhsquarespace.views.TweetAdapter;

import java.util.ArrayList;
import java.util.List;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.OAuth2Token;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Created by Noman on 11/5/2015.
 */
public class TwitterSearch extends AsyncTask<String, Void, Integer> {

    private ArrayList<Tweet> tweets;
    private RecyclerView tweetsRV;
    private String TWIT_CONS_KEY = "";
    private String TWIT_CONS_SEC_KEY = "";
    private Context context;
    final int SUCCESS = 1;
    final int FAILURE = 0;

    public TwitterSearch(RecyclerView tweetRV, Context c) {
        this.tweetsRV = tweetRV;
        this.context = c;
        this.TWIT_CONS_KEY = c.getString(R.string.twitter_customer_key);
        this.TWIT_CONS_SEC_KEY = c.getString(R.string.twitter_customer_secret);
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Integer doInBackground(String... params) {
        try {
            ConfigurationBuilder builder = new ConfigurationBuilder();
            builder.setApplicationOnlyAuthEnabled(true);
            builder.setOAuthConsumerKey(TWIT_CONS_KEY);
            builder.setOAuthConsumerSecret(TWIT_CONS_SEC_KEY);

            OAuth2Token token = new TwitterFactory(builder.build()).getInstance().getOAuth2Token();

            builder = new ConfigurationBuilder();
            builder.setApplicationOnlyAuthEnabled(true);
            builder.setOAuthConsumerKey(TWIT_CONS_KEY);
            builder.setOAuthConsumerSecret(TWIT_CONS_SEC_KEY);
            builder.setOAuth2TokenType(token.getTokenType());
            builder.setOAuth2AccessToken(token.getAccessToken());

            Twitter twitter = new TwitterFactory(builder.build()).getInstance();

            Query query = new Query(params[0]);
            query.setCount(25);
            QueryResult result;
            result = twitter.search(query);
            List<twitter4j.Status> TJtweets = result.getTweets();
            if (TJtweets != null) {
                this.tweets = new ArrayList<Tweet>();
                for (twitter4j.Status tweet : TJtweets) {
                    this.tweets.add(new Tweet("@" + tweet.getUser().getScreenName(), tweet.getText(), tweet.getUser().getBiggerProfileImageURL()));
                }
            }
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return FAILURE;
        }
    }

    @Override
    protected void onPostExecute(Integer result) {
        if (result == SUCCESS) {
            TweetAdapter ta = new TweetAdapter(tweets, context);
            tweetsRV.setAdapter(ta);
        }
        super.onPostExecute(result);
    }

}
