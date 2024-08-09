package play.pluv.login.exception;

import play.pluv.base.BaseException;

public class LoginException extends BaseException {

  public LoginException(final LoginExceptionType exceptionType) {
    super(exceptionType);
  }
}
