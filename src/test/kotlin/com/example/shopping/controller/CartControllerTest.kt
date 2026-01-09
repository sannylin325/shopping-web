package com.example.shopping.controller

import com.example.shopping.dto.CartItem
import com.example.shopping.service.CartService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito

class CartControllerTest {
    @Test
    fun `addToCart delegates to service`() {
        val cartService = Mockito.mock(CartService::class.java)
        val controller = CartController(cartService)

        controller.addToCart(userId = 1L, productId = 100L, quantity = 2)

        Mockito.verify(cartService).addToCart(1L, 100L, 2)
    }

    @Test
    fun `getCart returns items from service`() {
        val cartService = Mockito.mock(CartService::class.java)
        val controller = CartController(cartService)
        Mockito.`when`(cartService.getCart(1L)).thenReturn(listOf(CartItem(productId = 100L, quantity = 2)))

        val result = controller.getCart(1L)

        assertEquals(1, result.size)
        assertEquals(100L, result[0].productId)
        assertEquals(2, result[0].quantity)
    }
}