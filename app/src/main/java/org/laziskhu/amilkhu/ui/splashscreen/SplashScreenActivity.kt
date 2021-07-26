package org.laziskhu.amilkhu.ui.splashscreen

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import org.laziskhu.amilkhu.data.source.local.Prefs
import org.laziskhu.amilkhu.databinding.ActivitySplashScreenBinding
import org.laziskhu.amilkhu.ui.auth.login.LoginActivity
import org.laziskhu.amilkhu.ui.main.MainActivity

class SplashScreenActivity : AppCompatActivity() {

    private var _binding: ActivitySplashScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler(Looper.myLooper()!!).postDelayed({
            proceedToNextScreen()
        }, 5000)
    }

    private fun proceedToNextScreen() {
        if (isSignedIn()) {
             launchActivity(MainActivity())
        } else {
            launchActivity(LoginActivity())
        }
    }

    private fun launchActivity(activity: Activity) {
        startActivity(Intent(this, activity::class.java))
        finish()
    }

    private fun isSignedIn(): Boolean {
        return !Prefs.token.isNullOrEmpty()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}