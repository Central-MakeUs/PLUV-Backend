package play.pluv.oauth.application;

import play.pluv.playlist.domain.MusicStreaming;
import play.pluv.oauth.domain.OAuthMemberInfo;

public interface SocialLoginClient {

  OAuthMemberInfo fetchMember(final String authKey);

  MusicStreaming supportedType();
}
