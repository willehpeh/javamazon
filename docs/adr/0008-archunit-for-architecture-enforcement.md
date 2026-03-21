# ADR-0008: ArchUnit for Architecture Enforcement

**Status:** Proposed
**Date:** 2026-03-21

## Context

ADR-0006 established the hexagonal dependency rule (adapters depend on the domain, never the reverse) and ADR-0007 defined the package-per-use-case structure with specific visibility constraints. Java's package-private visibility helps, but does not fully enforce these rules — any public class can be imported from anywhere within the module. Without automated enforcement, architectural rules erode over time through well-intentioned shortcuts that pass code review.

## Decision

We use ArchUnit to enforce architectural rules as unit tests. Each module includes ArchUnit tests that verify:

- **Dependency direction:** Domain packages (`product/`, `category/`, use case packages) do not depend on adapter packages (`api/`, `infrastructure/`). The dependency rule from ADR-0006 is checked at build time.
- **No framework annotations in the domain:** Classes in vocabulary and use case packages do not use Spring, JPA, or other framework annotations. Domain logic stays as plain Java.
- **Adapter isolation:** Persistence adapters do not depend on web adapters and vice versa. Each adapter concern is independent.

These tests run as part of the standard `./gradlew test` cycle. A violation fails the build.

## Alternatives Considered

**JPMS (Java Platform Module System):** Provides compile-time enforcement of module boundaries via `module-info.java`. Stronger guarantees than ArchUnit, but adds significant complexity — every module needs explicit exports and requires declarations, and framework compatibility (Spring, JPA) with JPMS is still rough. Overkill for the current scale of the project.

**Code review only:** Rely on discipline and review to catch violations. Works for small teams in the short term, but architectural drift is invisible until it accumulates. A reviewer has to hold the full set of rules in their head for every PR.

**ArchUnit + JPMS:** Use both — ArchUnit for fine-grained rules within a module, JPMS for hard boundaries between modules. A reasonable future evolution, but not justified yet.

## Consequences

- Architectural rules from ADR-0006 and ADR-0007 become executable and self-documenting. New contributors learn the rules by reading the tests.
- Violations are caught immediately in the build, not days later in code review.
- ArchUnit is added as a test dependency across modules via the convention plugin (ADR-0005).
- Rules need to be maintained as the architecture evolves. A rule that no longer reflects reality should be updated or removed, not suppressed.
