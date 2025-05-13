package com.es.aplicacion.controller

import com.es.aplicacion.dto.Tarea.CrearTareaDTO
import com.es.aplicacion.model.Tarea
import com.es.aplicacion.service.TareaService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
/**
 * # Controlador para gestionar las tareas.
 *
 * Este controlador expone endpoints para realizar operaciones CRUD sobre las tareas,
 * como creación, obtención, actualización y eliminación.
 *
 * ### 🔒 Endpoint solo accesible para Administradores
 * ### 🔑 Endpoint accesible para cualquier usuario autenticado
 */
@RestController
@RequestMapping("/tareas")
class TareaController {
    @Autowired
    private lateinit var tareaService: TareaService

    /**
     * ### 🔑 Endpoint para crear una nueva tarea.
     *
     * @param request Objeto de tipo CrearTareaDTO que contiene la descripción de la tarea a crear.
     * @return ResponseEntity que contiene la tarea creada.
     */
    @PostMapping("/crear")
    fun crearTarea(@RequestBody request: CrearTareaDTO): ResponseEntity<Tarea> {
        val tarea = tareaService.crearTarea(request.descripcion)
        return ResponseEntity.ok(tarea)
    }

    /**
     * ### 🔒 Endpoint para crear una nueva tarea asociada a un usuario.
     *
     * @param request Objeto de tipo CrearTareaDTO que contiene la descripción de la tarea a crear.
     * @param idUsuario ID del usuario al que se asociará la tarea.
     * @return ResponseEntity que contiene la tarea creada.
     */
    @PostMapping("/crear/usuario/{idUsuario}")
    fun crearTareaUsuario(@RequestBody request: CrearTareaDTO, @PathVariable idUsuario: String): ResponseEntity<Tarea> {
        val tarea = tareaService.crearTareaUsuario(request.descripcion, idUsuario)
        return ResponseEntity.ok(tarea)
    }

    /**
     * ### 🔑 Endpoint para obtener las tareas asociadas al usuario autenticado, usada para mostrarte tus propias tareas.
     *
     * @return ResponseEntity que contiene una lista de tareas del usuario autenticado.
     */
    @GetMapping("/usuario")
    fun obtenerTareasPorUsuario(): ResponseEntity<List<Tarea>> {
        val tareas = tareaService.obtenerTareasPorAutentificacion()
        return ResponseEntity.ok(tareas)
    }

    /**
     * ### 🔒 Endpoint para obtener todas las tareas de todos los usuarios.
     *
     * @return ResponseEntity que contiene una lista de todas las tareas.
     */
    @GetMapping("/getAll")
    fun getAllTareas(): ResponseEntity<List<Tarea>> {
        val tareas = tareaService.obtenerTodasLasTareas()
        return ResponseEntity.ok(tareas)
    }

    /**
     * ### 🔑 Endpoint para eliminar una tarea por su ID.
     *
     * @param id ID de la tarea a eliminar.
     * @return ResponseEntity sin contenido si la eliminación fue exitosa.
     */
    @DeleteMapping("/delete/{id}")
    fun eliminarTarea(@PathVariable id: String?): ResponseEntity<Void?> {
        tareaService.eliminarTarea(id!!)
        return ResponseEntity.noContent().build<Void?>()
    }

    /**
     * ### 🔑 Endpoint para actualizar el estado de una tarea (Completada/Pendiente).
     *
     * @param id ID de la tarea cuyo estado se actualizará.
     * @return ResponseEntity que contiene la tarea actualizada.
     */
    @PutMapping("/update/status/{id}")
    fun actualzarEstadoTarea(@PathVariable id: String): ResponseEntity<Tarea> {
        val tarea = tareaService.actualizarEstadoTarea(id)
        return ResponseEntity.ok(tarea)
    }
}