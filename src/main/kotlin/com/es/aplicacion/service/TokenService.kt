package com.es.aplicacion.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.jwt.JwtClaimsSet
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.JwtEncoderParameters
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.Instant
import java.util.*

/**
 * # Servicio para la generación de tokens JWT.
 */
@Service
class TokenService {

    @Autowired
    private lateinit var jwtEncoder: JwtEncoder

    /**
     * Genera un token JWT basado en la autenticación proporcionada.
     *
     * @param authentication Objeto de tipo Authentication que contiene la información del usuario autenticado.
     * @return String que representa el token JWT generado.
     */
    fun generarToken(authentication: Authentication): String {
        // Obtiene los roles del usuario autenticado y los convierte en una cadena separada por espacios.
        val roles: String = authentication.authorities.joinToString(" ") { it.authority }

        // Construye el conjunto de claims del token JWT.
        val payload: JwtClaimsSet = JwtClaimsSet.builder()
            .issuer("self") // Emisor del token.
            .issuedAt(Instant.now()) // Fecha y hora de emisión del token.
            .expiresAt(Date().toInstant().plus(Duration.ofHours(1))) // Fecha y hora de expiración del token configurado en 1 hora.
            .subject(authentication.name) // Identificador del sujeto (normalmente el email del usuario).
            .claim("roles", roles) // Agrega los roles del usuario como un claim adicional.
            .build()

        // Codifica el conjunto de claims en un token JWT y lo retorna.
        return jwtEncoder.encode(JwtEncoderParameters.from(payload)).tokenValue
    }
}