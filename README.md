# Spring SQL API

A RESTful Spring Boot API for managing users, movies, and ratings. This application provides endpoints for creating and managing user accounts, movie catalog entries, and user ratings for movies.

## 🚀 Features

- **User Management**: Full CRUD operations for user accounts with partial updates
- **Movie Management**: Create and retrieve movie information
- **Rating System**: Users can rate movies with scores (one rating per user per movie)
- **Data Validation**: Comprehensive input validation using Jakarta Bean Validation
- **RESTful API**: Clean REST endpoints following best practices with API versioning
- **Database Integration**: MySQL database with JPA/Hibernate ORM
- **DTO Pattern**: Data Transfer Objects for request/response handling
- **Pagination**: Paginated endpoints for listing users and ratings
- **API Documentation**: OpenAPI/Swagger documentation for interactive API exploration
- **Error Handling**: Structured error responses with proper HTTP status codes
- **Transaction Management**: All data-modifying operations are transactional
- **Logging**: Comprehensive logging using SLF4J
- **Database Seeder**: Automatic population of sample data for development and testing

## 📋 Prerequisites

Before you begin, ensure you have the following installed:

- **Java 21** or higher
- **Maven 3.6+**
- **MySQL 8.0+** (or access to a MySQL database)
- **IDE** (IntelliJ IDEA, Eclipse, VS Code, etc.)

## 🛠️ Technology Stack

- **Framework**: Spring Boot 4.0.2
- **Language**: Java 21
- **Build Tool**: Maven
- **Database**: MySQL (Railway)
- **ORM**: Spring Data JPA / Hibernate
- **Validation**: Jakarta Bean Validation (Hibernate Validator)
- **API Documentation**: SpringDoc OpenAPI (Swagger)

## ⚡ Performance: Movies list endpoint

The `GET /api/v1/movies` endpoint was optimized from **~4000 ms** to **~350 ms** when using a remote DB (e.g. Railway).

**Why it was slow**
- The database is remote, so each round-trip adds significant latency.
- The original implementation caused **N+1 queries**: one query for the page of movies, then one query per movie to load ratings and director when building the response.
- It also loaded **every rating** for every movie on the list just to compute an average.

**What we changed**
1. **Eliminated N+1** – Use `@EntityGraph` (and a two-query pattern) so a page of movies is loaded with director in one go instead of one query per movie.
2. **List returns only the average** – The list endpoint now exposes only `ratingsAvg` (no array of ratings), so we don’t load the full `Rating` collection for listing.
3. **Single native query for the list** – One native SQL query returns the full list page: movie fields, director name (via joins to `directors` and `artists`), and `ratings_avg` (via a scalar subquery). Pagination uses Spring’s `Pageable` and a separate `countQuery`, so the list runs in **two round-trips** (count + page) instead of many.

**Relevant code**
- `MovieRepository.findMovieListPage(Pageable)` – native query joining `movies`, `directors`, `artists` and a subquery for `AVG(score)` from `ratings`.
- `MovieService.findAllMovies(Pageable)` – calls `findMovieListPage`, maps each row to `MovieResponse` (including `ratingsAvg`), and builds `PageResponse`.

## 📦 Project Structure

```
spring-api/
├── src/
│   ├── main/
│   │   ├── java/com/example/demo/
│   │   │   ├── controllers/        # REST controllers
│   │   │   │   ├── UserController.java
│   │   │   │   ├── MovieController.java
│   │   │   │   └── RatingController.java
│   │   │   ├── services/            # Business logic layer
│   │   │   │   ├── UserService.java
│   │   │   │   ├── MovieService.java
│   │   │   │   └── RatingService.java
│   │   │   ├── repositories/        # Data access layer
│   │   │   │   ├── UserRepository.java
│   │   │   │   ├── MovieRepository.java
│   │   │   │   └── RatingRepository.java
│   │   │   ├── models/              # Entity models
│   │   │   │   ├── User.java
│   │   │   │   ├── Movie.java
│   │   │   │   └── Rating.java
│   │   │   ├── DTOs/                # Data Transfer Objects
│   │   │   │   ├── CreateUserRequest.java
│   │   │   │   ├── UpdateUserRequest.java
│   │   │   │   ├── UserResponse.java
│   │   │   │   ├── CreateMovieRequest.java
│   │   │   │   ├── CreateRatingRequest.java
│   │   │   │   ├── RatingResponse.java
│   │   │   │   └── ErrorResponse.java
│   │   │   ├── exception/           # Exception handling
│   │   │   │   ├── GlobalExceptionHandler.java
│   │   │   │   ├── EmailAlreadyExistsException.java
│   │   │   │   └── DuplicateRatingException.java
│   │   │   ├── config/              # Configuration
│   │   │   │   ├── OpenApiConfig.java
│   │   │   │   └── DatabaseSeeder.java
│   │   │   ├── SpringSqlApiApplication.java
│   │   │   └── PingPong.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/com/example/demo/
└── pom.xml
```

