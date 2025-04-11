package com.es.aplicacion.controller

import com.es.aplicacion.dto.Usuario.LoginUsuarioDTO
import com.es.aplicacion.dto.Usuario.UsuarioDTO
import com.es.aplicacion.dto.Usuario.UsuarioRegisterDTO
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
import org.springframework.security.core.context.SecurityContextHolder
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
                    usuario.email, // Usar el email en lugar del nombre de usuario
                    usuario.password
                )
            )
        } catch (e: AuthenticationException) {
            throw UnauthorizedException("Credenciales incorrectas, ${e.message}")
        }
        val token = tokenService.generarToken(authentication)
        return ResponseEntity(mapOf("token" to token), HttpStatus.CREATED)
    }

    // Función para comprobobar admin
    @GetMapping("/admin")
    fun esAdmin(): ResponseEntity<Boolean> {
        val authentication = SecurityContextHolder.getContext().authentication
        val roles = authentication.authorities.map { it.authority }
        val esAdmin = roles.contains("ROLE_ADMIN")
        return ResponseEntity.ok(esAdmin)
    }
}