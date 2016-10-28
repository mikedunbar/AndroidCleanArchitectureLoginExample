# AndroidSample
Sample Android Application - for experimenting with various APIs, patterns, etc.

## Development History
I just had Android Studio generate a sample login activity application, and then:

- Added a landing page activity that we send the user to after successful login.
- Moved the login logic to the MVP UI architectural pattern
- Added unit tests for the non-Android code
- Used Dagger 2 for dependency injection

## Valid login credentials:
- foo@example.com / hello
- bar@example.com / world

## Notes
- The threading support in AbstractUseCase is definitely more complicated that just using an
  AsynchTask, but it only needs to be done that once and all use cases inherit support for
  running off of the UI thread until results are available. I think it's worth it to decouple that
  core business logic from Android, but there may be better ways to still achieve that.

## Possible Next Steps
- Simplify the threading code, maybe by using RxJava 
- move the auto-populate email code to MVP, but that would be a next step. I just ran out of time.
- Instrumentation (or Robolectric) unit tests for Android code
- Espresso integration tests
