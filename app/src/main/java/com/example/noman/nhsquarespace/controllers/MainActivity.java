package com.example.noman.nhsquarespace.controllers;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.noman.nhsquarespace.R;

public class MainActivity extends Activity {

    private EditText querySearch;
    private Button submit;
    private RecyclerView tweetList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        querySearch = (EditText) findViewById(R.id.queryET);
        submit = (Button) findViewById(R.id.submitQuery);
        tweetList = (RecyclerView) findViewById(R.id.tweetList);
        checkNetwork();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String theQuery = querySearch.getText().toString();
                if (theQuery.contains("Squarespace") || theQuery.contains("squarespace")) {
                    setUpView(theQuery);
                    querySearch.setVisibility(View.GONE);
                    submit.setVisibility(View.GONE);
                } else {
                    Toast submitToast = Toast.makeText(getApplicationContext(), "The query is not about Squarespace, please try again", Toast.LENGTH_SHORT);
                    submitToast.show();
                }
            }
        });

    }

    private void checkNetwork() {
        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        if (!isConnected) {
            Toast network = Toast.makeText(getApplicationContext(), "Please have a stable internet connection", Toast.LENGTH_SHORT);
            network.show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        tweetList.setVisibility(View.GONE);
        querySearch.setVisibility(View.VISIBLE);
        submit.setVisibility(View.VISIBLE);
    }

    private void setUpView(String query) {

        tweetList.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        tweetList.setLayoutManager(manager);

        TextView header = (TextView) findViewById(R.id.headerText);
        header.setText("You are currently viewing tweets about " + query);
        TwitterSearch ts = new TwitterSearch(tweetList, getApplicationContext());
        ts.execute(query);
    }
}
