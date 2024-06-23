package com.teamsparta.spa5reviewassignmentfeed.domain.user.dto.v1

import jakarta.validation.constraints.NotBlank

data class LoginRequestDto(
    @field:NotBlank
    val nickname: String,

    @field:NotBlank
    val password: String
)
