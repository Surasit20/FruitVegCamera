package com.example.fruitvegcamera;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
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


    TextToSpeech textToSpeechEng;
    TextToSpeech textToSpeechThai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showpedict);
        String name;
        String des;
        String nameThai;
        String imageFruit;
        TextView textViewTitle, textReading, textNamefruitthai;
        ImageView imageViewPredict;
        ImageButton Back, ButtonSoundEng, ButtonSoundTh;
        ////////////////////////////////////////////////////////
        textReading = findViewById(R.id.textReading);
        textNamefruitthai = findViewById(R.id.textNamefruitthai);
        Back = (ImageButton) findViewById(R.id.imageButtonBack);
        textViewTitle = findViewById(R.id.textTitle);
        imageViewPredict = findViewById(R.id.imageViewPredict);
        ButtonSoundEng = findViewById(R.id.imageButtonSoundEng);
        ButtonSoundTh = findViewById(R.id.imageButtonSoundThai);
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

            json = new String(buffer, "UTF-8");
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("fruit");


            JSONObject obj = jsonArray.getJSONObject(indexFruit);
            Log.d("name", String.valueOf(indexFruit));
            name = obj.getString("name");
            Log.d("name",name);
            des = obj.getString("des");
            nameThai = obj.getString("nameThai");
            imageFruit = obj.getString("image");
            Log.d("name",name);
            //แสดงผลลัพธ์
            textViewTitle.setText(name);
            textReading.setText(des);
            textNamefruitthai.setText(nameThai);
            //แสดงผลลัพธ์
            String uri = "@drawable/" +  imageFruit.toLowerCase(Locale.ROOT);  // where myresource (without the extension) is the file


            int imageResource = getResources().getIdentifier(uri, null, getPackageName());
            Drawable res = getResources().getDrawable(imageResource);
            imageViewPredict.setImageDrawable(res);


            //แปลงข้อความให้เป็นเสียง

            textToSpeechEng = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int i) {

                    if (i == TextToSpeech.SUCCESS) {
                        int lang = textToSpeechEng.setLanguage(Locale.ENGLISH);
                        if (lang == TextToSpeech.LANG_MISSING_DATA || lang == TextToSpeech.LANG_NOT_SUPPORTED) {
                            Toast.makeText(Showpedict.this, "Language is not supported", Toast.LENGTH_SHORT).show();
                        } else {
                            //textToSpeech.speak(name.toString(),TextToSpeech.QUEUE_ADD,null);
                            //Toast.makeText(Showpedict.this,"Language Supported",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

            textToSpeechThai = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onInit(int i) {

                    if (i == TextToSpeech.SUCCESS) {
                        int lang = textToSpeechThai.setLanguage(Locale.forLanguageTag("th"));
                        if (lang == TextToSpeech.LANG_MISSING_DATA || lang == TextToSpeech.LANG_NOT_SUPPORTED) {
                            Toast.makeText(Showpedict.this, "Language is not supported", Toast.LENGTH_SHORT).show();
                        } else {
                            //textToSpeech.speak(name.toString(),TextToSpeech.QUEUE_ADD,null);
                            //Toast.makeText(Showpedict.this,"Language Supported",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Back = new Intent(Showpedict.this, MainActivity.class);
                startActivity(Back);
            }
        });

        ButtonSoundTh.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {


                String name = new String(textNamefruitthai.getText().toString());
                if(name.equals("สตรอว์เบอร์รี")){
                    name = "สะตอเบอร์รี่";
                }

                textToSpeechThai.speak(name, TextToSpeech.QUEUE_FLUSH, null);

            }
        });


        ButtonSoundEng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = new String(textViewTitle.getText().toString());
                textToSpeechEng.speak(name, TextToSpeech.QUEUE_FLUSH, null);
            }
        });
    }


}




