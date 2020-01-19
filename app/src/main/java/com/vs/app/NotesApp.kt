package com.vs.app

import androidx.multidex.MultiDexApplication

/**
 * Created by Sachin
 */
class NotesApp : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        private val TAG = NotesApp::class.java.name
        @get:Synchronized
        var instance: NotesApp? = null
    }
}
