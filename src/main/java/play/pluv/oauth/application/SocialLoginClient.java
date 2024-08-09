package play.pluv.oauth.application;

import play.pluv.oauth.domain.OAuthMemberInfo;
import play.pluv.playlist.domain.MusicStreaming;

public interface SocialLoginClient {

  OAuthMemberInfo fetchMember(final String authKey);

  MusicStreaming supportedType();
}
