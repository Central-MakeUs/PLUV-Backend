package play.pluv.member.exception;

import play.pluv.base.BaseException;

public class MemberException extends BaseException {

  public MemberException(final MemberExceptionType exceptionType) {
    super(exceptionType);
  }
}
