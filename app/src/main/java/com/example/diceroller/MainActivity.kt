package com.example.diceroller

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rollButton = findViewById<Button>(R.id.rollButton)
        val diceImage = findViewById<ImageView>(R.id.diceImage)

        rollButton.setOnClickListener {

            val result = Dice(6).roll()
            Toast.makeText(this, "$result !", Toast.LENGTH_SHORT).show()
            when (result) {
                1 -> diceImage.setImageResource(R.drawable.dice_1)
                2 -> diceImage.setImageResource(R.drawable.dice_2)
                3 -> diceImage.setImageResource(R.drawable.dice_3)
                4 -> diceImage.setImageResource(R.drawable.dice_4)
                5 -> diceImage.setImageResource(R.drawable.dice_5)
                6 -> diceImage.setImageResource(R.drawable.dice_6)
            }
        }
    }
}

class Dice(private val numSides: Int) {
    fun roll(): Int {
        return (1..numSides).random()
    }
}