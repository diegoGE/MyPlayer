package com.example.myplayer

import android.view.ViewGroup
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

fun test(){

    GlobalScope.launch(Dispatchers.Main) {
        val result = withContext(Dispatchers.IO){ heavyTask()}
        print(result)
    }
}

fun heavyTask(): String = "Hello"

fun testNullable(){
    val x: Int? = null
    val l: Long = x?.toLong() ?: 0
}