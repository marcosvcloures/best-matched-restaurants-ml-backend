## Backend
### Implementation
The backend is a REST API written in Kotlin and using Boot Spring. The CSV files are stored as resources and, during initialization, they are parsed (using a custom function) and stored in an H2 Database. The filtering and sorting is done using Criteria API.

The backend exposes two endpoints (both documented using SpringDoc, with the documentation [available here](http://localhost:8080/swagger-ui.html)):

* GET /cuisines/ - used for the autocomplete input in the frontend

* GET /restaurant/filter - the endpoint that gets the restaurants recommendations based on the given criteria (received in the query string)

There are tests implementations for every class, implemented using JUnit 5.

### Running
* Run server: ``.\gradlew bootRun``
* Run tests: ``.\gradlew test``

The REST API server will run in [localhost:8080](http://localhost:8080/), with [Swagger enabled](http://localhost:8080/swagger-ui.html).