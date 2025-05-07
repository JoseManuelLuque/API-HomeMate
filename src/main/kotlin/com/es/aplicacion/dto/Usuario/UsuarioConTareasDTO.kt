package com.es.aplicacion.dto.Usuario

import com.es.aplicacion.dto.Tareas.TareaDTO

data class UsuarioConTareasDTO(
    val _id: String?,
    val username: String,
    val email: String,
    val roles: String,
    val tareas: List<TareaDTO>
)