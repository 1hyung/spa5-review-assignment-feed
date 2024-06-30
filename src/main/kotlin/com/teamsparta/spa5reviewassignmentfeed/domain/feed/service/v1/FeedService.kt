package com.teamsparta.spa5reviewassignmentfeed.domain.feed.service.v1

import com.teamsparta.spa5reviewassignmentfeed.domain.comment.dto.v1.CommentResponseDto
import com.teamsparta.spa5reviewassignmentfeed.domain.comment.repository.v1.CommentRepository
import com.teamsparta.spa5reviewassignmentfeed.domain.feed.dto.v1.FeedRequestDto
import com.teamsparta.spa5reviewassignmentfeed.domain.feed.dto.v1.FeedResponseDto
import com.teamsparta.spa5reviewassignmentfeed.domain.feed.model.v1.Feed
import com.teamsparta.spa5reviewassignmentfeed.domain.feed.repository.v1.FeedRepository
import com.teamsparta.spa5reviewassignmentfeed.domain.user.repository.v1.UserRepository
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.time.ZoneOffset

@Service
class FeedService(
    private val feedRepository: FeedRepository,
    private val userRepository: UserRepository,
    private val commentRepository: CommentRepository
) {
    @Transactional(readOnly = true)
    fun getFeeds(): List<FeedResponseDto> {
        val feeds = feedRepository.findAll(Sort.by(Sort.Direction.DESC, "createdDate"))
        return feeds.map {
            FeedResponseDto(
                id = it.id,
                title = it.title,
                content = it.content,
                nickname = it.user.nickname,
                createdDate = it.createdDate
            )
        }
    }

    @Transactional
    fun createFeed(feedRequestDto: FeedRequestDto, nickname: String): Feed {
        val user = userRepository.findByNickname(nickname)
            ?: throw IllegalArgumentException("사용자를 찾을 수 없습니다.")
        val feed = Feed(
            title = feedRequestDto.title,
            content = feedRequestDto.content,
            user = user
        )
        return feedRepository.save(feed)
    }

    @Transactional(readOnly = true)
    fun getFeed(id: Long): FeedResponseDto {
        val feed = feedRepository.findById(id)
            .orElseThrow { IllegalArgumentException("게시글을 찾을 수 없습니다.") }
        val comments = commentRepository.findAllByFeedId(id).map {
            CommentResponseDto(
                id = it.id,
                comment = it.comment,
                nickname = it.user.nickname,
                createdDate = it.createdDate,
                thumbUp = it.thumbUp
            )
        }
        return FeedResponseDto(
            id = feed.id,
            title = feed.title,
            content = feed.content,
            nickname = feed.user.nickname,
            createdDate = feed.createdDate,
            comments = comments // comments 필드 설정
        )
    }

    @Transactional
    fun updateFeed(id: Long, feedRequestDto: FeedRequestDto, nickname: String) {
        val feed = feedRepository.findById(id).orElseThrow { IllegalArgumentException("게시글을 찾을 수 없습니다.") }
        if (feed.user.nickname != nickname) {
            throw IllegalArgumentException("게시글 수정 권한이 없습니다.")
        }
        feed.title = feedRequestDto.title
        feed.content = feedRequestDto.content
        feedRepository.save(feed)
    }

    @Transactional
    fun deleteFeed(id: Long, nickname: String) {
        val feed = feedRepository.findById(id).orElseThrow { IllegalArgumentException("게시글을 찾을 수 없습니다.") }
        if (feed.user.nickname != nickname) {
            throw IllegalArgumentException("게시글 삭제 권한이 없습니다.")
        }
        feedRepository.delete(feed)
    }

    @Transactional
    fun deleteOldFeeds() {
        val thresholdDate = LocalDateTime.now().minusDays(90)
        feedRepository.deleteByModifiedDateBefore(thresholdDate)
    }

    fun deleteExpiredFeeds() {
        val thresholdDate = LocalDateTime.now(ZoneOffset.UTC).minusDays(90)
        feedRepository.deleteByModifiedDateBefore(thresholdDate)
    }
}
