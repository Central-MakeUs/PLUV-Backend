package play.pluv.base;

import org.springframework.http.HttpStatus;

public record BaseResponse<T>(
    int code,
    String msg,
    T data
) {

  private static final String EXCEPTION_DATA = "";

  public static <T> BaseResponse<T> ok(final T data) {
    return new BaseResponse<>(200, "Ok", data);
  }

  public static BaseResponse<String> created() {
    return new BaseResponse<>(201, "Created", "");
  }

  public static BaseResponse<String> badRequest(final String msg) {
    return new BaseResponse<>(400, msg, EXCEPTION_DATA);
  }

  public static BaseResponse<String> notFound(final String msg) {
    return new BaseResponse<>(404, msg, EXCEPTION_DATA);
  }

  public static BaseResponse<String> serverError(final String msg) {
    return new BaseResponse<>(500, msg, EXCEPTION_DATA);
  }

  public static BaseResponse<String> exception(final BaseExceptionType exceptionType) {
    return new BaseResponse<>(
        exceptionType.getHttpStatus().value(), exceptionType.getMessage(), EXCEPTION_DATA
    );
  }

  public static BaseResponse<String> of(final HttpStatus httpStatus, final String msg) {
    return new BaseResponse<>(httpStatus.value(), msg, EXCEPTION_DATA);
  }
}
