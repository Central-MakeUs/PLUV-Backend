package play.pluv.base;

public class BaseException extends RuntimeException {

  private final BaseExceptionType baseExceptionType;

  public BaseException(final BaseExceptionType baseExceptionType) {
    super(baseExceptionType.getMessage());
    this.baseExceptionType = baseExceptionType;
  }
}
