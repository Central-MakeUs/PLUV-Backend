package play.pluv.login.application

import jakarta.transaction.Transactional
import org.springframework.stereotype.Component
import play.pluv.login.domain.SocialLoginId
import play.pluv.login.domain.SocialLoginIdRepository
import play.pluv.member.application.MemberReader
import play.pluv.member.application.MemberUpdater
import play.pluv.member.application.NickNameGenerator
import play.pluv.member.domain.Member
import play.pluv.oauth.domain.OAuthMemberInfo

@Component
@Transactional
open class RegisterUpdater(
    private val socialLoginIdRepository: SocialLoginIdRepository,
    private val nickNameGenerator: NickNameGenerator,
    private val memberReader: MemberReader,
    private val memberUpdater: MemberUpdater,
) {

    open fun registerNewMember(oAuthMemberInfo: OAuthMemberInfo): Member {
        val nickName = nickNameGenerator.generateNickName()
        val member = memberUpdater.register(Member(nickName))

        val socialLoginId = SocialLoginId(
            member,
            oAuthMemberInfo,
        )

        socialLoginIdRepository.save(socialLoginId)

        return member
    }

    open fun addOtherLoginSource(memberId:Long, oAuthMemberInfo: OAuthMemberInfo) {
        if(socialLoginIdRepository.existsById(memberId)) {
            return
        }
        val member = memberReader.readById(memberId)

        val socialLoginId = SocialLoginId(
            member,
            oAuthMemberInfo,
        )

        socialLoginIdRepository.save(socialLoginId)
    }
}