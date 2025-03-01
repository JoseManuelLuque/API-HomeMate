package com.es.aplicacion.service

import com.es.aplicacion.dto.Hogares.CrearHogarDTO
import com.es.aplicacion.error.exception.NotFoundException
import com.es.aplicacion.model.Direccion
import com.es.aplicacion.model.Hogar
import com.es.aplicacion.model.Tarea
import com.es.aplicacion.model.Usuario
import com.es.aplicacion.repository.HogarRepository
import com.es.aplicacion.repository.UsuarioRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class HogarService {
    @Autowired
    private lateinit var hogarRepository: HogarRepository

    @Autowired
    private lateinit var usuarioRepository: UsuarioRepository

    fun crearHogar(nombre: String, direccion: Direccion, usuarioId: String): Hogar {
        // Se genera un codigo aleatorio de 9 cifeas para el hogar
        val codigo = generarCodigoUnico()

        //Usuario que crea el hogar, para hacerlo parte de el automaticamente
        val usuario =
            usuarioRepository.findById(usuarioId).orElseThrow { IllegalArgumentException("Usuario no encontrado") }

        // Se crea el hogar con los datos recibidos
        val hogarDTO = CrearHogarDTO(nombre, direccion)

        // Se crea el hogar con los datos recibidos para poder guardarlo en la base de datos
        val hogar = Hogar(
            nombre = hogarDTO.nombre,
            codigo = codigo,
            direccion = hogarDTO.direccion,
            usuarios = mutableListOf<Usuario>(usuario),
            tareas = mutableListOf<Tarea>()
        )

        // Se añade el usuario que ha creado el hogar como miembro del hogar
        return hogarRepository.save(hogar)
    }

    // Funcion que permite al usuario unirse a un hogar
    fun unirseAHogar(usuarioId: String, codigoHogar: String): Hogar {
        // Se busca el hogar por el codigo
        var hogar = hogarRepository.findByCodigo(codigoHogar) ?: throw NotFoundException("Hogar no encontrado")
        // Se busca el usuario por el id
        var usuario = usuarioRepository.findById(usuarioId).orElse(null) ?: throw NotFoundException("Usuario no encontrado")
        // Se añade el usuario al hogar
        hogar.usuarios.add(usuario)

        return hogarRepository.save(hogar)
    }

    // Funcion que genera un codigo unico para el hogar
    private fun generarCodigoUnico(): String {
        var codigo: String
        do {
            codigo = (100000000..999999999).random().toString()
        } while (hogarRepository.findByCodigo(codigo) != null)
        return codigo
    }
}