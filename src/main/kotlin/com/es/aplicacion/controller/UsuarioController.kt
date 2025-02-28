package com.es.aplicacion.controller

import com.es.aplicacion.dto.CrearHogarDTO
import com.es.aplicacion.dto.LoginUsuarioDTO
import com.es.aplicacion.dto.UnirseHogarDTO
import com.es.aplicacion.dto.UsuarioDTO
import com.es.aplicacion.dto.UsuarioRegisterDTO
import com.es.aplicacion.error.exception.UnauthorizedException
import com.es.aplicacion.service.TokenService
import com.es.aplicacion.service.UsuarioService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/usuarios")
class UsuarioController {

    @Autowired
    private lateinit var authenticationManager: AuthenticationManager

    @Autowired
    private lateinit var tokenService: TokenService

    @Autowired
    private lateinit var usuarioService: UsuarioService

    // Función de Registro
    @PostMapping("/register")
    fun insert(
        httpRequest: HttpServletRequest,
        @RequestBody usuarioRegisterDTO: UsuarioRegisterDTO
    ): ResponseEntity<UsuarioDTO>? {
        val usuarioDTO = usuarioService.insertUser(usuarioRegisterDTO)
        return ResponseEntity(usuarioDTO, HttpStatus.CREATED)
    }

    // Función de Inicio de Sesión
    @PostMapping("/login")
    fun login(@RequestBody usuario: LoginUsuarioDTO): ResponseEntity<Any>? {

        val authentication: Authentication
        try {
            authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(
                    usuario.username,
                    usuario.password
                )
            )
        } catch (e: AuthenticationException) {
            throw UnauthorizedException("Credenciales incorrectas, ${e.message}")
        }
        var token = tokenService.generarToken(authentication)

        return ResponseEntity(mapOf("token" to token), HttpStatus.CREATED)
    }

    // Función para unirse a un hogar
    @PostMapping("/{id}/unirse")
    fun unirseAHogar(@PathVariable id: String, @RequestBody request: UnirseHogarDTO): ResponseEntity<Any> {
        val usuario = usuarioService.unirseAHogar(id, request.codigo)
        return ResponseEntity.ok(usuario)
    }

}