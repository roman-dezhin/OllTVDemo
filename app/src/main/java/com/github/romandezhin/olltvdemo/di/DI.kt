package com.github.romandezhin.olltvdemo.di

import android.app.Application

object DI {

    fun initialize(app: Application) {
        NetworkModule.initialize(app)
        MainModule.initialize(app)
    }
}

