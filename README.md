# Android Patterns Demo

A small movie library app implemented with five Android presentation patterns.

One product with three screens (Login, List, Movie) is built five times: with MVC, MVP, MVVM, MVI and VIPER. All variants share the same business logic, so the differences between the patterns are easy to compare.

## Modules

| Module | Pattern | Package |
|---|---|---|
| `core` | Shared pure-Java logic: models, use cases, in-memory repositories, `AppContainer` | `org.demo.patterns.core` |
| `mvc` | Model–View–Controller | `org.demo.patterns.mvc` |
| `mvp` | Model–View–Presenter | `org.demo.patterns.mvp` |
| `mvvm` | Model–View–ViewModel | `org.demo.patterns.mvvm` |
| `mvi` | Model–View–Intent | `org.demo.patterns.mvi` |
| `viper` | View–Interactor–Presenter–Entity–Router | `org.demo.patterns.viper` |

## Requirements

- JDK 17
- Android SDK 34

## Build and test

```sh
./gradlew test                  # unit tests for all modules
./gradlew :mvvm:assembleDebug   # build one app (use any pattern module name)
```

## Test users

| Login | Password |
|---|---|
| `user` | `1234` |
| `admin` | `1234` |

## Simplifications

This is a teaching project. By design:

- Data is stored in memory and resets on restart.
- UI strings are hard-coded in the code, not in resources.
- There is no DI framework; `AppContainer` wires dependencies by hand.
- There is no asynchronous code; all calls are synchronous.
