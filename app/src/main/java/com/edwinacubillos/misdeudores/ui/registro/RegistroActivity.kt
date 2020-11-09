package com.edwinacubillos.misdeudores.ui.registro

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.edwinacubillos.misdeudores.R
import com.edwinacubillos.misdeudores.data.server.Usuario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_registro.*

class RegistroActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    companion object {
        private const val EMPTY = ""
        private const val SPACE = " "
        private val TAG = RegistroActivity::class.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        auth = FirebaseAuth.getInstance()

        registrar_button.setOnClickListener {
            val correo = correo_edit_text.text.toString()
            val contrasena = contrasena_edit_text.text.toString()
            val nombre = nombre_edit_text.text.toString()

            registroEnFirebase(correo, contrasena, nombre)

            val telefono = telefono_edit_text.text.toString()

            val repContrasena = rep_contrasena_edit_text.text.toString()
            val genero =
                if (masculino_radio_button.isChecked) getString(R.string.masculino) else getString(R.string.femenino)

            var pasatiempos = EMPTY
            if (nadar_check_box.isChecked) pasatiempos += getString(R.string.nadar) + SPACE
            if (cine_check_box.isChecked) pasatiempos += getString(R.string.cine) + SPACE
            if (comer_check_box.isChecked) pasatiempos += getString(R.string.comer)

            val ciudadDeNacimiento = ciudad_nacimiento_spinner.selectedItem
            respuesta_text_view.text = getString(
                R.string.respuesta,
                nombre,
                correo,
                telefono,
                genero,
                pasatiempos,
                ciudadDeNacimiento
            )
        }
    }

    private fun registroEnFirebase(correo: String, contrasena: String, nombre: String) {

        auth.createUserWithEmailAndPassword(correo, contrasena)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val uid = auth.currentUser?.uid
                    crearUsuarioEnBaseDatos(uid, correo, nombre)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun crearUsuarioEnBaseDatos(uid: String?, correo: String, nombre: String) {
        val database = FirebaseDatabase.getInstance()
        val myUsersReference = database.getReference("usuarios")

        val usuario = Usuario(uid, nombre, correo)
        uid?.let { myUsersReference.child(uid).setValue(usuario) }

        goToLoginActivity()
    }

    private fun goToLoginActivity() {
        onBackPressed()
    }

    override fun onStart() {
        super.onStart()
        Log.d("Método", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("Método", "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("Método", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("Método", "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("Método", "onDestroy")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("Método", "onRestart")
    }
}






