# ADR-0007: Package-per-Use-Case

**Status:** Accepted
**Date:** 2026-03-21

## Context

ADR-0006 established hexagonal architecture within each module. We now need to decide how to organize packages inside that structure. The most common approach is package-by-layer — grouping all ports together, all services together, all adapters together. This makes everything within a layer visible to everything else, which undermines the isolation that hexagonal architecture is trying to achieve.

## Decision

We organize code by use case. Each business operation gets its own package named by what the business does, not by CRUD verbs. Each package contains a handler (public, called directly by adapters) and any supporting types such as request DTOs. Internal helpers are package-private. Classes that have no reason to be public are not.

Instead of a god-class `ProductService` containing every operation, each use case gets its own handler — `AddToCatalogHandler`, `RepriceProductHandler`, `LookupProductHandler`. This is explicit, testable, and naturally enforces single responsibility.

Shared domain types that multiple use cases reference live in vocabulary packages named by business concept — `product/`, `category/` — not by architectural layer. A vocabulary package contains the entity, its identity type, and the port interface (e.g., `Product`, `ProductId`, `ProductRepository`). These are pure Java with no framework annotations.

Adapters live in dedicated packages at the module edge:

- **`api/`** — the REST entry point. Controllers translate HTTP to use case handler calls. They depend on handlers, not on domain internals.
- **`infrastructure/persistence/`** — database implementation. JPA entities, Spring Data interfaces, repository adapters. The `infrastructure/` parent gives future concerns (messaging, external APIs, file storage) a natural home.

Example layout for the catalog module:

```
catalog/
  addtocatalog/                      # use case
    AddToCatalogHandler.java
    AddToCatalogRequest.java
  discontinue/
    DiscontinueProductHandler.java
  reprice/
    RepriceProductHandler.java
    RepriceRequest.java
  search/
    SearchCatalogHandler.java
    SearchCriteria.java
  lookup/
    LookupProductHandler.java
  product/                           # vocabulary
    Product.java
    ProductId.java
    ProductRepository.java           # port (interface)
  category/                          # vocabulary
    Category.java
    CategoryId.java
  api/                               # REST entry point
    CatalogController.java
    CatalogExceptionHandler.java
  infrastructure/
    persistence/                     # database adapter
      ProductEntity.java
      CategoryEntity.java
      JpaProductRepository.java
      PostgresProductRepository.java
```

## Alternatives Considered

**Package-by-layer:** All ports in one package, all services in another, all adapters in a third. This is the default in most tutorials and codebases. The problem is that every class in a layer must be public so that the adjacent layer can reach it, which means any class in the module can reach it too. The layered structure becomes documentation rather than enforcement.

**Package-by-feature:** Group by business feature (e.g., `order`, `catalog`) rather than by layer. Better cohesion than package-by-layer, but at the module level this is already what ADR-0001 gives us. Within a module, features tend to be too coarse — a single feature may contain many unrelated use cases that don't need to see each other's internals.

## Consequences

- Internal classes (handlers, domain logic) stay package-private, limiting the blast radius of changes to a single use case package.
- Each use case is self-contained and independently testable. Adding a new use case means adding a new package, not modifying existing ones.
- The number of packages grows with the number of use cases. Navigation requires familiarity with the domain rather than a fixed set of layer names.
- Even simple read-only queries get the full use case package structure. This trades conciseness for predictability — every use case looks the same, so navigating the codebase never requires guessing where something lives.
- Vocabulary packages need discipline — they should contain only genuinely shared domain types, not become a dumping ground that couples use cases together.
