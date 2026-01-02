package com.example.shopping.entity

import jakarta.persistence.*

@Entity
@Table(name = "users")
class User (
    @Id @GeneratedValue
    val id: Long? = null,

    @Column(nullable = false, unique = true)
    var email: String,

    @Column(nullable = false)
    var password: String,

    @Column(nullable = false)
    var name: String
)