# Clean Architecture Login Example
Sample login screen, for showcasing the Clean architecture approach in Android.

See the [definitive article](https://8thlight.com/blog/uncle-bob/2012/08/13/the-clean-architecture.html) for more info

## Development History
I just had Android Studio generate a sample login activity application, and then:

- Added a landing page activity, where we send the user after successful login.
- Moved the logic to the MVP UI architecture pattern
- Remove 'auto-populate email code', since it detracts from what I want to highlight
- Added unit tests for the core (non-Android) code
- Use RxJava and RxAndroid for threading support (work in background thread, update UI on main thread)
- Use Dagger 2 for dependency injection
- Handle activity start/stop, so we attach/detach to the presenter appropriately and don't lose TXN status

## Valid login credentials:
- foo@example.com / hello
- bar@example.com / world

## Possible Next Steps
- RetroLambda (or similar) 
- Espresso integration tests
- Instrumentation (or Robolectric) unit tests for Android code
- Use a framework for MVP
