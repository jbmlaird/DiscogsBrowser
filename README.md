[![BuddyBuild](https://dashboard.buddybuild.com/api/statusImage?appID=58ff64f23f33870001d2e016&branch=master&build=latest)](https://dashboard.buddybuild.com/apps/58ff64f23f33870001d2e016/build/latest?branch=master)

![app_logo](app/src/main/res/drawable-xxxhdpi/ic_app.png)

# DiscogsBrowser
Android app to interact with Discogs' API

# Description

Common libraries used:

* [ButterKnife](https://github.com/JakeWharton/butterknife)
* [Dagger2](https://google.github.io/dagger/)
* [RxJava2](https://github.com/ReactiveX/RxJava)
* [RxBinding2](https://github.com/JakeWharton/RxBinding)
* [RetroLambda](https://github.com/evant/gradle-retrolambda)
* [LeakCanary](https://github.com/square/leakcanary)
* [RxCache](https://github.com/VictorAlbertos/RxCache) - new favourite library
* etc.

# Usage

This build is login only.

It is not ready for consumers at this stage.

# Features

* Reactive search
* Search history
* Displays artist, master, release and label information.
* Displays marketplace information on release page NB: marketplace searches only return 12"
* Collection/wantlist viewing
* Request caching

# Forthcoming

* Collection/wantlist amending
* Carousels for images
* Browsing history
* Recent releases
* Preview releases in app (Picture in picture?)
* Notifications (if API allows)

# Limitations

Currently, in April 2017, the public API does not allow any of the following:
* Order placing
* Marketplace actions. This includes purchasing in app, viewing purchases or searching listings.

# License

This project is licensed under the terms of the MIT license.
