package com.teamsparta.spa5reviewassignmentfeed.domain.user.repository.v1

import com.teamsparta.spa5reviewassignmentfeed.domain.user.model.v1.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByNickname(nickname: String): User?
    fun existsByNickname(nickname: String): Boolean
}