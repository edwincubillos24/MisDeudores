package com.edwinacubillos.misdeudores.data.server

import java.io.Serializable

class DeudorServer(
    val id: String? = "",
    val nombre: String = "",
    val telefono: String = "",
    val valor: Long = 0,
    val foto: String = ""
) : Serializable