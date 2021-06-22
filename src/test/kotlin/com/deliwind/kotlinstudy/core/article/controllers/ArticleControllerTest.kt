package com.deliwind.kotlinstudy.core.article.controllers

import com.deliwind.kotlinstudy.commons.Logger
import com.deliwind.kotlinstudy.core.article.controllers.models.ArticleDto
import com.deliwind.kotlinstudy.core.article.controllers.models.mappers.ArticleDtoMapper
import com.deliwind.kotlinstudy.core.article.controllers.models.mappers.ArticleDtoMapperImpl
import com.deliwind.kotlinstudy.core.article.domains.Article
import com.deliwind.kotlinstudy.core.article.domains.User
import com.deliwind.kotlinstudy.core.article.service.ArticleService
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.ninjasquad.springmockk.MockkBean
import com.ninjasquad.springmockk.SpykBean
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.time.LocalDateTime

@AutoConfigureMockMvc
@WebMvcTest(ArticleController::class)
@ExtendWith(MockKExtension::class)
@ContextConfiguration(classes = [
    ArticleController::class,
    ArticleDtoMapperImpl::class,
])
class ArticleControllerTest : DescribeSpec() {

    companion object: Logger

    @MockkBean
    lateinit var articleService: ArticleService

    @SpykBean
    lateinit var articleDtoMapper: ArticleDtoMapper

    @Autowired
    lateinit var mockMvc: MockMvc

    private val endpoint = ArticleController.PATH
    private val objectMapper = jacksonObjectMapper()

    init {

        afterEach {
            clearAllMocks()
        }

        describe("GET /articles 호출을 테스트 합니다") {
            val doRequestAndGetResult = {
                mockMvc.perform(
                    get("/$endpoint").contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk)
                    .andReturn()
                    .response
                    .getContentAsString(Charsets.UTF_8)
            }

            context("articleService.getArticles 가 article 목록을 return 할 때") {

                var slot = slot<Pageable>()

                beforeEach {
                    every { articleService.getArticles(capture(slot)) } returns PageImpl(
                        listOf(
                            Article(
                                id = 1,
                                title = "제목",
                                content = "글 내용",
                                createdAt = LocalDateTime.now(),
                                updatedAt = LocalDateTime.now(),
                                writer = User(
                                    id = 9,
                                    name = "익명1",
                                    email = "mail@naver.com"
                                )
                            )
                        ),
                        PageRequest.of(0, 20),
                        1,
                    )
                }

                it("articleDtoMapper.articleToDto 가 article 수만큼 호출되고 ArticleDto.Response를 response 합니다") {

                    // run it
                    val resultString = doRequestAndGetResult()
                    val articleResponseTypeRef = object: TypeReference<HashMap<String, Any>>() {}

                    log.debug("#resultString: {}", resultString)

                    val result = objectMapper.readValue(resultString, articleResponseTypeRef)

                    log.debug("{}", result)

                    result["totalElements"] shouldBe 1
                    val content = result["content"] as List<Map<String, Any>>
                    content.first()["title"] shouldBe "제목"

                    verify { articleDtoMapper.articleToDto(any()) }
                }
            }
        }
    }
}
