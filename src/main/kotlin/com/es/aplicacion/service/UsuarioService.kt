package com.es.aplicacion.service

import com.es.aplicacion.dto.UsuarioDTO
import com.es.aplicacion.dto.UsuarioRegisterDTO
import com.es.aplicacion.error.exception.UnauthorizedException
import com.es.aplicacion.model.Usuario
import com.es.aplicacion.repository.UsuarioRepository
import org.apache.coyote.BadRequestException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UsuarioService : UserDetailsService {

    @Autowired
    private lateinit var usuarioRepository: UsuarioRepository

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder


    override fun loadUserByUsername(username: String?): UserDetails {
        var usuario: Usuario = usuarioRepository
            .findByUsername(username!!)
            .orElseThrow {
                UnauthorizedException("$username no existente")
            }

        return User.builder()
            .username(usuario.username)
            .password(usuario.password)
            .roles(usuario.roles)
            .build()
    }

    fun insertUser(usuarioInsertadoDTO: UsuarioRegisterDTO): UsuarioDTO? {

        // Validar que el usuario no exista
        if (!usuarioRepository.findByUsername(usuarioInsertadoDTO.username).isEmpty) {
            throw BadRequestException("El usuario ya existe")
        }

        // Comprobar que el email no exista ya en la base de datos
        if (usuarioRepository.findByEmail(usuarioInsertadoDTO.email).toString() == usuarioInsertadoDTO.email) {
            throw BadRequestException("El email ya existe")
        }

        // Validar que las contraseñas coincidan
        if (usuarioInsertadoDTO.password != usuarioInsertadoDTO.passwordRepeat) {
            throw BadRequestException("Las contraseñas no coinciden")
        }


        // Codificar la contraseña
        val encodedPassword = passwordEncoder.encode(usuarioInsertadoDTO.password)

        // Crear el objeto Usuario
        val usuario = Usuario(
            _id = null,
            username = usuarioInsertadoDTO.username,
            password = encodedPassword,
            email = usuarioInsertadoDTO.email,
            roles = usuarioInsertadoDTO.rol ?: "USER"
        )

        // Guardar el usuario en la base de datos
        val savedUsuario = usuarioRepository.insert(usuario)

        savedUsuario

        // Convertir el objeto Usuario a UsuarioDTO
        return UsuarioDTO(
            username = savedUsuario.username,
            email = savedUsuario.email,
            rol = savedUsuario.roles
        )
    }
}