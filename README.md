# Gallery App
The app shows a list of photos with some information. 
## The app feature covers the following topic:
- [x] Jetpack compose is used for UI design.
- [x] Data fetching from the server and store in local DB:
    - [x] Retrofit for API Calling
    - [x] Background Service component
    - [x] Service is created into the same process in UI.
    - [x] Coroutine used for handling parallelism
    - [x] Broadcast messaging for letting know the data fetching status (downloading or completed)
- [x] Provide API to get data from Local DB to UI
  - [x] Room library is used for CRUD operation
- [x] Dagger Hilt is used for handling dependency injection
- [x] App Architecture and Good coding practices are followed
  - [x] Data layer, Domain layer, UI layer is used to handle Separation of Concert (SoP)
  - [x] Clean Architecture Concept is used
  - [x] MVVM pattern used.
  - [x] The following principles are covered to design every module
    - [x] SOLID Principle
    - [x] Keep It Simple,Stupid (KISS)
    - [x] Don't Repeat Yourself (DRY)
    - [x] Separation of Concern (SoP)
- [ ] Testing feature implemented
  - [ ] UI Testing
  - [ ] Unit Testing
- [x] Coil library is used for Image showing
## Implementation Details
