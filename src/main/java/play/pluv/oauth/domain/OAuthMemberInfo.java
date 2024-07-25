package play.pluv.oauth.domain;

import play.pluv.music.domain.MusicStreaming;

public record OAuthMemberInfo(
    String id,
    MusicStreaming source
) {

}
