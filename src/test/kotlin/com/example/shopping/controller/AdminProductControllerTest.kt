package com.example.shopping.controller

import com.example.shopping.service.ProductService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class AdminProductControllerTest {
    @Autowired
    lateinit var mockMvc: MockMvc
    @Autowired
    lateinit var productService: ProductService

    @Test
    @WithMockUser(roles = ["ADMIN"])
    fun `admin can create product`() {
        val json = """
            {
                "name": "New Product",
                "description": "A brand new product",
                "price": 99,
                "stock": 10
            }
        """.trimIndent()

        mockMvc.perform(
            post("/admin/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )
            .andExpect(status().isOk)
    }

    @Test
    @WithMockUser(roles = ["USER"])
    fun `user cannot create product`() {
        val json = """
            {
                "name": "New Product",
                "description": "A brand new product",
                "price": 99,
                "stock": 10
            }
        """.trimIndent()

        mockMvc.perform(
            post("/admin/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )
            .andExpect(status().isForbidden)
    }
}