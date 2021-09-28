package com.github.romandezhin.olltvdemo

import android.app.Application
import com.github.romandezhin.olltvdemo.di.DI

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        DI.initialize(this)
    }
}