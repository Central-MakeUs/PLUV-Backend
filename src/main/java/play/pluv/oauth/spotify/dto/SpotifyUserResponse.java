package play.pluv.oauth.spotify.dto;

import static play.pluv.music.domain.MusicStreaming.SPOTIFY;

import play.pluv.oauth.domain.OAuthMemberInfo;

public record SpotifyUserResponse(String id) {

  public OAuthMemberInfo toOAuthMemberInfo() {
    return new OAuthMemberInfo(id, SPOTIFY);
  }
}
