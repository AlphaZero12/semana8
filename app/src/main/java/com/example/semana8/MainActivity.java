package com.example.semana8;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    Button btnDescargar;
    ImageView imagen;
    Button btnGirar;
    float currentRotation = 0; // Ángulo actual de rotación

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnDescargar = findViewById(R.id.btnDescargar);
        imagen = findViewById(R.id.imagen);
        btnGirar = findViewById(R.id.btnGirar);

        btnDescargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final Bitmap bitmap = loadImageFromNetwork("https://www.wallpapersun.com/wp-content/uploads/2021/04/gojo-satoru-wallpaper-10-715x1011.jpg");
                        if (bitmap != null) {
                            imagen.post(new Runnable() {
                                @Override
                                public void run() {
                                    imagen.setImageBitmap(bitmap);
                                }
                            });
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "Error al descargar la imagen", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }).start();
            }
        });

        btnGirar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Girar la imagen en 90 grados en sentido horario
                currentRotation += 90;
                imagen.setRotation(currentRotation);
            }
        });
    }

    private Bitmap loadImageFromNetwork(String imageURL) {
        try {
            URL url = new URL(imageURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            return BitmapFactory.decodeStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
