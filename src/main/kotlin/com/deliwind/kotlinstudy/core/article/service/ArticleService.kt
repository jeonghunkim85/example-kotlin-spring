package com.deliwind.kotlinstudy.core.article.service

import com.deliwind.kotlinstudy.core.article.domains.Article
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface ArticleService {
    fun getArticles(pageable: Pageable): Page<Article>
}