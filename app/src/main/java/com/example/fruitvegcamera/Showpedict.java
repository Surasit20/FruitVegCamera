package com.example.fruitvegcamera;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

public class Showpedict extends AppCompatActivity {

    ImageButton Back;
    TextToSpeech textToSpeech;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showpedict);

        TextView textViewTitle,textReading,textNamefruitthai;
        ImageView imageViewPredict;
        textReading = findViewById(R.id.textReading);
        textNamefruitthai = findViewById(R.id.textNamefruitthai);

        textViewTitle = findViewById(R.id.textTitle);
        imageViewPredict = findViewById(R.id.imageViewPredict);
        //get id predict
        Intent receiverIntent = getIntent();
        String receiverValue = receiverIntent.getStringExtra("id");

        int indexFruit = Integer.parseInt(receiverValue);
        Log.d("id", String.valueOf(indexFruit));


        //แปลง json เปลี่ยน array เพื่อแสดง
        String json;
        try {
            InputStream XmlFileInputStream = getResources().openRawResource(R.raw.data);
            int size = XmlFileInputStream.available();
            byte[] buffer = new byte[size];
            XmlFileInputStream.read(buffer);
            XmlFileInputStream.close();

            json = new String(buffer,"UTF-8");
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("fruit");


            JSONObject obj = jsonArray.getJSONObject(indexFruit);

            String name= obj.getString("name");
            String des = obj.getString("des");
            String nameThai = obj.getString("nameThai");
            //แสดงผลลัพธ์
            textViewTitle.setText(name);
            textReading.setText(des);
            textNamefruitthai.setText(nameThai);
            //แสดงผลลัพธ์
            String uri = "@drawable/" + name.toLowerCase(Locale.ROOT);  // where myresource (without the extension) is the file
            Log.d("name",name.toLowerCase(Locale.ROOT));
            int imageResource = getResources().getIdentifier(uri, null, getPackageName());
            Drawable res = getResources().getDrawable(imageResource);
            imageViewPredict.setImageDrawable(res);


            //แปลงข้อความให้เป็นเสียง

            textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int i) {

                    if(i == TextToSpeech.SUCCESS){
                        int lang = textToSpeech.setLanguage(Locale.ENGLISH);
                        if(lang == TextToSpeech.LANG_MISSING_DATA || lang == TextToSpeech.LANG_NOT_SUPPORTED){
                            Toast.makeText(Showpedict.this,"Language is not supported",Toast.LENGTH_SHORT).show();
                        }else{
                            textToSpeech.speak(name.toString(),TextToSpeech.QUEUE_ADD,null);
                            //Toast.makeText(Showpedict.this,"Language Supported",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });


            textToSpeech.speak(name,TextToSpeech.QUEUE_FLUSH,null);


        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        Back = (ImageButton)findViewById(R.id.imageButtonBack);
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Back = new Intent(Showpedict.this,MainActivity.class);
                startActivity(Back);
            }
        });
    }
}




