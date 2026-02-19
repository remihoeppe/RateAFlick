# Spring SQL API

A RESTful Spring Boot API for managing users, movies, and ratings. This application provides endpoints for creating and managing user accounts, movie catalog entries, and user ratings for movies.

## 🚀 Features

- **User Management**: Full CRUD operations for user accounts
- **Movie Management**: Create and retrieve movie information
- **Rating System**: Users can rate movies with scores
- **Data Validation**: Input validation using Jakarta Bean Validation
- **RESTful API**: Clean REST endpoints following best practices
- **Database Integration**: MySQL database with JPA/Hibernate ORM
- **DTO Pattern**: Data Transfer Objects for request/response handling

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

## 📦 Project Structure

```
spring-api/
├── src/
│   ├── main/
│   │   ├── java/com/example/demo/
│   │   │   ├── controllers/        # REST controllers
│   │   │   │   ├── UserController.java
│   │   │   │   └── MovieController.java
│   │   │   ├── services/            # Business logic layer
│   │   │   │   ├── UserService.java
│   │   │   │   └── MovieService.java
│   │   │   ├── repositories/        # Data access layer
│   │   │   │   ├── UserRepository.java
│   │   │   │   └── MovieRepository.java
│   │   │   ├── models/              # Entity models
│   │   │   │   ├── User.java
│   │   │   │   ├── Movie.java
│   │   │   │   └── Rating.java
│   │   │   ├── DTOs/                # Data Transfer Objects
│   │   │   │   ├── CreateUserRequest.java
│   │   │   │   ├── UpdateUserRequest.java
│   │   │   │   ├── UserResponse.java
│   │   │   │   ├── CreateMovieRequest.java
│   │   │   │   └── RatingResponse.java
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

- **Users**: User accounts with name and email
- **Movies**: Movie catalog with title, release year, director, and language
- **Ratings**: User ratings for movies (Many-to-One relationship with both Users and Movies)

### Entity Relationships

```
User (1) ────< (Many) Rating (Many) >─── (1) Movie
```

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
```

**Note**: The current configuration uses Railway MySQL database. Update these values for your local or production environment.

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

## 📡 API Endpoints

### Health Check & Welcome

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/` | Welcome message |
| GET | `/ping` | Health check (returns "pong") |
| GET | `/welcome` | HTML welcome page |

### User Endpoints

| Method | Endpoint | Description | Request Body |
|--------|----------|-------------|--------------|
| GET | `/api/users` | Get all users | - |
| GET | `/api/users/{id}` | Get user by ID (with ratings) | - |
| POST | `/api/users` | Create a new user | `CreateUserRequest` |
| DELETE | `/api/users/{id}` | Delete a user | - |

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

| Method | Endpoint | Description | Request Body |
|--------|----------|-------------|--------------|
| GET | `/api/movies/{id}` | Get movie by ID | - |
| POST | `/api/movies` | Create a new movie | `CreateMovieRequest` |

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

## 🔍 Validation Rules

### User Validation

- **Name**: Required, 3-100 characters
- **Email**: Required, must be a valid email format

### Movie Validation

- **Title**: Required, 3-100 characters
- **Release Year**: Required
- **Director**: Optional, max 100 characters

## 🧪 Testing

Run tests using Maven:

```bash
mvn test
```

## 📝 Example API Usage

### Create a User

```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Jane Smith",
    "email": "jane.smith@example.com"
  }'
```

### Get All Users

```bash
curl http://localhost:8080/api/users
```

### Get User by ID

```bash
curl http://localhost:8080/api/users/1
```

### Create a Movie

```bash
curl -X POST http://localhost:8080/api/movies \
  -H "Content-Type: application/json" \
  -d '{
    "title": "The Matrix",
    "releaseYear": 1999,
    "director": "The Wachowskis"
  }'
```

### Get Movie by ID

```bash
curl http://localhost:8080/api/movies/1
```

### Delete a User

```bash
curl -X DELETE http://localhost:8080/api/users/1
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

The application handles common errors:

- **404 Not Found**: When a user or movie ID doesn't exist
- **400 Bad Request**: When validation fails
- **409 Conflict**: When attempting to create a user with an existing email

## 📚 Dependencies

Key dependencies include:

- `spring-boot-starter-web`: Web framework
- `spring-boot-starter-data-jpa`: JPA support
- `mysql-connector-j`: MySQL database driver
- `jakarta.validation-api`: Validation API
- `hibernate-validator`: Validation implementation

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

## 🔮 Future Enhancements

Potential improvements:

- [ ] Add authentication and authorization (Spring Security)
- [ ] Implement rating creation endpoints
- [ ] Add pagination for list endpoints
- [ ] Add search and filtering capabilities
- [ ] Implement comprehensive error handling
- [ ] Add API documentation (Swagger/OpenAPI)
- [ ] Add unit and integration tests
- [ ] Implement caching strategies
- [ ] Add logging and monitoring
- [ ] Support for multiple database types

## 📞 Support

For issues and questions, please open an issue in the repository.

---

**Happy Coding! 🎉**
