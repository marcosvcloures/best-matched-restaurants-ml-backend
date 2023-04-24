package com.example.bestmatchedrestaurants.cuisine

import org.springframework.data.repository.CrudRepository

interface CuisineRepository : CrudRepository<Cuisine, Int> {
}