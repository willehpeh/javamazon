# ADR-0000: Use ADRs With Nygard Format

**Status:** Accepted
**Date:** 2026-03-18

## Context

As the project grows, architectural decisions need to be recorded so that future contributors can understand why things are the way they are — not just what was decided, but what was considered and rejected.

## Decision

We record significant architectural decisions as Architecture Decision Records (ADRs) using the Michael Nygard format: Status, Context, Decision, Alternatives Considered, and Consequences. ADRs are stored in `docs/adr/` and numbered sequentially. ADRs are immutable once accepted — if a decision is reversed, a new ADR supersedes the old one rather than editing it.

## Alternatives Considered

**MADR (Markdown Any Decision Records):** More structured with explicit decision drivers, options, and pros/cons tables. More rigorous but heavier than we need at this stage.

**No formal records:** Rely on commit messages, PR descriptions, and tribal knowledge. This works until someone leaves or a new contributor joins and asks "why?"

## Consequences

- Decisions are discoverable and self-contained — each ADR stands on its own.
- The format is lightweight enough that writing an ADR is not a burden, making it more likely they actually get written.
