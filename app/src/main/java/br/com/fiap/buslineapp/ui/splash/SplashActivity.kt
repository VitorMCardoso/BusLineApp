package br.com.fiap.buslineapp.ui.splash

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import br.com.fiap.buslineapp.R
import br.com.fiap.buslineapp.ui.login.LoginActivity
import br.com.fiap.buslineapp.ui.main.MainActivity
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import webservice.controller.RetrofitInitializer

class SplashActivity : AppCompatActivity() {

    private var splashTimer: CountDownTimer? = null
    private val TEMPO_AGUARDO_SPLASHSCREEN = 5L
    private val TEMPO_AGUARDO_API = 40L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        var callHealth = RetrofitInitializer().busLineService().health()
        callHealth.enqueue(object : Callback<JsonElement> {
            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                Log.e("onFailure error", t?.message)
                val sameIntent = Intent(applicationContext, SplashActivity::class.java)
                showSplash(TEMPO_AGUARDO_API, sameIntent, false)
            }

            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                response.body()?.let {
                    if ((it as JsonObject).get("status").asString == "UP") {
                        val preferences =
                            getSharedPreferences("user_preferences", Context.MODE_PRIVATE)
                        val isFirstOpen = preferences.getBoolean("open_first", true)
                        if (isFirstOpen) {
                            markAppAlreadyOpen(preferences)
                            val mainIntent = Intent(applicationContext, LoginActivity::class.java)
                            showSplash(TEMPO_AGUARDO_SPLASHSCREEN, mainIntent, true)
                        } else {
                            showLogin()
                        }
                    } else {
                        val sameIntent = Intent(applicationContext, SplashActivity::class.java)
                        showSplash(TEMPO_AGUARDO_API, sameIntent, false)
                    }
                }
            }
        })
    }

    private fun showSplash(time: Long, intent: Intent, splash: Boolean) {
        super.onResume()
        splashTimer = object : CountDownTimer(time * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {}
            override fun onFinish() {
                startActivity(intent)
                if (splash)
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
