package com.es.aplicacion.model

import org.bson.codecs.pojo.annotations.BsonId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document

@Document("Tareas")
data class Tarea(
    @BsonId
    val _id: String? = null,
    val descripcion: String,
    var completada: Boolean,
    @DBRef
    val usuario: Usuario
)