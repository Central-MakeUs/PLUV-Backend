package play.pluv;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import java.net.BindException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import play.pluv.base.BaseException;
import play.pluv.base.BaseExceptionType;
import play.pluv.base.BaseResponse;

@RestControllerAdvice
@Slf4j
public class ControllerAdvice {

  @ExceptionHandler(BaseException.class)
  public ResponseEntity<BaseResponse<String>> handleCustomException(final BaseException exception) {
    final BaseExceptionType type = exception.getExceptionType();
    loggingClientException(exception, type.getMessage());
    return new ResponseEntity<>(BaseResponse.exception(type), type.getHttpStatus());
  }

  @ExceptionHandler({HttpMessageNotReadableException.class, MethodArgumentNotValidException.class})
  public ResponseEntity<BaseResponse<String>> handleHttpMessageNotReadableException(
      final Exception e
  ) {
    final String message = "입력 형식이 올바르지 않습니다.";
    loggingClientException(e, message);
    return new ResponseEntity<>(BaseResponse.badRequest(message), BAD_REQUEST);
  }

  @ExceptionHandler(BindException.class)
  public ResponseEntity<BaseResponse<String>> handleBindExceptionHandler(final Exception e) {
    final String message = "요청 파라미터가 올바르지 않습니다.";
    loggingClientException(e, message);
    return new ResponseEntity<>(BaseResponse.badRequest(message), BAD_REQUEST);
  }

  @ExceptionHandler(MissingServletRequestParameterException.class)
  public ResponseEntity<BaseResponse<String>> handleMethodParameter(final Exception e) {
    final String message = "request parameter가 적절하지 않습니다.";
    loggingClientException(e, message);
    return new ResponseEntity<>(BaseResponse.badRequest(message), BAD_REQUEST);
  }

  @ExceptionHandler(NoResourceFoundException.class)
  public ResponseEntity<Void> handleNoResourceFoundException() {
    return ResponseEntity.notFound().build();
  }

  private void loggingClientException(final Exception e, final String message) {
    log.warn("[WARN] MESSAGE: {}", message);
    log.debug("stackTrace : ", e);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<BaseResponse<String>> handleUnExpectedException(final Exception exception) {
    log.error("[ERROR] MESSAGE : ", exception);
    final var response = BaseResponse.serverError(
        "잠시 후 다시 시도해주시고 동일한 에러가 반복되는 경우 문의 부탁드립니다.");
    return ResponseEntity.internalServerError().body(response);
  }
}
