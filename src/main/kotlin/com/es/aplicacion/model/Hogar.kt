package com.es.aplicacion.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("Hogares")
data class Hogar(
    @Id
    val id: String? = null,
    val nombre: String,
    val direccion: Direccion,
    val codigo: String,
    val usuarios: List<Usuario?> = emptyList(),
    val tareas: List<Tarea?> = emptyList(),
)