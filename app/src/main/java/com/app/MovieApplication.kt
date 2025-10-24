package com.app

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MovieApplication: Application() {
	companion object {
		// App-wide context to be used by non-Android classes that need resources
		lateinit var appContext: Context
			private set
	}

	override fun onCreate() {
		super.onCreate()
		appContext = applicationContext
	}
}