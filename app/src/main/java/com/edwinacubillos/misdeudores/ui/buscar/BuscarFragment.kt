package com.edwinacubillos.misdeudores.ui.buscar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.edwinacubillos.misdeudores.R
import com.edwinacubillos.misdeudores.data.server.DeudorServer
import com.edwinacubillos.misdeudores.databinding.FragmentBuscarBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class BuscarFragment : Fragment() {

    private lateinit var binding: FragmentBuscarBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_buscar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentBuscarBinding.bind(view)

        binding.buscarButton.setOnClickListener {
            val nombre = binding.nombreBuscarEditText.text.toString()

            //    buscarEnDatabase(nombre)

            buscarEnFirebase(nombre)
        }
    }

    private fun buscarEnFirebase(nombre: String) {

        val database = FirebaseDatabase.getInstance()
        val myDeudorRef = database.getReference("deudores")
        val postListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (data: DataSnapshot in snapshot.children) {
                    val deudorServer = data.getValue(DeudorServer::class.java)
                    if (deudorServer?.nombre.equals(nombre)) {
                        binding.telefonoTextView.setText(deudorServer?.telefono)
                        binding.valorTextView.setText(deudorServer?.valor.toString())
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        }
        myDeudorRef.addValueEventListener(postListener)
    }

    /*   private fun buscarEnDatabase(nombre: String) {
           val deudorDAO: DeudorDAO = MisDeudores.database.DeudorDAO()
           val deudor = deudorDAO.searchDeudor(nombre)

           if (deudor != null) {
               binding.telefonoTextView.text = deudor.telefono
               binding.valorTextView.text = deudor.valor.toString()
           } else {
               Toast.makeText(context, "No existe", Toast.LENGTH_SHORT).show()
           }
       }*/
}