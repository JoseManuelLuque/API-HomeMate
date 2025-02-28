package com.es.aplicacion.controller

import com.es.aplicacion.dto.CrearHogarDTO
import com.es.aplicacion.model.Hogar
import com.es.aplicacion.service.HogarService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/hogares")
class HogarController @Autowired constructor(
    private val hogarService: HogarService
) {
    @PostMapping("/crear")
    fun crearHogar(@RequestBody request: CrearHogarDTO): ResponseEntity<Hogar> {
        val hogar = hogarService.crearHogar(request.nombre, request.direccion)
        return ResponseEntity.ok(hogar)
    }
}