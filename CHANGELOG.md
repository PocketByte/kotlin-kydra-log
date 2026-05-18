# Changelog

## [3.0.1] - in progress

### Documentation

- Conducted a full audit and rewrite of the KDoc documentation across the entire public API, covering everything from core classes to platform-specific implementations, while fixing grammar issues, inaccuracies, and missing documentation.
- Fixed typo in parameter name: `levelFiler` → `levelFilter` in `FilteredLoggerWrapper` and `FilteredLogger+Builders.kt`.

## [3.0.0] - 2026-05-18

### Breaking Changes

- **New packages for wrappers and collections** — Logger wrappers (`AbsLoggerWrapper`, `FilteredLoggerWrapper`, `InitializableLogger`, `LoggerTagTransform`, `LoggerToggle`) moved to `ru.pocketbyte.kydra.log.wrapper`; collection types (`LoggersSet`, `ExtendableLoggersSet`) moved to `ru.pocketbyte.kydra.log.collection`.
- **`KydraLog` extracted to a separate module** — `plug-and-play` is now a standalone module; consumers who only need the core library no longer pull in `KydraLog`.
- **Deprecated API removed or promoted** — `AbsPrintLogger` and `NSLogger` removed; previously soft-deprecated symbols are now errors. `PrintLogger` (JVM) simplified to delegate to `SimplePrintLogger`.

### New Features

- **`ThrowableWithMessage`** — new wrapper that attaches a custom message to any `Throwable`, useful when you need to override the exception message in log output.
- **Tag prefix/postfix transforms** — `LoggerTagTransform` now ships builder helpers to prepend or append a fixed string to every log tag.

### Improvements

- `InitializableLogger.init` extension is now `crossinline`-safe; logger function parameters marked `crossinline` across the API for better inlining.
- Default Android logger is stable in test environments.
- `AbsLoggerWrapper.logger` is now `protected` (was package-private).
- JVM `PrintLogger` refactored to reuse `SimplePrintLogger` internals.

### Build / Infrastructure

- Migrated to **Kotlin 2.3.10**.
- All dependencies migrated to **Gradle Version Catalog** (TOML).
- Build logic extracted into a **build-logic convention plugin**.
- WASM target restored.
