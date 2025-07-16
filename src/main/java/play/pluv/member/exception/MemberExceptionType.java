package play.pluv.member.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import play.pluv.base.BaseExceptionType;

@Getter
@RequiredArgsConstructor
public enum MemberExceptionType implements BaseExceptionType {

  NICK_NAME_LENGTH_IS_OVER(BAD_REQUEST, "닉네임 길이를 초과하셨습니다."),
  MEMBER_NOT_FOUND(NOT_FOUND, "멤버를 찾을 수 없습니다");

  private final HttpStatus httpStatus;
  private final String message;
}
