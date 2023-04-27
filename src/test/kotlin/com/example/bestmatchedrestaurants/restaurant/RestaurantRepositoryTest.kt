package com.example.bestmatchedrestaurants.restaurant

import com.example.bestmatchedrestaurants.cuisine.Cuisine
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager

@DataJpaTest
class RestaurantRepositoryTest @Autowired constructor(
    private val entityManager: TestEntityManager,
    private val restaurantRepository: RestaurantRepository
)
{
    private val chineseCuisine = Cuisine(id = 1, name = "Chinese")
    private val mexicanCuisine = Cuisine(id = 2, name = "Mexican")

    @BeforeEach
    fun setUp() {
        entityManager.clear()

        entityManager.persist(chineseCuisine)
        entityManager.persist(mexicanCuisine)

        entityManager.flush()

        val chineseRestaurants = List(5) {
            Restaurant(name = String.format("%s %d", "Chinese", it), customerRating = it, price = it, distance = it, cuisine = chineseCuisine)
        }

        val mexicanRestaurants = List(5) {
            Restaurant(name = String.format("%s %d", "Mexican", it), customerRating = it, price = it, distance = it, cuisine = mexicanCuisine)
        }

        for (restaurant in chineseRestaurants)
            entityManager.persist(restaurant)

        for (restaurant in mexicanRestaurants)
            entityManager.persist(restaurant)

        entityManager.flush()
    }

    @Test
    fun `findFiltered get at most 5`() {
        val response = restaurantRepository.findFiltered(
            RestaurantFilter(
                name = null,
                customerRating = null,
                price = null,
                distance = null,
                cuisine = null
            )
        )

        assertThat(response.count()).isEqualTo(5)
    }

    @Test
    fun `findFiltered name`() {
        val response = restaurantRepository.findFiltered(
            RestaurantFilter(
                name = "Mex",
                customerRating = null,
                price = null,
                distance = null,
                cuisine = null
            )
        )

        assertThat(response.count()).isEqualTo(5)
    }

    @Test
    fun `findFiltered name case insensitive`() {
        val response = restaurantRepository.findFiltered(
            RestaurantFilter(
                name = "mex",
                customerRating = null,
                price = null,
                distance = null,
                cuisine = null
            )
        )

        assertThat(response.count()).isEqualTo(5)
    }

    @Test
    fun `findFiltered customerRating`() {
        val response = restaurantRepository.findFiltered(
            RestaurantFilter(
                name = null,
                customerRating = 3,
                price = null,
                distance = null,
                cuisine = null
            )
        )

        assertThat(response.count()).isEqualTo(4)
    }

    @Test
    fun `findFiltered price`() {
        val response = restaurantRepository.findFiltered(
            RestaurantFilter(
                name = null,
                customerRating = null,
                price = 1,
                distance = null,
                cuisine = null
            )
        )

        assertThat(response.count()).isEqualTo(4)
    }

    @Test
    fun `findFiltered distance`() {
        val response = restaurantRepository.findFiltered(
            RestaurantFilter(
                name = null,
                customerRating = null,
                price = 1,
                distance = null,
                cuisine = null
            )
        )

        assertThat(response.count()).isEqualTo(4)
    }

    @Test
    fun `findFiltered cuisine`() {
        val response = restaurantRepository.findFiltered(
            RestaurantFilter(
                name = null,
                customerRating = null,
                price = null,
                distance = null,
                cuisine = "Mex"
            )
        )

        assertThat(response.count()).isEqualTo(5)
    }

    @Test
    fun `findFiltered cuisine case insensitive`() {
        val response = restaurantRepository.findFiltered(
            RestaurantFilter(
                name = null,
                customerRating = null,
                price = null,
                distance = null,
                cuisine = "mex"
            )
        )

        assertThat(response.count()).isEqualTo(5)
    }

    @Test
    fun `findFiltered name & cuisine`() {
        val response = restaurantRepository.findFiltered(
            RestaurantFilter(
                name = "2",
                customerRating = null,
                price = null,
                distance = null,
                cuisine = "mex"
            )
        )

        assertThat(response.count()).isEqualTo(1)
    }

    @Test
    fun `findFiltered filter & sort distance`() {
        val response = restaurantRepository.findFiltered(
            RestaurantFilter(
                name = "Mex",
                customerRating = null,
                price = null,
                distance = null,
                cuisine = null
            )
        )

        assertThat(response[0].distance).isEqualTo(0)
        assertThat(response[1].distance).isEqualTo(1)
        assertThat(response[2].distance).isEqualTo(2)
        assertThat(response[3].distance).isEqualTo(3)
    }

    @Test
    fun `findFiltered sort distance`() {
        val response = restaurantRepository.findFiltered(
            RestaurantFilter(
                name = null,
                customerRating = null,
                price = null,
                distance = null,
                cuisine = null
            )
        )

        assertThat(response[0].distance).isEqualTo(0)
        assertThat(response[1].distance).isEqualTo(0)
        assertThat(response[2].distance).isEqualTo(1)
        assertThat(response[3].distance).isEqualTo(1)
    }

    @Test
    fun `findFiltered sort distance & customerRating`() {
        val newRestaurant = Restaurant(name = "newRestaurant", customerRating = 2, price = 5, distance = 0, cuisine = mexicanCuisine)

        entityManager.persist(newRestaurant)

        val response = restaurantRepository.findFiltered(
            RestaurantFilter(
                name = null,
                customerRating = null,
                price = null,
                distance = null,
                cuisine = null
            )
        )

        assertThat(response[0].name).isEqualTo(newRestaurant.name)
    }

    @Test
    fun `findFiltered sort distance & customerRating & price`() {
        val newRestaurant1 = Restaurant(name = "newRestaurant1", customerRating = 2, price = 4, distance = 0, cuisine = mexicanCuisine)
        val newRestaurant2 = Restaurant(name = "newRestaurant2", customerRating = 2, price = 5, distance = 0, cuisine = mexicanCuisine)

        entityManager.persist(newRestaurant1)
        entityManager.persist(newRestaurant2)

        val response = restaurantRepository.findFiltered(
            RestaurantFilter(
                name = null,
                customerRating = null,
                price = null,
                distance = null,
                cuisine = null
            )
        )

        assertThat(response[0].name).isEqualTo(newRestaurant1.name)
    }
}