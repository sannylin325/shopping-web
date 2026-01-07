package com.example.shopping.security
import com.example.shopping.auth.Role
import com.example.shopping.entity.User
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNotNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

//@SpringBootTest
class JwtTokenProviderTest{
//    @Autowired
//    lateinit var jwtTokenProvider: JwtTokenProvider

    // val for pure unit test
    private val jwtSecret = "01234567890123456789012345678901"
    private val expirationMs = 60_000L
    private val jwtTokenProvider = JwtTokenProvider(
        secret = jwtSecret,
        expirationMs = expirationMs
    )

    @Test
    fun `generate token should contain userId & role`() {
        val user = User(
            id = 1L,
            email = "test@example.com",
            password = "password",
            name = "test",
            role = Role.ROLE_USER
        )
        val token = jwtTokenProvider.generateToken(user)
        assertNotNull(token)

        val userId = jwtTokenProvider.getUserId(token)
        val role = jwtTokenProvider.getRole(token)

        assertEquals(user.id, userId)
        assertEquals(user.role, role)
    }

    @Test
    fun `validate should return true for valid token`() {
        val user = User(
            id = 1L,
            email = "a@b.com",
            password = "pwd",
            name = "test2",
            role = Role.ROLE_USER)
        val token = jwtTokenProvider.generateToken(user)
        assertTrue(jwtTokenProvider.validate(token))
    }

    @Test
    fun `validate should return false for invalid token`() {
        val invalidToken = "invalid.token.for.test"
        assertFalse(jwtTokenProvider.validate(invalidToken))
    }
}
