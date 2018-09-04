package com.udacity.gradle.builditbigger;

import android.os.AsyncTask;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;

public class EndpointsAsyncTask extends AsyncTask<String, Void, String> {
    private static MyApi myApiService = null;

    ApiResultListener apiResultListener;
    public void setResultListener(ApiResultListener apiResultListener){
        this.apiResultListener = apiResultListener;
    }
    @Override
    protected String doInBackground(String... strings) {
        if(myApiService == null){// Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    //AbstractGoogleClient: Application name is not set. Call Builder#setApplicationName.
                    .setApplicationName("Build it Bigger")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> request) {
                            request.setDisableGZipContent(true);
                        }
                    });
            myApiService = builder.build();
        }
        try{
            return myApiService.jokeTeller().execute().getJoke();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    //Before running AsyncTaskStringTest class do not forget to remove/comment below method.
    @Override
    protected void onPostExecute(String result) {
        // get joke from java library with an interface
        apiResultListener.onJokeRetrieved(result);
    }
}
