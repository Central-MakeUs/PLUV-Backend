package play.pluv.security

import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpHeaders.AUTHORIZATION
import play.pluv.security.exception.SecurityException
import play.pluv.security.exception.SecurityExceptionType.INVALID_ACCESS_TOKEN_TYPE
import play.pluv.security.exception.SecurityExceptionType.NOT_FOUND_AUTHORIZATION_TOKEN

class AuthorizationExtractor {

    companion object {
        private const val BEARER_TYPE = "Bearer"

        fun extract(request: HttpServletRequest): String {
            val authorizationHeader = request.getHeader(AUTHORIZATION)

            validateAuthorizationHeader(authorizationHeader)

            return authorizationHeader.substring(BEARER_TYPE.length).trim()
        }

        private fun validateAuthorizationHeader(authorizationHeader: String) {
            if (authorizationHeader.isBlank()) {
                throw SecurityException(NOT_FOUND_AUTHORIZATION_TOKEN)
            }
            if (!authorizationHeader.startsWith(BEARER_TYPE)) {
                throw SecurityException(INVALID_ACCESS_TOKEN_TYPE)
            }
        }
    }
}