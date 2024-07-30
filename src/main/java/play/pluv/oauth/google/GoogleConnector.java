package play.pluv.oauth.google;

import static play.pluv.music.domain.MusicStreaming.YOUTUBE;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import play.pluv.music.domain.MusicStreaming;
import play.pluv.oauth.application.SocialLoginClient;
import play.pluv.oauth.domain.OAuthMemberInfo;

@Component
@RequiredArgsConstructor
public class GoogleConnector implements SocialLoginClient {

  private final GoogleApiClient googleApiClient;

  @Override
  public OAuthMemberInfo fetchMember(final String idToken) {
    return googleApiClient.verifyIdToken(idToken)
        .toOAuthMemberInfo();
  }

  @Override
  public MusicStreaming supportedType() {
    return YOUTUBE;
  }
}
