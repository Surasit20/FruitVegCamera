package com.example.fruitvegcamera;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class Showdatafruit extends AppCompatActivity {

    ImageButton Watermelon,tomato,Strawberry,Rambutan,Papaya,Mango,Pumpkin,
            Potato,Pineapple,Orange,Grapes,Cucumber,WaterSpinach,Coconut,ChineseCabbage,
            Carrot,Cabbage,Banana,Broccoli,Apple;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showdatafruit);

        Watermelon = findViewById(R.id.Watermelon);
        tomato = findViewById(R.id.tomato);
        Strawberry = findViewById(R.id.Strawberry);
        Rambutan = findViewById(R.id.Rambutan);
        Papaya = findViewById(R.id.Papaya);
        Mango = findViewById(R.id.Mango);
        Pumpkin = findViewById(R.id.Pumpkin);
        Potato = findViewById(R.id.Potato);
        Pineapple = findViewById(R.id.Pineapple);
        Orange = findViewById(R.id.Orange);
        Grapes = findViewById(R.id.Grapes);
        Cucumber = findViewById(R.id.Cucumber);
        WaterSpinach = findViewById(R.id.WaterSpinach);
        Coconut = findViewById(R.id.Coconut);
        ChineseCabbage = findViewById(R.id.ChineseCabbage);
        Carrot = findViewById(R.id.Carrot);
        Cabbage = findViewById(R.id.Cabbage);
        Banana =  findViewById(R.id.Banana);
        Broccoli = findViewById(R.id.Broccoli);
        Apple = findViewById(R.id.Apple);

        Intent start = new Intent(Showdatafruit.this, Showpedict.class);

        Watermelon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                start.putExtra("id", String.valueOf(0));
                startActivity(start);
            }
        });
        tomato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start.putExtra("id", String.valueOf(1));
                startActivity(start);
            }
        });
        Strawberry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start.putExtra("id", String.valueOf(2));
                startActivity(start);
            }
        });
        Rambutan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start.putExtra("id", String.valueOf(3));
                startActivity(start);
            }
        });
        Papaya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start.putExtra("id", String.valueOf(4));
                startActivity(start);
            }
        });
        Mango.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start.putExtra("id", String.valueOf(5));
                startActivity(start);
            }
        });
        Pumpkin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start.putExtra("id", String.valueOf(6));
                startActivity(start);
            }
        });
        Potato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start.putExtra("id", String.valueOf(7));
                startActivity(start);
            }
        });
        Pineapple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start.putExtra("id", String.valueOf(8));
                startActivity(start);
            }
        });
       Orange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start.putExtra("id", String.valueOf(9));
                startActivity(start);
            }
        });
        Grapes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start.putExtra("id", String.valueOf(10));
                startActivity(start);
            }
        });
        Cucumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start.putExtra("id", String.valueOf(11));
                startActivity(start);
            }
        });
        WaterSpinach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start.putExtra("id", String.valueOf(12));
                startActivity(start);
            }
        });
        Coconut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start.putExtra("id", String.valueOf(13));
                startActivity(start);
            }
        });
        ChineseCabbage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start.putExtra("id", String.valueOf(14));
                startActivity(start);
            }
        });
        Carrot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start.putExtra("id", String.valueOf(15));
                startActivity(start);
            }
        });
        Cabbage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start.putExtra("id", String.valueOf(16));
                startActivity(start);
            }
        });
        Banana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start.putExtra("id", String.valueOf(17));
                startActivity(start);
            }
        });
       Broccoli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start.putExtra("id", String.valueOf(8));
                startActivity(start);
            }
        });
        Apple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start.putExtra("id", String.valueOf(19));
                startActivity(start);
            }
        });

    }
}