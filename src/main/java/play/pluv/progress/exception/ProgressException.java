package play.pluv.progress.exception;

import play.pluv.base.BaseException;
import play.pluv.base.BaseExceptionType;

public class ProgressException extends BaseException {

  public ProgressException(final ProgressExceptionType progressExceptionType) {
    super(progressExceptionType);
  }
}
