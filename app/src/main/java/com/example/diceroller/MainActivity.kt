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

        rollButton.setOnClickListener {
            val result = rollDice()
            val toastText = "${result}${"!".repeat(result)}"
            Toast.makeText(this, toastText, Toast.LENGTH_SHORT).show()
        }
        rollDice()
    }
    private fun rollDice() : Int {
        val result = Dice(6).roll()

        val diceImage = findViewById<ImageView>(R.id.diceImage)
        val correctDrawable = when (result) {
            1 -> (R.drawable.dice_1)
            2 -> (R.drawable.dice_2)
            3 -> (R.drawable.dice_3)
            4 -> (R.drawable.dice_4)
            5 -> (R.drawable.dice_5)
            else -> (R.drawable.dice_6)
        }
        diceImage.setImageResource(correctDrawable)
        diceImage.contentDescription = result.toString()
        return result
    }
}

class Dice(private val numSides: Int) {
    fun roll(): Int {
        return (1..numSides).random()
    }
}