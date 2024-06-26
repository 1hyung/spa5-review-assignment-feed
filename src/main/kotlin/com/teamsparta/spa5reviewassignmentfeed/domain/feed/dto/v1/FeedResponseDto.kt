package com.teamsparta.spa5reviewassignmentfeed.domain.feed.dto.v1

import com.teamsparta.spa5reviewassignmentfeed.domain.comment.dto.v1.CommentResponseDto
import java.time.LocalDateTime

data class FeedResponseDto(
    val id: Long,
    val title: String,
    val content: String,
    val nickname: String,
    val createdDate: LocalDateTime,
    val comments: List<CommentResponseDto> = emptyList()
)
