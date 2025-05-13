package com.es.aplicacion.model

import org.bson.codecs.pojo.annotations.BsonId
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document

/**
 * Representa una tarea en el sistema.
 *
 * @property _id Identificador único de la tarea.
 * @property descripcion Descripción de la tarea.
 * @property completada Indica si la tarea ha sido completada.
 * @property usuario Usuario al que está asociada la tarea.
 */
@Document("Tareas")
data class Tarea(
    @BsonId
    val _id: String? = null,
    val descripcion: String,
    var completada: Boolean,
    @DBRef
    val usuario: Usuario
)