package com.example.shopping.service

import com.example.shopping.dto.CreateProductRequest
import com.example.shopping.entity.Product
import com.example.shopping.repository.ProductRepository
import jakarta.transaction.Transactional
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNotNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@Transactional  // rollback after test
class ProductServiceTest {
    @Autowired
    lateinit var productService: ProductService
    @Autowired
    lateinit var productRepository: ProductRepository

    @Test
    fun `create product should have product id`() {
        val req = CreateProductRequest(
            name = "test_product",
            price = 30000,
            stock = 10,
            description = "test_description"
        )

        val product = productService.create(req)

        assertNotNull(product.id)
        assertEquals("test_product", product.name)
        assertEquals(30000, product.price)
        assertFalse(product.deleted)
    }

    @Test
    fun `delete product should mark deleted true`() {
        val product = productRepository.save(
            Product(
                name = "to_be_deleted",
                price = 20000,
                stock = 5,
                description = "to be deleted"
            )
        )

        productService.delete(product.id!!)
        val deletedProduct = productRepository.findById(product.id!!)
        assertTrue(deletedProduct.isEmpty)
    }

    @Test
    fun `findAllActive should return only non-deleted products`() {
        productRepository.save(
            Product(name = "A", price = 100, stock = 1)
        )
        productRepository.save(
            Product(name = "B", price = 200, stock = 2, deleted = true)
        )

        val result = productService.findAllActive()
        assertEquals(1, result.size)
        assertEquals("A", result[0].name)
    }
}