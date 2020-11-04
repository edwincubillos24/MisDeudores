package com.edwinacubillos.misdeudores.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.edwinacubillos.misdeudores.R
import com.edwinacubillos.misdeudores.ui.bottom.BottomActivity
import com.edwinacubillos.misdeudores.ui.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import java.util.*
import kotlin.concurrent.timerTask

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val timer = Timer()
        timer.schedule(
            timerTask {
                val auth = FirebaseAuth.getInstance().currentUser
                if (auth == null) {
                    goToLoginActivity()
                } else {
                    goToBottomActivity()
                }
            }, 2000
        )
    }

    fun goToLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun goToBottomActivity() {
        val intent = Intent(this, BottomActivity::class.java)
        startActivity(intent)
        finish()
    }


}