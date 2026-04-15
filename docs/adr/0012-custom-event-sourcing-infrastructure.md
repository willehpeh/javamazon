# ADR-0012: Custom Event Sourcing Infrastructure

**Status:** Accepted
**Date:** 2026-04-14

## Context

ADR-0011 decided to event source the Order aggregate. The next question is how to implement the event sourcing infrastructure.

Frameworks like Axon provide event sourcing out of the box, but they own the aggregate lifecycle: aggregates must extend framework base classes, command handlers are discovered via framework annotations, and command routing is managed by the framework's command bus. This means infrastructure reaches into the domain and application layers — the framework becomes the architecture. This violates the hexagonal boundary established in ADR-0006, where the domain has no dependencies on infrastructure.

ADR-0009 established CQRS with command handlers routed through a mediator. An event sourcing framework would introduce a second command routing mechanism, duplicating responsibility and splitting the application layer across two dispatch models.

## Decision

Build event sourcing infrastructure as a single port interface — the **EventStore** — with adapter implementations for in-memory (testing) and PostgreSQL (production). Command handlers continue to use the existing mediator from ADR-0009.

Command handlers are functions, not aggregate methods. A handler loads the event stream from the EventStore, passes it along with the command data to a pure decision function, and appends the resulting events back to the store. The decision function takes a stream of past events and command data as input, and returns a stream of new events. There is no aggregate object to reconstitute — domain logic lives in stateless functions that derive their decisions directly from the event history.

## Alternatives Considered

**Axon Framework:** Provides event sourcing, command handling, projections, and sagas. Mature and well-documented. However, aggregates must extend `AggregateRoot`, commands are routed via `@CommandHandler` annotations on the aggregate, and the framework manages the aggregate lifecycle. This couples the domain to Axon and replaces the mediator with Axon's command bus.

**Marten (JVM port or similar):** Document-oriented event store libraries tend to be lighter-touch than Axon but still impose conventions on aggregate shape and event serialisation. The JVM ecosystem options are less mature.

## Consequences

- More code to write and maintain — event store schema and stream loading are our responsibility.
- Decision functions are pure: events in, events out. They are trivially testable with no infrastructure or mocks.
- The in-memory EventStore adapter enables fast, deterministic acceptance tests without a database.
- The existing mediator remains the single entry point for command dispatch, keeping the application layer consistent across all packages.
- The EventStore is the only infrastructure port — it can be swapped, evolved, or optimised independently of the domain.
