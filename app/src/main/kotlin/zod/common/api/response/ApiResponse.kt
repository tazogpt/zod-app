package zod.common.api.response

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

data class ApiResponse<T>(
    val data: T?,
    val code: String = "SUCCESS",
    val message: String = "",
) {
    companion object {
        fun <T> success(data: T? = null): ApiResponse<T> = ApiResponse(data)
        fun error(code: String, message: String): ApiResponse<Nothing> =
            ApiResponse(null, code, message)
    }

    fun toResponseEntity(status: HttpStatus = HttpStatus.OK): ResponseEntity<ApiResponse<T>> {
        return ResponseEntity.status(status).body(this)
    }
}
