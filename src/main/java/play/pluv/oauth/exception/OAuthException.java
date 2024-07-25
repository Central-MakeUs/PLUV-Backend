package play.pluv.oauth.exception;

import play.pluv.base.BaseException;
import play.pluv.base.BaseExceptionType;

public class OAuthException extends BaseException {

  public OAuthException(final OAuthExceptionType exceptionType) {
    super(exceptionType);
  }
}
