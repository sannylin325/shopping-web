package com.example.shopping.service

import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.ValueOperations
import java.util.concurrent.ConcurrentHashMap

class CartServiceTest {
    private lateinit var cartService: CartService
    private lateinit var redisTemplate: RedisTemplate<String, Any>
    private lateinit var valueOps: ValueOperations<String, Any>
    private val store = ConcurrentHashMap<String, Any>()

    @BeforeEach
    @Suppress("UNCHECKED_CAST")
    fun setUp() {
        store.clear()

        redisTemplate = Mockito.mock(RedisTemplate::class.java) as RedisTemplate<String, Any>
        valueOps = Mockito.mock(ValueOperations::class.java) as ValueOperations<String, Any>
        cartService = CartService(redisTemplate)

        Mockito.`when`(redisTemplate.opsForValue()).thenReturn(valueOps)
        Mockito.`when`(valueOps.get(ArgumentMatchers.anyString())).thenAnswer { invocation ->
            store[invocation.arguments[0] as String]
        }
        Mockito.doAnswer { invocation ->
            val key = invocation.arguments[0] as String
            val value = invocation.arguments[1] as Any
            store[key] = value
            null
        }.`when`(valueOps).set(ArgumentMatchers.anyString(), ArgumentMatchers.any())

        Mockito.`when`(redisTemplate.delete(ArgumentMatchers.anyString())).thenAnswer { invocation ->
            store.remove(invocation.arguments[0] as String) != null
        }
    }

    @Test
    fun `addToCart should add new item`() {
        val userId = 1L
        val productId = 100L

        cartService.addToCart(userId, productId, 2)
        val cart = cartService.getCart(userId)

        assertEquals(1, cart.size)
        assertEquals(productId, cart[0].productId)
        assertEquals(2, cart[0].quantity)
    }

    @Test
    fun `addToCart should increase quantity if item exists`() {
        val userId = 1L
        val productId = 100L

        cartService.addToCart(userId, productId, 1)
        cartService.addToCart(userId, productId, 2)
        val cart = cartService.getCart(userId)

        assertEquals(1, cart.size)
        assertEquals(3, cart[0].quantity)
    }

    @Test
    fun `removeFromCart shold remove item`() {
        val userId = 1L
        val productId = 100L

        cartService.addToCart(userId, productId, 1)
        cartService.addToCart(userId, 200L, 1)
        cartService.removeFromCart(userId, productId)
        val cart = cartService.getCart(userId)

        assertEquals(1, cart.size)
        assertEquals(200L, cart[0].productId)
    }

    @Test
    fun `clearCart should delete cart`() {
        val userId = 1L

        cartService.addToCart(userId, 1L, 1)
        cartService.clearCart(userId)
        val cart = cartService.getCart(userId)

        assertTrue(cart.isEmpty())
    }
}