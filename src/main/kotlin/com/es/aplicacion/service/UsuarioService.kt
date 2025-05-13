package com.es.aplicacion.service

import com.es.aplicacion.dto.Tareas.TareaDTO
import com.es.aplicacion.dto.Usuario.UsuarioConTareasDTO
import com.es.aplicacion.dto.Usuario.UsuarioDTO
import com.es.aplicacion.dto.Usuario.UsuarioRegisterDTO
import com.es.aplicacion.error.exception.BadRequestException
import com.es.aplicacion.error.exception.NotFoundException
import com.es.aplicacion.model.Usuario
import com.es.aplicacion.repository.TareaRepository
import com.es.aplicacion.repository.UsuarioRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

/**
 * # Servicio para gestionar las operaciones relacionadas con los usuarios.
 */
@Service
class UsuarioService : UserDetailsService {

    @Autowired
    private lateinit var tareaRepository: TareaRepository

    @Autowired
    private lateinit var usuarioRepository: UsuarioRepository

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    /**
     * ### Carga los detalles de un usuario por su email.
     *
     * @param email Email del usuario a buscar.
     * @return UserDetails con la información del usuario.
     * @throws NotFoundException Si el email no existe en la base de datos.
     */
    override fun loadUserByUsername(email: String?): UserDetails {
        val usuario: Usuario = usuarioRepository
            .findByEmail(email!!)
            .orElseThrow { NotFoundException("Email $email no existente") }

        return User.builder()
            .username(usuario.email) // Usar el email como nombre de usuario
            .password(usuario.password)
            .roles(usuario.roles)
            .build()
    }

    /**
     * ### Busca un usuario por su email.
     *
     * @param email Email del usuario a buscar.
     * @return Usuario encontrado.
     * @throws NotFoundException Si el email no existe en la base de datos.
     */
    fun findByEmail(email: String?): Usuario {
        return usuarioRepository
            .findByEmail(email!!)
            .orElseThrow { NotFoundException("Email $email no existente") }
    }

    /**
     * ### Inserta un nuevo usuario en la base de datos.
     *
     * @param usuarioInsertadoDTO Objeto con los datos del usuario a registrar.
     * @return UsuarioDTO con los datos del usuario registrado.
     * @throws BadRequestException Si los datos son inválidos o el usuario ya existe.
     */
    fun insertUser(usuarioInsertadoDTO: UsuarioRegisterDTO): UsuarioDTO? {
        //Validar campos no nulos o vacios
        if (usuarioInsertadoDTO.username.isEmpty() || usuarioInsertadoDTO.email.isEmpty() || usuarioInsertadoDTO.password.isEmpty() || usuarioInsertadoDTO.passwordRepeat.isEmpty()) {
            throw BadRequestException("Se deben rellenar todos los campos")
        }

        // Validar que el usuario no exista
        if (!usuarioRepository.findByUsername(usuarioInsertadoDTO.username).isEmpty) {
            throw BadRequestException("El usuario ya existe")
        }

        // Comprobar que el email no exista ya en la base de datos
        if (usuarioRepository.findByEmail(usuarioInsertadoDTO.email).isPresent) {
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
            roles = usuarioInsertadoDTO.rol ?: "USER",
            hogar = null
        )

        // Guardar el usuario en la base de datos
        val savedUsuario = usuarioRepository.insert(usuario)

        savedUsuario

        // Convertir el objeto Usuario a UsuarioDTO y devolverlo
        return UsuarioDTO(
            username = savedUsuario.username,
            email = savedUsuario.email,
            rol = savedUsuario.roles
        )
    }

    /**
     * ### Actualiza los datos de un usuario existente.
     *
     * @param usuarioActualizado Objeto Usuario con los datos actualizados.
     * @return Usuario actualizado.
     * @throws NotFoundException Si el usuario no existe.
     */
    fun updateUser(usuarioActualizado: Usuario): Usuario {
        val usuario = usuarioRepository.findById(usuarioActualizado._id!!)
            .orElseThrow { NotFoundException("Usuario no encontrado") }

        // Actualizar los campos del usuario
        usuario.username = usuarioActualizado.username
        usuario.email = usuarioActualizado.email
        usuario.roles = usuarioActualizado.roles

        // Guardar el usuario actualizado en la base de datos
        val updatedUsuario = usuarioRepository.save(usuario)

        return updatedUsuario
    }

    /**
     * ### Elimina un usuario por su ID.
     *
     * @param id ID del usuario a eliminar.
     * @throws NotFoundException Si el usuario no existe.
     */
    fun eliminarUsuario(id: String) {
        if (!usuarioRepository.existsById(id)) {
            throw NotFoundException("Usuario no encontrado")
        }
        usuarioRepository.deleteById(id)
    }

    /**
     * ### Obtiene una lista de usuarios con sus tareas asociadas.
     *
     * @return Lista de UsuarioConTareasDTO con los usuarios y sus tareas.
     */
    fun obtenerUsuariosConTareas(): List<UsuarioConTareasDTO> {
        return usuarioRepository.findAll().map { usuario ->
            val tareas = tareaRepository.findByUsuario(usuario).map { tarea ->
                TareaDTO(
                    _id = tarea._id,
                    descripcion = tarea.descripcion,
                    completada = tarea.completada
                )
            }
            UsuarioConTareasDTO(
                _id = usuario._id,
                username = usuario.username,
                email = usuario.email,
                roles = usuario.roles,
                tareas = tareas
            )
        }
    }
}