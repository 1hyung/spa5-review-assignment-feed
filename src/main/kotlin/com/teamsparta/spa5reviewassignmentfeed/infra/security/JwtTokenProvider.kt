package com.teamsparta.spa5reviewassignmentfeed.infra.security

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Component
import java.util.Date

@Component
class JwtTokenProvider {
    private val secretKey = "mySecretKey"
    private val validityInMilliseconds: Long = 3600000 // 1h

    fun createToken(nickname: String): String {
        val claims = Jwts.claims().setSubject(nickname)
        val now = Date()
        val validity = Date(now.time + validityInMilliseconds)

        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(validity)
            .signWith(SignatureAlgorithm.HS256, secretKey.toByteArray()) // HS256 알고리즘을 사용하여 토큰 서명
            .compact()
    }

    fun getNicknameFromToken(token: String): String {
        return Jwts.parserBuilder()
            .setSigningKey(secretKey.toByteArray())
            .build()
            .parseClaimsJws(token)
            .body
            .subject
    }
}