## 🗄️ Database Schema

The application uses three main entities with the following relationships:

- **Users**: User accounts with name and email (email must be unique)
- **Movies**: Movie catalog with title, release year, director, and language
- **Ratings**: User ratings for movies (Many-to-One relationship with both Users and Movies)
    - Unique constraint: A user can only rate a movie once (enforced by unique constraint on `user_id` and `movie_id`)

### Entity Relationships

```
User (1) ────< (Many) Rating (Many) >─── (1) Movie
```

### Database Constraints

- **Users.email**: Unique constraint
- **Ratings(user_id, movie_id)**: Unique constraint (prevents duplicate ratings)

## ⚙️ Configuration

### Database Setup

Update `src/main/resources/application.properties` with your database credentials:

```properties
spring.application.name=spring-sql-api
spring.datasource.url=jdbc:mysql://your-host:port/database-name
spring.datasource.username=your-username
spring.datasource.password=your-password
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.hibernate.show_sql=true

# Database Seeder Configuration
app.seeder.enabled=true
```

**Note**: The current configuration uses Railway MySQL database. Update these values for your local or production environment.

### Database Seeder

The application includes a database seeder that automatically populates the database with sample data on startup (when enabled). The seeder will:

- Create 8 sample users with various names and emails
- Create 15 sample movies from different genres and languages
- Create ~35 sample ratings linking users to movies with diverse scores (7-10)
- Only run if the database is empty (won't duplicate existing data)
- Automatically disabled in production profile

**Rating Seeder Details:**

- Creates ratings for various user-movie combinations
- Scores range from 7 to 10 (realistic rating distribution)
- Respects the unique constraint (one rating per user per movie)
- Only seeds if users and movies already exist

**Configuration:**

- `app.seeder.enabled=true` - Enable/disable the seeder (default: true)
- The seeder runs automatically on application startup (except in `prod` profile)

**To disable the seeder:**

```properties
app.seeder.enabled=false
```

**To disable in production:**
The seeder is automatically disabled when running with the `prod` Spring profile.

## 🚀 Getting Started

### 1. Clone the Repository

```bash
git clone <repository-url>
cd spring-api
```

### 2. Configure Database

Update the database connection details in `src/main/resources/application.properties`.

### 3. Build the Project

```bash
mvn clean install
```

### 4. Run the Application

```bash
mvn spring-boot:run
```

Or run the main class `SpringSqlApiApplication` from your IDE.

The application will start on `http://localhost:8080` by default.

### 5. Verify Database Seeding

If the seeder is enabled and the database is empty, you should see log messages indicating that users, movies, and ratings have been seeded. You can verify by:

```bash
# Check seeded users
curl http://localhost:8080/api/v1/users

# Check seeded movies
curl http://localhost:8080/api/v1/movies/1

# Check seeded ratings
curl http://localhost:8080/api/v1/ratings
```

### 6. Access API Documentation

Once the application is running, you can access:

- **Swagger UI**: `http://localhost:8080/swagger-ui.html`
    - Interactive API documentation with "Try it out" functionality
    - Test endpoints directly from the browser
- **OpenAPI JSON**: `http://localhost:8080/v3/api-docs`
    - Machine-readable API specification in JSON format

## 📡 API Endpoints

**Base URL**: All API endpoints use the `/api/v1/` prefix for versioning.

### Health Check & Welcome

| Method | Endpoint   | Description                   |
| ------ | ---------- | ----------------------------- |
| GET    | `/`        | Welcome message               |
| GET    | `/ping`    | Health check (returns "pong") |
| GET    | `/welcome` | HTML welcome page             |

### User Endpoints

| Method | Endpoint             | Description                   | Request Body        |
| ------ | -------------------- | ----------------------------- | ------------------- |
| GET    | `/api/v1/users`      | Get all users (paginated)     | -                   |
| GET    | `/api/v1/users/{id}` | Get user by ID (with ratings) | -                   |
| POST   | `/api/v1/users`      | Create a new user             | `CreateUserRequest` |
| PUT    | `/api/v1/users/{id}` | Update user (partial update)  | `UpdateUserRequest` |
| DELETE | `/api/v1/users/{id}` | Delete a user                 | -                   |

**Pagination Parameters** (for GET `/api/v1/users`):

- `page` (default: 0): Page number (0-indexed)
- `size` (default: 20): Number of items per page
- `sort` (default: id): Sort field(s), e.g., `sort=name,desc`

#### Create User Request Example

```json
{
    "name": "John Doe",
    "email": "john.doe@example.com"
}
```

#### User Response Example

```json
{
    "id": 1,
    "name": "John Doe",
    "email": "john.doe@example.com",
    "ratings": [
        {
            "id": 1,
            "score": 8,
            "movieId": 1,
            "movieTitle": "Inception"
        }
    ]
}
```

### Movie Endpoints

| Method | Endpoint              | Description        | Request Body         |
| ------ | --------------------- | ------------------ | -------------------- |
| GET    | `/api/v1/movies/{id}` | Get movie by ID    | -                    |
| POST   | `/api/v1/movies`      | Create a new movie | `CreateMovieRequest` |

### Rating Endpoints

| Method | Endpoint               | Description                 | Request Body          |
| ------ | ---------------------- | --------------------------- | --------------------- |
| GET    | `/api/v1/ratings`      | Get all ratings (paginated) | -                     |
| GET    | `/api/v1/ratings/{id}` | Get rating by ID            | -                     |
| POST   | `/api/v1/ratings`      | Create a new rating         | `CreateRatingRequest` |
| DELETE | `/api/v1/ratings/{id}` | Delete a rating             | -                     |

**Pagination Parameters** (for GET `/api/v1/ratings`):

- `page` (default: 0): Page number (0-indexed)
- `size` (default: 20): Number of items per page
- `sort` (default: id): Sort field(s), e.g., `sort=score,desc`

#### Create Movie Request Example

```json
{
    "title": "Inception",
    "releaseYear": 2010,
    "director": "Christopher Nolan"
}
```

#### Movie Response Example

```json
{
    "id": 1,
    "title": "Inception",
    "releaseYear": 2010,
    "director": "Christopher Nolan",
    "language": null,
    "ratings": []
}
```

#### Create Rating Request Example

```json
{
    "userId": 1,
    "movieId": 1,
    "score": 8
}
```

#### Rating Response Example

```json
{
    "id": 1,
    "score": 8,
    "movieId": 1,
    "movieTitle": "Inception"
}
```

## 🔍 Validation Rules

### User Validation

- **Name**: Required (on create), 3-100 characters, optional on update (partial updates supported)
- **Email**: Required (on create), must be a valid email format, optional on update (partial updates supported)
- **ID**: Must be >= 1 (path variable validation)

### Movie Validation

- **Title**: Required, 3-100 characters
- **Release Year**: Required, must be between 1888 and 2100
- **Director**: Optional, max 100 characters
- **ID**: Must be >= 1 (path variable validation)

### Rating Validation

- **User ID**: Required, must exist
- **Movie ID**: Required, must exist
- **Score**: Required, must be a valid integer
- **Unique Constraint**: A user can only rate a movie once (enforced at database and service level)
- **ID**: Must be >= 1 (path variable validation)

## 🧪 Testing

Run tests using Maven:

```bash
mvn test
```

## 📝 Example API Usage

### Create a User

```bash
curl -X POST http://localhost:8080/api/v1/users \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Jane Smith",
    "email": "jane.smith@example.com"
  }'
```

### Get All Users (Paginated)

```bash
# Get first page (default: 20 items)
curl http://localhost:8080/api/v1/users

# Get second page with 10 items per page
curl "http://localhost:8080/api/v1/users?page=1&size=10"

# Get users sorted by name descending
curl "http://localhost:8080/api/v1/users?sort=name,desc"
```

### Get User by ID

```bash
curl http://localhost:8080/api/v1/users/1
```

### Update User (Partial Update)

```bash
# Update only the name
curl -X PUT http://localhost:8080/api/v1/users/1 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Jane Doe"
  }'

# Update only the email
curl -X PUT http://localhost:8080/api/v1/users/1 \
  -H "Content-Type: application/json" \
  -d '{
    "email": "jane.doe@example.com"
  }'
```

### Create a Movie

```bash
curl -X POST http://localhost:8080/api/v1/movies \
  -H "Content-Type: application/json" \
  -d '{
    "title": "The Matrix",
    "releaseYear": 1999,
    "director": "The Wachowskis"
  }'
```

### Get Movie by ID

```bash
curl http://localhost:8080/api/v1/movies/1
```

### Create a Rating

```bash
curl -X POST http://localhost:8080/api/v1/ratings \
  -H "Content-Type: application/json" \
  -d '{
    "userId": 1,
    "movieId": 1,
    "score": 8
  }'
```

### Get All Ratings (Paginated)

```bash
curl "http://localhost:8080/api/v1/ratings?page=0&size=10"
```

### Delete a User

```bash
curl -X DELETE http://localhost:8080/api/v1/users/1
```

## 🏗️ Architecture

The application follows a layered architecture:

1. **Controller Layer**: Handles HTTP requests and responses
2. **Service Layer**: Contains business logic
3. **Repository Layer**: Data access using Spring Data JPA
4. **Model Layer**: JPA entities representing database tables
5. **DTO Layer**: Data Transfer Objects for API communication

## 🔒 Security Notes

⚠️ **Important**: The current `application.properties` file contains database credentials. For production:

1. Use environment variables or a secrets management system
2. Never commit credentials to version control
3. Consider using Spring Cloud Config or similar solutions
4. Implement proper authentication and authorization

## 🐛 Error Handling

The application uses a centralized `GlobalExceptionHandler` with structured `ErrorResponse` DTOs. All errors follow a consistent format:

```json
{
    "timestamp": "2026-02-20T10:30:00",
    "status": 404,
    "error": "Not Found",
    "message": "User with ID: 999, was not found"
}
```

### Error Types

- **400 Bad Request**:
    - Validation failures (includes field-level errors)
    - Invalid arguments (e.g., empty strings, whitespace-only values)
- **404 Not Found**: When a user, movie, or rating ID doesn't exist
- **409 Conflict**:
    - When attempting to create a user with an existing email
    - When attempting to create a duplicate rating (user already rated the movie)
- **500 Internal Server Error**: Unexpected errors (sanitized message returned to client)

### Validation Error Example

```json
{
    "timestamp": "2026-02-20T10:30:00",
    "status": 400,
    "error": "Validation Failed",
    "message": "Request validation failed",
    "errors": {
        "email": "Email must be valid",
        "name": "Name must be between 3 and 100 characters"
    }
}
```

## 📚 Dependencies

Key dependencies include:

- `spring-boot-starter-web`: Web framework
- `spring-boot-starter-data-jpa`: JPA support (includes pagination)
- `mysql-connector-j`: MySQL database driver
- `jakarta.validation-api`: Validation API
- `hibernate-validator`: Validation implementation
- `springdoc-openapi-starter-webmvc-ui`: OpenAPI/Swagger documentation

See `pom.xml` for the complete list.

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 License

This project is open source and available under the [MIT License](LICENSE).

## 👤 Author

Developed as a Spring Boot learning project.

## ✅ Completed Improvements

### Critical Issues (Fixed)

- ✅ Added `MethodArgumentNotValidException` handler in `GlobalExceptionHandler`
- ✅ Added `@Valid` to `MovieController.createMovie()` method parameter
- ✅ Fixed `@NotBlank` on `CreateMovieRequest.releaseYear` (changed to `@NotNull` with `@Min/@Max`)
- ✅ Added validation annotations to `UpdateUserRequest`
- ✅ Created `EmailAlreadyExistsException` and use 409 Conflict instead of 400 for email conflicts

### Important Issues (Fixed)

- ✅ Prevent duplicate ratings (added unique constraint and service-level check)
- ✅ Added `@Transactional` to service methods that modify data
- ✅ Added logging (SLF4J) to service classes
- ✅ Added validation for path variables (`@Min(1)` for IDs)
- ✅ Narrowed generic exception handler (logs errors, returns sanitized messages)

### Nice-to-Have Improvements (Completed)

- ✅ Added pagination to `getAllUsers()` and `getAllRatings()`
- ✅ Added `@Min/@Max` validation for releaseYear range (1888-2100)
- ✅ Added email uniqueness check in `updateUser()` (excludes current user)
- ✅ Replaced `@Validated` with `@Valid` in controllers (for request bodies)
- ✅ Created `ErrorResponse` DTO instead of using `Map<String, Object>`
- ✅ Added OpenAPI/Swagger documentation
- ✅ Added API versioning (`/api/v1/` prefix)
- ✅ Added database seeder for Users, Movies, and Ratings

## 🔮 Future Enhancements

Potential improvements:

- [ ] Add authentication and authorization (Spring Security)
- [ ] Add search and filtering capabilities
- [ ] Add unit and integration tests
- [ ] Implement caching strategies
- [ ] Add request/response logging middleware
- [ ] Support for multiple database types
- [ ] Add rate limiting
- [ ] Implement soft delete for users/movies
- [ ] Add audit logging for data changes
- [ ] Add movie search by title, director, or year
- [ ] Add user rating history endpoint
- [ ] Add average rating calculation for movies

## 📞 Support

For issues and questions, please open an issue in the repository.

---

**Happy Coding! 🎉**
