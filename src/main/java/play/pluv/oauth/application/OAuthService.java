package play.pluv.oauth.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import play.pluv.oauth.application.dto.GoogleAccessToken;
import play.pluv.oauth.google.GoogleConnector;
import play.pluv.oauth.google.dto.GoogleOAuthResponse;

@Service
@RequiredArgsConstructor
public class OAuthService {

  private final GoogleConnector googleConnector;

  public GoogleAccessToken getAccessToken(final String authCode) {
    final GoogleOAuthResponse tokenResponse = googleConnector.getAccessToken(authCode);
    return new GoogleAccessToken(tokenResponse.accessToken());
  }
}
