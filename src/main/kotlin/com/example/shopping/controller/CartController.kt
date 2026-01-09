package com.example.shopping.controller

import com.example.shopping.dto.CartItem
import com.example.shopping.service.CartService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/cart")
class CartController (
    private val cartService: CartService
){
    @PostMapping("/add")
    fun addToCart(@RequestParam userId: Long,
                      @RequestParam productId: Long,
                      @RequestParam quantity: Int) {
        cartService.addToCart(userId, productId, quantity)
    }

    @GetMapping
    fun getCart(@RequestParam userId: Long): List<CartItem> =
        cartService.getCart(userId)

    @PostMapping("/remove")
    fun removeFromCart(@RequestParam userId: Long,
                            @RequestParam productId: Long) {
        cartService.removeFromCart(userId, productId)
    }
}