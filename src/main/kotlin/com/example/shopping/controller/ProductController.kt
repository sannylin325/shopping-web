package com.example.shopping.controller

import com.example.shopping.dto.ProductResponse
import com.example.shopping.entity.Product
import com.example.shopping.service.ProductService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/products")
class ProductController(
    private val productService: ProductService
) {
    @GetMapping
    fun getAllProducts(): List<ProductResponse> =
        productService.findAllActive()
            .map{  // Product -> ProductResponse
                ProductResponse(
                    it.id!!,
                    it.name,
                    it.price,
                    it.stock,
                    it.description
                )
            }
}