package com.example.shopping.service

import com.example.shopping.dto.CreateProductRequest
import com.example.shopping.dto.UpdateProductRequest
import com.example.shopping.entity.Product
import com.example.shopping.repository.ProductRepository
import org.springframework.stereotype.Service

@Service
class ProductService (
    private val productRepository: ProductRepository
){
    fun findAll(): List<Product> = productRepository.findAll()

//    fun save(product: Product): Product = productRepository.save(product)
    fun create(request: CreateProductRequest): Product {
        val product = Product(
            name = request.name,
            price = request.price,
            stock = request.stock
        )
        return productRepository.save(product)
    }

    fun update(id: Long, request: UpdateProductRequest): Product {
        val product = productRepository.findById(id)
            .orElseThrow { IllegalArgumentException("Product with id $id not found") }

        product.name = request.name
        product.price = request.price
        product.stock = request.stock

        return productRepository.save(product)
    }

    fun delete(id: Long) {
        val product = productRepository.findById(id)
            .orElseThrow { IllegalArgumentException("Product with id $id not found") }
        productRepository.delete(product)
    }
}