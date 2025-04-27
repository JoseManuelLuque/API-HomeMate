package com.es.aplicacion.dto.Usuario

import com.es.aplicacion.dto.Tareas.TareaDTO
import com.es.aplicacion.model.Tarea

data class UsuarioConTareasDTO(
    val id: String?,
    val username: String,
    val email: String,
    val roles: String,
    val tareas: List<TareaDTO>
)