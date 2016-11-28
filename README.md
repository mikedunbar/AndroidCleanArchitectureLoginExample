# AndroidSample
Sample Android Application - for experimenting with various APIs, patterns, etc.

## Development History
I just had Android Studio generate a sample login activity application, and then:

- Added a landing page activity that we send the user to after successful login.
- Moved the login logic to the MVP UI architectural pattern (brute force/no framework)
- Added unit tests for the non-Android code
- Use RxJava and RxAndroid for threading support (work in background thread, update UI on main thread)
- Used Dagger 2 for dependency injection
- Gracefully handle activity lifecycle events, so we attach/detach to the presenter appropriately and don't lose TXN status
- Organize classes into a sensible package structure
- Remove 'auto-populate email code', since it detracts from what I want to highlight
- Added a 'browse contacts' screen to showcase use of runtime permissions
- Add an option to bypass login, to quickly access other pages

## Valid login credentials:
- foo@example.com / hello
- bar@example.com / world

## Possible Next Steps
- RetroLambda (or similar) 
- Espresso integration tests
- Instrumentation (or Robolectric) unit tests for Android code
- Use a framework for MVP
