package play.pluv.oauth.domain;

import static jakarta.persistence.EnumType.STRING;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Enumerated;
import play.pluv.playlist.domain.MusicStreaming;

@Embeddable
public record OAuthMemberInfo(
    @Column(nullable = false)
    String oauthMemberId,

    @Enumerated(value = STRING)
    @Column(nullable = false)
    MusicStreaming source
) {

    public static OAuthMemberInfo blankMemberInfo(){
        return new OAuthMemberInfo("", MusicStreaming.APPLE);
    }
}
