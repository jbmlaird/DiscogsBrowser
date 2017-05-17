[![BuddyBuild](https://dashboard.buddybuild.com/api/statusImage?appID=58ff64f23f33870001d2e016&branch=master&build=latest)](https://dashboard.buddybuild.com/apps/58ff64f23f33870001d2e016/build/latest?branch=master)

<img src="/featuregraphic/featuregraphic.png" height=300/>

# DiscogsBrowser
Android app to interact with the [Discogs' public API](https://www.discogs.com/developers). This is production-ready, tested, MVP code that is an example of a medium-sized Android app with modern architecture.

![app_gif](https://media.giphy.com/media/UHn06Zh8EBCGA/giphy.gif) ![another_app_gif](https://media.giphy.com/media/3IH00o747keju/giphy.gif)

# Usage

Currently going through submission for the Play Store. For now, must be built from source.

# Technical

Android development is in a great place with all of the libraries available. This app uses the usual suspects:
* [Dagger2](https://github.com/google/dagger)
* [Retrofit2](https://github.com/square/retrofit)
* [RxJava2](https://github.com/ReactiveX/RxJava)/[RxAndroid](https://github.com/ReactiveX/RxAndroid)

Plus:
* [RxBinding2](https://github.com/JakeWharton/RxBinding) - turn your views into RxJava Observables
* [RxCache](https://github.com/VictorAlbertos/RxCache) - reactive caching
* [Airbnb's Epoxy](https://github.com/airbnb/epoxy) - never use RecyclerView.Adapter again
* [GreenDao](https://github.com/greenrobot/greenDAO) - SQLite ORM

Bug/leak catching:
* [BugSnag](https://www.bugsnag.com/product/)
* [LeakCanary](https://github.com/square/leakcanary)

Of course, the usual testing frameworks plus [DaggerMock](https://github.com/fabioCollini/DaggerMock)
* JUnit4
* Mockito
* Espresso
* [DaggerMock](https://github.com/fabioCollini/DaggerMock) - will change your life

Others:
* [ButterKnife](https://github.com/JakeWharton/butterknife) - view binding
* [Glide](https://github.com/bumptech/glide) - image loading and caching
* [Jsoup](https://jsoup.org/) - [BeautifulSoup](https://www.crummy.com/software/BeautifulSoup/bs4/doc/) for Java
* [CircleImageView](https://github.com/hdodenhof/CircleImageView)
* [Toasty](https://github.com/GrenderG/Toasty)
* [MaterialAboutLibrary](https://github.com/daniel-stoneuk/material-about-library)
* [MaterialDrawer](https://github.com/mikepenz/MaterialDrawer)
* [TapTargetView](https://github.com/KeepSafe/TapTargetView)
* [Iconify](https://github.com/JoanZapata/android-iconify)

# Discogs API Limitations

Currently, in May 2017, the public API does not allow any of the following:
* Order placing/purchasing
* Marketplace actions. This includes purchasing in app, viewing purchases or searching listings.
* Seeing order comments/updates for purchases
* Recent releases
* Notifications

# License

This project is licensed under the terms of the MIT license.
