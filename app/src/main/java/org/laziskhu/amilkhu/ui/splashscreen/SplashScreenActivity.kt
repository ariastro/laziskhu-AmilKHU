package org.laziskhu.amilkhu.ui.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import org.laziskhu.amilkhu.databinding.ActivitySplashScreenBinding
import org.laziskhu.amilkhu.ui.auth.login.LoginActivity

class SplashScreenActivity : AppCompatActivity() {

    private var _binding: ActivitySplashScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler(Looper.myLooper()!!).postDelayed({
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }, 5000)

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}