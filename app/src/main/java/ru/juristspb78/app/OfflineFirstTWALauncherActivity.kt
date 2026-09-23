package ru.juristspb78.app

import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.google.androidbrowserhelper.trusted.LauncherActivity

class OfflineFirstTWALauncherActivity : LauncherActivity() {

    private val prefsName = "twa_prefs"
    private val keyTwaLaunched = "twa_launched"

    override fun shouldLaunchImmediately(): Boolean {
        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tryLaunchTwa()
    }

    private fun tryLaunchTwa() {
        // Проверяем, запускался ли TWA ранее (сохраняем сами)
        val prefs = getSharedPreferences(prefsName, Context.MODE_PRIVATE)
        val hasLaunched = prefs.getBoolean(keyTwaLaunched, false)

        if (hasLaunched) {
            launchTwa()
        } else if (isOnline()) {
            // Сохраняем флаг, что TWA запускается первый раз с сетью
            prefs.edit().putBoolean(keyTwaLaunched, true).apply()
            launchTwa()
        } else {
            renderOfflineFallback()
        }
    }

    private fun isOnline(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = cm.activeNetwork ?: return false
        val capabilities = cm.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    private fun renderOfflineFallback() {
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(64, 64, 64, 64)
            setBackgroundColor(Color.parseColor("#f7f9f8"))
        }

        val title = TextView(this).apply {
            text = "Нет подключения к интернету"
            textSize = 20f
            setTextColor(Color.parseColor("#1a2228"))
            gravity = Gravity.CENTER
        }

        val subtitle = TextView(this).apply {
            text = "Для первого запуска необходимо подключение к сети.\nПосле этого приложение будет работать офлайн."
            textSize = 14f
            setTextColor(Color.parseColor("#3a4850"))
            gravity = Gravity.CENTER
            setPadding(0, 32, 0, 32)
        }

        val retryButton = Button(this).apply {
            text = "Повторить"
            setOnClickListener { tryLaunchTwa() }
        }

        layout.addView(title)
        layout.addView(subtitle)
        layout.addView(retryButton)

        setContentView(layout)
    }
}