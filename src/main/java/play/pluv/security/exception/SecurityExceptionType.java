package play.pluv.security.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import play.pluv.base.BaseExceptionType;

@Getter
@RequiredArgsConstructor
public enum SecurityExceptionType implements BaseExceptionType {

  INVALID_ACCESS_TOKEN(UNAUTHORIZED, "토큰이 유효하지 않습니다."),
  NOT_FOUND_AUTHORIZATION_TOKEN(BAD_REQUEST, "인증 토큰을 찾을 수 없습니다."),
  INVALID_ACCESS_TOKEN_TYPE(BAD_REQUEST, "Access Token Type이 올바르지 않습니다.");

  private final HttpStatus httpStatus;
  private final String message;
}
