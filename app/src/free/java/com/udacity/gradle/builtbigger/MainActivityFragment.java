package com.udacity.gradle.builtbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.udacity.gradle.builditbigger.ApiResultListener;
import com.udacity.gradle.builditbigger.EndpointsAsyncTask;
import com.udacity.gradle.builditbigger.R;

import gradle.udacity.com.jokesactivity.LibraryActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainActivityFragment extends Fragment {
    //@see 'https://developers.google.com/admob/android/interstitial'
    private InterstitialAd mInterstitialAd;

    // OPTIONAL TASK: Add Loading Indicator
    private ProgressBar spinner;

    public MainActivityFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_main, container, false);
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
        mInterstitialAd = new InterstitialAd(root.getContext());
        mInterstitialAd.setAdUnitId(getString(R.string.banner_ad_unit_id));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener(){
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                //on ad closed make progress bar visible to user
                spinner.setVisibility(View.VISIBLE);
                //instantiate Google Cloud Endpoints AsyncTask object to retrieve jokes from java library
                EndpointsAsyncTask jokeTask = new EndpointsAsyncTask();
                //set interface object
                jokeTask.setResultListener(apiResultListener);
                //execute AsyncTask
                jokeTask.execute();
            }
        });

        Button mButton = root.findViewById(R.id.tell_joke_button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //To show an interstitial ad first checking if its loaded
                if (mInterstitialAd.isLoaded()){
                    mInterstitialAd.show();
                } else {
                    Log.d(MainActivityFragment.class.getSimpleName(), "The interstitial wasn't loaded yet.");
                }
            }
        });
        return root;
    }
}
