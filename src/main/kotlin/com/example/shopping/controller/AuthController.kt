package com.example.shopping.controller

import com.example.shopping.dto.LoginRequest
import com.example.shopping.dto.LoginResponse
import com.example.shopping.repository.UserRepository
import com.example.shopping.security.JwtTokenProvider
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
    private val jwtTokenProvider: JwtTokenProvider,
    private val userRepository: UserRepository
){
    @PostMapping("/login")
    fun login(@RequestBody req: LoginRequest): LoginResponse {
        // (1) check email & password
        val auth = UsernamePasswordAuthenticationToken(
            req.email,
            req.password
        )
        authenticationManager.authenticate(auth)
        // (2) get user
        val user = userRepository.findByEmail(req.email)
            ?: throw IllegalArgumentException("User not found")
        // (3) generate token
        val token = jwtTokenProvider.generateToken(user)
        return LoginResponse(token)
    }
}