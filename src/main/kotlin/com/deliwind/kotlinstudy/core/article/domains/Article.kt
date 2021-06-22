package com.deliwind.kotlinstudy.core.article.domains

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table
class Article (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,
    var title: String,
    var content: String,

    var createdAt: LocalDateTime,
    var updatedAt: LocalDateTime,

    @ManyToOne
    @JoinColumn
    var writer: User,
) {

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