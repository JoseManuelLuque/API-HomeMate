package com.es.aplicacion.service

import com.es.aplicacion.error.exception.EntityNotFoundException
import com.es.aplicacion.error.exception.NotFoundException
import com.es.aplicacion.model.Tarea
import com.es.aplicacion.repository.TareaRepository
import com.es.aplicacion.repository.UsuarioRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

/**
 * # Servicio para gestionar las operaciones relacionadas con las tareas.
 */
@Service
class TareaService {

    @Autowired
    private lateinit var tareaRepository: TareaRepository

    @Autowired
    private lateinit var usuarioRepository: UsuarioRepository

    /**
     * ### Crea una nueva tarea asociada al usuario autenticado.
     *
     * @param descripcion Descripción de la tarea.
     * @return La tarea creada.
     * @throws IllegalArgumentException Si la descripción está vacía.
     * @throws NotFoundException Si el usuario autenticado no es encontrado.
     */
    fun crearTarea(descripcion: String): Tarea {
        val authEmail = SecurityContextHolder.getContext().authentication.name
        val usuario = usuarioRepository.findByEmail(authEmail)
            .orElseThrow { NotFoundException("Usuario no encontrado") }

        if (descripcion.isEmpty()) {
            throw IllegalArgumentException("La descripcion no puede estar vacia")
        }

        val tarea = Tarea(descripcion = descripcion, completada = false, usuario = usuario)
        return tareaRepository.save(tarea)
    }

    /**
     * ### Crea una nueva tarea asociada a un usuario específico.
     *
     * @param descripcion Descripción de la tarea.
     * @param idUsuario ID del usuario al que se asociará la tarea.
     * @return La tarea creada.
     * @throws IllegalArgumentException Si la descripción está vacía.
     * @throws NotFoundException Si el usuario no es encontrado.
     */
    fun crearTareaUsuario(descripcion: String, idUsuario: String): Tarea {
        val usuario = usuarioRepository.findById(idUsuario)
            .orElseThrow { NotFoundException("Usuario no encontrado") }

        if (descripcion.isEmpty()) {
            throw IllegalArgumentException("La descripcion no puede estar vacia")
        }

        val tarea = Tarea(descripcion = descripcion, completada = false, usuario = usuario)
        return tareaRepository.save(tarea)
    }

    /**
     * ### Elimina una tarea por su ID.
     *
     * @param id ID de la tarea a eliminar.
     * @throws EntityNotFoundException Si la tarea no existe.
     */
    fun eliminarTarea(id: String) {
        if (tareaRepository.existsById(id)) {
            tareaRepository.deleteById(id)
        } else {
            throw EntityNotFoundException("Tarea no encontrada")
        }
    }

    /**
     * ### Obtiene las tareas asociadas al usuario autenticado.
     *
     * @return Lista de tareas del usuario autenticado.
     * @throws NotFoundException Si el usuario autenticado no es encontrado.
     */
    fun obtenerTareasPorAutentificacion(): List<Tarea> {
        val authEmail = SecurityContextHolder.getContext().authentication.name
        val usuario = usuarioRepository.findByEmail(authEmail)
            .orElseThrow { NotFoundException("Usuario no encontrado") }

        return tareaRepository.findByUsuario(usuario)
    }

    /**
     * ### Obtiene todas las tareas almacenadas en la base de datos.
     *
     * @return Lista de todas las tareas.
     */
    fun obtenerTodasLasTareas(): List<Tarea> {
        return tareaRepository.findAll()
    }

    /**
     * ### Actualiza el estado de una tarea (completada o no completada).
     *
     * @param id ID de la tarea a actualizar.
     * @return La tarea con el estado actualizado.
     * @throws NotFoundException Si la tarea no es encontrada.
     */
    fun actualizarEstadoTarea(id: String): Tarea {
        val tarea = tareaRepository.findById(id)
            .orElseThrow { NotFoundException("Tarea no encontrada") }

        tarea.completada = !tarea.completada
        return tareaRepository.save(tarea)
    }
}