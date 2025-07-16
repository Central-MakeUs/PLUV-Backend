package play.pluv.base

import org.springframework.http.HttpStatus

interface BaseExceptionType {
    fun message(): String
    fun httpStatus(): HttpStatus
}
