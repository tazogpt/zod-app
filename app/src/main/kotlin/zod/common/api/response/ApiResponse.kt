package zod.common.api.response

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

data class ApiResponse<T>(
    val data: T? = null,
    val code: String = "SUCCESS",
    val message: String = "",
) {
    companion object {
        fun success(): ApiResponse<Nothing> = ApiResponse()
        fun <T> success(data: T): ApiResponse<T> = ApiResponse(data)
        fun error(code: String, message: String): ApiResponse<Nothing> =
            ApiResponse(code = code, message = message)
    }

    fun toResponseEntity(status: HttpStatus = HttpStatus.OK): ResponseEntity<ApiResponse<T>> {
        return ResponseEntity.status(status).body(this)
    }
}
