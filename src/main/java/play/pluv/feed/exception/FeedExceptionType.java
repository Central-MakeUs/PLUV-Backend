package play.pluv.feed.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import play.pluv.base.BaseExceptionType;

@Getter
@RequiredArgsConstructor
public enum FeedExceptionType implements BaseExceptionType {

  FEED_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 feed를 찾을 수 없습니다");

  private final HttpStatus httpStatus;
  private final String message;
}
