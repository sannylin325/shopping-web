package com.example.shopping.service

import com.example.shopping.auth.Role
import com.example.shopping.dto.RegisterRequest
import com.example.shopping.entity.User
import com.example.shopping.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService (
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
){
    fun register(request: RegisterRequest) {
        if (userRepository.existsByEmail(request.email)) {
            throw IllegalArgumentException("This email is already registered.")
        }

        val user = User(
            email = request.email,
            password = requireNotNull(passwordEncoder.encode(request.password)) { "Password encoding failed" },
            name = request.name
        )
        userRepository.save(user)
    }
}