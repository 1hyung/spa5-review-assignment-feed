package com.teamsparta.spa5reviewassignmentfeed.domain.feed.repository.v1

import com.teamsparta.spa5reviewassignmentfeed.domain.feed.model.v1.Feed
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface FeedRepository : JpaRepository<Feed, Long> {
    fun findByUserId(userId: Long): List<Feed>
    fun deleteByModifiedDateBefore(date: LocalDateTime)
}