package play.pluv.base;

public record BaseResponse<T>(
    int code,
    String msg,
    T data
) {

  public static <T> BaseResponse<T> ok(final T data) {
    return new BaseResponse<>(200, "Ok", data);
  }

  public static <T> BaseResponse<T> badRequest(final T data, final String msg) {
    return new BaseResponse<>(400, msg, data);
  }

  public static <T> BaseResponse<T> notFound(final T data, final String msg) {
    return new BaseResponse<>(404, msg, data);
  }
}
