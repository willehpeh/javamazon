# ADR-0005: Gradle Over Maven

**Status:** Accepted
**Date:** 2026-03-19

**Supersedes:** ADR-0002 (Maven Over Gradle)

## Context

ADR-0002 chose Maven for its familiarity and predictability. After further consideration, we revisited that decision. The project is a learning exercise, and optimizing for familiarity works against that goal.

## Decision

We use Gradle (with the Kotlin DSL) as the build tool. Gradle is the modern standard in the Java ecosystem — Spring Boot, Android, and most contemporary Java projects use it. It offers a flexible, programmable build model and faster incremental builds. Its primary downside is a steeper learning curve, which in this context is a feature, not a drawback.

## Alternatives Considered

**Stay with Maven (ADR-0002):** Familiar and predictable, but optimizing for comfort over growth. Maven's rigidity, which was framed as a strength in ADR-0002, is also a ceiling.

## Consequences

- The build system needs to be migrated from Maven POM files to Gradle build scripts.
- Build logic is written in Kotlin, which is more expressive but requires understanding a programming language beyond XML configuration.
- The project aligns with modern Java ecosystem conventions and tooling.
