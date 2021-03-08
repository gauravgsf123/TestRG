package com.gaurav.testrg.app

import android.app.Application
import com.gaurav.testrg.database.DatabaseHelper


class MyApp: Application() {
    private lateinit var app: MyApp

    override fun onCreate() {
        super.onCreate()
        app = this
        DatabaseHelper.initDatabaseInstance(this)
    }
}