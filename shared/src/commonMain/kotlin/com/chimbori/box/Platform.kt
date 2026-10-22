package com.chimbori.box

interface Platform {
  val name: String
}

expect fun getPlatform(): Platform
