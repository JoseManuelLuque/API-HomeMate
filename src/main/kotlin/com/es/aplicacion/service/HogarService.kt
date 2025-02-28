package com.es.aplicacion.service

import com.es.aplicacion.dto.CrearHogarDTO
import com.es.aplicacion.model.Direccion
import com.es.aplicacion.model.Hogar
import com.es.aplicacion.repository.HogarRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class HogarService  {
    @Autowired
    private lateinit var hogarRepository: HogarRepository

    fun crearHogar(nombre: String, direccion: Direccion): Hogar {
        val codigo = generarCodigoUnico()
        val direccionEjemplo =
            Direccion(calle = "Calle Falsa", numero = 123, municipio = "Springfield", provincia = "Cadiz")
        val hogarDTO = CrearHogarDTO(nombre = "Mi hogar", direccion = direccionEjemplo)

        val hogar = Hogar(
            nombre = hogarDTO.nombre,
            codigo = codigo,
            direccion = hogarDTO.direccion
        )

        return hogarRepository.save(hogar)
    }

    fun obtenerHogarPorCodigo(codigo: String): Hogar? {
        return hogarRepository.findByCodigo(codigo)
    }

    private fun generarCodigoUnico(): String {
        var codigo: String
        do {
            codigo = generarCodigo()
        } while (hogarRepository.findByCodigo(codigo) != null)
        return codigo
    }

    private fun generarCodigo(): String {
        return (100000000..999999999).random().toString()
    }
}