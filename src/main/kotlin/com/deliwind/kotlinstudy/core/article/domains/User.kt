package com.deliwind.kotlinstudy.core.article.domains

import javax.persistence.*

@Entity
@Table
class User (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    var name: String,

    var email: String,
)