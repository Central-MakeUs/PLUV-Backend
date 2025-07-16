package play.pluv.base

import org.springframework.http.HttpStatus

@JvmRecord
data class BaseResponse<T>(
    val code: Int,
    val msg: String,
    val data: T
) {
    companion object {
        private const val EXCEPTION_DATA = ""

        @JvmStatic
        fun <T> ok(data: T): BaseResponse<T> {
            return BaseResponse(200, "Ok", data)
        }

        @JvmStatic
        fun created(): BaseResponse<String> {
            return BaseResponse(201, "Created", "")
        }

        @JvmStatic
        fun badRequest(msg: String): BaseResponse<String> {
            return BaseResponse(400, msg, EXCEPTION_DATA)
        }

        fun notFound(msg: String): BaseResponse<String> {
            return BaseResponse(404, msg, EXCEPTION_DATA)
        }

        @JvmStatic
        fun serverError(msg: String): BaseResponse<String> {
            return BaseResponse(500, msg, EXCEPTION_DATA)
        }

        @JvmStatic
        fun exception(exceptionType: BaseExceptionType): BaseResponse<String> {
            return BaseResponse(
                exceptionType.httpStatus.value(), exceptionType.message, EXCEPTION_DATA
            )
        }

        fun of(httpStatus: HttpStatus, msg: String): BaseResponse<String> {
            return BaseResponse(httpStatus.value(), msg, EXCEPTION_DATA)
        }
    }
}
