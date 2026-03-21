# ADR-0006: Hexagonal Architecture

**Status:** Accepted
**Date:** 2026-03-21

## Context

ADR-0001 established a modular monolith with compile-time boundary enforcement between business domains. Within each module, we still need to decide how to structure code so that business logic remains independent of infrastructure concerns — databases, HTTP frameworks, message brokers, and so on.

Traditional layered architecture (controller → service → repository) creates a top-down dependency chain where business logic depends on infrastructure types. This coupling makes the domain hard to test in isolation and easy to pollute with framework-specific concerns.

## Decision

We adopt hexagonal architecture (ports and adapters) within each module. Business logic lives at the centre with no dependencies on infrastructure. It defines ports — interfaces describing what it needs from the outside world (secondary/driven ports) and what it offers to the outside world (primary/driving ports). Adapters sit at the edges, implementing or calling those ports to connect the domain to real infrastructure.

The dependency rule is strict: adapters depend on the domain, never the reverse.

## Alternatives Considered

**Traditional layered architecture:** Controllers call services, services call repositories. Simple and well-understood, but the dependency arrows point downward through the layers — business logic ends up depending on persistence types. Testing a service typically requires mocking repository implementations or spinning up a database.

**Clean architecture:** Conceptually similar to hexagonal but prescribes more layers (entities, use cases, interface adapters, frameworks). The additional layering adds structure that isn't justified at the current scale of the project. Hexagonal gives us the same core benefit — dependency inversion at the domain boundary — with less ceremony.

## Consequences

- Business logic is expressed as plain Java with no framework annotations or infrastructure types. It can be tested with simple unit tests and lambda-based test doubles rather than mocks or containers.
- Each infrastructure concern (persistence, HTTP, messaging) is isolated in its own adapter. Swapping or upgrading an adapter does not require changes to the domain.
- Ports must be defined explicitly as interfaces, which adds some upfront design work compared to calling a repository directly from a service.
- Java's visibility model (`public`/package-private) does not fully enforce the dependency rule — an adapter can still import domain internals if they are public. ADR-0008 introduces ArchUnit to enforce this automatically at build time.
