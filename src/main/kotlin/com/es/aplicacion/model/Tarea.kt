package com.es.aplicacion.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document

@Document("Tareas")
data class Tarea(
    @Id
    val id: String? = null,
    val descripcion: String,
    val completada: Boolean,
    @DBRef
    val usuario: Usuario
)