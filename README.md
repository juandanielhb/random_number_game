# Random Number Game

Random Number Game is a simple odds-based game implemented in Kotlin with Spring Boot. This project allows players to bet on a randomly generated number and win or lose based on the odds of their bet.

## Features

- **REST API:** Exposes endpoints for player registration, viewing wallet transactions, placing bets, and retrieving bet results.
- **Database Storage:** Player accounts, bets, and wallet transactions are stored in an in-memory database (H2 for simplicity).
- **Unit Tests:** Includes unit tests to ensure the correctness of the game logic and API endpoints.
- **Leaderboard:** Keeps track of player scores and displays the top performers.

## Technologies Used

- Kotlin
- Spring Boot
- H2
- Maven

## Getting Started

### Prerequisites

- Java 17
- Kotlin compiler
- Maven build tool

### API Endpoints
- POST /players/register: Register a new player.
- GET /players/{playerId}/wallet-transactions: - Get wallet transactions by player.
- GET /players/{playerId}/bets: Get bets by player.
- POST /games/{playMode}/play: Play a game with a list of bets.
- GET /leaderboards: Get Leaderboard.

### Usage

Explore the API using the provided Postman collection available in the API folder at the root of the repository. Additionally, the OpenAPI YAML file is also provided in the same folder for detailed API documentation.
