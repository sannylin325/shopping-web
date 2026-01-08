package com.example.shopping.repository

import com.example.shopping.entity.Product
import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository : JpaRepository<Product, Long> {
    fun findByActiveTrue(): List<Product>
}