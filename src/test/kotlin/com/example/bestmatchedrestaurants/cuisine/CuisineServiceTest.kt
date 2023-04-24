package com.example.bestmatchedrestaurants.cuisine

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
class CuisineServiceTest ()
{
    @MockBean
    private lateinit var cuisineRepository: CuisineRepository

    @BeforeEach
    fun setUp() {
        given(cuisineRepository.findAll()).willReturn(List<Cuisine>(2) {
            Cuisine(id = it, name = it.toString())
        })
    }

    @Test
    fun findAll() {
        assert(CuisineService(cuisineRepository).findAll().count() == 2)
    }
}