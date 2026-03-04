# Post Viewer Application

Application Android de consultation de posts.

## Instructions pour build

1. Ouvrir le projet avec Android Studio
2. Synchroniser les dépendances Gradle (Sync Now)
3. Connecter un appareil ou lancer un émulateur (API 21+)
4. Cliquer sur Run (▶) ou utiliser Shift+F10

## Version Android Studio utilisée

- Android Studio Iguana | 2023.2.1
- Build #AI-232.10227.8.2321.11479570

## Version SDK

- Minimum SDK : API 24 
- Target SDK : API 33 
- Compile SDK : API 33

## Choix techniques

- **Langage** : Java (conformité avec la demande)
- **Architecture** : MVVM avec ViewModel et LiveData
- **Réseau** : Retrofit pour les appels API
- **Persistance** : SharedPreferences pour la session utilisateur
- **UI** : ViewBinding pour la gestion des vues
- **Navigation** : Fragments avec gestion du back stack
- **Tests** : JUnit pour les tests unitaires

## Améliorations possibles

- Mode hors-ligne avec Room Database
- Pull to refresh pour recharger la liste
- Pagination pour charger plus de posts
- Recherche de posts par titre
- Partager un post
- Notifications push (FCM)
