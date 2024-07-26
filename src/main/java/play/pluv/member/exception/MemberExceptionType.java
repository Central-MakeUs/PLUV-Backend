package play.pluv.member.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import play.pluv.base.BaseExceptionType;

@Getter
@RequiredArgsConstructor
public enum MemberExceptionType implements BaseExceptionType {

  NICK_NAME_LENGTH_IS_OVER(HttpStatus.BAD_REQUEST, "닉네임 길이를 초과하셨습니다.");

  private final HttpStatus httpStatus;
  private final String message;
}
