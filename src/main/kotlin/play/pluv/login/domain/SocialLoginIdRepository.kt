package play.pluv.login.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import play.pluv.member.domain.Member
import play.pluv.oauth.domain.OAuthMemberInfo

interface SocialLoginIdRepository : JpaRepository<SocialLoginId, Long> {

    @Query("""
      select s
      from SocialLoginId s
      join fetch s.member
      where s.oauthMemberInfo = :oAuthMemberInfo
      """)
    fun findByOAuthMemberInfo(oAuthMemberInfo: OAuthMemberInfo) : SocialLoginId?

    fun existsByOauthMemberInfo(oAuthMemberInfo: OAuthMemberInfo) : Boolean

    fun findAllByMember(member: Member): List<SocialLoginId>
}