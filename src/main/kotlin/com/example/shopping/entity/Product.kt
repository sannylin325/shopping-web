package com.example.shopping.entity

import jakarta.persistence.*
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction

@Entity
@Table(name = "products")
@SQLRestriction("deleted = false")
@SQLDelete(sql = "UPDATE products SET deleted = true WHERE id = ?")
data class Product (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    var name: String,
    var price: Int,
    var stock: Int,

    var deleted: Boolean = false
)