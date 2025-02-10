package com.es.aplicacion.domain

import com.es.mongocomp.domain.Provincia

data class DatosProvincias(
    val update_date: String,
    val size: Int,
    val data: List<Provincia>?,
    val warning: String?,
    val error: String?
)
/*
¿Por qué estos datos?
Si vamos a la documentación de la API: https://apiv1.geoapi.es/docs/api/respuesta.html
veremos que nos dice que las respuestas siempre van a tener los campos que ponemos en esta data class

**Datos**
Una respuesta normal de la API tiene el siguiente formato:
  //
  {
          "update_date": "2015.06",
          "size": 42,
          "data": [
                  {}, {}, {}, {}...
          ]
  }

Es decir, un objeto con las siguientes claves:
    update_date - Especifica la fecha (año y mes) en la cual los datos devueltos fueron actualizados por última vez.
    size - Contiene el número de elementos contenidos en data.
    data - Es un array de objetos. Dependiendo de la petición enviada, cada objeto tendrá unos datos u otros.

**Avisos**
La API puede embeber una clave en la respuesta, llamada warning, que contiene una cadena de texto con cualquier aviso que la API decida que debe ser devuelto, como por ejemplo que el modo sandbox está activado.

**Errores**
La API puede embeber una clave en la respuesta, llamada error, y contiene una cadena de texto con cualquier error que la API decida que debe ser devuelto, como por ejemplo que no quedan peticiones en la API Key que se está usando.

 */