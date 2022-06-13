package com.amangarg.awesometextview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import com.amangarg.library.AwesomeTextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tv = findViewById<AwesomeTextView>(R.id.atv_main)
        val etFa = findViewById<AppCompatEditText>(R.id.et_fa)
        val etText = findViewById<AppCompatEditText>(R.id.et_text)
        val btnMain = findViewById<AppCompatButton>(R.id.btn_main)

        btnMain.setOnClickListener {
            tv.setFontAwesomeText(
                etFa.text.toString(),
                etText.text.toString()
            )
            tv.setAlternateTextFont(R.font.comin_soon_regular)
            tv.setAlternateTextColour(R.color.purple_200)
        }
    }
}