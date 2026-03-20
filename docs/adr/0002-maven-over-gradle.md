# ADR-0002: Maven Over Gradle

**Status:** Superseded by ADR-0005
**Date:** 2026-03-18

## Context

We needed to choose a build tool for the project. The two mainstream options in the Java ecosystem are Maven and Gradle.

## Decision

We chose Maven. It is the "boring" choice — declarative, rigid, and extremely well understood across the Java ecosystem. Nearly every Java developer has worked with Maven, and its convention-over-configuration model means builds behave predictably with minimal effort.

## Alternatives Considered

**Gradle:** More flexible and performant for large builds. However, that flexibility comes at a cost: build logic is written in a programming language (Groovy or Kotlin), which means build scripts can diverge across projects, are harder to read at a glance, and can require real debugging. That power is not needed here.

## Consequences

- Any Java developer can understand and contribute to the build immediately.
- The build is declarative and predictable — there is no imperative build logic to reason about or debug.
- Maven's rigidity means some tasks that would be simple in Gradle may require plugins or workarounds.
