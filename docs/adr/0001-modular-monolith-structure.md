# ADR-0001: Modular Monolith Structure

**Status:** Accepted
**Date:** 2026-03-18

## Context

JavAmazon is an e-commerce platform with several distinct business domains. We needed to decide how to organize the codebase to enforce domain boundaries while keeping development and deployment simple in the early stages of the project.

## Decision

We adopted a modular monolith architecture using Maven multi-module projects. Each business domain gets its own Maven module with its own source tree and test suite. A shared `common` module provides cross-cutting types and utilities that multiple domains depend on.

Dependencies between domain modules should be avoided unless explicitly justified.

## Alternatives Considered

**Single-module monolith:** All code in one module with package-based separation. Simpler to set up but provides no compile-time enforcement of domain boundaries. Nothing prevents one domain from reaching into another's internals.

**Microservices from the start:** Each domain as an independently deployable service. Microservices are primarily a technical solution to an organizational problem — enabling independent teams to deploy independently. That organizational problem does not exist at the moment. Adopting microservices prematurely would introduce network boundaries, distributed data management, and operational complexity with no offsetting benefit.

## Consequences

- Domain boundaries are enforced at compile time via Maven module dependencies. Code in `order` cannot accidentally import code from `catalog` unless an explicit dependency is declared.
- Each module can have its own dependency set, keeping classpaths focused.
- The `common` module requires discipline — it should contain only genuinely shared types, not become a dumping ground that couples all modules together.
- Migrating an individual module to a standalone service in the future is straightforward since it already has clear boundaries, its own source tree, and an explicit dependency graph.
