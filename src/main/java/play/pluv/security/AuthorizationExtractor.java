package play.pluv.security;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static play.pluv.security.exception.SecurityExceptionType.INVALID_ACCESS_TOKEN_TYPE;
import static play.pluv.security.exception.SecurityExceptionType.NOT_FOUND_AUTHORIZATION_TOKEN;

import jakarta.servlet.http.HttpServletRequest;
import play.pluv.login.exception.LoginException;
import play.pluv.security.exception.SecurityException;

public class AuthorizationExtractor {

  private static final String BEARER_TYPE = "Bearer";

  public static String extract(final HttpServletRequest request) {
    final String authorizationHeader = request.getHeader(AUTHORIZATION);

    validateAuthorizationHeader(authorizationHeader);

    return authorizationHeader.substring(BEARER_TYPE.length()).trim();
  }

  private static void validateAuthorizationHeader(final String authorizationHeader) {
    if (authorizationHeader == null || authorizationHeader.isBlank()) {
      throw new SecurityException(NOT_FOUND_AUTHORIZATION_TOKEN);
    }
    if (!authorizationHeader.toLowerCase().startsWith(BEARER_TYPE.toLowerCase())) {
      throw new SecurityException(INVALID_ACCESS_TOKEN_TYPE);
    }
  }
}
