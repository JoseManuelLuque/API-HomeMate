package com.es.aplicacion.repository

import com.es.aplicacion.model.Hogar
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface HogarRepository : MongoRepository<Hogar, String> {
    fun findByCodigo(codigo: String): Hogar?
}