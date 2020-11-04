package com.edwinacubillos.misdeudores.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.edwinacubillos.misdeudores.R
import com.edwinacubillos.misdeudores.ui.bottom.BottomActivity
import com.edwinacubillos.misdeudores.ui.registro.RegistroActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    companion object {
        private val TAG = RegistroActivity::class.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        login_button.setOnClickListener {
            val correo = correo_edit_text.text.toString()
            val contrasena = contrasena_edit_text.text.toString()

            loginWithFirebase(correo, contrasena)
        }

        registrar_button.setOnClickListener {
            goToRegisterActivity()
        }
    }

    private fun loginWithFirebase(correo: String, contrasena: String) {
        auth.signInWithEmailAndPassword(correo, contrasena)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    goToBottomActivity()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun goToBottomActivity() {
        val intent = Intent(this, BottomActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun goToRegisterActivity() {
        val intent = Intent(this, RegistroActivity::class.java)
        startActivity(intent)
    }
}