package com.example.bestmatchedrestaurants.cuisine

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController()
@RequestMapping("/cuisine/")
@CrossOrigin(origins = ["http://localhost:3000"])
class CuisineController(private val service: CuisineService) {
    @Operation(summary = "Get all cuisines")
    @GetMapping("/")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Returns an array containing all available cuisines", content = [
            (Content(mediaType = "application/json", array = (ArraySchema(schema = Schema(implementation = Cuisine::class)))))])])
    fun findAll(): Iterable<Cuisine> = service.findAll()
}