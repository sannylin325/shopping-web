package com.example.shopping.service

import com.example.shopping.dto.CreateProductRequest
import com.example.shopping.dto.ProductResponse
import com.example.shopping.dto.UpdateProductRequest
import com.example.shopping.entity.Product
import com.example.shopping.repository.ProductRepository
import org.springframework.stereotype.Service

@Service
class ProductService (
    private val productRepository: ProductRepository
){
    fun findAllActive(): List<Product> = productRepository.findByActiveTrue()

//    fun save(product: Product): Product = productRepository.save(product)
    fun create(request: CreateProductRequest): Product {
        val product = Product(
            name = request.name,
            price = request.price,
            stock = request.stock,
            description = request.description
        )
        return productRepository.save(product)
    }

    fun update(id: Long, request: CreateProductRequest): Product {
        val product = productRepository.findById(id)
            .orElseThrow { IllegalArgumentException("Product with id $id not found") }

        return productRepository.save(
            product.copy(
                name = request.name,
                price = request.price,
                stock = request.stock,
                description = request.description
            ))
    }

    fun deactivate(id: Long) {
        val product = productRepository.findById(id)
            .orElseThrow { IllegalArgumentException("Product with id $id not found") }
        productRepository.save(product.copy(active = false))
    }

    fun delete(id: Long) {
        val product = productRepository.findById(id)
            .orElseThrow { IllegalArgumentException("Product with id $id not found") }
        productRepository.delete(product)
    }
}