package com.chimbori.box

import kotlin.js.ExperimentalJsExport
import kotlin.js.JsExport

@OptIn(ExperimentalJsExport::class)
@JsExport
class JsGreeting {
  fun greet(): String = Greeting().greet()
}
