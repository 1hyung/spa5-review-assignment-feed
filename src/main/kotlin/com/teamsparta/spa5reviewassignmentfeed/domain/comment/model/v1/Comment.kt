package com.teamsparta.spa5reviewassignmentfeed.domain.comment.model.v1

import com.teamsparta.spa5reviewassignmentfeed.domain.feed.model.v1.Feed
import com.teamsparta.spa5reviewassignmentfeed.domain.user.model.v1.User
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import java.time.LocalDateTime

@Entity
data class Comment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    var comment: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feed_id")
    val feed: Feed,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: User,

    @Column(nullable = false)
    val createdDate: LocalDateTime = LocalDateTime.now(),

    @Column(nullable = false)
    var modifiedDate: LocalDateTime = LocalDateTime.now(),

    @Column(nullable = false)
    var thumbUp: Int = 0
)