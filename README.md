# Derivatives Analytics Engine

A financial derivatives analytics engine built with Java and Spring Boot, specialized in option pricing and Greeks calculation using the Black-Scholes model.

## 🎯 Features

- **Option Pricing**: Theoretical price calculation for CALL and PUT options
- **Greeks Calculation**: Delta, Gamma, and Vega for risk analysis
- **Black-Scholes Model**: Complete implementation of the pricing model
- **REST API**: Web interface for financial calculations with comprehensive endpoints
- **OpenAPI Documentation**: Interactive API documentation with Swagger UI
- **Data Validation**: Automatic validation of input parameters with detailed error messages
- **Global Error Handling**: Centralized exception handling for robust API responses
- **Documentation**: Fully documented code with Javadoc

## 🛠️ Technologies Used

- **Java 17** - Programming language
- **Spring Boot 3.5.4** - Main framework
- **Spring Web** - REST API
- **Spring Validation** - Data validation
- **Spring Boot Actuator** - Monitoring and metrics
- **Lombok 3.6.1** - Boilerplate code reduction
- **SpringDoc OpenAPI 2.8.9** - API documentation and Swagger UI
- **Maven** - Dependency management and build

## 📊 Financial Models

### Black-Scholes Model
The project implements the Black-Scholes model for:
- European option pricing
- Greeks calculation

### Calculated Greeks
- **Delta (Δ)**: Sensitivity of option price to underlying asset price changes
- **Gamma (Γ)**: Sensitivity of delta to underlying asset price changes
- **Vega (ν)**: Sensitivity of option price to volatility changes

## 🏗️ Architecture

### Project Structure
```
src/main/java/com/sallyvnge/derivativesanalyticsengine/
├── DerivativesAnalyticsEngineApplication.java
├── controller/
│   └── OptionPricingController.java
├── dto/
│   ├── OptionPricingResponseDto.java
│   └── OptionRequestDto.java
├── exception/
│   ├── GlobalExceptionHandler.java
│   └── UnsupportedOrMissingOptionTypeException.java
├── model/
│   ├── Greeks.java
│   ├── OptionType.java
│   └── PricingModel.java
├── service/
│   ├── BlackScholesPricingService.java
│   ├── GreeksCalculatorService.java
│   └── OptionPricingService.java
└── util/
    ├── BlackScholesUtil.java
    └── NormalDistributionUtil.java
```

### Main Components

#### OptionPricingController
REST controller that exposes the option pricing API endpoint at `/api/v1/price`.

#### GlobalExceptionHandler
Centralized exception handling for validation errors and malformed requests, providing structured error responses.

#### OptionPricingService
Main service that orchestrates option price and Greeks calculation.

#### BlackScholesPricingService
Implements pricing calculations according to the Black-Scholes model.

#### GreeksCalculatorService
Calculates option sensitivities (Delta, Gamma, Vega).

## 🌐 API Endpoints

### POST `/api/v1/price`
Calculate option price and Greeks for a given set of parameters.

**Request Body:**
```json
{
  "underlyingPrice": 100.0,
  "strikePrice": 105.0,
  "timeToMaturity": 0.25,
  "riskFreeRate": 0.05,
  "volatility": 0.25,
  "optionType": "CALL"
}
```

**Response:**
```json
{
  "price": 10.45,
  "delta": 0.5234,
  "gamma": 0.0123,
  "vega": 25.67,
  "volatilityUsed": 0.25,
  "pricingModel": "BLACK_SCHOLES",
  "input": {
    "underlyingPrice": 100.0,
    "strikePrice": 105.0,
    "timeToMaturity": 0.25,
    "riskFreeRate": 0.05,
    "volatility": 0.25,
    "optionType": "CALL"
  }
}
```

### Error Responses
The API provides structured error responses for validation failures:

```json
{
  "status": 400,
  "error": "Validation error",
  "fields": {
    "underlyingPrice": "must be greater than 0",
    "volatility": "must be greater than 0"
  }
}
```

## 📋 Input Parameters

To calculate an option price, the following parameters are required:

| Parameter | Type | Description | Constraints |
|-----------|------|-------------|-------------|
| `underlyingPrice` | double | Current price of the underlying asset | > 0 |
| `strikePrice` | double | Option's strike price | > 0 |
| `timeToMaturity` | double | Time to expiration (in years) | > 0 |
| `riskFreeRate` | double | Risk-free rate (decimal) | None |
| `volatility` | double | Annualized volatility (decimal) | > 0 |
| `optionType` | OptionType | Option type (CALL or PUT) | Required |

## 🚀 Installation and Running

### Prerequisites
- Java 17 or higher
- Maven 3.6 or higher

### Installation
```bash
# Clone the repository
git clone <repository-url>
cd derivatives-analytics-engine

# Compile the project
./mvnw clean compile

# Run tests
./mvnw test

# Start the application
./mvnw spring-boot:run
```

The application will be accessible at `http://localhost:8080`

### API Documentation
Once the application is running, you can access:
- **Swagger UI**: `http://localhost:8080/swagger-ui.html`
- **OpenAPI Specification**: `http://localhost:8080/v3/api-docs`

## 🧪 Testing

The project includes comprehensive unit and integration tests:
- `OptionPricingServiceTest` - Unit tests for pricing service
- `BlackScholesPricingServiceTest` - Unit tests for Black-Scholes implementation
- `OptionPricingControllerIT` - Integration tests for the REST API

Run tests:
```bash
./mvnw test
```

## 📚 Documentation

The project includes multiple layers of documentation:
- **Javadoc**: Complete source code documentation
- **OpenAPI/Swagger**: Interactive API documentation
- **Code Comments**: Detailed explanations of mathematical formulas and business logic

The documentation details:
- Input and output parameters
- Mathematical formulas used (Black-Scholes model)
- Validation constraints
- Use cases for different services
- API usage examples

## 🔧 Monitoring

The application includes Spring Boot Actuator for monitoring:
- Health checks
- Application metrics
- Environment information

Accessible via `/actuator/*` endpoints.

## 📝 Version

**Current version:** 0.0.1-SNAPSHOT

## 🤝 Contributing

This project follows Java and Spring Boot development best practices. Contributions are welcome via pull requests.

## 📄 License

This project is licensed under [to be defined].
