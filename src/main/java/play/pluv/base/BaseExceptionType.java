package play.pluv.base;

import org.springframework.http.HttpStatus;

public interface BaseExceptionType {

  String getMessage();

  HttpStatus getHttpStatus();
}
