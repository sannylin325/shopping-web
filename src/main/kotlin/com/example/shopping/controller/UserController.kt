package com.example.shopping.controller

import com.example.shopping.dto.RegisterRequest
import com.example.shopping.service.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController (
    private val userService: UserService
){
    @PostMapping("/register")
    fun register(@RequestBody request: RegisterRequest) {
        userService.register(request)
    }

    @GetMapping("/test")
    fun userOnly() = "USER OK"
}