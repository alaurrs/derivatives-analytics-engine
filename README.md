# 📈 Volatility Smile & Option Pricing Microservice

A Spring Boot microservice that models a **volatility smile** for equity options and uses it to compute the **theoretical price of European vanilla options** via the **Black-Scholes model**.

---

## 🎯 Features

- 🧊 **Expose a volatility smile** (list of strike/implied volatility points) for a given underlying and expiry
- 📐 **Linear interpolation** of implied volatility for arbitrary strikes
- 💰 **Black-Scholes option pricing** (CALL or PUT)
- 🧠 **Greeks calculation** (Delta, Gamma, Vega – coming soon)
- ⚠️ **Graceful error handling** for out-of-bounds strikes
- 🧪 Easily testable, modular structure (clean separation of concerns)

---

## 📚 API Overview

### `GET /api/v1/smile`

**Params**: `underlying`, `expiry` (ISO format)

**Response**:
```json
{
  "underlying": "AAPL",
  "expiry": "2025-12-01",
  "volatilitySmile": [
    { "strike": 90, "impliedVolatility": 0.32 },
    { "strike": 100, "impliedVolatility": 0.28 },
    { "strike": 110, "impliedVolatility": 0.30 }
  ]
}
```

---

### `GET /api/v1/interpolation`

**Params**: `underlying`, `expiry`, `strike`

**Response**:
```json
{
  "strike": 105,
  "impliedVolatility": 0.29
}
```

---

### `POST /api/v1/price`

**Payload**:
```json
{
  "underlyingPrice": 100,
  "strike": 105,
  "expiry": "2025-12-01",
  "optionType": "CALL",
  "interestRate": 0.01,
  "volatility": 0.29
}
```

**Response**:
```json
{
  "price": 6.12,
  "delta": 0.53,
  "gamma": 0.017,
  "vega": 0.24
}
```

---

## 🧱 Architecture

- `VolatilityPoint`: represents a (strike, volatility) pair
- `VolatilitySmile`: holds and interpolates a smile
- `SmileRepository`: interface to load smiles (mock/in-memory now)
- `VolatilityService`: business logic (interpolation, retrieval)
- `OptionPricingService`: Black-Scholes logic
- REST controllers (Spring MVC)

---

## 🧪 Run the project

```bash
./mvnw spring-boot:run
```

Then open [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) (if using `springdoc-openapi`).

---

## 🚧 Future Improvements

- [ ] Add support for Greeks (Vega, Theta, Rho)
- [ ] Integrate with real market data feeds
- [ ] Persist volatility curves in a database
- [ ] Add unit/integration tests (JUnit5 + Mockito)

---

## 📜 License

MIT (or internal project if private)