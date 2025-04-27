package com.es.aplicacion.controller

import com.es.aplicacion.dto.Tarea.CrearTareaDTO
import com.es.aplicacion.model.Tarea
import com.es.aplicacion.service.TareaService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/tareas")
class TareaController {
    @Autowired
    private lateinit var tareaService: TareaService

    @PostMapping("/crear")
    fun crearTarea(@RequestBody request: CrearTareaDTO): ResponseEntity<Tarea> {
        val tarea = tareaService.crearTarea(request.descripcion)
        return ResponseEntity.ok(tarea)
    }

    @GetMapping("/usuario")
    fun obtenerTareasPorUsuario(): ResponseEntity<List<Tarea>> {
        val tareas = tareaService.obtenerTareasPorAutentificacion()
        return ResponseEntity.ok(tareas)
    }

    @GetMapping("/getAll")
    fun getAllTareas(): ResponseEntity<List<Tarea>> {
        val tareas = tareaService.obtenerTodasLasTareas()
        return ResponseEntity.ok(tareas)
    }

    @DeleteMapping("/delete/{id}")
    fun eliminarTarea(@PathVariable id: String?): ResponseEntity<Void?> {
        tareaService.eliminarTarea(id!!)
        return ResponseEntity.noContent().build<Void?>()
    }
}