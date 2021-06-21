package com.deliwind.kotlinstudy.core.article.controllers.models.mappers

import com.deliwind.kotlinstudy.commons.MapStructConfig
import com.deliwind.kotlinstudy.core.article.controllers.models.ArticleDto
import com.deliwind.kotlinstudy.core.article.domains.Article
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings

@Mapper(config = MapStructConfig::class)
interface ArticleDtoMapper {

    @Mappings(
        Mapping(source="writer.id", target = "writerId"),
        Mapping(source="writer.name", target = "writerName"),
    )
    fun articleToDto(article: Article): ArticleDto.Response
}