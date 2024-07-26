package play.pluv.oauth.application;

import play.pluv.music.domain.MusicStreaming;
import play.pluv.oauth.domain.OAuthMemberInfo;

public interface SocialLoginClient {

  OAuthMemberInfo fetchMember(final String authCode);

  MusicStreaming supportedType();
}
