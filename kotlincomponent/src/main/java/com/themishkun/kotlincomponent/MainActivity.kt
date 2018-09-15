package com.themishkun.kotlincomponent

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import di.DaggerMainComponent

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val component = DaggerMainComponent.create()
    }
}
