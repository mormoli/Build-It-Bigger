package com.udacity.gradle.builditbigger;

import android.support.test.runner.AndroidJUnit4;
import android.text.TextUtils;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertFalse;

@RunWith(AndroidJUnit4.class)
public class AsyncTaskStringTest {
    @Test
    public void nonEmptyStringCheck() throws ExecutionException, InterruptedException {
        //in order to run this test AsyncTask's onPostExecute method must be commented
        //because implementing an interface to get result
        // STEP 4 Add code to test that your Async task successfully retrieves a non-empty string
        String result = new EndpointsAsyncTask().execute().get();
        assertFalse(TextUtils.isEmpty(result));
    }
}
