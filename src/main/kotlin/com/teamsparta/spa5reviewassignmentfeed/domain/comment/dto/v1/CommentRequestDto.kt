package com.teamsparta.spa5reviewassignmentfeed.domain.comment.dto.v1

import jakarta.validation.constraints.NotBlank

data class CommentRequestDto(
    @field:NotBlank(message = "댓글 내용은 필수입니다.")
    val comment: String
)