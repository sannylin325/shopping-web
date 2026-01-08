package com.example.shopping.dto

data class ProductResponse (
    val id: Long,
    val name: String,
    val price: Int,
    val stock: Int,
    val description: String?
)