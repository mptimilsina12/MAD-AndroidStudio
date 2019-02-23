package com.example.helloworld;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private int nCount;
    private TextView textCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textCount= (TextView) findViewById(R.id.textView_count);
    }

    public void showToast(View view) {
        Toast toast= Toast.makeText(this, "Hello Toast", Toast.LENGTH_LONG);
        toast.show();
    }

    public void CountUp(View view) {
        nCount++;
        textCount.setText(""+nCount);
    }
}
