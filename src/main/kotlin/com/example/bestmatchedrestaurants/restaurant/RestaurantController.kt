package com.example.bestmatchedrestaurants.restaurant

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.*
import io.swagger.v3.oas.annotations.responses.*
import org.springframework.http.*
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.*
import java.util.*
import java.util.stream.Collectors


@RestController()
@RequestMapping("/restaurant/")
@CrossOrigin(origins = ["http://localhost:3000"])
class RestaurantController(private val service: RestaurantService) {
    @GetMapping("/filter")
    @Operation(summary = "Gets at most 5 restaurants based on the filter received by query and ordered by priority")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Returns an array at most 5 restaurants", content = [
            (Content(mediaType = "application/json", array = (ArraySchema(schema = Schema(implementation = Restaurant::class)))))]),
        ApiResponse(responseCode = "400", description = "Returns the list of errors that occurred", content = [
            (Content(mediaType = "application/json", array = (ArraySchema(schema = Schema(implementation = String::class)))))])])
    fun findFiltered(restaurantFilter: RestaurantFilter) = service.findFiltered(restaurantFilter)

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleException(exception: MethodArgumentNotValidException) : ResponseEntity<MutableList<String>> {
        val errors: MutableList<String> = exception.bindingResult.fieldErrors.stream()
            .map(FieldError::getDefaultMessage).collect(Collectors.toList())

        return ResponseEntity<MutableList<String>>(errors, HttpHeaders(), HttpStatus.BAD_REQUEST)
    }
}