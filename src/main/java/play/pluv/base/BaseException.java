package play.pluv.base;

import lombok.Getter;

@Getter
public abstract class BaseException extends RuntimeException {

  private final BaseExceptionType exceptionType;

  public BaseException(final BaseExceptionType exceptionType) {
    super(exceptionType.getMessage());
    this.exceptionType = exceptionType;
  }
}
