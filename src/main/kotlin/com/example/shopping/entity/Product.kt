package com.example.shopping.entity

import jakarta.persistence.*
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction

@Entity
@Table(name = "products")
@SQLRestriction("deleted = false")
@SQLDelete(sql = "UPDATE products SET deleted = true WHERE id = ?")
class Product (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    val name: String,

    @Column(nullable = false)
    val price: Int,

    val description: String? = null,

    @Column(nullable = false)
    val stock: Int,

    @Column(nullable = false)
    val active: Boolean = true,

    @Column(nullable = false)
    val deleted: Boolean = false
)