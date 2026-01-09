package com.example.shopping.service

import com.example.shopping.dto.CartItem
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service

@Service
class CartService (
    private val redisTemplate: RedisTemplate<String, Any>
){
    private fun getKey(userId: Long) = "cart:$userId"

    @Suppress("UNCHECKED_CAST")
    fun getCart(userId: Long): List<CartItem> {
        val key = getKey(userId)
        return redisTemplate.opsForValue().get(key) as? List<CartItem> ?: emptyList()
    }

    fun addToCart(userId: Long, productId: Long, quantity: Int) {
        val key = getKey(userId)
        val cart = getCart(userId).toMutableList()
        val existing = cart.find { it.productId == productId}
        if (existing != null) {
            existing.quantity += quantity
        } else {
            cart.add(CartItem(productId, quantity))
        }
        redisTemplate.opsForValue().set(key, cart)
    }

    fun removeFromCart(userId: Long, productId: Long) {
        val key = getKey(userId)
        val cart = getCart(userId).filter { it.productId != productId }
        redisTemplate.opsForValue().set(key, cart)
    }

    fun updateQuantity(userId: Long, productId: Long, quantity: Int) {
        val key = getKey(userId)
        val cart = getCart(userId)
        val existing = cart.find {it.productId == productId }
        if (existing != null) {
            existing.quantity = quantity
        }
        redisTemplate.opsForValue().set(key, cart)
    }

    fun clearCart(userId: Long) {
        redisTemplate.delete(getKey(userId))
    }
}