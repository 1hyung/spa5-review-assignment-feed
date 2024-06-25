package com.teamsparta.spa5reviewassignmentfeed.domain.feed.controller.v1

import com.teamsparta.spa5reviewassignmentfeed.domain.feed.dto.v1.FeedRequestDto
import com.teamsparta.spa5reviewassignmentfeed.domain.feed.dto.v1.FeedResponseDto
import com.teamsparta.spa5reviewassignmentfeed.domain.feed.service.v1.FeedService
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
@RequestMapping("/api/v1/feeds")
class FeedController(
    private val feedService: FeedService,
    private val jwtTokenProvider: JwtTokenProvider
) {
    @GetMapping
    fun getFeeds(): ResponseEntity<List<FeedResponseDto>> {
        return ResponseEntity.ok(feedService.getFeeds())
    }

    @PostMapping
    fun createFeed(
        @RequestBody @Validated feedRequestDto: FeedRequestDto,
        request: HttpServletRequest
    ): ResponseEntity<String> {
        val token = request.cookies?.find { it.name == "token" }?.value
            ?: throw IllegalArgumentException("토큰이 없습니다.")
        val nickname = jwtTokenProvider.getNicknameFromToken(token)
        feedService.createFeed(feedRequestDto, nickname)
        return ResponseEntity.ok("게시글이 작성되었습니다.")
    }

    @GetMapping("/{id}")
    fun getFeed(@PathVariable id: Long): ResponseEntity<FeedResponseDto> {
        val feed = feedService.getFeed(id)
        return ResponseEntity.ok(feed)
    }

    @PutMapping("/{id}")
    fun updateFeed(
        @PathVariable id: Long,
        @RequestBody @Validated feedRequestDto: FeedRequestDto,
        request: HttpServletRequest
    ): ResponseEntity<String> {
        val token = request.cookies?.find { it.name == "token" }?.value
            ?: throw IllegalArgumentException("토큰이 없습니다.")
        val nickname = jwtTokenProvider.getNicknameFromToken(token)
        feedService.updateFeed(id, feedRequestDto, nickname)
        return ResponseEntity.ok("게시글이 수정되었습니다.")
    }

    @DeleteMapping("/{id}")
    fun deleteFeed(
        @PathVariable id: Long,
        request: HttpServletRequest
    ): ResponseEntity<String> {
        val token = request.cookies?.find { it.name == "token" }?.value
            ?: throw IllegalArgumentException("토큰이 없습니다.")
        val nickname = jwtTokenProvider.getNicknameFromToken(token)
        feedService.deleteFeed(id, nickname)
        return ResponseEntity.ok("게시글이 삭제되었습니다.")
    }
}