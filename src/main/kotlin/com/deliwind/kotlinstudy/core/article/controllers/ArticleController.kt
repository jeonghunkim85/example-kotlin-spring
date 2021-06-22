package com.deliwind.kotlinstudy.core.article.controllers

import com.deliwind.kotlinstudy.core.article.controllers.models.ArticleDto
import com.deliwind.kotlinstudy.core.article.controllers.models.mappers.ArticleDtoMapper
import com.deliwind.kotlinstudy.core.article.domains.Article
import com.deliwind.kotlinstudy.core.article.service.ArticleService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid
import javax.validation.constraints.Positive
import javax.validation.constraints.PositiveOrZero

@RestController
@RequestMapping(ArticleController.PATH)
class ArticleController(
    private val articleService: ArticleService,
    private val articleDtoMapper: ArticleDtoMapper,
) {

    companion object {
        const val PATH = "articles"
    }

    @GetMapping
    fun getArticles(
        @Valid searchCommand: ArticlesSearchCommand
    ): Page<ArticleDto.Response>
            = articleService.getArticles(searchCommand.pageable())
        .toResponse()

    fun Page<Article>.toResponse() = this.map(articleDtoMapper::articleToDto)
}

data class ArticlesSearchCommand(
    @get:Positive
    val pageSize: Int? = 20,

    @get:PositiveOrZero
    val pageNumber: Int? = 0,
) {
    fun pageable(): Pageable = PageRequest.of(pageNumber?: 0, pageSize ?: 20)
}