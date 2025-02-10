package com.es.mongocomp.domain

data class Provincia(
    val CCOM: String,
    val CPRO: String,
    val PRO: String
)

/*
¿Por qué estos datos?
Si vamos a la documentación oficial de la API: https://apiv1.geoapi.es/docs/api/metodos.html
e investigamos lo que devuelve la petición al método de obtener todas las provincias,
veremos que cada provincia se compone de tres campos:
CCOM : Código Comunidad Autónoma
CPRO: Código provincia
PRO: Nombre provincia

Para que se mapeen los campos correctamente tenemos que nombrar a nuestros atributos de
igual manera que como vienen en la petición.
 */
