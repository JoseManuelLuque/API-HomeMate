package com.es.aplicacion.service

import com.es.aplicacion.error.exception.EntityNotFoundException
import com.es.aplicacion.error.exception.NotFoundException
import com.es.aplicacion.model.Tarea
import com.es.aplicacion.repository.TareaRepository
import com.es.aplicacion.repository.UsuarioRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class TareaService {
    @Autowired
    private lateinit var tareaRepository: TareaRepository

    @Autowired
    private lateinit var usuarioRepository: UsuarioRepository

    fun crearTarea(descripcion: String): Tarea {
        // Obtener el email del contexto de seguridad
        val authEmail = SecurityContextHolder.getContext().authentication.name

        // Buscar el usuario por el email
        val usuario =
            usuarioRepository.findByEmail(authEmail).orElseThrow { NotFoundException("Usuario no encontrado") }

        if (descripcion.isEmpty()) {
            throw IllegalArgumentException("La descripcion no puede estar vacia")
        }

        // Crear la tarea, con la descripcion y el usuario
        val tarea = Tarea(descripcion = descripcion, completada = false, usuario = usuario)

        return tareaRepository.save(tarea)
    }

    fun eliminarTarea(id: String) {
        if (tareaRepository.existsById(id)) {
            tareaRepository.deleteById(id)
        } else {
            throw EntityNotFoundException("Tarea no encontrada")
        }
    }

    fun obtenerTareasPorAutentificacion(): List<Tarea> {
        // Obtener el email del contexto de seguridad
        val authEmail = SecurityContextHolder.getContext().authentication.name

        // Buscar el usuario por el email
        val usuario =
            usuarioRepository.findByEmail(authEmail).orElseThrow { NotFoundException("Usuario no encontrado") }

        return tareaRepository.findByUsuario(usuario)
    }

    fun obtenerTodasLasTareas(): List<Tarea> {
        return tareaRepository.findAll()
    }
}