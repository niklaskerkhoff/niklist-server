package de.nikstack.niklist_server.lib.spring.security

import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.jwt.JwsHeader
import org.springframework.security.oauth2.jwt.JwtClaimsSet
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.JwtEncoderParameters
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.temporal.ChronoUnit


@Service
class TokenService(private val jwtEncoder: JwtEncoder) {
    fun generateToken(authentication: Authentication): String {
        val now = Instant.now()
        val scope: String = authentication.authorities.joinToString(" ")
        val claims = JwtClaimsSet.builder()
            .issuer("self")
            .issuedAt(now)
            .expiresAt(now.plus(365, ChronoUnit.DAYS))
            .subject(authentication.name)
            .claim("scope", scope)
            .build()
        val jwsHeader = JwsHeader.with { "HS256" }.build()
        return jwtEncoder.encode(
            JwtEncoderParameters.from(
                jwsHeader,
                claims
            )
        ).tokenValue
    }
}
