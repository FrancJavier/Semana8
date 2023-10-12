package com.example.semana8;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private ImageView mImageView;
    private Button descargar;
    private Button rotateButton;
    private float rotationAngle = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageView = findViewById(R.id.imagen);
        descargar = findViewById(R.id.imagenDescargar);
        rotateButton = findViewById(R.id.Girar);

        descargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    public void run() {
                        final Bitmap bitmap = loadImageFromNetwork("https://img.freepik.com/vector-premium/ilustracion-pelota-futbol-vector-recurso-grafico-fondo-simple-deporte-futbol-dibujo_762952-55.jpg?w=740");
                        mImageView.post(new Runnable() {
                            public void run() {
                                mImageView.setImageBitmap(bitmap);
                            }
                        });
                    }
                }).start();
            }
        });

        rotateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Incrementa el ángulo de rotación en 90 grados al hacer clic en el botón.
                rotationAngle += 90;

                // Gira la imagen utilizando el ángulo de rotación actual.
                mImageView.setRotation(rotationAngle);
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
