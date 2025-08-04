# Mensa Web Application

A Java-based application for managing and filtering menu items from a university mensa (cafeteria) system, built with Test-Driven Development (TDD) principles. This application allows users to view and filter menu items based on various dietary requirements and preferences.

## Project Overview

This application was developed as part of a university project focusing on:
- Clean code architecture
- Test-Driven Development (TDD) practices
- Java programming best practices
- Handling of external data sources (CSV)
- Implementation of business logic for dietary restrictions

## Key Features

- **Menu Management**: View dishes available on specific dates
- **Dietary Filtering**: Filter dishes based on:
  - Allergens (e.g., gluten, nuts, dairy)
  - Food additives and preservatives
  - Dietary preferences (vegetarian, vegan, etc.)
- **Pricing Information**: Different pricing for students, staff, and guests
- **Data Management**: Automatic handling of CSV data with caching and cleanup

## Technical Stack

- **Language**: Java 11+
- **Build Tool**: Maven
- **Testing Framework**: JUnit 5
- **Key Dependencies**:
  - Java NIO for file operations
  - Java Time API for date handling
  - Google Guava Preconditions for input validation

## Project Structure

```
src/
├── main/java/
│   └── de/uni_passau/fim/se2/st/mensawebapp/
│       ├── business/service/       # Business logic services
│       │   ├── DishService.java    # Main service for dish operations
│       │   ├── IngredientService.java # Service for ingredient info
│       │   └── CalendarService.java # Date-related utilities
│       │
│       ├── global/dish/            # Domain models
│       │   ├── Dish.java          # Dish entity with all properties
│       │   ├── Additive.java      # Food additives
│       │   ├── Allergen.java      # Allergen information
│       │   └── Tag.java           # Dietary tags
│       │
│       └── persistence/csv/        # Data access layer
│           ├── CSVFile.java       # CSV file handling
│           └── CSVParser.java     # CSV parsing logic
│
└── test/                          # Test files mirroring the main structure
    └── ...
```

## Key Components

### Domain Models
- `Dish`: Represents a menu item with properties like name, type, prices, and dietary information
- `Additive`: Represents food additives and preservatives
- `Allergen`: Represents common food allergens
- `Tag`: Represents dietary tags (vegetarian, vegan, etc.)

### Services
- `DishService`: Core service for retrieving and filtering dishes
- `IngredientService`: Provides information about available additives, allergens, and tags
- `CalendarService`: Handles date-related calculations

### Data Access
- `CSVFile`: Manages CSV file operations and caching
- `CSVParser`: Parses CSV data into domain objects

## Testing Approach

The project follows Test-Driven Development (TDD) principles with comprehensive test coverage:

- Unit tests for all service methods
- Data parsing tests with various input scenarios
- Edge case testing for date handling and filtering
- Integration tests for the complete data flow

Example test cases include:
- Filtering dishes by date
- Handling of invalid input data
- Proper parsing of CSV files
- Edge cases in date calculations

## Getting Started

### Prerequisites
- Java 11 or higher
- Maven

### Building the Project
```bash
mvn clean install
```

### Running Tests
```bash
mvn test
```

## Usage Example

```java
// Initialize the DishService
DishService dishService = new DishService(
    "dd.MM.yyyy", 
    Paths.get("./data"), 
    new URI("http://example.com/menu-data"), 
    TimeUnit.DAYS.toSeconds(1) // Cache for 1 day
);

// Get dishes for a specific date
LocalDate date = LocalDate.of(2023, 12, 1);
List<Dish> dishes = dishService.getDishes(date);

// Filter dishes based on dietary restrictions
List<Dish> filteredDishes = dishService.filterDishes(
    date,
    List.of(Additive.A, Additive.B),  // Additives to exclude
    List.of(Allergen.A, Allergen.C),  // Allergens to exclude
    List.of(Tag.VEGETARIAN)           // Tags to include
);
```

## Project Goals

This project demonstrates:
1. Clean code architecture and design patterns
2. Test-Driven Development practices
3. Robust error handling and input validation
4. Efficient data processing and filtering
5. Clear separation of concerns

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgements

- University of Passau - For the project requirements and inspiration
- STWNO - For the original CSV data format and menu information

---

*This README provides an overview of the project. For more detailed documentation, please refer to the source code and test files.*
