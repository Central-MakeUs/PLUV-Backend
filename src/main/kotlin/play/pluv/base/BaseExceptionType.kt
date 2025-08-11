package play.pluv.base

import org.springframework.http.HttpStatus

interface BaseExceptionType {
    val message: String
    val httpStatus: HttpStatus
}
