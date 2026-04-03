# ADR-0010: Data Contracts May Cross the API Boundary

**Status:** Accepted
**Date:** 2026-04-03

## Context

ADR-0006 established hexagonal architecture with a strict dependency rule: adapters depend on the domain, never the reverse. Commands and requests entering the system from API adapters should use primitive or standard types so that the adapter remains ignorant of domain internals.

However, some domain types are pure data structures — sealed interfaces of records with no behaviour, no validation, and no invariants. `PromotionTarget` is an example: it is a discriminated union (`AllProducts`, `ByCategory`) that flows unchanged from the HTTP request body, through the command, into the handler, through the entity, and into a JSONB column. Converting between identical structures at every boundary adds complexity without protecting any invariant.

We need a clear rule for when a domain type may appear in a command or cross the API boundary.

## Decision

Sealed interfaces and records that are pure data contracts — carrying no behaviour, no validation logic, and no invariants — may be used in commands and may cross the API boundary. Domain types that enforce invariants (value objects such as `PromotionDiscountPercent`, `ProductName`, `Money`) must not cross the API boundary; the handler is responsible for constructing them from primitives.

The distinction is behaviour, not package location. If a type validates, constrains, or transforms its inputs, it belongs to the domain and must be constructed inside the handler. If it is just a shape of data, it may flow freely.

## Alternatives Considered

**Primitives everywhere:** Represent targets as a `String` type discriminator plus a `Map<String, Object>` for associated data. This keeps commands fully primitive but introduces stringly-typed mapping logic in the handler, loses compile-time exhaustiveness checking from the sealed interface, and duplicates the structure that already exists in the domain.

**Separate DTO hierarchy:** Create a parallel set of records (`PromotionTargetRequest`, `AllProductsRequest`, `ByCategoryRequest`) in the API adapter layer, then map them to the domain types in the handler. This maintains strict layer separation but the DTO types would be structurally identical to the domain types, adding pure ceremony.

## Consequences

- Types like `PromotionTarget` flow from HTTP to database with no intermediate mapping, reducing boilerplate.
- Jackson's `@JsonTypeInfo` on the sealed interface handles serialisation at both the HTTP and persistence boundaries.
- The rule requires judgement: developers must assess whether a type is a pure data contract or a behavioural value object. When in doubt, keep it in the domain and use primitives in the command.
- If a data contract later gains validation logic, it must be pulled back behind the handler boundary and the command updated to use primitives. This is a straightforward refactor.
