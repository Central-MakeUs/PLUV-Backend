package play.pluv.oauth.exception;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import play.pluv.base.BaseExceptionType;

@Getter
@RequiredArgsConstructor
public enum OAuthExceptionType implements BaseExceptionType {

  GENERATE_APPLE_CLIENT_SECRET_ERROR(INTERNAL_SERVER_ERROR, "private key를 만드는데 오류가 발생했습니다."),
  SOCIAL_LOGIN_CLIENT_NOT_FOUND(NOT_FOUND, "해당하는 소셜로그인 방식을 찾을 수 없습니다.");

  private final HttpStatus httpStatus;
  private final String message;
}
