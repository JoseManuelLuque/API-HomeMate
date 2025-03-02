package com.es.aplicacion.service

import com.es.aplicacion.error.exception.NotFoundException
import com.es.aplicacion.model.Tarea
import com.es.aplicacion.model.Usuario
import com.es.aplicacion.repository.TareaRepository
import com.es.aplicacion.repository.UsuarioRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.stereotype.Service

@Service
class TareaService {
    @Autowired
    private lateinit var tareaRepository: TareaRepository

    @Autowired
    private lateinit var usuarioRepository: UsuarioRepository

    fun crearTarea(descripcion: String, usuarioId: String): Tarea {
        val usuario = usuarioRepository.findById(usuarioId).orElseThrow { IllegalArgumentException("Usuario no encontrado") }
        val tarea = Tarea(descripcion = descripcion, completada = false, usuario = usuario)
        return tareaRepository.save(tarea)
    }

    fun obtenerTareasPorUsuario(): List<Tarea> {
        // Obtener el email del contexto de seguridad
        val authEmail = SecurityContextHolder.getContext().authentication.name

        // Buscar el usuario por el email
        val usuario = usuarioRepository.findByEmail(authEmail).orElseThrow { NotFoundException("Usuario no encontrado") }

        return tareaRepository.findByUsuario(usuario)
    }
}