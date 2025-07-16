package play.pluv.security.exception

import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.HttpStatus.UNAUTHORIZED
import play.pluv.base.BaseExceptionType

enum class SecurityExceptionType(
    override val httpStatus: HttpStatus,
    override val message: String
) : BaseExceptionType {

    INVALID_ACCESS_TOKEN(UNAUTHORIZED, "토큰이 유효하지 않습니다."),
    NOT_FOUND_AUTHORIZATION_TOKEN(BAD_REQUEST, "인증 토큰을 찾을 수 없습니다."),
    INVALID_ACCESS_TOKEN_TYPE(BAD_REQUEST, "Access Token Type이 올바르지 않습니다.");
}
