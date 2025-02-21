package com.es.aplicacion.error.exception

class UnauthorizedException(message: String) : Exception("Not authorized (401). $message") {
}