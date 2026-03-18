# ADR-0004: OpenTelemetry as Instrumentation Framework

**Status:** Accepted
**Date:** 2026-03-19

## Context

Having adopted a unified, wide-event approach to observability (see ADR-0003), we need an instrumentation framework that supports emitting structured, high-cardinality events with trace context from application code.

## Decision

We use OpenTelemetry as the instrumentation framework. It provides vendor-neutral APIs for producing spans enriched with arbitrary attributes, which aligns directly with the wide-event model. Application code depends only on the OpenTelemetry API — the choice of observability backend is a deployment concern, not an application concern.

## Alternatives Considered

**Vendor-specific SDKs (Honeycomb, Datadog, New Relic, etc.):** Capable of producing wide events, but couple application code to a specific backend. Migrating the instrumentation layer is expensive, and these decisions outlast vendor contracts.

**Direct SLF4J/Logback structured logging:** Can produce structured events, but lacks native trace context propagation and the span model that gives wide events their power in distributed systems.

## Consequences

- Application code is decoupled from the observability backend. Switching backends requires configuration changes, not code changes.
- The team needs to learn OpenTelemetry's API and concepts (spans, attributes, context propagation).
- OpenTelemetry is an actively evolving project — we accept that APIs and conventions may shift, and prefer tracking the standard over locking to a stable but proprietary alternative.
