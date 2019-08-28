package br.com.fiap.buslineapp.ui.splash

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import br.com.fiap.buslineapp.R
import br.com.fiap.buslineapp.ui.login.LoginActivity
import br.com.fiap.buslineapp.ui.main.MainActivity

class SplashActivity : AppCompatActivity() {

    private var splashTimer: CountDownTimer? = null
    private val TEMPO_AGUARDO_SPLASHSCREEN = 5L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val preferences = getSharedPreferences("user_preferences", Context.MODE_PRIVATE)
        val isFirstOpen = preferences.getBoolean("open_first", true)
        if (isFirstOpen) {
            markAppAlreadyOpen(preferences)
            showSplash()
        } else {
            showLogin()
        }
    }

    private fun showSplash() {
        super.onResume()
        splashTimer = object : CountDownTimer(TEMPO_AGUARDO_SPLASHSCREEN * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {}
            override fun onFinish() {
                val mainIntent = Intent(applicationContext, LoginActivity::class.java)
                startActivity(mainIntent)
                overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left)
                finish()
            }
        }.start()
    }

    override fun onStop() {
        super.onStop()
        cancelTimer()
    }

    private fun cancelTimer() {
        if (splashTimer != null)
            splashTimer!!.cancel()
    }

    private fun markAppAlreadyOpen(preferences: SharedPreferences) {
        val editor = preferences.edit()
        editor.putBoolean("open_first", false)
        editor.apply()
    }

    private fun showLogin() {
        val nextScreen = Intent(this@SplashActivity, LoginActivity::class.java)
        startActivity(nextScreen)
        finish()
    }
}
