package com.example.shopping.controller

import com.example.shopping.dto.LoginRequest
import com.example.shopping.dto.LoginResponse
import com.example.shopping.jwt.JwtUtil
import com.example.shopping.repository.UserRepository
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController (
    private val authenticationManager: AuthenticationManager,
    private val jwtUtil: JwtUtil,
    private val userRepository: UserRepository
){
    @PostMapping("/login")
    fun login(@RequestBody req: LoginRequest): LoginResponse {
        val auth = UsernamePasswordAuthenticationToken(
            req.email,
            req.password
        )
        authenticationManager.authenticate(auth)
        val token = jwtUtil.generateToken(
            userId = requireNotNull(userRepository.findByEmail(req.email)?.id) { "User not found" },
            email = req.email
        )
        return LoginResponse(token)
    }
}