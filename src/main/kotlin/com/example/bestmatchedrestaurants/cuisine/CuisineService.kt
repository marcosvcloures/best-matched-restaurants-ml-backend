package com.example.bestmatchedrestaurants.cuisine;

import org.springframework.stereotype.Service;

@Service
class CuisineService(val cuisineRepository: CuisineRepository) {
    fun findAll(): Iterable<Cuisine> = cuisineRepository.findAll()
}
