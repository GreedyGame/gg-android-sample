package com.greedygames.sample.sdk8

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.greedygames.sample.sdk8.showcasemenu.ShowcaseMenu
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setup()
    }

    private fun setup(){
        setupButton.setOnClickListener {
            val label = setupButton.text.toString().toUpperCase()
            if(label == "SETUP"){
                setupButton.text = "NEXT"
                return@setOnClickListener

            }
            else
                startActivity(Intent(this,ShowcaseMenu::class.java))
        }
    }

}
