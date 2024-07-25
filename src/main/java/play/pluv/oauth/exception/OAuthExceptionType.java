package play.pluv.oauth.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import play.pluv.base.BaseExceptionType;

@Getter
@RequiredArgsConstructor
public enum OAuthExceptionType implements BaseExceptionType {

  SOCIAL_LOGIN_CLIENT_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 소셜로그인 방식을 찾을 수 없습니다.");

  private final HttpStatus httpStatus;
  private final String message;
}
