package com.es.aplicacion.repository

import com.es.aplicacion.model.Usuario
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.Optional
/**
 * # Repositorio para gestionar las operaciones relacionadas con los usuarios en la base de datos.
 *
 * @property MongoRepository Proporciona métodos CRUD y de consulta para la entidad Usuario.
 */
@Repository
interface UsuarioRepository : MongoRepository<Usuario, String> {

    /**
     * # Busca un usuario por su nombre de usuario.
     *
     * @param username Nombre de usuario a buscar.
     * @return Un Optional que contiene el usuario si se encuentra, o vacío si no.
     */
    fun findByUsername(username: String) : Optional<Usuario>

    /**
     * # Busca un usuario por su email.
     *
     * @param email Email del usuario a buscar.
     * @return Un Optional que contiene el usuario si se encuentra, o vacío si no.
     */
    fun findByEmail(email: String) : Optional<Usuario>
}