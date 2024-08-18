package play.pluv.playlist.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import play.pluv.base.BaseExceptionType;

@RequiredArgsConstructor
@Getter
public enum PlayListExceptionType implements BaseExceptionType {

  PLAYLIST_PROVIDER_NOT_FOUND(NOT_FOUND, "지원하지 않는 스트리밍 서비스입니다");

  private final HttpStatus httpStatus;
  private final String message;
}
