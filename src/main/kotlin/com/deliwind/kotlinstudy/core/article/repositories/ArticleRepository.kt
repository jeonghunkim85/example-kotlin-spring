package com.deliwind.kotlinstudy.core.article.repositories

import com.deliwind.kotlinstudy.core.article.domains.Article
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface ArticleRepository: JpaRepository<Article, Long>