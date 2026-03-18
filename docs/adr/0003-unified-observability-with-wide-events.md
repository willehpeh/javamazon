# ADR-0003: Unified Observability With Wide Events

**Status:** Accepted
**Date:** 2026-03-19

## Context

Traditional observability relies on three separate pillars — logs, metrics, and traces — each with its own tooling, storage, and query model. This fragmentation is not just an operational inconvenience. The deeper problem is that these signals are typically low-cardinality and pre-aggregated, meaning you must predict which questions you'll need to answer before an incident occurs. When something unexpected happens, you're stuck with the dimensions you pre-chose.

## Decision

We adopt the unified observability approach described in *Observability Engineering* (Charity Majors, Liz Fong-Jones, George Miranda). Instead of logs, metrics, and traces as separate concerns, we instrument using wide events — structured, high-cardinality events that capture rich context about a unit of work. This allows us to ask arbitrary questions after the fact, slicing and filtering across any dimension without having anticipated the query in advance.

In practice, this means instrumentation is about enriching the current event with contextual fields (user ID, tenant, feature flag state, query parameters, etc.) rather than emitting separate log lines or incrementing pre-defined counters.

## Alternatives Considered

**Traditional three-pillar approach:** Separate libraries and backends for logging, metrics, and tracing. Requires predicting which dimensions matter upfront. When an incident involves an unanticipated combination of factors, the data to investigate it simply doesn't exist.

## Consequences

- Debugging starts from exploring high-cardinality fields and slicing across dimensions, rather than grepping logs or staring at dashboards.
- Developers need to think about "what dimensions would help me understand this request?" at instrumentation time, rather than sprinkling log statements reactively.
- Pre-aggregated metrics and dashboards become derived outputs rather than primary investigation tools.
