package com.example.fruitvegcamera;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

public class Howtouse extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_howtouse);
        String name;
        String howtouse;
        String imageFruit;
        TextView textViewTitle, textHowtouse;
        ImageView imageViewPredict;
        ImageButton Back, ButtonSoundEng, ButtonSoundTh;
        ////////////////////////////////////////////////////////

        Back = (ImageButton) findViewById(R.id.imageButtonBack);
        textHowtouse = findViewById(R.id.textHowtouse);
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
            InputStream XmlFileInputStream = getResources().openRawResource(R.raw.datause);
            int size = XmlFileInputStream.available();
            byte[] buffer = new byte[size];
            XmlFileInputStream.read(buffer);
            XmlFileInputStream.close();

            json = new String(buffer, "UTF-8");
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("Howtouse");


            JSONObject obj = jsonArray.getJSONObject(indexFruit);
            Log.d("name", String.valueOf(indexFruit));
            name = obj.getString("name");
            Log.d("name",name);
            howtouse = obj.getString("howtouse");
            imageFruit = obj.getString("image");
            Log.d("name",name);
            //แสดงผลลัพธ์
            textViewTitle.setText(name);
            textHowtouse.setText(howtouse);
            //แสดงผลลัพธ์รูปภาพ
            String uri = "@drawable/" +  imageFruit.toLowerCase(Locale.ROOT);  // where myresource (without the extension) is the file


            int imageResource = getResources().getIdentifier(uri, null, getPackageName());
            Drawable res = getResources().getDrawable(imageResource);
            imageViewPredict.setImageDrawable(res);


        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Back = new Intent(Howtouse.this, MainActivity.class);
                startActivity(Back);
            }
        });

    }


}




