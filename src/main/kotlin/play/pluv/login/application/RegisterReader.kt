package play.pluv.login.application

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import play.pluv.login.domain.SocialLoginId
import play.pluv.login.domain.SocialLoginIdRepository
import play.pluv.member.application.MemberReader
import play.pluv.member.domain.Member
import play.pluv.oauth.domain.OAuthMemberInfo

@Component
@Transactional(readOnly = true)
open class RegisterReader(
    private val socialLoginIdRepository: SocialLoginIdRepository,
    private val memberReader: MemberReader,
) {

    open fun findByOAuthMemberInfo(memberInfo: OAuthMemberInfo): Member? {
        return socialLoginIdRepository.findByOAuthMemberInfo(memberInfo)?.member
    }

    open fun findMemberSocialLoginIds(memberId : Long) : List<SocialLoginId> {
        val member = memberReader.readById(memberId)
        return socialLoginIdRepository.findAllByMember(member)
    }

    open fun findById(memberId: Long): Member {
        return memberReader.readById(memberId)
    }
}