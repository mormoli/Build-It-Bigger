package gradle.udacity.com.jokesactivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class LibraryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);

        if(getIntent().hasExtra("joke")){
            Intent intent = getIntent();
            String joke = intent.getStringExtra("joke");
            // Capture the layout's TextView and set the string as its text
            TextView textView = findViewById(R.id.jokes_view_tv);
            textView.setText(joke);
        }
    }
}
