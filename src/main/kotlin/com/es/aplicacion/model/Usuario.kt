package com.es.aplicacion.model

import org.bson.codecs.pojo.annotations.BsonId
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document

/**
 * # Representa un usuario en el sistema.
 *
 * @property _id Identificador único del usuario.
 * @property username Nombre de usuario.
 * @property password Contraseña del usuario.
 * @property email Correo electrónico del usuario.
 * @property roles Roles asignados al usuario (por defecto "USER").
 * @property hogar Referencia al hogar asociado al usuario (puede ser nulo).
 */
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
)