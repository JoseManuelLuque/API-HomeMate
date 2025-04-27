package com.es.aplicacion.model

import org.bson.codecs.pojo.annotations.BsonId
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document

@Document("Usuarios")
data class Usuario(
    @BsonId
    val _id: String?,
    var username: String,
    var password: String,
    var email: String,
    var roles: String = "USER",
    @DBRef
    var hogar: Hogar? = null,
    // Almacenomos las id de las tareas del usuario en una lista

)