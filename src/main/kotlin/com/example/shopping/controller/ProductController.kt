package com.example.shopping.controller

import com.example.shopping.dto.CreateProductRequest
import com.example.shopping.dto.UpdateProductRequest
import com.example.shopping.entity.Product
import com.example.shopping.service.ProductService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/products")
class ProductController(
    private val productService: ProductService
) {
    @GetMapping
    fun getAllProducts(): List<Product> =
        productService.findAll()

    @PostMapping
    fun createProduct(@RequestBody request: CreateProductRequest): Product =
        productService.create(request)

    @PutMapping("/{id}")
    fun updateProduct(
        @PathVariable id: Long,
        @RequestBody request: UpdateProductRequest
    ): Product =
        productService.update(id, request)

    @DeleteMapping("/{id}")
    fun deleteProduct(@PathVariable id: Long) {
        productService.delete(id)
    }
}