package play.pluv.history.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import play.pluv.base.BaseExceptionType;

@Getter
@RequiredArgsConstructor
public enum HistoryExceptionType implements BaseExceptionType {

  HISTORY_NOT_FOUND(NOT_FOUND, "요청한 이전 내역을 찾을 수 없습니다.");

  private final HttpStatus httpStatus;
  private final String message;
}
