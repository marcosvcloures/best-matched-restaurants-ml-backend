package com.example.bestmatchedrestaurants

import com.example.bestmatchedrestaurants.cuisine.Cuisine
import com.example.bestmatchedrestaurants.cuisine.CuisineRepository
import com.example.bestmatchedrestaurants.restaurant.Restaurant
import com.example.bestmatchedrestaurants.restaurant.RestaurantRepository
import com.example.bestmatchedrestaurants.util.CSVParser
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration




@Configuration
class Configuration {
    @Bean
    fun databaseInitializer(restaurantRepository: RestaurantRepository,
                            cuisineRepository: CuisineRepository
    ) = ApplicationRunner {
        loadCuisines(cuisineRepository)
        loadRestaurants(restaurantRepository, cuisineRepository)
    }

    fun loadCuisines(cuisineRepository: CuisineRepository)
    {
        val cuisinesCSVContent = this::class.java.classLoader.getResource("static/cuisines.csv")?.readText() ?: ""

        val cuisineCSV = CSVParser(cuisinesCSVContent).parse();

        for (entry in cuisineCSV)
        {
            val cuisine = Cuisine(
                id = Integer.parseInt(entry["id"]),
                name = entry["name"] ?: ""
            )

            cuisineRepository.save(cuisine)
        }
    }

    fun loadRestaurants(restaurantRepository: RestaurantRepository, cuisineRepository: CuisineRepository)
    {
        val restaurantsCSVContent = this::class.java.classLoader.getResource("static/restaurants.csv")?.readText() ?: ""

        val restaurantCSV = CSVParser(restaurantsCSVContent).parse();

        for (entry in restaurantCSV)
        {
            val restaurant = Restaurant(
                name = entry["name"] ?: "",
                customerRating = Integer.parseInt(entry["customer_rating"]),
                distance = Integer.parseInt(entry["distance"]),
                price = Integer.parseInt(entry["price"]),
                cuisine = cuisineRepository.findById(Integer.parseInt(entry["cuisine_id"])).get()
            )

            restaurantRepository.save(restaurant)
        }
    }
}