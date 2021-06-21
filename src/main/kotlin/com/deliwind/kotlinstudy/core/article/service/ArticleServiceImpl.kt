package com.deliwind.kotlinstudy.core.article.service

import com.deliwind.kotlinstudy.core.article.domains.Article
import com.deliwind.kotlinstudy.core.article.repositories.ArticleRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ArticleServiceImpl(
    val articleRepository: ArticleRepository
): ArticleService {

    @Transactional(readOnly = true)
    override fun getArticles(pageable: Pageable): Page<Article> =
        articleRepository.findAll(pageable)
}