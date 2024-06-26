package com.teamsparta.spa5reviewassignmentfeed.domain.user.service.v1

import com.teamsparta.spa5reviewassignmentfeed.domain.user.dto.v1.LoginRequestDto
import com.teamsparta.spa5reviewassignmentfeed.domain.user.dto.v1.SignupRequestDto
import com.teamsparta.spa5reviewassignmentfeed.domain.user.model.v1.User
import com.teamsparta.spa5reviewassignmentfeed.domain.user.repository.v1.UserRepository
import com.teamsparta.spa5reviewassignmentfeed.infra.security.JwtTokenProvider
import jakarta.transaction.Transactional
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtTokenProvider: JwtTokenProvider
) {
    @Transactional
    fun signup(signupRequestDto: SignupRequestDto): User {
        if (userRepository.existsByNickname(signupRequestDto.nickname)) {
            throw IllegalArgumentException("중복된 닉네임입니다.")
        }

        if (signupRequestDto.password != signupRequestDto.confirmPassword) {
            throw IllegalArgumentException("비밀번호와 비밀번호 확인이 일치하지 않습니다.")
        }

        if (signupRequestDto.password.contains(signupRequestDto.nickname)) {
            throw IllegalArgumentException("비밀번호에 닉네임이 포함될 수 없습니다.")
        }

        val encodedPassword = passwordEncoder.encode(signupRequestDto.password)
        val user = User(
            nickname = signupRequestDto.nickname,
            password = encodedPassword
        )

        return userRepository.save(user)
    }

    fun isNicknameAvailable(nickname: String): Boolean {
        return !userRepository.existsByNickname(nickname)
    }

    @Transactional
    fun login(loginRequestDto: LoginRequestDto): String {
        val user = userRepository.findByNickname(loginRequestDto.nickname)
            ?: throw IllegalArgumentException("존재하지 않는 사용자입니다.")

        if (!passwordEncoder.matches(loginRequestDto.password, user.password)) {
            throw IllegalArgumentException("비밀번호가 일치하지 않습니다.")
        }

        return jwtTokenProvider.createToken(user.nickname)
    }
}
