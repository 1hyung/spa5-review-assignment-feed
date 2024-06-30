package com.teamsparta.spa5reviewassignmentfeed.domain.user.dto.v1

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

data class SignupRequestDto(
    @field:NotBlank
    @field:Size(min = 3, max = 20)
    @field:Pattern(regexp = "^[a-zA-Z0-9]*$")
    val nickname: String,

    @field:NotBlank
    @field:Size(min = 4, max = 100)
    val password: String,

    @field:NotBlank
    @field:Size(min = 4, max = 100)
    val confirmPassword: String
)
