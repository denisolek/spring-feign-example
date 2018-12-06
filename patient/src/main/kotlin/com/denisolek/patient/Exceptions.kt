package com.denisolek.patient

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

class PatientNotFound : ServiceException(HttpStatus.NOT_FOUND, "Patient not found")
class PatientAlreadyExists : ServiceException(HttpStatus.CONFLICT, "Patient already exists")

data class ResponseTemplate(var code: String, var message: String)

open class ServiceException(
    var httpStatus: HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR,
    var body: ResponseTemplate

) : RuntimeException(body.message) {
    constructor(httpStatus: HttpStatus, body: String) : this(httpStatus, ResponseTemplate(httpStatus.toString(), body))
}

@ControllerAdvice
class ExceptionHandlerAdvice {
    var ex: Exception? = null
    private val log = LoggerFactory.getLogger(ExceptionHandlerAdvice::class.java)

    @ExceptionHandler(ServiceException::class)
    fun handleException(e: ServiceException): ResponseEntity<*> {
        ex = e
        log.error("$e.stackTrace $e.cause?.message")
        return ResponseEntity.status(e.httpStatus).body(e.body)
    }
}