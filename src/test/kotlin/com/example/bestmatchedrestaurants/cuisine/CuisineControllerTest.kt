package com.example.bestmatchedrestaurants.cuisine

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean

@SpringBootTest
class CuisineControllerTest (
    @Autowired
    val cuisineController: CuisineController)
{
    @MockBean
    private lateinit var cuisineService : CuisineService

    @BeforeEach
    fun setUp() {
        given(cuisineService.findAll()).willReturn(List<Cuisine>(2) {
            Cuisine(id = it, name = it.toString())
        })
    }

    @Test
    fun findAll() {
        assert(cuisineController.findAll().count() == 2)
    }
}