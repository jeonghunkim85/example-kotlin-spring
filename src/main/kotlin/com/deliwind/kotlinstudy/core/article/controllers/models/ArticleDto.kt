package com.deliwind.kotlinstudy.core.article.controllers.models

object ArticleDto {

    data class Response(
        val id: Long,
        var title: String,
        var content: String,
        val writerId: Long,
        val writerName: String,
    )
}