# Popular Movies

An Android app that displays a list of movies from The Movie DB (themoviedb.org). The movies may be ordered by popularity or by rating. Selecting a movie will display details about it. Project 2 of the Udacity Android Development Nanodegree, developed from scratch.

## Building the Project

The project builds using Gradle (tested in Android Studio 3.1.2). It requires an API key from The Movie DB in order to download the movie data. If you wish to build your own app from the source code, first obtain an API key by creating an account with [The Movie DB](https://www.themoviedb.org/) and following the instructions to register for an API key [here](https://developers.themoviedb.org/3/getting-started/introduction). Then add a line to your `gradle.properties` file containing your API_KEY:

```
API_KEY="0123456789abcdef0123456789abcdef"
```

Replace `0123456789abcdef0123456789abcdef` with your actual API key. The Movie DB requires you to keep your API key private. The `gradle.properties` file has been added to `.gitignore` to prevent it from being uploaded to a public Git repository. 

## Acknowledgments

[Android Working with Retrofit HTTP Library](https://www.androidhive.info/2016/05/android-working-with-retrofit-http-library/)

[GitHub: Hiding (secret) API keys – Tutorial](https://richardroseblog.wordpress.com/2016/05/29/hiding-secret-api-keys-from-git/)