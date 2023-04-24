package com.example.bestmatchedrestaurants.cuisine
import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
class Cuisine (
    @Id
    var id: Int,
    var name: String
)