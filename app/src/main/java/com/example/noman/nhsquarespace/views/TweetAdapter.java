package com.example.noman.nhsquarespace.views;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.noman.nhsquarespace.R;
import com.example.noman.nhsquarespace.models.Tweet;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Noman on 11/5/2015.
 */
public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.TweetViewHolder> {

    private ArrayList<Tweet> tweets;
    private Context context;
    private int lastPosition = -1;

    public TweetAdapter(ArrayList<Tweet> myTweets, Context c) {
        this.tweets = myTweets;
        this.context = c;
    }

    @Override
    public void onBindViewHolder(TweetViewHolder holder, int position) {
        Tweet t = tweets.get(position);
        holder.username.setText(t.getUsername());
        holder.tweetText.setText(t.getTweetText());
        Picasso.with(context).load(t.getProfileImageUrl()).into(holder.profileImage);
        final String url= "https://twitter.com/" + t.getUsername()
                + "/status/" + t.getId();
        holder.tweetCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(browserIntent);
            }
        });
        setAnimation(holder.itemView, position);
    }



    @Override
    public TweetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.tweet_cards, parent, false);

        return new TweetViewHolder(itemView);
    }

    private void setAnimation(View viewToAnimate, int position) {
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }

    public static class TweetViewHolder extends RecyclerView.ViewHolder {

        protected TextView username;
        protected TextView tweetText;
        protected ImageView profileImage;
        protected CardView tweetCard;

        public TweetViewHolder(View itemView) {
            super(itemView);
            tweetCard = (CardView) itemView.findViewById(R.id.tweetCardView);
            username = (TextView) itemView.findViewById(R.id.tweetUser);
            tweetText = (TextView) itemView.findViewById(R.id.tweetText);
            profileImage = (ImageView) itemView.findViewById(R.id.tweetProfile);
        }
    }
}
