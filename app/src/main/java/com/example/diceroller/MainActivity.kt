package com.example.diceroller

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rollResult = findViewById<TextView>(R.id.rollResult)
        val rollButton = findViewById<Button>(R.id.rollButton)
        val diceFaceInput = findViewById<TextView>(R.id.inputDiceFaces)

        rollButton.setOnClickListener {
            Toast.makeText(this, "Dice rolled!", Toast.LENGTH_SHORT).show()
            var str = diceFaceInput.text.toString();
            if(str == "") str = "0"
            var faces = Integer.parseInt(str)
            if(faces < 2) {
                faces = 2
                diceFaceInput.text = faces.toString()
            } else if (faces > 100) {
                faces = 100
                diceFaceInput.text = faces.toString()
            }
            val die = Dice(faces)
            rollResult.text = die.roll().toString()
        }
    }
}

class Dice(private val numSides: Int) {
    fun roll(): Int {
        return (1..numSides).random()
    }
}