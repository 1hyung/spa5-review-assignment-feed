package com.teamsparta.spa5reviewassignmentfeed.infra.scheduler

import com.teamsparta.spa5reviewassignmentfeed.domain.feed.service.v1.FeedService
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled

@Configuration
@EnableScheduling
class SchedulerConfig(
    private val feedService: FeedService
) {
    @Scheduled(cron = "0 0 0 * * *")
    fun deleteExpiredFeeds() {
        feedService.deleteExpiredFeeds()
    }
}