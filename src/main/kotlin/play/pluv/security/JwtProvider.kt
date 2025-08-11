package play.pluv.security

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders.BASE64
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtProvider(
    @param:Value($$"${jwt.secret}") private val encodedSecretKey: String
) {

    companion object {
        private const val MEMBER_ID_IDENTIFIER = "memberId"
        private const val TOKEN_ISSUER = "pluv"
        private const val DURATION_DAY = 90
    }

    fun createAccessTokenWith(memberId: Long): String {
        val now = Date()
        val expires = Date(now.time + DURATION_DAY * 1000)
        val claims = mapOf(MEMBER_ID_IDENTIFIER to memberId)

        return Jwts.builder()
            .issuer(TOKEN_ISSUER)
            .issuedAt(now)
            .expiration(expires)
            .claims(claims)
            .signWith(Keys.hmacShaKeyFor(BASE64.decode(encodedSecretKey)))
            .compact()
    }

    fun parseMemberId(jwt: String): Long {
        val memberId = Jwts.parser()
            .verifyWith(Keys.hmacShaKeyFor(BASE64.decode(encodedSecretKey)))
            .build()
            .parseSignedClaims(jwt)
            .payload[MEMBER_ID_IDENTIFIER] as Number

        return memberId.toLong()
    }
}