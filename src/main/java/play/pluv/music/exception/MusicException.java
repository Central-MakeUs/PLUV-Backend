package play.pluv.music.exception;

import play.pluv.base.BaseException;

public class MusicException extends BaseException {

  public MusicException(final MusicExceptionType exceptionType) {
    super(exceptionType);
  }
}
