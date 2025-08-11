package play.pluv.login.domain

import org.apache.commons.codec.digest.DigestUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import play.pluv.login.exception.LoginException
import play.pluv.login.exception.LoginExceptionType.INVALID_TESTER_ID_PASSWORD

@Component
class TesterLoginValidator(
    @param:Value("\${tester.secret}") private val validSecret: String,
    @param:Value("\${tester.id}") private val testerId: Long
) {
    fun getTesterId(id: String, password: String): Long {
        val validSecret = DigestUtils.sha256Hex(id + password)
        if (this.validSecret != validSecret) {
            throw LoginException(exceptionType = INVALID_TESTER_ID_PASSWORD)
        }
        return testerId
    }
}
