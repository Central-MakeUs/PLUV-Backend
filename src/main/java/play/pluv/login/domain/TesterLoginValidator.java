package play.pluv.login.domain;

import static org.apache.commons.codec.digest.DigestUtils.sha256Hex;
import static play.pluv.login.exception.LoginExceptionType.INVALID_TESTER_ID_PASSWORD;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import play.pluv.login.exception.LoginException;

@Component
public class TesterLoginValidator {

  private final String validSecret;
  private final Long testerId;

  public TesterLoginValidator(
      @Value("${tester.secret}") final String validSecret,
      @Value("${tester.id}") final Long testerId
  ) {
    this.validSecret = validSecret;
    this.testerId = testerId;
  }

  public Long getTesterId(final String id, final String password) {
    final String validSecret = sha256Hex(id + password);
    if (!this.validSecret.equals(validSecret)) {
      throw new LoginException(INVALID_TESTER_ID_PASSWORD);
    }
    return testerId;
  }
}
