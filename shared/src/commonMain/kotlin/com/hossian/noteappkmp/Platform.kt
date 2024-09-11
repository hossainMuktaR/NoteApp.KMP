package com.hossian.noteappkmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform