package play.pluv.oauth.exception;

import play.pluv.base.BaseException;

public class OAuthException extends BaseException {

  public OAuthException(final OAuthExceptionType exceptionType) {
    super(exceptionType);
  }
}
