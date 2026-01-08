package zod.common.api.error.exception

import zod.common.api.error.ErrorCode

class ApiException(
    val errorCode: ErrorCode,
    cause: Throwable? = null,
) : RuntimeException(errorCode.message, cause)
