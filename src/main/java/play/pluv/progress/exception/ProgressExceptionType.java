package play.pluv.progress.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import play.pluv.base.BaseExceptionType;

@Getter
@RequiredArgsConstructor
public enum ProgressExceptionType implements BaseExceptionType {

  NOT_FINISHED_TRANSFER_PROGRESS(BAD_REQUEST, "진행중인 이전 작업이 있습니다.");

  private final HttpStatus httpStatus;
  private final String message;
}
