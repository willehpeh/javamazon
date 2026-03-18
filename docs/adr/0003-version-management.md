# ADR-0003: Version Management With Hardcoded Versions

**Status:** Accepted
**Date:** 2026-03-18

## Context

In a Maven multi-module project, the parent version in each child module's `<parent>` block must be hardcoded — Maven does not allow `${project.version}` there. We needed a strategy for keeping versions aligned across modules when bumping the project version.

## Decision

We hardcode the version in all POM files and use `mvn versions:set -DnewVersion=X.Y.Z` to update them in a single command.

## Alternatives Considered

**`${revision}` property with flatten-maven-plugin:** Allows defining the version once as a property and referencing it everywhere. However, it requires the flatten-maven-plugin to rewrite POMs at build time, adding indirection and plugin machinery for a problem that `versions:set` already solves simply.

## Consequences

- Version bumps require running a single command rather than editing one property.
- POMs are explicit and readable — every version is visible as a literal value, with no indirection.
- All standard Maven tooling works without additional plugin configuration.
