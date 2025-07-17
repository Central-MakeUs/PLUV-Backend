package play.pluv.login.application

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import play.pluv.login.domain.TesterLoginValidator
import play.pluv.oauth.application.SocialLoginClientComposite
import play.pluv.playlist.domain.MusicStreaming

@Service
open class LoginService(
    private val registerUpdater: RegisterUpdater,
    private val registerReader: RegisterReader,
    private val socialLoginClientComposite: SocialLoginClientComposite,
    private val testerLoginValidator: TesterLoginValidator
) {

    @Transactional
    open fun registerAndGetMemberId(serverType: MusicStreaming, key: String): Long {
        val memberInfo = socialLoginClientComposite.fetchMemberInfo(serverType, key)
        val member = registerReader.findByOAuthMemberInfo(memberInfo)
            ?: registerUpdater.registerNewMember(oAuthMemberInfo = memberInfo)

        return member.identifier
    }

    @Transactional
    open fun addOtherLoginWay(serverType: MusicStreaming, memberId: Long, key: String) {
        val memberInfo = socialLoginClientComposite.fetchMemberInfo(serverType, key)
        registerUpdater.addOtherLoginSource(memberId, memberInfo)
    }

    @Transactional(readOnly = true)
    open fun getLoginTypes(memberId: Long): List<MusicStreaming> {
        return registerReader.findMemberSocialLoginIds(memberId)
            .map { it.source }
            .toList()
    }

    @Transactional(readOnly = true)
    open fun getTesterId(id: String, password: String): Long {
        val testerId = testerLoginValidator.getTesterId(id, password)
        return registerReader.findById(testerId).identifier
    }
}