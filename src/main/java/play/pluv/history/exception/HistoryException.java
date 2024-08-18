package play.pluv.history.exception;

import play.pluv.base.BaseException;

public class HistoryException extends BaseException {

  public HistoryException(final HistoryExceptionType exceptionType) {
    super(exceptionType);
  }
}
