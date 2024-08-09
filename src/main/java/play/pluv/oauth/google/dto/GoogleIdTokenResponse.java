package play.pluv.oauth.google.dto;

import static play.pluv.playlist.domain.MusicStreaming.YOUTUBE;

import play.pluv.oauth.domain.OAuthMemberInfo;

public record GoogleIdTokenResponse(String sub) {

  public OAuthMemberInfo toOAuthMemberInfo() {
    return new OAuthMemberInfo(sub, YOUTUBE);
  }
}
