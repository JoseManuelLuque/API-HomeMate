package com.es.aplicacion.repository

import com.es.aplicacion.model.Tarea
import com.es.aplicacion.model.Usuario
import org.springframework.data.mongodb.repository.MongoRepository

interface TareaRepository : MongoRepository<Tarea, String> {
    fun findByUsuario(usuario: Usuario): List<Tarea>
}