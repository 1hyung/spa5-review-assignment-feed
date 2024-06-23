package com.teamsparta.spa5reviewassignmentfeed.domain.user.controller.v1

import com.teamsparta.spa5reviewassignmentfeed.domain.user.dto.v1.LoginRequestDto
import com.teamsparta.spa5reviewassignmentfeed.domain.user.dto.v1.SignupRequestDto
import com.teamsparta.spa5reviewassignmentfeed.domain.user.service.v1.UserService
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class UserController(
    private val userService: UserService
) {

    @PostMapping("/signup")
    fun signup(@RequestBody @Validated signupRequestDto: SignupRequestDto): ResponseEntity<String> {
        userService.signup(signupRequestDto)
        return ResponseEntity.ok("회원가입이 완료되었습니다.")
    }

    @GetMapping("/signup/check-nickname")
    fun checkNickname(@RequestParam nickname: String): ResponseEntity<String> {
        if (userService.isNicknameAvailable(nickname)) {
            return ResponseEntity.ok("사용 가능한 닉네임입니다.")
        }
        return ResponseEntity.badRequest().body("중복된 닉네임입니다.")
    }

    @PostMapping("/login")
    fun login(
        @RequestBody @Validated loginRequestDto: LoginRequestDto,
        response: HttpServletResponse
    ): ResponseEntity<String> {
        val token = userService.login(loginRequestDto) // 로그인 로직을 실행하여 JWT 토큰 생성
        val cookie = Cookie("token", token) // 토큰을 쿠키에 담아서 응답
        cookie.isHttpOnly = true
        cookie.path = "/"
        response.addCookie(cookie)
        return ResponseEntity.ok("로그인에 성공했습니다.")
    }
}