package com.example.bestmatchedrestaurants.restaurant

import org.springframework.stereotype.Service

@Service
class RestaurantService(val restaurantRepository: RestaurantRepository) {
    fun findFiltered(restaurantFilter: RestaurantFilter) : List<Restaurant> = restaurantRepository.findFiltered(restaurantFilter)
}