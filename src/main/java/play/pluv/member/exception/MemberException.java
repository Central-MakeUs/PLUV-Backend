package play.pluv.member.exception;

import play.pluv.base.BaseException;
import play.pluv.base.BaseExceptionType;

public class MemberException extends BaseException {

  public MemberException(final BaseExceptionType exceptionType) {
    super(exceptionType);
  }
}
