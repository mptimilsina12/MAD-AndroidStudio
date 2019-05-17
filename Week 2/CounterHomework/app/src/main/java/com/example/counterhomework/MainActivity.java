package com.example.counterhomework;

import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView txtview;
    private static final String LOG_TAG =
            MainActivity.class.getSimpleName();
    int count=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(LOG_TAG, "-------");
        Log.d(LOG_TAG, "onCreate");

        txtview=findViewById(R.id.textView);
// Restore the state.
        if (savedInstanceState != null) {
            txtview.setText(savedInstanceState.getString("reply_text"));

        }


    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

             outState.putBoolean("reply_visible", true);
             outState.putString("reply_text",txtview.getText().toString());


    }

    public void btn_count(View view) {

        int count= Integer.parseInt(txtview.getText().toString());
        count++;
        txtview.setText(""+count);
    }
}
