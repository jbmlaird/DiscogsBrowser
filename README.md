![app_logo](app/src/main/res/drawable-xxxhdpi/ic_app.png)

# DiscogsBrowser
Android app to interact with Discogs' API

# Description

I don't have any of my Android code uploaded anywhere so this is just an example of what I can do.

Common libraries used:

* [ButterKnife](https://github.com/JakeWharton/butterknife)
* [Dagger2](https://google.github.io/dagger/)
* [RxJava2](https://github.com/ReactiveX/RxJava)
* [RxBinding2](https://github.com/JakeWharton/RxBinding)
* [RetroLambda](https://github.com/evant/gradle-retrolambda)
* [LeakCanary](https://github.com/square/leakcanary)
etc.

# Usage

In order to run this app you will need to replace `{{YOUR_TOKEN}}` in strings.xml with your token provided by [Discogs](https://www.discogs.com/settings/developers).

# Features

Searches your query reactively and displays a list of results. 
Displays artist details, release and label information.
Displays marketplace information on release page NB: ,marketplace only searches 12"s. No 7", CD etc.

# Forthcoming

* Login
* Collection/Wantlist amending
* Order placing
* Results user filtering
* Carousels for images
* Refactor models with duplicate parameters
* Notifications
