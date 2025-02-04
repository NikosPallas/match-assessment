# Match REST API

This project is a Spring Boot application that provides a REST API for managing **matches** and their **odds**. It uses Maven for building the project and PostgreSQL as the database.

## Technologies Used
- **Java 17** (for Spring Boot)
- **Maven** (build tool)
- **PostgreSQL** (database)
- **Swagger** (API documentation)

## Project Structure

- **Entities**:
    - `Match`: Represents a match with relevant details.
    - `MatchOdds`: Represents the odds associated with each match.

- **API Layer**:
    - `MatchController`: Handles API requests related to matches.
    - `MatchOddsController`: Handles API requests related to match odds.

- **Service Layer**:
    - `MatchService`: Contains business logic for matches.
    - `MatchOddsService`: Contains business logic for match odds.

- **Repositories**:
    - `MatchRepository`: Manages database operations for the `Match` entity.
    - `MatchOddsRepository`: Manages database operations for the `MatchOdds` entity.

- **Test**:
    - `MatchControllerTest`: Unit tests for the `MatchController`(only for requests here).
    -  We can add an IntegrationTest to fully test the implementation

## Setup & Installation

### Prerequisites:
- Java 17
- Maven
- PostgreSQL

### To run the application locally:

1. Clone the repository:
   ```bash
   git clone https://github.com/NikosPallas/match-assessment.git
   
2. Run via Docker: 
   ```bash
   docker-compose up --build

### To test the endpoints of the application and check the documentation in detail(while it is running):
    
1. Swagger API can be reached through this http address (http://localhost:8080/swagger-ui/index.html#/):
   ```bash
Swagger API: [here](http://localhost:8080/swagger-ui/index.html#/).

2. Or simply through Postman API testing tool:
 ```bash 
    Example for POST request:
POST http://localhost:8080/api/matches
With Body: {
   "description": "Serie A 2024 - Matchday 30",
   "matchDate": "2024-04-10",
   "matchTime": "18:30",
   "teamA": "Inter Milan",
   "teamB": "AS Roma",
   "sport": "FOOTBALL",
   "matchOdds": [
   {
   "specifier": "1x2",
   "odd": 2.3
   },
   {
   "specifier": "Over/Under 2.5",
   "odd": 1.9
   },
   {
   "specifier": "Exact Score",
   "odd": 8.5
   }
   ]
   }


