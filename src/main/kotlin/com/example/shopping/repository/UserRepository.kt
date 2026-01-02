package com.example.shopping.repository

import com.example.shopping.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    fun existsByEmail(email: String): Boolean
}