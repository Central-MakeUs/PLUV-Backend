package play.pluv.oauth.application;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;
import static play.pluv.oauth.exception.OAuthExceptionType.SOCIAL_LOGIN_CLIENT_NOT_FOUND;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.springframework.stereotype.Component;
import play.pluv.playlist.domain.MusicStreaming;
import play.pluv.oauth.domain.OAuthMemberInfo;
import play.pluv.oauth.exception.OAuthException;

@Component
public class SocialLoginClientComposite {

  private final Map<MusicStreaming, SocialLoginClient> socialLoginClientMap;

  public SocialLoginClientComposite(final Set<SocialLoginClient> socialLoginClients) {
    this.socialLoginClientMap = socialLoginClients.stream()
        .collect(toMap(SocialLoginClient::supportedType, identity()));
  }

  public OAuthMemberInfo fetchMemberInfo(final MusicStreaming serverType, final String accessToken) {
    return getClient(serverType).fetchMember(accessToken);
  }

  private SocialLoginClient getClient(final MusicStreaming serverType) {
    return Optional.ofNullable(socialLoginClientMap.get(serverType))
        .orElseThrow(() -> new OAuthException(SOCIAL_LOGIN_CLIENT_NOT_FOUND));
  }
}
