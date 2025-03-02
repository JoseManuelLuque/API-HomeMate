package com.es.aplicacion.model

import org.springframework.data.mongodb.core.mapping.Document

@Document("Direcciones")
data class Direccion(
    val calle: String,
    val numero: Int,
    val municipio: String,
    val provincia: String
)