package com.example.bestmatchedrestaurants.cuisine

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager

@DataJpaTest
class CuisineRepositoryTest @Autowired constructor(
    private val entityManager: TestEntityManager,
    private val cuisineRepository: CuisineRepository) {

    @BeforeEach
    fun setUp() {
        val chinese = Cuisine(id = 1, name = "Chinese")
        val mexican = Cuisine(id = 2, name = "Mexican")

        entityManager.persist(chinese);
        entityManager.persist(mexican);

        entityManager.flush()
    }

    @AfterEach
    fun tearDown() {
        entityManager.clear()
    }

    @Test
    fun findAll() {
        assertThat(cuisineRepository.findAll().count()).isEqualTo(2)
    }

    @Test
    fun `findAll and has Chinese`() {
        val existsChinese = cuisineRepository.findAll().toList().stream().anyMatch { cuisine -> cuisine.id == 1 && cuisine.name == "Chinese" }

        assertThat(existsChinese).isEqualTo(true)
    }

    @Test
    fun `findAll and has Mexican`() {
        val existsMexican = cuisineRepository.findAll().toList().stream().anyMatch { cuisine -> cuisine.id == 2 && cuisine.name == "Mexican" }

        assertThat(existsMexican).isEqualTo(true)
    }
}