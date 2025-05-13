package com.es.aplicacion.controller

import com.es.aplicacion.dto.Hogares.CrearHogarDTO
import com.es.aplicacion.dto.Hogares.UnirseHogarDTO
import com.es.aplicacion.model.Hogar
import com.es.aplicacion.service.HogarService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/hogares")
class HogarController {
    @Autowired
    private lateinit var hogarService: HogarService
    // Función para crear un hogar
    @PostMapping("/{idUsuario}/crear")
    fun crearHogar(@RequestBody request: CrearHogarDTO, @PathVariable idUsuario: String): ResponseEntity<Hogar> {
        val hogar = hogarService.crearHogar(request.nombre, request.direccion, idUsuario)
        return ResponseEntity.ok(hogar)
    }

    // Función para unirse a un hogar
    @PostMapping("/{idUsuario}/unirse")
    fun unirseAHogar(@PathVariable idUsuario: String, @RequestBody request: UnirseHogarDTO): ResponseEntity<Any> {
        val usuario = hogarService.unirseAHogar(idUsuario, request.codigo)
        return ResponseEntity.ok(usuario)
    }
}