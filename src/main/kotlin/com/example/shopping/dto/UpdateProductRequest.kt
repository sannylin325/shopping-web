package com.example.shopping.dto

data class UpdateProductRequest (
    val name: String,
    val price: Int,
    val stock: Int
)