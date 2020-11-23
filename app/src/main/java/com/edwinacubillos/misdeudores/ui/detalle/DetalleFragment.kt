package com.edwinacubillos.misdeudores.ui.detalle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.edwinacubillos.misdeudores.R
import com.edwinacubillos.misdeudores.databinding.FragmentDetalleBinding

class DetalleFragment : Fragment() {

    private lateinit var binding: FragmentDetalleBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detalle, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentDetalleBinding.bind(view)

        val args: DetalleFragmentArgs by navArgs()
        val deudorDetalle = args.deudorSeleccionado
        binding.nombreTextView.text = deudorDetalle.nombre

    }


}