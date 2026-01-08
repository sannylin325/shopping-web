package com.example.shopping.controller

import com.example.shopping.dto.CreateProductRequest
import com.example.shopping.dto.ProductResponse
import com.example.shopping.service.ProductService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/admin/products")
@PreAuthorize("hasRole('ADMIN')")
class AdminProductController (
    private val productService: ProductService
) {
    @PostMapping
    fun createProduct(@RequestBody request: CreateProductRequest): ProductResponse {
        val p = productService.create(request)
        return ProductResponse(p.id!!, p.name, p.price, p.stock, p.description)
    }

    @PutMapping("/{id}")
    fun updateProduct(
        @PathVariable id: Long,
        @RequestBody request: CreateProductRequest
    ): ProductResponse {
        val p = productService.update(id, request)
        return ProductResponse(p.id!!, p.name, p.price, p.stock, p.description)
    }

    @DeleteMapping("/{id}")
    fun deleteProduct(@PathVariable id: Long) {
        productService.delete(id)
    }
}