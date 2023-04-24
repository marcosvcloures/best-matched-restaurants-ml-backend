package com.example.bestmatchedrestaurants.restaurant

import org.springframework.data.repository.CrudRepository

interface RestaurantRepository : CrudRepository<Restaurant, String>, RestaurantRepositoryCustom {
}