# users-list
## Description
This android app shows a list of users that are stored in a local database. It only has two screens: the users list fragment, 
which allows you to see and delete users that you have already added, and the users manipulation fragment that lets you add or edit them.

Although the task did not required to store the users in the local storage, I used room to persist them and to be able to recover them even after
the application closes.

## Libraries
For this project I used:
- MVVM with Jetpack Architecture components (https://developer.android.com/jetpack/guide)
- Hilt for dependency injection (https://developer.android.com/training/dependency-injection/hilt-android)
- Coroutines (https://developer.android.com/kotlin/coroutines)
- Room (https://developer.android.com/training/data-storage/room)
- Navigation (https://developer.android.com/guide/navigation)
- LiveData ktx (https://developer.android.com/kotlin/ktx#livedata)
