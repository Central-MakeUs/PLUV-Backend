package play.pluv.login.domain

import jakarta.persistence.*
import play.pluv.member.domain.Member
import play.pluv.oauth.domain.OAuthMemberInfo
import play.pluv.playlist.domain.MusicStreaming

@Entity
class SocialLoginId(
    member: Member,
    oauthMemberInfo: OAuthMemberInfo
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
        private set

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.PERSIST])
    @JoinColumn(name = "member_id", nullable = false)
    var member: Member = member
        private set

    @Embedded
    var oauthMemberInfo: OAuthMemberInfo = oauthMemberInfo
        private set

    val source: MusicStreaming
        get() = oauthMemberInfo.source
}
