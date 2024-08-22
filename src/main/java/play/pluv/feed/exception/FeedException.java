package play.pluv.feed.exception;

import play.pluv.base.BaseException;
import play.pluv.base.BaseExceptionType;

public class FeedException extends BaseException {

  public FeedException(final FeedExceptionType feedExceptionType) {
    super(feedExceptionType);
  }
}
