package play.pluv.progress.exception;

import play.pluv.base.BaseException;

public class ProgressException extends BaseException {

  public ProgressException(final ProgressExceptionType progressExceptionType) {
    super(progressExceptionType);
  }
}
