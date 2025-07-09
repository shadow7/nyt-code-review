# Android Martian News Reader
This app is a news reader for both, humans and martians. The app has 3 screens. The main screen (or
landing page), contains a list of articles. When tapping an article the user is taken to de details 
screen. There is also a language selector screen to allow the user select between two languages (English
or Martian).

## Architectural overview
This is a single activity app, each screen is a fragment and we use [androidx navigation](https://developer.android.com/guide/navigation)
to navigate between fragments. We use [Hilt](https://developer.android.com/training/dependency-injection/hilt-android)
for dependency injection and [Retrofit](https://github.com/square/retrofit) for API calls. We are using
XML for the UI, and [LiveData](https://developer.android.com/topic/libraries/architecture/livedata)
to get UI states updates.

We are using the MVVM architecture with a Repository pattern. There is a Shared ViewModel, scoped to 
the activity. This Shared ViewModel gets the data from an API Call through the Repository using Retrofit
and updates the states, which updates the views using [LiveData](https://developer.android.com/topic/libraries/architecture/livedata).
The reason why a Shared ViewModel is used, is to hoist the state through all our different fragments,
so when we get an update anywhere in the app, all observers (Fragments listening to LiveData changes)
will update their state.