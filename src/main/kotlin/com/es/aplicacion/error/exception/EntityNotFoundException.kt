package com.es.aplicacion.error.exception

class EntityNotFoundException(message: String) : Exception("Entity not Found (400). $message")  {
}