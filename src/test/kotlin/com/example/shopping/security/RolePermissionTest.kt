package com.example.shopping.security

import com.example.shopping.auth.Role
import com.example.shopping.entity.User
import com.example.shopping.repository.UserRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@SpringBootTest
@AutoConfigureMockMvc
class RolePermissionTest {
    @Autowired
    lateinit var mockMvc: MockMvc
    @Autowired
    lateinit var jwtTokenProvider: JwtTokenProvider
    @Autowired
    lateinit var userRepository: UserRepository

    // Use stable emails; IDs will be assigned by JPA/H2 when saving
    private val user = User(
        email = "user_test@example.com",
        password = "password",
        name = "user_test",
        role = Role.ROLE_USER
    )
    private val admin = User(
        email = "admin_test@example.com",
        password = "password",
        name = "admin_test",
        role = Role.ROLE_ADMIN
    )

    private lateinit var savedUser: User
    private lateinit var savedAdmin: User

    private lateinit var userToken: String
    private lateinit var adminToken: String

    @BeforeEach
    fun setup() {
        userRepository.deleteAll()
        savedUser = userRepository.save(user)
        savedAdmin = userRepository.save(admin)

        userToken = jwtTokenProvider.generateToken(savedUser)
        adminToken = jwtTokenProvider.generateToken(savedAdmin)
    }

    @Test
    fun `USER can access user endpoint`() {
        mockMvc.get("/users/test") {
            header("Authorization", "Bearer $userToken")
        }.andExpect {
            status { isOk() }
            content { string("USER OK")}
        }
    }

    @Test
    fun `USER cannot access admin endpoint`() {
        mockMvc.get("/admin/test") {
            header("Authorization", "Bearer $userToken")
        }.andExpect {
            status { isForbidden() }
        }
    }

    @Test
    fun `ADMIN can access admin endpoint`() {
        mockMvc.get("/admin/test") {
            header("Authorization", "Bearer $adminToken")
        }.andExpect {
            status { isOk() }
            content { string("ADMIN OK")}
        }
    }
}