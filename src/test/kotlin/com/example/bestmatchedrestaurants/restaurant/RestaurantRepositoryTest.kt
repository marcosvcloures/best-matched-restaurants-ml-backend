package com.example.bestmatchedrestaurants.restaurant

import com.example.bestmatchedrestaurants.cuisine.Cuisine
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
        entityManager.clear();

        entityManager.persist(chineseCuisine);
        entityManager.persist(mexicanCuisine);

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
        assert(
            restaurantRepository.findFiltered(
                RestaurantFilter(
                    name = null,
                    customerRating = null,
                    price = null,
                    distance = null,
                    cuisine = null
                )
            ).count() == 5
        )
    }

    @Test
    fun `findFiltered name`() {
        assert(
            restaurantRepository.findFiltered(
                RestaurantFilter(
                    name = "Mexican",
                    customerRating = null,
                    price = null,
                    distance = null,
                    cuisine = "Mexi"
                )
            ).count() == 5
        )
    }

    @Test
    fun `findFiltered name case insensitive`() {
        assert(
            restaurantRepository.findFiltered(
                RestaurantFilter(
                    name = "Mexican",
                    customerRating = null,
                    price = null,
                    distance = null,
                    cuisine = "mexi"
                )
            ).count() == 5
        )
    }

    @Test
    fun `findFiltered customerRating`() {
        assert(
            restaurantRepository.findFiltered(
                RestaurantFilter(
                    name = null,
                    customerRating = 3,
                    price = null,
                    distance = null,
                    cuisine = null
                )
            ).count() == 4
        )
    }

    @Test
    fun `findFiltered price`() {
        assert(
            restaurantRepository.findFiltered(
                RestaurantFilter(
                    name = null,
                    customerRating = null,
                    price = 1,
                    distance = null,
                    cuisine = null
                )
            ).count() == 4
        )
    }

    @Test
    fun `findFiltered distance`() {
        assert(
            restaurantRepository.findFiltered(
                RestaurantFilter(
                    name = null,
                    customerRating = null,
                    price = null,
                    distance = 1,
                    cuisine = null
                )
            ).count() == 4
        )
    }

    @Test
    fun `findFiltered cuisine`() {
        assert(
            restaurantRepository.findFiltered(
                RestaurantFilter(
                    name = null,
                    customerRating = null,
                    price = null,
                    distance = null,
                    cuisine = "Mex"
                )
            ).count() == 5
        )
    }

    @Test
    fun `findFiltered cuisine case insensitive`() {
        assert(
            restaurantRepository.findFiltered(
                RestaurantFilter(
                    name = null,
                    customerRating = null,
                    price = null,
                    distance = null,
                    cuisine = "mex"
                )
            ).count() == 5
        )
    }

    @Test
    fun `findFiltered name & cuisine`() {
        assert(
            RestaurantService(restaurantRepository).findFiltered(
                RestaurantFilter(
                    name = "2",
                    customerRating = null,
                    price = null,
                    distance = null,
                    cuisine = "Mex"
                )
            ).count() == 1
        )
    }

    @Test
    fun `findFiltered filter & sort distance`() {
        val filteredResults = restaurantRepository.findFiltered(
            RestaurantFilter(
                name = "Mexican",
                customerRating = null,
                price = null,
                distance = null,
                cuisine = null
            )
        )

        assert(filteredResults[0].distance == 0)
        assert(filteredResults[1].distance == 1)
        assert(filteredResults[2].distance == 2)
        assert(filteredResults[3].distance == 3)
    }

    @Test
    fun `findFiltered sort distance`() {
        val filteredResults = restaurantRepository.findFiltered(
            RestaurantFilter(
                name = null,
                customerRating = null,
                price = null,
                distance = null,
                cuisine = null
            )
        )

        assert(filteredResults[0].distance == 0)
        assert(filteredResults[1].distance == 0)
        assert(filteredResults[2].distance == 1)
        assert(filteredResults[3].distance == 1)
    }

    @Test
    fun `findFiltered sort distance & customerRating`() {
        val newRestaurant = Restaurant(name = "newRestaurant", customerRating = 2, price = 5, distance = 0, cuisine = mexicanCuisine)

        entityManager.persist(newRestaurant)

        val filteredResults = restaurantRepository.findFiltered(
            RestaurantFilter(
                name = null,
                customerRating = null,
                price = null,
                distance = null,
                cuisine = null
            )
        )

        assert(filteredResults[0].name == "newRestaurant")
    }

    @Test
    fun `findFiltered sort distance & customerRating & price`() {
        val newRestaurant1 = Restaurant(name = "newRestaurant1", customerRating = 2, price = 4, distance = 0, cuisine = mexicanCuisine)
        val newRestaurant2 = Restaurant(name = "newRestaurant2", customerRating = 2, price = 5, distance = 0, cuisine = mexicanCuisine)

        entityManager.persist(newRestaurant1)
        entityManager.persist(newRestaurant2)

        val filteredResults = restaurantRepository.findFiltered(
            RestaurantFilter(
                name = null,
                customerRating = null,
                price = null,
                distance = null,
                cuisine = null
            )
        )

        assert(filteredResults[0].name == "newRestaurant1")
    }
}