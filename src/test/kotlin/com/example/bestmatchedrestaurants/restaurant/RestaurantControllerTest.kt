package com.example.bestmatchedrestaurants.restaurant

import com.example.bestmatchedrestaurants.cuisine.Cuisine
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(RestaurantController::class)
class RestaurantControllerTest (@Autowired val mockMvc: MockMvc)
{
    @MockkBean
    lateinit var restaurantService: RestaurantService

    @BeforeEach
    fun setUp() {
        val sampleCuisine = Cuisine(id = 1, name = "sample")

        every { restaurantService.findFiltered(any()) } returns List<Restaurant>(5) {
            Restaurant(name = it.toString(), customerRating = it, distance = it, price = it, cuisine = sampleCuisine)
        }
    }

    @Test
    fun findAll() {
        mockMvc.perform(get("/restaurant/filter").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("\$.length()").value(5))
    }

    @Test
    fun `fails with invalid data`() {
        mockMvc.perform(get("/restaurant/filter?distance=a").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
    }
}