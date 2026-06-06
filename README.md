# Recetópolis — Android
 
Native Android app for the Recetópolis platform. Browse, create, and manage recipes from your mobile device.
 
---
 
## Tech Stack
 
| Layer | Technology |
|-------|------------|
| Language | Kotlin |
| UI | Jetpack Compose |
| Architecture | MVVM + Clean Architecture |
| DI | Dagger Hilt |
| HTTP | Retrofit + OkHttp |
| Local DB | Room |
| Session | DataStore |
| Async | Coroutines + StateFlow |
| Images | Coil |
 
---
 
## Features
 
- Browse predefined and community recipes
- Search and filter by category, difficulty, and keyword
- User authentication (register, login, logout)
- Create and manage personal recipes
- Add and remove favorite recipes
- Leave reviews and ratings
---
 
## Architecture
 
The app follows Clean Architecture principles with 4 main layers:
 
```
Presentation → Domain → Data → Remote/Local
```
 
- **Presentation** — Compose screens and ViewModels
- **Domain** — Use cases, models, and repository interfaces
- **Data** — Repository implementations, DTOs, mappers, Room entities
- **Core** — Retrofit setup, DataStore, utilities
---
 
## Project Structure

---
```
app/
├── core/
│   ├── network/
│   ├── session/
│   └── utils/
│
├── data/
│   ├── remote/
│   │   ├── api/
│   │   └── dto/
│   ├── local/
│   │   ├── database/
│   │   ├── dao/
│   │   └── entity/
│   ├── mapper/
│   └── repository/
│
├── domain/
│   ├── model/
│   ├── repository/
│   └── usecase/        
│       ├── recipe/
│       ├── auth/
│       └── favorites/
│
├── presentation/
│   ├── theme/
│   ├── navigation/
│   ├── components/
│   ├── auth/
│   │   ├── login/
│   │   └── register/
│   ├── recipes/
│   │   ├── list/
│   │   ├── detail/
│   │   └── create/
│   ├── profile/
│   └── favorites/
│
└── di/
    ├── NetworkModule
    ├── DatabaseModule
    ├── RepositoryModule
    └── UseCaseModule  
```
---
