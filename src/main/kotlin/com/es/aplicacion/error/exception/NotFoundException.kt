package com.es.aplicacion.error.exception

class NotFoundException(message: String) : Exception("Not Found (400). $message")  {
}