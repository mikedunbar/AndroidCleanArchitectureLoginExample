# AndroidSample
Sample Android Application

I just had Android Studio generate a sample login activity application, and moved the login logic to the MVP UI architectural pattern.
I didn't get a chance to move the auto-populate email code to MVP, but that would be a next step. I just ran out of time.

I also added a landing page activity that we send the user to after successful login.

I added unit tests for the non-Android code. A next step would be to add Android instrumentation (or Robolectric) tests for the activity.
Again I just ran out of time. But one of the main benefits of MVP is that we move as much code as possible out of Android-dependent classes
like activities and into Plain Old Java Object classes for easier testabiliity.
