package com.example.shopping.entity

import com.example.shopping.auth.Role
import jakarta.persistence.*

@Entity
@Table(name = "users")
class User (
    @Id @GeneratedValue
    val id: Long? = null,

    @Column(nullable = false, unique = true)
    val email: String,

    @Column(nullable = false)
    val password: String,

    @Column(nullable = false)
    val name: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val role: Role = Role.ROLE_USER
)