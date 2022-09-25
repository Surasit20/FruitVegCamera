package com.example.fruitvegcamera;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.fruitvegcamera.ml.Model;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class MainActivity extends AppCompatActivity {

    // Button selectBtn, predictBtn,cameraBtn;
    ImageButton selectImageBtn, selectCameraBtn, selectPredictBtn,ShowdatafruitBtn,howtouseBtn;
    Bitmap bitmap;
    ImageView imageView;
    int imageSize = 128;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*selectBtn = findViewById(R.id.selectBtn);
        predictBtn = findViewById(R.id.predictBtn);
        cameraBtn = findViewById(R.id.cameraBtn);*/
        selectImageBtn = findViewById(R.id.selectImageBtn);
        selectCameraBtn = findViewById(R.id.selectCameraBtn);
        selectPredictBtn = findViewById(R.id.selectPredictBtn);
        ShowdatafruitBtn = findViewById(R.id.ShowdatafruitBtn);
        howtouseBtn = findViewById(R.id.howtouseBtn);
        imageView = findViewById(R.id.imageView);


        //กดเพื่อดูข้อมุล ผลไม้
        howtouseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent howtouseBtn = new Intent(MainActivity.this, Imageslideuse.class);
                startActivity(howtouseBtn);
            }
        });
        //กดเพื่อดูข้อมุล ผลไม้
        ShowdatafruitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ShowdatafruitBtn = new Intent(MainActivity.this, Showdatafruit.class);
                startActivity(ShowdatafruitBtn);
            }
        });

        // เลือกรูปจากเกลรอรี่
        selectImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 10);
            }
        });
        //ถ่ายรูป
        selectCameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ขอสิทธิ์กล้องถ่ายรูป
                if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, 12);
                } else {
                    //Request camera permission if we don't have it.
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, 11);
                }
            }
        });

        selectPredictBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ถ้าไม่มีรูปให้แสดง alert
                if (bitmap == null) {
                    AlertDialog alert = new AlertDialog.Builder(MainActivity.this).create();
                    alert.setTitle("ผู้ใช้กรุณาถ่ายภาพ หรือ เลือกรูปภาพ!!");
                    alert.setMessage("ถ่ายรูปผลไม้ หรือ เลือกรูปภาพผลไม้.");
                    alert.setButton(alert.BUTTON_NEUTRAL, "ตกลง", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            alert.dismiss();
                        }
                    });
                    alert.show();
                    Log.i("alert", "alert out");

                    return;
                }
                Intent start = new Intent(MainActivity.this, Showpedict.class);
                int dimension = Math.min(bitmap.getWidth(), bitmap.getHeight());
                bitmap = ThumbnailUtils.extractThumbnail(bitmap, dimension, dimension);
                imageView.setImageBitmap(bitmap);
                bitmap = Bitmap.createScaledBitmap(bitmap, imageSize, imageSize, false);

                try {
                    Model model = Model.newInstance(getApplicationContext());

                    // Creates inputs for reference.
                    TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 128, 128, 3}, DataType.FLOAT32);
                    ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3);
                    byteBuffer.order(ByteOrder.nativeOrder());

                    // get 1D array of 128 * 128 pixels in image
                    int[] intValues = new int[imageSize * imageSize];
                    bitmap.getPixels(intValues, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());

                    // iterate over pixels and extract R, G, and B values. Add to bytebuffer.
                    int pixel = 0;
                    for (int i = 0; i < imageSize; i++) {
                        for (int j = 0; j < imageSize; j++) {
                            int val = intValues[pixel++]; // RGB
                            byteBuffer.putFloat(((val >> 16) & 0xFF) * (1.f / 255.f));
                            byteBuffer.putFloat(((val >> 8) & 0xFF) * (1.f / 255.f));
                            byteBuffer.putFloat((val & 0xFF) * (1.f / 255.f));
                        }
                    }

                    inputFeature0.loadBuffer(byteBuffer);

                    // Runs model inference and gets result.
                    Model.Outputs outputs = model.process(inputFeature0);
                    TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

                    float[] confidences = outputFeature0.getFloatArray();
                    // find the index of the class with the biggest confidence.
                    int maxPos = 0;
                    float maxConfidence = 0;
                    for (int i = 0; i < confidences.length; i++) {
                        if (confidences[i] > maxConfidence) {
                            maxConfidence = confidences[i];
                            maxPos = i;
                        }
                    }

                    //ถ้าค่าความแม่นยำน้อยกว่า 50% จะทำนายผิด
                    Log.i("predict", String.valueOf(confidences[maxPos]));
                    if (confidences[maxPos] < 0.5f) {
                        AlertDialog alert = new AlertDialog.Builder(MainActivity.this).create();
                        alert.setTitle("การทำนายผลไม้ผิดพลาด!!");
                        alert.setMessage("ไม่สามารถทำนายได้!!");
                        alert.setButton(alert.BUTTON_NEUTRAL, "ตกลง", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                alert.dismiss();
                            }
                        });
                        alert.show();
                        Log.i("alert", "alert out");

                        return;
                    }

                    Log.i("res", String.valueOf(maxPos));

                    // Releases model resources if no longer used.
                    start.putExtra("id", String.valueOf(maxPos));
                    startActivity(start);
                    model.close();

                } catch (IOException e) {
                    // TODO Handle the exception
                }
            }
        });


    }

    @Override
    public void onBackPressed() {
        Intent start = new Intent(MainActivity.this, Introapp.class);
        startActivity(start);
        super.onBackPressed();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    void getPermission() {

        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, 11);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 11) {
            if (grantResults.length > 0) {
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    this.getPermission();
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 10) {
            if (data != null) {
                Uri uri = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                    int dimension = Math.min(bitmap.getWidth(), bitmap.getHeight());
                    bitmap = ThumbnailUtils.extractThumbnail(bitmap, dimension, dimension);
                    imageView.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else if (requestCode == 12) {
            bitmap = (Bitmap) data.getExtras().get("data");
            int dimension = Math.min(bitmap.getWidth(), bitmap.getHeight());
            bitmap = ThumbnailUtils.extractThumbnail(bitmap, dimension, dimension);
            imageView.setImageBitmap(bitmap);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


}