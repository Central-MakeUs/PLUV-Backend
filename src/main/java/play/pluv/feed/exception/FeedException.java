package play.pluv.feed.exception;

import play.pluv.base.BaseException;

public class FeedException extends BaseException {

  public FeedException(final FeedExceptionType feedExceptionType) {
    super(feedExceptionType);
  }
}
