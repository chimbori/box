package com.chimbori.box

import android.app.Activity
import android.os.Bundle
import android.widget.TextView

class MainActivity : Activity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val textView = TextView(this).apply {
      text = Greeting().greet()
      textSize = 24f
      setPadding(64, 64, 64, 64)
    }
    setContentView(textView)
  }
}
