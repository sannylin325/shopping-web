package com.example.shopping.controller

import com.example.shopping.dto.LoginRequest
import com.example.shopping.entity.User
import com.example.shopping.jwt.JwtUtil
import com.example.shopping.repository.UserRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc
import org.springframework.http.MediaType
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import tools.jackson.databind.ObjectMapper

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {
    @Autowired
    lateinit var mockMvc: MockMvc
    @Autowired
    lateinit var objectMapper: ObjectMapper
    @Autowired
    lateinit var userRepository: UserRepository
    @Autowired
    lateinit var passwordEncoder: PasswordEncoder
    @Autowired
    lateinit var jwtUtil: JwtUtil
    lateinit var testUser: User

    @BeforeEach
    fun setup() {
        userRepository.deleteAll()
        testUser = userRepository.save(
            User(
                email = "test@test.com",
                password = requireNotNull(passwordEncoder.encode("123456")) { "Password not found" },
                name = "test user"
            )
        )
    }

    @Test  // test success but haven't solved warnings
    fun `login returns token`() {
        val loginRequest = LoginRequest(email = "test@test.com", password = "123456")
        val result = mockMvc.post("/auth/login") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(loginRequest)
        }.andExpect {
            status {isOk()}
        }.andReturn()

        val responseJson = result.response.contentAsString
        val token = objectMapper.readTree(responseJson).get("token").asText()
        println("JWT Token: $token")
    }

//    @Test  // not test yet
//    fun `access protected endpoint with token`() {
//        val token = jwtUtil.generateToken(testUser.id, testUser.email)
//        mockMvc.get("") {  // API request
//            header("Authorization", "Bearer $token")
//        }.andExpect {
//            status { isOk() }
//        }
//    }
//
//    @Test  // not test yet
//    fun `access protected endpoint without token - fails`() {
//        mockMvc.get("") {  // API request
//        }.andExpect {
//            status {isUnauthorized()}  // 401
//        }
//    }
}