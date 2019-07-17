# Space Tourism Management System

REST API Service (SPRING BOOT REST) runs on port: 9000
Web Application (SPRING BOOT MVC) runs on port: 8080

## HTTP Methods

### Flight Controller:
GET: api/flight <- gets all flights
GET: api/flight/{id} <- get a single flight with {id}
POST: api/flight <- create a flight
PUT: api/flight/{id} <- update a flight with {id}
PUT: api/flight/{flightId}/tourist/{touristId} <- add a tourist {touristId} to a flight {flightId}
DELETE: api/flight/{flightId}/tourist/{touristId} <- delete a tourist {touristId} from a flight {flightId}
DELETE: api/flight/{id} <- delete a single flight with {id}

### Flight Controller:
GET: api/tourist <- gets all tourists
GET: api/tourist/{id} <- get a single tourist with {id}
POST: api/tourist <- create a tourist
PUT: api/tourist/{id} <- update a tourist with {id}
DELETE: api/tourist/{id} <- delete a single tourist with {id}
