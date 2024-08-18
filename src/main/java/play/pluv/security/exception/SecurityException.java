package play.pluv.security.exception;

import play.pluv.base.BaseException;

public class SecurityException extends BaseException {

  public SecurityException(final SecurityExceptionType exceptionType) {
    super(exceptionType);
  }
}
