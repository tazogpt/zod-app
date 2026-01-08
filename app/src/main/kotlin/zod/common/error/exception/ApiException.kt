package zod.common.error.exception

import zod.common.error.ErrorCode

class ApiException(
    val errorCode: ErrorCode,
    cause: Throwable? = null,
) : RuntimeException(errorCode.message, cause)
