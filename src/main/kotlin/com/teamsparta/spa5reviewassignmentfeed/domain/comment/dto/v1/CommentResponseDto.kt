package com.teamsparta.spa5reviewassignmentfeed.domain.comment.dto.v1

import java.time.LocalDateTime

data class CommentResponseDto(
    val id: Long,
    val comment: String,
    val nickname: String,
    val createdDate: LocalDateTime,
    val thumbUp: Int
)
