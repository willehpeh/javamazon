# ADR-0009: CQRS by Default

**Status:** Accepted
**Date:** 2026-03-21

## Context

ADR-0007 established package-per-use-case as the internal structure for each module. We now need a convention for how use cases interact with data — specifically, whether the same model and code path should handle both reads and writes.

## Decision

We adopt Command Query Responsibility Segregation (CQRS) as the default pattern. Commands (writes) and queries (reads) are handled by separate use case handlers with separate models where appropriate.

- **Command handlers** operate on domain entities through ports. They enforce invariants and modify state.
- **Query handlers** are not required to go through the domain's repository port. They can define their own read-specific ports or query the database directly through dedicated read projections, views, or SQL.

This aligns naturally with the package-per-use-case structure from ADR-0007 — each use case is already its own package, so commands and queries are separated by default.

CQRS is the default, not a mandate. If a module's read and write models are identical and likely to stay that way, a simpler approach can be used — but this should be a conscious exception, not the default path.

## Alternatives Considered

**Shared read/write model:** One model for everything. Simple and familiar, but read and write concerns inevitably pull the model in different directions. Query performance suffers when reads must deserialize full aggregate graphs, and domain logic gets polluted with presentation concerns.

**Event Sourcing everywhere:** State derived from a sequence of events rather than stored directly. Powerful for audit and temporal queries, but adds significant complexity in storage, replay, and eventual consistency. Not appropriate as a blanket default — a separate ADR will cover selective use of event sourcing for core modules.

## Consequences

- Read and write paths are decoupled. Query handlers can be optimized independently — using database views, denormalized tables, or direct SQL — without affecting command-side domain logic.
- Developers must decide for each new feature whether it is a command or a query. This is a small overhead that reinforces intentional design. ADR-0007's package-per-use-case structure already separates handlers by business operation, so the command/query distinction names what was already implicit.
- Core modules (e.g., order, payment) can adopt event sourcing on top of CQRS without rearchitecting, since the command/query separation is already in place. Supporting modules can stay with straightforward CRUD.
