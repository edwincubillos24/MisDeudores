package com.edwinacubillos.misdeudores.ui.lista

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edwinacubillos.misdeudores.R
import com.edwinacubillos.misdeudores.data.server.DeudorServer
import com.edwinacubillos.misdeudores.databinding.FragmentListaBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ListaFragment : Fragment(), DeudoresRVAdapter.OnItemClickListener {

    private lateinit var binding: FragmentListaBinding
    var listDeudores: MutableList<DeudorServer> = mutableListOf()
    private lateinit var deudoresRVAdapter: DeudoresRVAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lista, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentListaBinding.bind(view)

        binding.deudoresRecyclerView.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.deudoresRecyclerView.setHasFixedSize(true)

        deudoresRVAdapter =
            DeudoresRVAdapter(listDeudores as ArrayList<DeudorServer>, this@ListaFragment)

        binding.deudoresRecyclerView.adapter = deudoresRVAdapter

        //    cargarDesdeDatabase()

        cargarDesdeFirebase()

        deudoresRVAdapter.notifyDataSetChanged()
    }

    fun cargarDesdeFirebase() {
        val database = FirebaseDatabase.getInstance()
        val myDeudoresRef = database.getReference("deudores")

        listDeudores.clear()

        val postListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dato: DataSnapshot in snapshot.children) {
                    val deudorServer = dato.getValue(DeudorServer::class.java)
                    deudorServer?.let { listDeudores.add(it) }
                }
                deudoresRVAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        }

        myDeudoresRef.addValueEventListener(postListener)
    }

    override fun onItemClick(deudor: DeudorServer) {
        val action = ListaFragmentDirections.actionNavListaToDetalleFragment(deudor)
        findNavController().navigate(action)
    }

    /*  fun cargarDesdeDatabase(){
          val deudorDAO = MisDeudores.database.DeudorDAO()
          listDeudores = deudorDAO.getDeudores()
      }*/
}