package com.teamsparta.spa5reviewassignmentfeed.domain.comment.controller.v1

import com.teamsparta.spa5reviewassignmentfeed.domain.comment.dto.v1.CommentRequestDto
import com.teamsparta.spa5reviewassignmentfeed.domain.comment.dto.v1.CommentResponseDto
import com.teamsparta.spa5reviewassignmentfeed.domain.comment.service.v1.CommentService
import com.teamsparta.spa5reviewassignmentfeed.infra.security.JwtTokenProvider
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/comments")
class CommentController(
    private val commentService: CommentService,
    private val jwtTokenProvider: JwtTokenProvider
) {
    @PostMapping("/{feedId}")
    fun createComment(
        @PathVariable feedId: Long,
        @RequestBody @Validated commentRequestDto: CommentRequestDto,
        request: HttpServletRequest
    ): ResponseEntity<String> {
        val token = request.cookies?.find { it.name == "token" }?.value
            ?: return ResponseEntity.status(401).body("토큰이 없습니다.")
        val nickname = jwtTokenProvider.getNicknameFromToken(token)
        commentService.createComment(feedId, nickname, commentRequestDto)
        return ResponseEntity.ok("댓글이 작성되었습니다.")
    }

    @GetMapping("/{feedId}")
    fun getComments(@PathVariable feedId: Long): ResponseEntity<List<CommentResponseDto>> {
        val comments = commentService.getComments(feedId)
        return ResponseEntity.ok(comments)
    }

    @PutMapping("/{commentId}")
    fun updateComment(
        @PathVariable commentId: Long,
        @RequestBody @Validated commentRequestDto: CommentRequestDto,
        request: HttpServletRequest
    ): ResponseEntity<String> {
        val token = request.cookies?.find { it.name == "token" }?.value
            ?: return ResponseEntity.status(401).body("토큰이 없습니다.")
        val nickname = jwtTokenProvider.getNicknameFromToken(token)
        commentService.updateComment(commentId, nickname, commentRequestDto)
        return ResponseEntity.ok("댓글이 수정되었습니다.")
    }

    @DeleteMapping("/{commentId}")
    fun deleteComment(
        @PathVariable commentId: Long,
        request: HttpServletRequest
    ): ResponseEntity<String> {
        val token = request.cookies?.find { it.name == "token" }?.value
            ?: return ResponseEntity.status(401).body("토큰이 없습니다.")
        val nickname = jwtTokenProvider.getNicknameFromToken(token)
        commentService.deleteComment(commentId, nickname)
        return ResponseEntity.ok("댓글이 삭제되었습니다.")
    }
}
