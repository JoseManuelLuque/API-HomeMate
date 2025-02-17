package com.es.aplicacion.model

data class Hogar(
    val id: Int,
    val nombre: String,
    val direccion: Direccion,
    val usuarios: List<Usuario>,
    val tareas: List<Tarea>,
)