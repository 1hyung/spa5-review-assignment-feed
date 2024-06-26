package com.teamsparta.spa5reviewassignmentfeed.domain.comment.service.v1

import com.teamsparta.spa5reviewassignmentfeed.domain.comment.dto.v1.CommentRequestDto
import com.teamsparta.spa5reviewassignmentfeed.domain.comment.dto.v1.CommentResponseDto
import com.teamsparta.spa5reviewassignmentfeed.domain.comment.model.v1.Comment
import com.teamsparta.spa5reviewassignmentfeed.domain.comment.repository.v1.CommentRepository
import com.teamsparta.spa5reviewassignmentfeed.domain.feed.repository.v1.FeedRepository
import com.teamsparta.spa5reviewassignmentfeed.domain.user.repository.v1.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class CommentService(
    private val commentRepository: CommentRepository,
    private val feedRepository: FeedRepository,
    private val userRepository: UserRepository
) {
    @Transactional
    fun createComment(feedId: Long, nickname: String, commentRequestDto: CommentRequestDto): Comment {
        val user = userRepository.findByNickname(nickname) ?: throw IllegalArgumentException("사용자를 찾을 수 없습니다.")
        val feed = feedRepository.findById(feedId).orElseThrow { IllegalArgumentException("게시글을 찾을 수 없습니다.") }
        val comment = Comment(
            comment = commentRequestDto.comment,
            user = user,
            feed = feed
        )
        return commentRepository.save(comment)
    }

    @Transactional(readOnly = true)
    fun getComments(feedId: Long): List<CommentResponseDto> {
        return commentRepository.findAllByFeedId(feedId).map {
            CommentResponseDto(
                id = it.id,
                comment = it.comment,
                nickname = it.user.nickname,
                createdDate = it.createdDate
            )
        }
    }

    @Transactional
    fun updateComment(commentId: Long, nickname: String, commentRequestDto: CommentRequestDto) {
        val comment = commentRepository.findById(commentId).orElseThrow { IllegalArgumentException("댓글을 찾을 수 없습니다.") }
        if (comment.user.nickname != nickname) {
            throw IllegalArgumentException("작성자만 수정할 수 있습니다.")
        }
        comment.comment = commentRequestDto.comment
        comment.modifiedDate = LocalDateTime.now()
        commentRepository.save(comment)
    }

    @Transactional
    fun deleteComment(commentId: Long, nickname: String) {
        val comment = commentRepository.findById(commentId).orElseThrow { IllegalArgumentException("댓글을 찾을 수 없습니다.") }
        if (comment.user.nickname != nickname) {
            throw IllegalArgumentException("작성자만 삭제할 수 있습니다.")
        }
        commentRepository.delete(comment)
    }
}
