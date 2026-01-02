package com.example.shopping.dto

data class CreateProductRequest (
    val name: String,
    val price: Int,
    val stock: Int
)