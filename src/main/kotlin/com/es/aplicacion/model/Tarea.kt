package com.es.aplicacion.model

data class Tarea(
    val id: Int,
    val descripcion: String,
    val completada: Boolean,
    val usuario: Usuario,
    val hogar: Hogar
)