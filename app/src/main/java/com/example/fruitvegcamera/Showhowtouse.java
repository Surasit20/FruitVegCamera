package com.example.fruitvegcamera;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class Showhowtouse extends AppCompatActivity {

    ImageButton Use1,Use2,Use3,Use4,Use5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showhowtouse);

        Use1 = findViewById(R.id.Use1);
        Use2 = findViewById(R.id.Use2);
        Use3 = findViewById(R.id.Use3);
        Use4 = findViewById(R.id.Use4);
        Use5 = findViewById(R.id.Use5);
        Intent start = new Intent(Showhowtouse.this, Howtouse.class);

        Use1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                start.putExtra("id", String.valueOf(0));
                startActivity(start);
            }
        });
        Use2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                start.putExtra("id", String.valueOf(1));
                startActivity(start);
            }
        });
        Use3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                start.putExtra("id", String.valueOf(2));
                startActivity(start);
            }
        });
        Use4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                start.putExtra("id", String.valueOf(3));
                startActivity(start);
            }
        });
        Use5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                start.putExtra("id", String.valueOf(4));
                startActivity(start);
            }
        });
    }
}