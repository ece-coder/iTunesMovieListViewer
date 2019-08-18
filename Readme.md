# iTunes Movie List Viewer
This app displays the available movies from iTunes (AU) and show some details such as price, genre
and synopsis. The app uses Retrofit, Dagger2, Glide, Room and RxJava2. The code was written using Kotlin.
Created by Jibriel Ong
## MVVM Software Architectural Pattern (Model-View-ViewModel)
* Using MVVM, a Viewmodel is used to separate the View and the Model of the app
* The Viewmodel controls the view using Databinding
* Using Viewmodel, there's a persistence during configuration changes while holding data for UI. So
when the screen changes orientation, it will still retain the data properly.
## Observer Pattern
* Use to observe when there's a change of state in one object
* Useful when dealing with database (Room) and API calls
* Easier to handle observable objects and run asynchronous tasks in the background
## Repository Pattern
* Using Repository to have an abstraction of the data layer
* Useful when working with database
* Use when handling data from different sources such as APIs and databases
## Persistence Mechanism
### SharePreference
The SharedPreference was used in the app to store the reference of the last screen of the user
before turning off the app. An integer is used to represent a specific screen to be restored.
So when the user opens the app, it will check first the value stored in the SharePreference
and load the corresponding page if possible.
### Room Persistence
The Room Persistence Library was used to save iTunes data in a database. The Room acts as an
abstraction layer for the existing SQLite APIs.

The iTunes data from the API calls will be converted to another data type and will be stored
in a database. By doing that, API calls are not required every start of the app as long as there
are data in the database. It can save significant mobile data consumed by the app. It can be also
used to support offline mode, the user will still experience viewing the list if there's no
internet connection as long as the previous data are stored.

Every time the app starts, it will check if the database is empty and has an internet connection.
If empty, it will trigger the API to get iTunes Movie list. Otherwise, it will just load the content from
the database. The user can also manually refresh the the database by swiping down the screen to get
updated iTunes list from the API and store it in the database.

The date and time when the user last visited a certain movie is also stored in the database. It will trigger
every time an item is clicked from the list.
