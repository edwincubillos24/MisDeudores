package com.edwinacubillos.misdeudores.ui.borrar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.edwinacubillos.misdeudores.MisDeudores
import com.edwinacubillos.misdeudores.R
import com.edwinacubillos.misdeudores.data.database.dao.DeudorDAO
import com.edwinacubillos.misdeudores.data.server.DeudorServer
import com.edwinacubillos.misdeudores.databinding.FragmentBorrarBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class BorrarFragment : Fragment() {

    private lateinit var binding: FragmentBorrarBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_borrar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentBorrarBinding.bind(view)

        binding.borrarButton.setOnClickListener {
            val nombre = binding.nombreBorrarEditText.text.toString()

            //        borrarDeDatabase(nombre)

            borrarDeFirebase(nombre)
        }
    }

    private fun borrarDeFirebase(nombre: String) {
        val database = FirebaseDatabase.getInstance()
        val myDeudorRef = database.getReference("deudores")
        val postListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (data: DataSnapshot in snapshot.children) {
                    val deudorServer = data.getValue(DeudorServer::class.java)
                    if (deudorServer?.nombre == nombre) {
                        deudorServer.id?.let { myDeudorRef.child(it).removeValue() }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        }
        myDeudorRef.addValueEventListener(postListener)
    }

    private fun borrarDeDatabase(nombre: String) {
        val deudorDAO: DeudorDAO = MisDeudores.database.DeudorDAO()
        val deudor = deudorDAO.searchDeudor(nombre)

        if (deudor != null)
            deudorDAO.deleteDeudor(deudor)
        else {
            Toast.makeText(context, "No existe", Toast.LENGTH_SHORT).show()
        }
    }
}