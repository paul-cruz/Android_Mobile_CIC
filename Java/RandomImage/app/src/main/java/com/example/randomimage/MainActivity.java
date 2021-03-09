package com.example.randomimage;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    public static int[] images = {R.drawable.image_1, R.drawable.image_2, R.drawable.image_3, R.drawable.image_4};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).hide();
    }

    public void onClickButton(View view) {
        ImageView imageView = this.findViewById(R.id.imgCarousel);
        int index = (int) (Math.random() * 4);
        imageView.setImageDrawable(ContextCompat.getDrawable(this, images[index]));
        imageView.setTag("image_" + index);
        Toast.makeText(this, String.format("Image %d", index + 1), Toast.LENGTH_SHORT).show();
    }
}