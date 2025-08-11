package play.pluv.base

abstract class BaseException(
    val exceptionType: BaseExceptionType
) : RuntimeException(
    exceptionType.message
)
