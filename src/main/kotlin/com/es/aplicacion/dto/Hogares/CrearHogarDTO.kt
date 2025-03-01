package com.es.aplicacion.dto.Hogares

import com.es.aplicacion.model.Direccion

data class CrearHogarDTO(
    val nombre: String,
    val direccion: Direccion
)