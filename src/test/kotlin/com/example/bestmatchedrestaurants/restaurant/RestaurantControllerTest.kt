package com.example.bestmatchedrestaurants.restaurant

import com.example.bestmatchedrestaurants.cuisine.Cuisine
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean

@SpringBootTest
class RestaurantControllerTest (
    @Autowired
    val restaurantController: RestaurantController)
{
    @MockBean
    private lateinit var restaurantService: RestaurantService

    @BeforeEach
    fun setUp() {
        val sampleCuisine = Cuisine(id = 1, name = "sample")

        given(restaurantService.findFiltered(any())).willReturn(List<Restaurant>(5) {
            Restaurant(name = it.toString(), customerRating = it, distance = it, price = it, cuisine = sampleCuisine)
        })
    }

    @Test
    fun findAll() {
        assert(restaurantController.findFiltered(RestaurantFilter(null, null, null, null, null)).count() == 5)
    }
}