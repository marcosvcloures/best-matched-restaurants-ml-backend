package com.example.bestmatchedrestaurants.cuisine

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@WebMvcTest(CuisineController::class)
class CuisineControllerTest (@Autowired val mockMvc: MockMvc)
{
    @MockkBean
    private lateinit var cuisineService : CuisineService

    @BeforeEach
    fun setUp() {
        every { cuisineService.findAll() } returns List<Cuisine>(2) {
            Cuisine(id = it, name = it.toString())
        }
    }

    @Test
    fun findAll() {
        mockMvc.perform(MockMvcRequestBuilders.get("/cuisine/").accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.length()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.[0].name").value("0"))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.[1].name").value("1"))
    }
}