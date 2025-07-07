package com.health.diafit

import android.app.Application
import android.content.Context
import com.health.diafit.data.local.UserPreference
import com.health.diafit.util.LocaleHelper
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.runBlocking
import java.util.Locale
import javax.inject.Inject

@HiltAndroidApp
class DiaFit : Application() {

    @Inject
    lateinit var userPreference: UserPreference

    override fun attachBaseContext(base: Context) {
        val updatedBase = LocaleHelper.updateLocale(base, Locale.getDefault().language)
        super.attachBaseContext(updatedBase)
    }

    override fun onCreate() {
        super.onCreate()

        runBlocking {
            val storedLanguage = userPreference.getLanguageSync()
            if (storedLanguage != "id" && storedLanguage != Locale.getDefault().language) {
                LocaleHelper.updateLocale(this@DiaFit, storedLanguage)
            }
        }
    }
}