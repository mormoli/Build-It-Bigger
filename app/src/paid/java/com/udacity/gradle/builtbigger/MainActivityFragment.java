package com.udacity.gradle.builtbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.udacity.gradle.builditbigger.ApiResultListener;
import com.udacity.gradle.builditbigger.EndpointsAsyncTask;
import com.udacity.gradle.builditbigger.R;

import gradle.udacity.com.jokesactivity.LibraryActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainActivityFragment extends Fragment {
    // OPTIONAL TASK: Add Loading Indicator
    private ProgressBar spinner;
    public MainActivityFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        spinner = root.findViewById(R.id.progress_bar_tv);
        spinner.setVisibility(View.GONE);
        //using an interface to retrieve result string from AsyncTask's onPostExecute method.
        final ApiResultListener apiResultListener = new ApiResultListener() {
            @Override
            public void onJokeRetrieved(String result) {
                spinner.setVisibility(View.GONE);
                //show joke on android library activity with an intent extra
                Intent intent = new Intent(getContext(), LibraryActivity.class);
                intent.putExtra("joke", result);
                startActivity(intent);
            }
        };
        Button mButton = root.findViewById(R.id.tell_joke_button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.setVisibility(View.VISIBLE);
                //instantiate Google Cloud Endpoints AsyncTask object to retrieve jokes from java library
                EndpointsAsyncTask jokeTask = new EndpointsAsyncTask();
                //set interface object
                jokeTask.setResultListener(apiResultListener);
                //execute AsyncTask
                jokeTask.execute();
            }
        });
        return root;
    }
}
