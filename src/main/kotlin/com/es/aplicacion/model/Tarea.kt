package com.es.aplicacion.model

data class Tarea(
    val id: Int,
    val descripcion: String,
    val estado: Estado,
    val usuario: Usuario,
    val hogar: Hogar
)