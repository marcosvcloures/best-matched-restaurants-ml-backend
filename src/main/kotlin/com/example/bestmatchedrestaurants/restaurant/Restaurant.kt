package com.example.bestmatchedrestaurants.restaurant

import com.example.bestmatchedrestaurants.cuisine.Cuisine
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne

@Entity
class Restaurant(
    @Id
    var name: String,
    var customerRating: Int,
    var distance: Int,
    var price: Int,
    @ManyToOne
    var cuisine: Cuisine
)