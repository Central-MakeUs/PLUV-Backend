package play.pluv.login.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import play.pluv.base.BaseExceptionType;

@Getter
@RequiredArgsConstructor
public enum LoginExceptionType implements BaseExceptionType {

  PLAYLIST_PROVIDER_NOT_FOUND(NOT_FOUND, "지원하지 않는 스트리밍 서비스입니다"),
  INVALID_ACCESS_TOKEN(UNAUTHORIZED, "토큰이 유효하지 않습니다."),
  GENERATE_APPLE_CLIENT_SECRET_ERROR(INTERNAL_SERVER_ERROR, "private key를 만드는데 오류가 발생했습니다."),
  NOT_FOUND_AUTHORIZATION_TOKEN(BAD_REQUEST, "인증 토큰을 찾을 수 없습니다."),
  INVALID_ACCESS_TOKEN_TYPE(BAD_REQUEST, "Access Token Type이 올바르지 않습니다.");

  private final HttpStatus httpStatus;
  private final String message;
}
