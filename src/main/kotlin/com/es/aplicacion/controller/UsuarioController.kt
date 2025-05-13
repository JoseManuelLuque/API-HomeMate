package com.es.aplicacion.controller

    import com.es.aplicacion.dto.Usuario.LoginUsuarioDTO
    import com.es.aplicacion.dto.Usuario.UsuarioConTareasDTO
    import com.es.aplicacion.dto.Usuario.UsuarioDTO
    import com.es.aplicacion.dto.Usuario.UsuarioRegisterDTO
    import com.es.aplicacion.error.exception.UnauthorizedException
    import com.es.aplicacion.model.Usuario
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

/**
     * # Controlador para gestionar las operaciones relacionadas con los usuarios.
     *
     * Este controlador expone endpoints para registrar, autenticar, actualizar, eliminar usuarios,
     * y realizar operaciones relacionadas con sus datos y tareas.
     * ### 🔒 Endpoints solo accesibles para Administradores
     * ### 🔑 Endpoints accesibles para cualquier usuario autenticado
     * ### 🟩 Endpoints accesibles para cualquier usuario no autenticado
     */
    @RestController
    @RequestMapping("/usuarios")
    class UsuarioController {

        @Autowired
        private lateinit var authenticationManager: AuthenticationManager

        @Autowired
        private lateinit var tokenService: TokenService

        @Autowired
        private lateinit var usuarioService: UsuarioService

        /**
         * ### 🟩 Endpoint para registrar un nuevo usuario.
         *
         * @param httpRequest Objeto HttpServletRequest para obtener información de la solicitud.
         * @param usuarioRegisterDTO Objeto con los datos necesarios para registrar un usuario.
         * @return ResponseEntity con los datos del usuario registrado.
         */
        @PostMapping("/register")
        fun insert(
            httpRequest: HttpServletRequest,
            @RequestBody usuarioRegisterDTO: UsuarioRegisterDTO
        ): ResponseEntity<UsuarioDTO>? {
            val usuarioDTO = usuarioService.insertUser(usuarioRegisterDTO)
            return ResponseEntity(usuarioDTO, HttpStatus.CREATED)
        }

        /**
         * ### 🟩 Endpoint para iniciar sesión.
         *
         * @param usuario Objeto LoginUsuarioDTO con las credenciales del usuario.
         * @return ResponseEntity con el token JWT generado.
         * @throws UnauthorizedException Si las credenciales son incorrectas.
         */
        @PostMapping("/login")
        fun login(@RequestBody usuario: LoginUsuarioDTO): ResponseEntity<Any>? {
            val authentication: Authentication
            try {
                authentication = authenticationManager.authenticate(
                    UsernamePasswordAuthenticationToken(
                        usuario.email,
                        usuario.password
                    )
                )
            } catch (e: AuthenticationException) {
                throw UnauthorizedException("Credenciales incorrectas, ${e.message}")
            }
            val token = tokenService.generarToken(authentication)
            return ResponseEntity(mapOf("token" to token), HttpStatus.CREATED)
        }

        /**
         * ### 🔑 Endpoint para verificar si el usuario autenticado es administrador.
         *
         * @return ResponseEntity con un booleano indicando si el usuario es administrador.
         */
        @GetMapping("/admin")
        fun esAdmin(): ResponseEntity<Boolean> {
            val authentication = SecurityContextHolder.getContext().authentication
            val roles = authentication.authorities.map { it.authority }
            val esAdmin = roles.contains("ROLE_ADMIN")
            return ResponseEntity.ok(esAdmin)
        }

        /**
         * ### 🔑 Endpoint para obtener los datos del usuario autenticado.
         *
         * @return ResponseEntity con los datos del usuario autenticado.
         */
        @GetMapping("/me")
        fun obtenerDatosUsuario(): ResponseEntity<Usuario> {
            val authentication = SecurityContextHolder.getContext().authentication
            val email = authentication.name
            val usuario = usuarioService.findByEmail(email)
            return ResponseEntity.ok(usuario)
        }

        /**
         * ### 🔑 Endpoint para actualizar los datos de un usuario.
         *
         * @param usuario Objeto Usuario con los datos actualizados.
         * @return ResponseEntity con el usuario actualizado.
         */
        @PutMapping("/update")
        fun updateUsuario(
            @RequestBody usuario: Usuario
        ): ResponseEntity<Usuario?> {
            val usuarioActualizado = usuarioService.updateUser(usuario)
            return ResponseEntity.ok(usuarioActualizado)
        }

        /**
         * ### 🔒 Endpoint para eliminar un usuario por su ID.
         *
         * @param id ID del usuario a eliminar.
         * @return ResponseEntity sin contenido si la eliminación fue exitosa.
         */
        @DeleteMapping("/delete/{id}")
        fun eliminarUsuario(@PathVariable id: String): ResponseEntity<Any> {
            usuarioService.eliminarUsuario(id)
            return ResponseEntity.noContent().build()
        }

        /**
         * ### 🔒 Endpoint para obtener todas las tareas asociadas a los usuarios, se usa para la parte de aministración.
         *
         * @return Lista de objetos UsuarioConTareasDTO con los usuarios y sus tareas.
         */
        @GetMapping("/tareas")
        fun obtenerUsuariosConTareas(): List<UsuarioConTareasDTO> {
            return usuarioService.obtenerUsuariosConTareas()
        }
    }