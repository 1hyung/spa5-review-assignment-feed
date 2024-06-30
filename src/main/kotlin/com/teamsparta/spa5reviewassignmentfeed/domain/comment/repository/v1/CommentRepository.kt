package com.teamsparta.spa5reviewassignmentfeed.domain.comment.repository.v1

import com.teamsparta.spa5reviewassignmentfeed.domain.comment.model.v1.Comment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CommentRepository : JpaRepository<Comment, Long> {
    fun findAllByFeedId(feedId: Long): List<Comment>
}
