package play.pluv.oauth.domain;

import static jakarta.persistence.EnumType.STRING;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Enumerated;
import play.pluv.playlist.domain.MusicStreaming;

@Embeddable
public record OAuthMemberInfo(
    String oauthMemberId,
    @Enumerated(value = STRING)
    MusicStreaming source
) {

}
