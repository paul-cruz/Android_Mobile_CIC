package com.example.randomimage

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val images: List<Int> = listOf(R.drawable.image_1, R.drawable.image_2, R.drawable.image_3, R.drawable.image_4)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
    }

    fun onClickButton(view: View) {
        val img: ImageView = findViewById(R.id.imgCarousel)
        val index = (0..3).random()
        img.setImageResource(images[index])
        img.tag = "image_$index"
        Toast.makeText(this, "Image " + (index + 1), Toast.LENGTH_SHORT).show()
    }
}