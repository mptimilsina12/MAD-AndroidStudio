package com.example.usernavigationhomework;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imgDonut = findViewById(R.id.donut);
        ImageView imgIceCream = findViewById(R.id.ice_cream);
        ImageView imgFroyo = findViewById(R.id.froyo);
        imgDonut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, donut.class);
                startActivity(intent);
            }
        });

        imgIceCream.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, icecream.class);
                startActivity(intent);
            }
        });

        imgFroyo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, froyo.class);
                startActivity(intent);
            }
        });
    }
}
