package com.es.aplicacion.dto.Usuario

import com.es.aplicacion.model.Hogar

data class UsuarioRegisterDTO(
    val username: String,
    val email: String,
    val password: String,
    val passwordRepeat: String,
    val rol: String?,
    val hogar: Hogar?
)
