package com.deliwind.kotlinstudy.core.article.domains

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table
class Article (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    var title: String,
    var content: String,

    @ManyToOne
    @JoinColumn
    var writer: User,
) {

    lateinit var createdAt: LocalDateTime
    lateinit var updatedAt: LocalDateTime

    @PrePersist
    fun prePersist() {
        this.createdAt = LocalDateTime.now()
        this.updatedAt = createdAt
    }

    @PreUpdate
    fun preUpdate() {
        this.updatedAt = LocalDateTime.now()
    }
}