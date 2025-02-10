package com.es.aplicacion.error.exception

class BadRequestException(message: String) : Exception("Bad Request (400). $message")  {
}