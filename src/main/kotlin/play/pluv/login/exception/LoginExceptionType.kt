package play.pluv.login.exception

import org.springframework.http.HttpStatus
import play.pluv.base.BaseExceptionType

enum class LoginExceptionType(
    override val httpStatus: HttpStatus,
    override val message: String
) : BaseExceptionType {

    PLAYLIST_PROVIDER_NOT_FOUND(HttpStatus.NOT_FOUND, "지원하지 않는 스트리밍 서비스입니다"),
    INVALID_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "토큰이 유효하지 않습니다."),
    INVALID_TESTER_ID_PASSWORD(HttpStatus.UNAUTHORIZED, "유효한 아이디와 비밀번호가 아닙니다."),
    NOT_FOUND_AUTHORIZATION_TOKEN(HttpStatus.BAD_REQUEST, "인증 토큰을 찾을 수 없습니다."),
    INVALID_ACCESS_TOKEN_TYPE(HttpStatus.BAD_REQUEST, "Access Token Type이 올바르지 않습니다.");
}
