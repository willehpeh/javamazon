# Code Review — Javamazon

## Bug

### 1. `ProductEntity.fromProductState()` overwrites `createdAt` on every save
**File:** `catalog/src/main/java/.../infrastructure/persistence/product/ProductEntity.java:50`

`entity.createdAt = LocalDateTime.now()` is called every time, including on updates. When `addOrUpdate` is called for a reprice or discontinue, the product's `createdAt` will be reset. This is data corruption.

---

## Design Issues

### ~~2. `Money` rejects valid inputs unnecessarily~~ FIXED
**File:** `common/src/main/java/.../common/Money.java:8-9`

`new Money(new BigDecimal("1"))` throws because scale isn't exactly 2. This means `new BigDecimal("10.0")` or `new BigDecimal("5")` are rejected. The constructor should normalize to scale 2 via `setScale(2)`, not reject valid monetary amounts. This is especially painful because `AddProductCommand` accepts a raw `BigDecimal` from the API — a client sending `{"price": 10}` (which Jackson deserializes as scale 0) will blow up.

### 3. `DateRange` exists but `Promotion` doesn't use it
**File:** `catalog/src/main/java/.../promotion/Promotion.java:15-16`

Stores `startDate` and `endDate` as raw `LocalDate` fields and duplicates the "start before end" validation that `DateRange` already handles. This is exactly what `DateRange` was built for. Same issue in `PromotionEntity` and `Promotion.State`.

### ~~4. `Categories` repository is scoped to a use-case package~~ FIXED
**File:** `catalog/src/main/java/.../category/Categories.java`

The repository interface lives inside `definecategory/` — a use-case package. But `Category` is an aggregate with its own `category/` package. The repository interface is a domain concept and should live alongside the aggregate (in `category/`), not inside a single use case. Contrast with `Products` which correctly lives in `catalog/product/`.

### ~~5. `ProductPrice` is a class, not a record~~ FIXED
**File:** `catalog/src/main/java/.../product/ProductPrice.java`

All other value objects (`ProductId`, `ProductName`, `ProductDescription`, `CategoryId`, `CategoryName`, `PromotionId`, etc.) are records. `ProductPrice` is a plain class, so it lacks `equals`/`hashCode`/`toString` — you can't compare two prices, and debugging output will be `ProductPrice@3a4f1c`. This breaks the value object contract.

### ~~6. `Product.State` leaks `BigDecimal` instead of `Money`~~ FIXED
**File:** `catalog/src/main/java/.../product/Product.java:38`

`Product.State` uses `BigDecimal price` rather than carrying the `Money` type. Infrastructure code that reads the state must know to reconstruct `Money` from a raw `BigDecimal`, and the scale-2 invariant of `Money` is silently lost. The state record could use `Money` directly.

### 7. `SearchCatalogRequest.withCategory()` is a misleading instance method
**File:** `catalog/src/main/java/.../search/SearchCatalogRequest.java:15-17`

`withCategory` creates a new instance, but it's called on an existing instance that gets thrown away. It reads like a builder but it's not — it's a factory method that should be static or removed. The no-arg constructor + `withCategory` pattern is confusing.

### 8. No idempotency guard on `AddProductHandler`
**File:** `catalog/src/main/java/.../addproduct/AddProductHandler.java`

If the same `AddProductCommand` is sent twice with the same `productId`, it silently overwrites. The `addOrUpdate` naming suggests this is intentional, but then `AddProduct` is a misleading name — it implies creation only. Either guard against duplicates or rename to reflect upsert semantics.

---

## Incomplete / Missing

### 9. `CatalogController` only exposes 2 of 7 use cases
**File:** `catalog/src/main/java/.../api/CatalogController.java`

There are 5 command handlers and 2 query handlers, but only `AddProduct` and `LookupProduct` are wired up as endpoints. Reprice, discontinue, search, define category, and schedule promotion have no API exposure. The acceptance tests can only cover what's exposed.

### 10. No validation annotations on most commands
**Files:** `SchedulePromotionCommand.java`, `RepriceProductCommand.java`, `DiscontinueProductCommand.java`, `DefineCategoryCommand.java`

`AddProductCommand` has `@NotNull`, `@NotBlank`, `@Positive` — good. The other commands have zero validation annotations, so garbage can flow through the API layer without any 400 response.

### 11. Acceptance test category creation bypasses the domain
**File:** `catalog/src/acceptanceTest/java/.../driver/HttpCatalogDriver.java:25-27`

`createCategory` uses raw JDBC to insert directly into the database, bypassing the `DefineCategoryHandler` entirely. This means the define-category use case is never acceptance-tested. Once a category endpoint exists, the driver should use it.

### 12. Missing `NOT NULL` constraint on `target_data`
**File:** `catalog/src/main/resources/db/migration/V4__create_promotion_table.sql`

`target_data JSONB` allows null. A promotion without a target is meaningless. Should be `NOT NULL`.
