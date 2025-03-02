package com.es.aplicacion.controller

import com.es.aplicacion.dto.Tarea.CrearTareaDTO
import com.es.aplicacion.model.Tarea
import com.es.aplicacion.service.TareaService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/tareas")
class TareaController @Autowired constructor(
    private val tareaService: TareaService
) {
    @PostMapping("/crear")
    fun crearTarea(@RequestBody request: CrearTareaDTO): ResponseEntity<Tarea> {
        val tarea = tareaService.crearTarea(request.descripcion, request.usuarioId)
        return ResponseEntity.ok(tarea)
    }

    @GetMapping("/usuario")
    fun obtenerTareasPorUsuario(): ResponseEntity<List<Tarea>> {
        val tareas = tareaService.obtenerTareasPorUsuario()
        return ResponseEntity.ok(tareas)
    }
}