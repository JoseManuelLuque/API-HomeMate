package com.es.aplicacion.error.exception

class ForbiddenException(message: String) : Exception("Forbidden (403). $message")  {
}