package com.example.fuelcalcv2

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val calcButton = findViewById<Button>(R.id.Calc_button)

        calcButton.setOnClickListener {

            val textViewStint = findViewById<TextView>(R.id.textViewSTINT)
            val textViewAvg = findViewById<TextView>(R.id.textViewAVG)
            val textViewTotal = findViewById<TextView>(R.id.textViewTotal)
            val textViewLapCount = findViewById<TextView>(R.id.textViewLapCount)

            val raceHours = findViewById<EditText>(R.id.Hours).text.toString().toIntOrNull() ?: 0
            val raceMinutes = findViewById<EditText>(R.id.Minutes).text.toString().toIntOrNull() ?: 0

            val lapMinutes = findViewById<EditText>(R.id.Minutes_laptime).text.toString().toIntOrNull() ?: 0
            val lapSeconds = findViewById<EditText>(R.id.Second_laptime).text.toString().toIntOrNull() ?: 0
            val lapMilliseconds = findViewById<EditText>(R.id.Miliseconds_laptime).text.toString().toIntOrNull() ?: 0

            val fuelUsage = findViewById<EditText>(R.id.Fuel_usage).text.toString().toFloatOrNull() ?: 0f
            val maxFuel = findViewById<EditText>(R.id.Max_fuel).text.toString().toFloatOrNull() ?: 0f

            val maxLapsPerTank = maxFuel / fuelUsage
            val raceTime = (raceHours * 60 * 60 * 1000) + (raceMinutes * 60 * 1000)
            val totalLapTime = (lapMinutes * 60 * 1000) + (lapSeconds * 1000) + lapMilliseconds

            val lapsInRace = raceTime.toFloat() / totalLapTime.toFloat()

            val totalLapsNeeded = lapsInRace + 1f



            //stinty
            val stints = kotlin.math.floor(totalLapsNeeded / maxLapsPerTank).toInt()

            //średnia ile okrążeń trzeba oszczędzić na jeden stint
            val averageLapSave = kotlin.math.abs((totalLapsNeeded - (stints * maxLapsPerTank)) / stints)

            //ile okrążeń trzeba osczędzić w całości
            val totalLapsSave = kotlin.math.abs((averageLapSave * stints))




            //czy trzeba oszczędzać i ile
            if (totalLapsNeeded <= maxLapsPerTank) {
                textViewLapCount.text = "%.2f LAPS IN RACE".format(lapsInRace)
                textViewStint.text = "Stint count: $stints"
                textViewAvg.text = "No fuel saving needed"
                textViewTotal.text = ""

            } else {



                textViewLapCount.text = "%.2f LAPS IN RACE".format(lapsInRace)
                textViewStint.text = "Stint count: $stints"
                textViewAvg.text = "Target (per stint) \n %.2f laps saved".format(averageLapSave)
                textViewTotal.text = "Target (total) \n %.2f laps saved".format( totalLapsSave)
            }




        }
    }
}
