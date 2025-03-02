package com.es.aplicacion.service

import com.es.aplicacion.model.Tarea
import com.es.aplicacion.model.Usuario
import com.es.aplicacion.repository.TareaRepository
import com.es.aplicacion.repository.UsuarioRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TareaService @Autowired constructor(
    private val tareaRepository: TareaRepository,
    private val usuarioRepository: UsuarioRepository
) {
    fun crearTarea(descripcion: String, usuarioId: String): Tarea {
        val usuario = usuarioRepository.findById(usuarioId).orElseThrow { IllegalArgumentException("Usuario no encontrado") }
        val tarea = Tarea(descripcion = descripcion, completada = false, usuario = usuario)
        return tareaRepository.save(tarea)
    }

    fun obtenerTareasPorUsuario(usuarioId: String): List<Tarea> {
        val usuario = usuarioRepository.findById(usuarioId).orElseThrow { IllegalArgumentException("Usuario no encontrado") }
        return tareaRepository.findByUsuario(usuario)
    }
}