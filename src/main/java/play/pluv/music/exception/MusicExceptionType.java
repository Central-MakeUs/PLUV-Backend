package play.pluv.music.exception;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import play.pluv.base.BaseExceptionType;

public enum MusicExceptionType implements BaseExceptionType {

  MUSIC_STREAMING_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 음원 서비스를 찾을 수 없습니다.");

  private final HttpStatus httpStatus;
  private final String message;

  MusicExceptionType(final HttpStatus httpStatus, final String message) {
    this.httpStatus = httpStatus;
    this.message = message;
  }

  @NotNull
  @Override
  public String message() {
    return message;
  }

  @NotNull
  @Override
  public HttpStatus httpStatus() {
    return httpStatus;
  }
}
