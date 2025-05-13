package com.es.aplicacion.repository

import com.es.aplicacion.model.Tarea
import com.es.aplicacion.model.Usuario
import org.springframework.data.mongodb.repository.MongoRepository

/**
 * # Repositorio para gestionar las operaciones relacionadas con las tareas en la base de datos.
 */
interface TareaRepository : MongoRepository<Tarea, String> {

    /**
     * ### Busca todas las tareas asociadas a un usuario específico.
     *
     * @param usuario El usuario cuyas tareas se desean buscar.
     * @return Una lista de tareas asociadas al usuario proporcionado.
     */
    fun findByUsuario(usuario: Usuario): List<Tarea>
}