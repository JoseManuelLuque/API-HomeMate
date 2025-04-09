package com.es.aplicacion.error.exception

class EntityNotFoundException(message: String) : Exception("Bad Request (400). $message")  {
}