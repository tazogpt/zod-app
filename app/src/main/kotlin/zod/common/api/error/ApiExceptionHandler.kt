package zod.common.api.error

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import zod.common.api.error.exception.ApiException
import zod.common.api.response.ApiResponse

@RestControllerAdvice
class ApiExceptionHandler {
    private val log = LoggerFactory.getLogger(ApiExceptionHandler::class.java)

    @ExceptionHandler(ApiException::class)
    fun handleApiException(ex: ApiException): ResponseEntity<ApiResponse<Nothing>> {
        val errorCode = ex.errorCode
        log.warn("ApiException: {}", errorCode.name, ex)
        return ApiResponse.error(errorCode.name, errorCode.message).toResponseEntity(errorCode.status)
    }

    @ExceptionHandler(RuntimeException::class)
    fun handleRuntimeException(ex: RuntimeException): ResponseEntity<ApiResponse<Nothing>> {
        log.error("RuntimeException", ex)
        return ApiResponse
            .error("INTERNAL_ERROR", ex.message ?: "Internal server error")
            .toResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(Exception::class)
    fun handleException(ex: Exception): ResponseEntity<ApiResponse<Nothing>> {
        log.error("Exception", ex)
        return ApiResponse
            .error("INTERNAL_ERROR", ex.message ?: "Internal server error")
            .toResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
    }
}
