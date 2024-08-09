package play.pluv.playlist.exception;

import play.pluv.base.BaseException;

public class PlayListException extends BaseException {

  public PlayListException(final PlayListExceptionType playListExceptionType) {
    super(playListExceptionType);
  }
}
