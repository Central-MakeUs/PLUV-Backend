package play.pluv.playlist.exception;

import play.pluv.base.BaseException;
import play.pluv.base.BaseExceptionType;

public class PlayListException extends BaseException {

  public PlayListException(final PlayListExceptionType playListExceptionType) {
    super(playListExceptionType);
  }
}
