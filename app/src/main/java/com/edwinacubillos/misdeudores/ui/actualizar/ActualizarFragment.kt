package com.edwinacubillos.misdeudores.ui.actualizar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.edwinacubillos.misdeudores.R
import com.edwinacubillos.misdeudores.data.server.DeudorServer
import com.edwinacubillos.misdeudores.databinding.FragmentActualizarBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlin.toString as toString1

class ActualizarFragment : Fragment() {

    private lateinit var binding: FragmentActualizarBinding
    private var isSearching = true
    private var idDeudor: String? = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_actualizar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentActualizarBinding.bind(view)

        binding.modificarButton.setOnClickListener {
            val nombre = binding.nombreBuscarEditText.text.toString1()
            val telefono = binding.telefonoEditText.text.toString1()
            val valor = binding.valorEditText.text.toString1()

            //    actualizarEnDatabase(nombre, telefono, valor)

            actualizarEnFirebase(nombre, telefono, valor)
        }
    }

    private fun actualizarEnFirebase(nombre: String, telefono: String, valor: String) {
        val database = FirebaseDatabase.getInstance()
        val myDeudorRef = database.getReference("deudores")
        if (isSearching) {
            val postListener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (data: DataSnapshot in snapshot.children) {
                        val deudorServer = data.getValue(DeudorServer::class.java)
                        if (deudorServer?.nombre.equals(nombre)) {
                            binding.telefonoEditText.setText(deudorServer?.telefono)
                            binding.valorEditText.setText(deudorServer?.valor?.toString())
                            isSearching = false
                            binding.modificarButton.text = getString(R.string.actualizar)
                            idDeudor = deudorServer?.id
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }
            }
            myDeudorRef.addValueEventListener(postListener)

        } else {
            val childUpdates = HashMap<String, Any>()
            childUpdates["nombre"] = nombre
            childUpdates["telefono"] = telefono
            childUpdates["valor"] = valor.toLong()
            idDeudor?.let { myDeudorRef.child(it).updateChildren(childUpdates) }
            isSearching = true
            binding.modificarButton.text = getString(R.string.buscar)
        }
    }


/*    private fun actualizarEnDatabase(
        nombre: String,
        telefono: String,
        valor: String
    ) {
        val deudorDAO = MisDeudores.database.DeudorDAO()

        if (isSearching) { //buscando
            val deudor = deudorDAO.searchDeudor(nombre)
            if (deudor != null) {
                isSearching = false
                binding.modificarButton.text = getString(R.string.actualizar)
                idDeudor = deudor.id
                binding.telefonoEditText.setText(deudor.telefono)
                binding.valorEditText.setText(deudor.valor?.toString())
            } else {
                Toast.makeText(context, "No existe", Toast.LENGTH_LONG).show()
            }
        } else {  //actualizando
            val deudor = Deudor(idDeudor, nombre, telefono, valor.toLong())

            deudorDAO.updateDeudor(deudor)
            isSearching = true
            binding.modificarButton.text = getString(R.string.buscar)
        }
    }*/
}