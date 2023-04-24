package com.example.bestmatchedrestaurants.restaurant

import com.example.bestmatchedrestaurants.cuisine.Cuisine
import jakarta.persistence.EntityManager
import jakarta.persistence.criteria.Order
import jakarta.persistence.criteria.Predicate

class RestaurantRepositoryCustomImpl (private val entityManager: EntityManager) : RestaurantRepositoryCustom {
    override fun findFiltered(restaurantFilter: RestaurantFilter) : List<Restaurant>
    {
        val builder = entityManager.criteriaBuilder
        val query = builder.createQuery(Restaurant::class.java)
        val entity = query.from(Restaurant::class.java)

        val where = mutableListOf<Predicate>()

        restaurantFilter.name ?.let {
            where.add(builder.like(builder.lower(entity.get<String>("name")), "%" + restaurantFilter.name.lowercase() + "%"))
        }

        restaurantFilter.customerRating ?. let {
            where.add(builder.ge(entity.get<Int>("customerRating"), restaurantFilter.customerRating))
        }

        restaurantFilter.distance ?. let {
            where.add(builder.le(entity.get<Int>("distance"), restaurantFilter.distance))
        }

        restaurantFilter.price ?. let {
            where.add(builder.le(entity.get<Int>("price"), restaurantFilter.price))
        }

        restaurantFilter.cuisine ?. let {
            where.add(builder.like(builder.lower(entity.get<Cuisine>("cuisine").get<String>("name")), "%" + restaurantFilter.cuisine.lowercase() + "%"))
        }

        if (where.size > 0)
            query.where(*where.toTypedArray())

        val orderBy = mutableListOf<Order>()

        orderBy.add(builder.asc(entity.get<Int>("distance")))
        orderBy.add(builder.desc(entity.get<Int>("customerRating")))
        orderBy.add(builder.asc(entity.get<Int>("price")))

        query.orderBy(*orderBy.toTypedArray())

        return entityManager.createQuery(query).setMaxResults(5).resultList
    }
}