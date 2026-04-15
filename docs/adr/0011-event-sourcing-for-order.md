# ADR-0011: Event Sourcing for the Order Aggregate

**Status:** Accepted
**Date:** 2026-04-14

## Context

The Order aggregate has a rich lifecycle with business-significant transitions: placed, confirmed, shipped, delivered, cancelled, refunded. Each transition carries data that is valuable beyond the current state — why an order was cancelled, when it shipped, what the order looked like at confirmation time. Traditional state-based persistence captures only the latest snapshot, discarding this history.

ADR-0009 established CQRS by default. Event sourcing is a natural complement: commands produce events, events are persisted as the source of truth, and read models are projected from the event stream.

## Decision

Event source the Order aggregate. The event store is the sole source of truth for order state. The aggregate is reconstituted by replaying its event stream, and each command handler appends new events rather than mutating a row.

Domain events (e.g. `OrderPlaced`, `OrderConfirmed`, `OrderShipped`, `OrderCancelled`) are first-class objects in the domain layer. The aggregate exposes a method to apply a command, which returns the resulting events. State is derived by folding events over an initial empty state.

## Alternatives Considered

**State-based persistence with an outbox:** Store the current order state in a row and publish events via a transactional outbox. This is simpler to query but loses the guarantee that events and state are the same thing — they can drift. It also discards intermediate states and the full audit trail.

**State-based persistence with an audit log:** Store the current state and append audit records in a separate table. This preserves history but duplicates it: the audit log and the state table can disagree, and the audit log is not the source of truth.

## Consequences

- Events are the source of truth. There is no mutable order row to fall out of sync.
- Full audit trail is inherent — every state transition is recorded with its data and timestamp.
- Reconstituting an aggregate requires replaying its event stream, which grows over time. Snapshotting can be introduced later if performance requires it.
- Read models must be projected from events. This aligns with ADR-0009 (CQRS) — the query side reads from projections, not from the event store directly.
- Developers must think in terms of events and state transitions rather than CRUD updates. This is a shift in mindset but matches the domain language closely.
