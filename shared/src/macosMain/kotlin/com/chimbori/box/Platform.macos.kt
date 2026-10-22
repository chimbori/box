package com.chimbori.box

import platform.Foundation.NSProcessInfo

class MacosPlatform : Platform {
  override val name: String =
    "macOS " + NSProcessInfo.processInfo.operatingSystemVersionString
}

actual fun getPlatform(): Platform = MacosPlatform()
