package com.example.bestmatchedrestaurants.restaurant

interface RestaurantRepositoryCustom {
    fun findFiltered(restaurantFilter: RestaurantFilter) : List<Restaurant>
}